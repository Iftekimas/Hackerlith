package co.edu.unbosque.model;

public class Amenaza {

    private int fila;
    private int columna;
    private String tipo;

    public Amenaza(int fila, int columna, String tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
