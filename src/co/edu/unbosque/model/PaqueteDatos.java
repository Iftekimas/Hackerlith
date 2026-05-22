package co.edu.unbosque.model;

public class PaqueteDatos {
    private int fila;
    private int columna;
    private int puertosVisitados;

    public PaqueteDatos(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.puertosVisitados = 0;
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
}
