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

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getNumeroPuerto() {
        return numeroPuerto;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
