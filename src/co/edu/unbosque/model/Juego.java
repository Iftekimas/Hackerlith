package co.edu.unbosque.model;

public class Juego {
    public static final String JUGANDO = "JUGANDO";
    public static final String GANADO = "GANADO";
    public static final String PERDIDO = "PERDIDO";

    private Tablero tablero;
    private Jugador jugador;
    private PaqueteDatos paquete;
    private Puerto[] puertos;
    private Amenaza[] amenazas;
    private NodoEnergia[] nodos;
    private String estado;
    private int movimientosMax;
    private boolean ordenInverso;

    // Constructor del juego
    public Juego(int filas, int columnas, int numPuertos, int movimientosMax, boolean ordenInverso) {
        this.movimientosMax = movimientosMax;
        this.ordenInverso = ordenInverso;
        this.estado = JUGANDO;
        this.tablero = new Tablero(filas, columnas);
        this.jugador = new Jugador(0, 0, movimientosMax);
        this.paquete = new PaqueteDatos(1, 1);
        this.puertos = new Puerto[numPuertos];
        this.amenazas = new Amenaza[0];
        this.nodos = new NodoEnergia[0];
    }

    // Verifica si el jugador ha ganado
    public boolean verificarVictoria() {
        if (paquete.getPuertosVisitados() >= puertos.length) {
            estado = GANADO;
            return true;
        }
        return false;
    }

    // Verifica si el jugador ha perdido
    public boolean verificarDerrota() {
        // Verificar colisión con amenazas
        if (jugador.getMovimientosRestantes() <= 0) {
            estado = PERDIDO;
            return true;
        }
        return false;
    }

    // Mueve las amenazas
    public void moverAmenazas() {
        for (int i = 0; i < amenazas.length; i++) {

            if (amenazas[i] != null)
                amenazas[i].mover(tablero);

        }

    }

    // setters y getters
    public Tablero getTablero() {
        return tablero;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public PaqueteDatos getPaquete() {
        return paquete;
    }

    public Puerto[] getPuertos() {
        return puertos;
    }

    public Amenaza[] getAmenazas() {
        return amenazas;
    }

    public NodoEnergia[] getNodos() {
        return nodos;
    }

    public String getEstado() {
        return estado;
    }

    public int getMovimientosMax() {
        return movimientosMax;
    }

    public boolean isOrdenInverso() {
        return ordenInverso;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setAmenazas(Amenaza[] amenazas) {
        this.amenazas = amenazas;
    }

    public void setPuertos(Puerto[] puertos) {
        this.puertos = puertos;
    }

    public void setNodos(NodoEnergia[] nodos) {
        this.nodos = nodos;
    }

    // iniciliza el juego
    public void inicializar() {

        // Agregar puertos
        puertos[0] = new Puerto(1, 6, 1);
        puertos[1] = new Puerto(4, 2, 2);
        puertos[2] = new Puerto(6, 6, 3);

        // Amenazas

        amenazas = new Amenaza[2];
        amenazas[0] = new Amenaza(2, 2, "ANTIVIRUS");
        amenazas[1] = new Amenaza(5, 5, "ESCANER");
        tablero.setCelda(2, 2, "ANTIVIRUS");
        tablero.setCelda(5, 5, "ESCANER");

        paquete = new PaqueteDatos(3, 3);
        tablero.setCelda(3, 3, "PAQUETE");

        for (Puerto p : puertos)
            tablero.setCelda(p.getFila(), p.getColumna(), "PUERTO");
    }

}