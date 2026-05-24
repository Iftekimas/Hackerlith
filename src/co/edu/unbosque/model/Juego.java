package co.edu.unbosque.model;

/**
 * Clase principal del modelo. Contiene el estado completo del juego.
 */
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
    private String dificultad;

    /**
     * Crea una nueva partida con las dimensiones y parámetros dados.
     * @param filas filas del tablero
     * @param columnas columnas del tablero
     * @param numPuertos cantidad de puertos a visitar
     * @param movimientosMax movimientos máximos del jugador
     * @param ordenInverso si los puertos deben visitarse en orden inverso
     */
    public Juego(int filas, int columnas, int numPuertos, int movimientosMax, boolean ordenInverso) {
        this.movimientosMax = movimientosMax;
        this.ordenInverso = ordenInverso;
        this.estado = JUGANDO;
        this.tablero = new Tablero(filas, columnas);
        this.jugador = new Jugador(2, 2, movimientosMax); // Posición inicial del jugador
        this.paquete = new PaqueteDatos(1, 1);
        this.puertos = new Puerto[numPuertos];
        this.amenazas = new Amenaza[0];
        this.nodos = new NodoEnergia[0];
    }

    /**
     * Verifica si el jugador ya visitó todos los puertos.
     * @return true si ganó
     */
    public boolean verificarVictoria() {
        if (paquete.getPuertosVisitados() >= puertos.length) {
            estado = GANADO;
            return true;
        }
        return false;
    }

    /**
     * Verifica si el jugador se quedó sin movimientos.
     * @return true si perdió
     */
    public boolean verificarDerrota() {
        // Verificar colisión con amenazas
        if (jugador.getMovimientosRestantes() <= 0) {
            estado = PERDIDO;
            return true;
        }
        return false;
    }

    /**
     * Mueve todas las amenazas del tablero un paso aleatorio.
     */
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

    /**
     * Coloca todos los elementos en el tablero según la dificultad elegida.
     * @param dificultad nivel de dificultad: BAJA, MEDIA o ALTA
     */
    public void inicializar(String dificultad) {
        this.dificultad = dificultad;
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        java.util.Random rnd = new java.util.Random();

        // Paredes exteriores
        for (int c = 0; c < columnas; c++) {
            tablero.setCelda(0, c, "PARED");
            tablero.setCelda(filas - 1, c, "PARED");
        }
        for (int r = 1; r < filas - 1; r++) {
            tablero.setCelda(r, 0, "PARED");
            tablero.setCelda(r, columnas - 1, "PARED");
        }

        // Nodos de energía (2)
        nodos = new NodoEnergia[2];
        for (int i = 0; i < 2; i++) {
            int[] pos = posAleatoria(rnd);
            nodos[i] = new NodoEnergia(pos[0], pos[1]);
            tablero.setCelda(pos[0], pos[1], "NODO");
        }

        // Puertos — alejados de paredes
        for (int i = 0; i < puertos.length; i++) {
            int[] pos = posAleatoriaSegura(rnd);
            puertos[i] = new Puerto(pos[0], pos[1], i + 1);
            tablero.setCelda(pos[0], pos[1], "PUERTO");
        }

        // Paquete — alejado de paredes, se coloca ANTES de firewalls para verificar conectividad
        int[] posPaq = posAleatoriaSegura(rnd);
        paquete = new PaqueteDatos(posPaq[0], posPaq[1]);
        tablero.setCelda(posPaq[0], posPaq[1], "PAQUETE");

        // Jugador — se marca temporalmente para que los firewalls no caigan encima
        int[] posJug = posAleatoria(rnd);
        jugador.setFila(posJug[0]);
        jugador.setColumna(posJug[1]);
        tablero.setCelda(posJug[0], posJug[1], "JUG");

        // Calcular cuántos firewalls colocar según dificultad y tamaño
        int celdasInternas = (filas - 2) * (columnas - 2);
        int numAmenazas = 1;
        if (dificultad.equals("MEDIA")) numAmenazas = 2;
        if (dificultad.equals("ALTA")) numAmenazas = 3;
        int ocupadas = 2 + puertos.length + numAmenazas + 2;

        int cantFW = 0;
        if (dificultad.equals("MEDIA")) {
            cantFW = celdasInternas / 12;
            if (cantFW < 2) cantFW = 2;
        } else if (dificultad.equals("ALTA")) {
            cantFW = celdasInternas / 8;
            if (cantFW < 4) cantFW = 4;
        }

        int maxFW = celdasInternas - ocupadas - 2;
        if (maxFW < 0) maxFW = 0;
        int totalFW = cantFW;
        if (totalFW > maxFW) totalFW = maxFW;

        // Amenazas según dificultad
        if (dificultad.equals("BAJA")) {
            amenazas = new Amenaza[1];
            int[] pos = posAleatoria(rnd);
            amenazas[0] = new Amenaza(pos[0], pos[1], "ANTIVIRUS");
            tablero.setCelda(pos[0], pos[1], "ANTIVIRUS");
        } else if (dificultad.equals("ALTA")) {
            amenazas = new Amenaza[3];
            String[] tipos = { "ANTIVIRUS", "ESCANER", "ANTIVIRUS" };
            for (int i = 0; i < 3; i++) {
                int[] pos = posAleatoria(rnd);
                amenazas[i] = new Amenaza(pos[0], pos[1], tipos[i]);
                tablero.setCelda(pos[0], pos[1], tipos[i]);
            }
        } else { // MEDIA
            amenazas = new Amenaza[2];
            String[] tipos = { "ANTIVIRUS", "ESCANER" };
            for (int i = 0; i < 2; i++) {
                int[] pos = posAleatoria(rnd);
                amenazas[i] = new Amenaza(pos[0], pos[1], tipos[i]);
                tablero.setCelda(pos[0], pos[1], tipos[i]);
            }
        }

        // Firewalls — no se coloca si está pegado a un puerto, paquete o jugador
        for (int i = 0; i < totalFW; i++) {
            int[] pos = posAleatoria(rnd);
            if (fireWallPermitido(pos[0], pos[1])) {
                tablero.setCelda(pos[0], pos[1], "FIREWALL");
            }
        }

        // Restaurar celda del jugador a vacío
        tablero.setCelda(posJug[0], posJug[1], "VACIO");
    }

    // Verifica que el firewall no quede pegado a un puerto, paquete o jugador
    private boolean fireWallPermitido(int f, int c) {
        // Arriba
        if (tablero.estaEnRango(f - 1, c)) {
            String celda = tablero.getCelda(f - 1, c);
            if (celda.equals("PUERTO") || celda.equals("PAQUETE") || celda.equals("JUG")) return false;
        }
        // Abajo
        if (tablero.estaEnRango(f + 1, c)) {
            String celda = tablero.getCelda(f + 1, c);
            if (celda.equals("PUERTO") || celda.equals("PAQUETE") || celda.equals("JUG")) return false;
        }
        // Izquierda
        if (tablero.estaEnRango(f, c - 1)) {
            String celda = tablero.getCelda(f, c - 1);
            if (celda.equals("PUERTO") || celda.equals("PAQUETE") || celda.equals("JUG")) return false;
        }
        // Derecha
        if (tablero.estaEnRango(f, c + 1)) {
            String celda = tablero.getCelda(f, c + 1);
            if (celda.equals("PUERTO") || celda.equals("PAQUETE") || celda.equals("JUG")) return false;
        }
        return true;
    }

    private int[] posAleatoria(java.util.Random rnd) {
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        int f = 1, c = 1;
        for (int i = 0; i < 1000; i++) {
            f = 1 + rnd.nextInt(filas - 2);
            c = 1 + rnd.nextInt(columnas - 2);
            if (tablero.getCelda(f, c).equals("VACIO")) break;
        }
        return new int[] { f, c };
    }

    // Coloca el paquete con margen de paredes para que no quede atrapado en una esquina
    private int[] posAleatoriaSegura(java.util.Random rnd) {
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        int mf = filas - 4;
        int mc = columnas - 4;
        if (mf < 1) mf = 1;
        if (mc < 1) mc = 1;
        int f = 2, c = 2;
        for (int i = 0; i < 1000; i++) {
            f = 2 + rnd.nextInt(mf);
            c = 2 + rnd.nextInt(mc);
            if (tablero.getCelda(f, c).equals("VACIO")) {
                break;
            }
        }
        return new int[] { f, c };
    }

    public String getDificultad() {
        return dificultad;
    }

}