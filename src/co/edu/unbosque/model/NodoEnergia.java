package co.edu.unbosque.model;

/**
 * Nodo de energía que el jugador puede recoger para recuperar movimientos.
 */
public class NodoEnergia {
    private int fila;
    private int columna;
    private boolean activo;

    /**
     * Crea un nodo de energía activo en la posición dada.
     * @param fila fila del nodo
     * @param columna columna del nodo
     */
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
