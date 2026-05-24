package co.edu.unbosque.model;

/**
 * Representa una amenaza del juego (Antivirus o Escaner) que se mueve por el tablero.
 * @author Santiago Forero Garibello
 * @author Alejandra Fernandez Espinosa
 * @author Michael Andres Sanchez
 * @version 1.0
 */
public class Amenaza {

    private int fila;
    private int columna;
    private String tipo;

    /**
     * Crea una amenaza en la posición dada.
     * @param fila fila inicial
     * @param columna columna inicial
     * @param tipo tipo de amenaza: ANTIVIRUS o ESCANER
     */
    public Amenaza(int fila, int columna, String tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    /**
     * Mueve la amenaza a una celda vacía aleatoria adyacente.
     * @param tablero tablero del juego
     */
    public void mover(Tablero tablero) {
        if (tipo.equals("FIREWALL"))
            return;

        int dir = (int) (Math.random() * 4);
        int nuevaFila = fila;
        int nuevaColumna = columna;

        if (dir == 0) nuevaFila = fila - 1;       // arriba
        else if (dir == 1) nuevaFila = fila + 1;  // abajo
        else if (dir == 2) nuevaColumna = columna - 1; // izquierda
        else nuevaColumna = columna + 1;           // derecha

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
