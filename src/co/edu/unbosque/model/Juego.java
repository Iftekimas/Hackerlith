package co.edu.unbosque.model;

public class Juego {

    public static final String JUGANDO = "JUGANDO";
    public static final String GANADO = "GANADO";
    public static final String PERDIDO = "PERDIDO";

    private Tablero tablero;
    private String estado;
    private Jugador jugador;
    private Amenaza[] amenazas;
    private Puerto[] puertos;
    private Archivo[] archivos;

    public Juego(int filas, int columnas, int numPuertos, int movimientosMax) {
        this.tablero = new Tablero(filas, columnas);
        this.jugador = new Jugador(0, 0, movimientosMax);
        this.archivos = new Archivo[numPuertos];
        this.puertos = new Puerto[numPuertos];
        this.amenazas = new Amenaza[0];
        this.estado = JUGANDO;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Archivo[] getArchivos() {
        return archivos;
    }

    public Puerto[] getPuertos() {
        return puertos;
    }

    public Amenaza[] getAmenazas() {
        return amenazas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setAmenazas(Amenaza[] amenazas) {
        this.amenazas = amenazas;
    }

    public boolean verificarVictoria() {
        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i] == null || !archivos[i].isEntregado())
                return false;
        }
        estado = GANADO;
        return true;
    }

    public boolean verificarDerrota() {
        if (jugador.getMovimientosRestantes() <= 0) {
            estado = PERDIDO;
            return true;
        }
        return false;
    }

    public void moverAmenazas() {
        for (int i = 0; i < amenazas.length; i++) {
            if (amenazas[i] != null)
                amenazas[i].mover(tablero);
        }
    }
}
