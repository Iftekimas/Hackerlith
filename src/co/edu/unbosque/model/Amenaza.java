package co.edu.unbosque.model;

public class Amenaza {

    private int fila;
    private int columna;
    private String tipo;

    // Constructor
    public Amenaza(int fila, int columna, String tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    // Método para mover la amenaza aleatoriamente
    public void mover(Tablero tablero) {
        int[] dFilas = { -1, 1, 0, 0 };
        int[] dColumnas = { 0, 0, -1, 1 };

        if (tipo.equals("FIREWALL"))
            return;

        int dir = (int) (Math.random() * 4);
        int nuevaFila = fila + dFilas[dir];
        int nuevaColumna = columna + dColumnas[dir];

        if (tablero.estaEnRango(nuevaFila, nuevaColumna)
                && tablero.getCelda(nuevaFila, nuevaColumna).equals("VACIO")) {
            if (tablero.getCelda(fila, columna).equals(tipo)) {
                tablero.setCelda(fila, columna, "VACIO");
            }
            fila = nuevaFila;
            columna = nuevaColumna;
            tablero.setCelda(fila, columna, tipo);
        }
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
