import java.sql.*;
import java.util.HashMap;

public class ResultadosPro {
    public HashMap<Integer, HashMap<String, String>> pronosticos;//pronosticos es un HashMap que tiene un conjunto de pronósticos para diferentes partidos, donde cada pronóstico está representado por un HashMap (nombre, resultado)

    public ResultadosPro(){
        pronosticos = new HashMap<>();
    }

    public void agregarPronostico(Pronostico pronostico) {
        int nPartido = pronostico.getnPartido();
        String nombre = pronostico.getNombre();
        String resultado = pronostico.getResultado();

        String nombreModificado = nombre; // Inicializa nombreModificado con el valor original

        boolean nombreExiste = pronosticos.containsKey(nPartido) && pronosticos.get(nPartido).containsKey(nombre);//Si ambas condiciones son verdaderas, significa que ya existe un pronóstico para el partido y el nombre.
        if (nombreExiste) { // Si el nombre original ya existe, agregar sufijo para hacerlo diferente
            nombreModificado = nombre + " (2)";
            int i = 2;
            while (pronosticos.containsKey(nPartido) && pronosticos.get(nPartido).containsKey(nombreModificado)) { //Si el nombre del jugador ya está registrado se le agrega un número para crear un nombre modificado.
                nombreModificado = nombre + " (" + i + ")" ;
                i++;
            }
        }
        if (pronosticos.containsKey(nPartido)) {
            pronosticos.get(nPartido).put(nombreModificado, resultado);
        } else {
            HashMap<String, String> jugadores = new HashMap<>();
            jugadores.put(nombreModificado, resultado);
            pronosticos.put(nPartido, jugadores);
        }
    }
    public ResultadosPro obtenerResultados() {
        Connection conn = null;
        Statement st1 = null;
        ResultSet rs1 = null;

        ResultadosPro listaPronostico = new ResultadosPro();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Tpiprode", "root", "root");
            st1 = conn.createStatement();
            rs1 = st1.executeQuery("select * from pronosticos");

            while (rs1.next()){

                int parPro = rs1.getInt("partido");
                String nomPro = rs1.getString("participante");
                String resPro = rs1.getString("Resultado");

                Pronostico nuevo2 = new Pronostico(parPro,resPro,nomPro);//Genera nuevos Pronosticos
                listaPronostico.agregarPronostico(nuevo2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs1 != null) {
                    rs1.close();
                }
                if (st1 != null) {
                    st1.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaPronostico;
    }
}
