import java.sql.*;
import java.util.HashMap;

public class Ronda {
    public HashMap<Integer,Partido> partidos;

    public Ronda(){
        partidos = new HashMap<>();
    }

    public void agregarPartido(Partido nuevo){
        partidos.put(nuevo.getnRonda(),nuevo);
    }

    public int cantidadPartido(){
        return partidos.size();
    }
    public Ronda obtenerPartidos() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        Ronda lista = new Ronda();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Tpiprode", "root", "root");
            st = conn.createStatement();
            rs = st.executeQuery("select * from partidos");

            while (rs.next()) {
                int nPar = rs.getInt("nPar");
                String e1 = rs.getString("Equipo1");
                int r1 = rs.getInt("golesequipo1");
                int r2 = rs.getInt("golesequipo2");
                String e2 = rs.getString("equipo2");

                Partido nuevo = new Partido(nPar,e1,r1,e2,r2);
                lista.agregarPartido(nuevo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

}


