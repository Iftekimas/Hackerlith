package co.edu.unbosque.model;

public class Puerto {

    private int fila;
    private int columna;
    private int numero;
    private boolean visitado;

    public Puerto(int fila, int columna, int numero) {
        this.fila = fila;
        this.columna = columna;
        this.numero = numero;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

}
