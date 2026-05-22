package co.edu.unbosque.model;

public class NodoEnergia {
    private int fila;
    private int columna;
    private boolean activo;

    public NodoEnergia(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.activo = true;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
