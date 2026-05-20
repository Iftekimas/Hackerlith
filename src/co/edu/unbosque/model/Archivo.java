package co.edu.unbosque.model;

public class Archivo {
    private int fila;
    private int columna;
    private int numeroPuerto;
    private boolean entregado;

    public Archivo(int fila, int columna, int numeroPuerto) {
        this.fila = fila;
        this.columna = columna;
        this.numeroPuerto = numeroPuerto;
        this.entregado = false;
    }
}
