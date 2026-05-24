package co.edu.unbosque.model;

/**
 * Representa un puerto de enlace que el paquete debe visitar.
 */
public class Puerto {

    private int fila;
    private int columna;
    private int numero;
    private boolean visitado;

    /**
     * Crea un puerto en la posición dada con su número de orden.
     * @param fila fila del puerto
     * @param columna columna del puerto
     * @param numero número de orden del puerto
     */
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
