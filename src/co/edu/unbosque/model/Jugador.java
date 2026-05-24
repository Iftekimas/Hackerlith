package co.edu.unbosque.model;

/**
 * Representa al jugador dentro del tablero.
 */
public class Jugador {

    private int fila;
    private int columna;
    private int movimientosRestantes;
    private boolean modoSigilo;
    private boolean sigiloUsado = false;

    /**
     * Crea un jugador en la posición dada con los movimientos iniciales.
     * @param fila fila inicial
     * @param columna columna inicial
     * @param movimientosRestantes movimientos disponibles al inicio
     */
    public Jugador(int fila, int columna, int movimientosRestantes) {
        this.fila = fila;
        this.columna = columna;
        this.movimientosRestantes = movimientosRestantes;
        this.modoSigilo = false;

    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getMovimientosRestantes() {
        return movimientosRestantes;
    }

    public boolean isModoSigilo() {
        return modoSigilo;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setMovimientosRestantes(int movimientosRestantes) {
        this.movimientosRestantes = movimientosRestantes;
    }

    public void setModoSigilo(boolean modoSigilo) {
        this.modoSigilo = modoSigilo;
    }

    public boolean isSigiloUsado() {
        return sigiloUsado;
    }

    public void setSigiloUsado(boolean b) {
        this.sigiloUsado = b;
    }
}
