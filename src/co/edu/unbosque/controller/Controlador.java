package co.edu.unbosque.controller;

import co.edu.unbosque.model.*;

public class Controlador {

    private Juego juego;

    public Controlador() {
        // Configuración inicial del juego: tablero de 8x8, 3 amenazas, 64 movimientos,
        juego = new Juego(8, 8, 3, 64, false);
        juego.inicializar();

    }

    public Juego getJuego() {
        return juego;
    }

    public void moverJugador(String direccion) {
        if (!juego.getEstado().equals(Juego.JUGANDO))
            return;

        int df = 0, dc = 0;
        if (direccion.equals("W"))
            df = -1;
        else if (direccion.equals("S"))
            df = 1;
        else if (direccion.equals("A"))
            dc = -1;
        else if (direccion.equals("D"))
            dc = 1;

        int nuevaFila = juego.getJugador().getFila() + df;
        int nuevaColumna = juego.getJugador().getColumna() + dc;

        if (!juego.getTablero().estaEnRango(nuevaFila, nuevaColumna))
            return;

        String celda = juego.getTablero().getCelda(nuevaFila, nuevaColumna);

        if (celda.equals("FIREWALL"))
            return;

        juego.getJugador().setFila(nuevaFila);
        juego.getJugador().setColumna(nuevaColumna);
        juego.getJugador().setMovimientosRestantes(juego.getJugador().getMovimientosRestantes() - 1);

        juego.verificarVictoria();
        juego.verificarDerrota();
        juego.moverAmenazas();
    }
}
