package co.edu.unbosque.model;

/**
 * Representa la cuadrícula del juego. Guarda el estado de cada celda.
 */
public class Tablero {
    private int filas;
    private int columnas;
    private String[][] celdas;

    /**
     * Crea el tablero e inicializa todas las celdas como vacías.
     * @param filas número de filas
     * @param columnas número de columnas
     */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = "VACIO";
            }
        }
    }

    /**
     * Retorna el contenido de una celda.
     * @param fila fila de la celda
     * @param columna columna de la celda
     * @return tipo de celda como String
     */
    public String getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }

    /**
     * Asigna un valor a una celda.
     * @param fila fila de la celda
     * @param columna columna de la celda
     * @param valor nuevo valor de la celda
     */
    public void setCelda(int fila, int columna, String valor) {
        celdas[fila][columna] = valor;
    }

    /**
     * Verifica si una posición está dentro del tablero.
     * @param fila fila a verificar
     * @param columna columna a verificar
     * @return true si está dentro del tablero
     */
    public boolean estaEnRango(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    /** @return número de filas del tablero */
    public int getFilas() {
        return filas;
    }

    /** @return número de columnas del tablero */
    public int getColumnas() {
        return columnas;
    }

}
