import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        Ronda lista = new Ronda();//HashMap partidos.
        Ronda nuevoPar = lista.obtenerPartidos();

        ResultadosPro listaPronostico = new ResultadosPro();//HashMap pronosticos.
        ResultadosPro nuevoPro = listaPronostico.obtenerResultados();

        System.out.println("");

        String equipoGanador = "";
        HashMap<String, Integer> puntajesJugadores = new HashMap<>();//Almacena puntaje jugadores

        for (int i = 1; i < nuevoPar.cantidadPartido()+1 ; i++) {//Recorre todos los partidos
            Partido partido = nuevoPar.partidos.get(i);
            int resEquipo1 = partido.getGoles1();
            int resEquipo2 = partido.getGoles2();

            if (resEquipo1 > resEquipo2) { //Calcula equipos ganadores
                equipoGanador = partido.getEquipo1();
                System.out.println("Resultado nº " + i + " : " +  equipoGanador);
            } else {
                if (resEquipo1 == resEquipo2) {
                    equipoGanador = "Empate";
                    System.out.println("Resultado nº " + i + " : " +  equipoGanador);
                } else {
                    equipoGanador = partido.getEquipo2();
                    System.out.println("Resultado nº " + i + " : " +  equipoGanador);
                }
            }
            HashMap<String, String> jugadores = nuevoPro.pronosticos.get(i);//jugadores contendrá un mapeo de todos los jugadores que hicieron un pronóstico para el partido i.
            for (String jugador : jugadores.keySet()) { //Recorre pronosticos por jugador. keySet()devuelve un conjunto que contiene todas las claves mapeadas en el mapa.
                String resultadoJugador = jugadores.get(jugador);//Se obtiene el resultado del jugador jugador para un partido determinado.

                if (resultadoJugador.trim().equals(equipoGanador.trim())) { //El jugador acerto  y se agrega un punto a su puntaje actual en el HashMap "puntajesJugadores".
                    int puntajeActual = puntajesJugadores.getOrDefault(jugador, 0); //Utilizando el método "getOrDefault", que busca en el HashMap el valor asociado con la clave "jugador" y devuelve el valor si existe, o un valor predeterminado (0 en este caso) si no existe.
                    puntajesJugadores.put(jugador, puntajeActual + 1);
                    System.out.println("Jugador : "+ jugador + " +1");
                }else {
                    int puntajeActual = puntajesJugadores.getOrDefault(jugador, 0);
                    puntajesJugadores.put(jugador, puntajeActual);
                    System.out.println("Jugador : "+ jugador + " (" + resultadoJugador + ")");
                }
            }
            System.out.println("");

        }
        String mensaje = "Tabla de Posiciones";
        String subrayado = "-".repeat(mensaje.length());
        System.out.println(mensaje);
        System.out.println(subrayado);

        int puntajeMaximo = 0;
        String jugadorMaximo = "";

        for (String jugador : puntajesJugadores.keySet()) {//Calcula el Mayor.
            int puntaje = puntajesJugadores.get(jugador);

            if (puntaje > puntajeMaximo) {
                puntajeMaximo = puntaje;
                jugadorMaximo = jugador;
            }
        }
        puntajesJugadores.entrySet().stream()//Se convierte el mapa de puntajes de jugadores en un flujo de datos (stream), lo que permitirá procesar los elementos del mapa de forma más eficiente.

                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                // Los elementos del flujo se ordenan utilizando el método sorted() y la clase Map.Entry.
                // Se utiliza comparingByValue() para ordenar los elementos por su valor (en este caso, el puntaje), y el método reversed() invierte el orden para que los elementos estén en orden descendente.

                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        //Se utiliza el método forEach() para imprimir cada elemento del flujo en la consola.
        //Toma cada elemento del flujo (entry) y lo imprime en la consola, concatenando la clave (getKey()) y el valor (getValue()) del objeto Map.Entry.

        System.out.println(subrayado);
        System.out.println("");

        System.out.println("El jugador Ganador es :  " + jugadorMaximo + " ( " + puntajeMaximo + " ) puntos.");
        JOptionPane.showMessageDialog(null, "El jugador Ganador es :  " + jugadorMaximo + " ( " + puntajeMaximo + " ) puntos.");

    }
}