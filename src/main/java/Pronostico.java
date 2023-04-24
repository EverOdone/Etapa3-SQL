public class Pronostico {
    private int nPartido;

    private String resultado;

    private String nombre;

    public int getnPartido() {
        return nPartido;
    }

    public void setnPartido(int nPartido) {
        this.nPartido = nPartido;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pronostico(int nPartido, String resultado, String nombre) {
        this.nPartido = nPartido;
        this.resultado = resultado;
        this.nombre = nombre;
    }

    public String toString() {
        return "Nº " + nPartido + " " + resultado + " " + nombre;
    }
}