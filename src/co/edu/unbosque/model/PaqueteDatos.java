package co.edu.unbosque.model;

/**
 * Representa el paquete de datos que el jugador debe empujar por los puertos.
 * @author Santiago Forero Garibello
 * @author Alejandra Fernandez Espinosa
 * @author Michael Andres Sanchez
 * @version 1.0
 */
public class PaqueteDatos {
    private int fila;
    private int columna;
    private int puertosVisitados;
    private int[][] rastro;
    private int cantidadRastro;

    // Constructor

    /**
     * Crea el paquete de datos en la posición inicial dada.
     * @param fila fila inicial del paquete
     * @param columna columna inicial del paquete
     */
    public PaqueteDatos(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.puertosVisitados = 0;
        this.rastro = new int[400][2]; // Almacena hasta 400 movimientos (fila, columna)
        this.cantidadRastro = 0;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getPuertosVisitados() {
        return puertosVisitados;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void visitarPuerto() {
        puertosVisitados++;
    }

    public void agregarRastro(int fila, int columna) {
        if (cantidadRastro < rastro.length) {
            rastro[cantidadRastro][0] = fila;
            rastro[cantidadRastro][1] = columna;
            cantidadRastro++;
        }
    }

    public int[][] getRastro() {
        return rastro;
    }

    public int getCantidadRastro() {
        return cantidadRastro;
    }
}
