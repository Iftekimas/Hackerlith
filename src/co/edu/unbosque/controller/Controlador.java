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

        if (!juego.getEstado().equals(Juego.JUGANDO)) {
            return; // No mover si el juego ya terminó
        }

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

        if (!juego.getTablero().estaEnRango(nuevaFila, nuevaColumna)) {
            return; // No mover si está fuera de rango
        }

        String celda = juego.getTablero().getCelda(nuevaFila, nuevaColumna);

        if (celda.equals("FIREWALL")) {
            return; // No mover si hay un firewall
        }

        if (celda.equals("PAQUETE")) {
            int paqNuevaFila = nuevaFila + df;
            int paqNuevaColumna = nuevaColumna + dc;

            if (!juego.getTablero().estaEnRango(paqNuevaFila, paqNuevaColumna))
                return; // No mover si el paquete va a salir del tablero

            if (juego.getTablero().getCelda(paqNuevaFila, paqNuevaColumna).equals("FIREWALL"))
                return; // No mover si el paquete choca con un firewall

            juego.getTablero().setCelda(nuevaFila, nuevaColumna, "VACIO");
            juego.getPaquete().setFila(paqNuevaFila);
            juego.getPaquete().setColumna(paqNuevaColumna);
            juego.getTablero().setCelda(paqNuevaFila, paqNuevaColumna, "PAQUETE");

            for (Puerto p : juego.getPuertos()) {
                if (p.getFila() == paqNuevaFila && p.getColumna() == paqNuevaColumna && !p.isVisitado()) {
                    p.setVisitado(true);
                    juego.getPaquete().visitarPuerto();
                    juego.getTablero().setCelda(paqNuevaFila, paqNuevaColumna, "PAQUETE");
                    break;
                }
            }

        }

        juego.getJugador().setFila(nuevaFila);
        juego.getJugador().setColumna(nuevaColumna);
        juego.getJugador().setMovimientosRestantes(juego.getJugador().getMovimientosRestantes() - 1);

        juego.verificarVictoria();
        juego.verificarDerrota();
        juego.moverAmenazas();

    }
}
