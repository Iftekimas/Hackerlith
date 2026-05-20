package co.edu.unbosque.model;

public class Tablero {
    private int filas;
    private int columnas;
    private String[][] celdas;

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

    public String getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }
}
