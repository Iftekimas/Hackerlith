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

            juego.getPaquete().agregarRastro(nuevaFila, nuevaColumna);

            // Mover el paquete
            juego.getTablero().setCelda(nuevaFila, nuevaColumna, "VACIO");
            juego.getPaquete().setFila(paqNuevaFila);
            juego.getPaquete().setColumna(paqNuevaColumna);
            juego.getTablero().setCelda(paqNuevaFila, paqNuevaColumna, "PAQUETE");

            // Verificar si el paquete ha llegado a un puerto
            for (Puerto p : juego.getPuertos()) {
                if (p.getFila() == paqNuevaFila && p.getColumna() == paqNuevaColumna
                        && !p.isVisitado()
                        && p.getNumero() == juego.getPaquete().getPuertosVisitados() + 1) {
                    p.setVisitado(true);
                    juego.getPaquete().visitarPuerto();
                    juego.getTablero().setCelda(paqNuevaFila, paqNuevaColumna, "PAQUETE");
                    break;
                }
            }

        }
        // Mover al jugador
        juego.getJugador().setFila(nuevaFila);
        juego.getJugador().setColumna(nuevaColumna);
        // Reducir movimientos restantes
        juego.getJugador().setMovimientosRestantes(juego.getJugador().getMovimientosRestantes() - 1);
        // Verificar encuentros con amenazas
        verificarEncuentros();

        // Si el jugador estaba en modo sigilo, salir de ese modo al moverse
        if (juego.getJugador().isModoSigilo()) {
            juego.getJugador().setModoSigilo(false);
        }
        juego.verificarVictoria();
        juego.verificarDerrota();
        if (!juego.getEstado().equals(Juego.JUGANDO)) {
            guardarLog();
        }

        juego.moverAmenazas();

    }

    private void verificarEncuentros() {
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();

        for (Amenaza a : juego.getAmenazas()) {
            if (a == null)
                continue;

            boolean adyacente = (Math.abs(a.getFila() - jf) + Math.abs(a.getColumna() - jc)) == 1;

            if (adyacente) {
                if (a.getTipo().equals("ANTIVIRUS")) {
                    if (juego.getJugador().isModoSigilo()) {
                        juego.getJugador().setModoSigilo(false);
                    } else {
                        juego.setEstado(Juego.PERDIDO);
                    }
                } else if (a.getTipo().equals("ESCANER")) {
                    int reduccion = (int) (juego.getJugador().getMovimientosRestantes() * 0.05);
                    if (reduccion < 1)
                        reduccion = 1;
                    juego.getJugador()
                            .setMovimientosRestantes(juego.getJugador().getMovimientosRestantes() - reduccion);
                }
            }
        }
    }

    public void guardarLog() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("log_partida.txt");
            fw.write("=== LOG DE PARTIDA HACKERLITH ===\n");
            fw.write("Estado final: " + juego.getEstado() + "\n");
            fw.write("Movimientos restantes: " + juego.getJugador().getMovimientosRestantes() + "\n");
            fw.write("Puertos visitados: " + juego.getPaquete().getPuertosVisitados() + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar log: " + e.getMessage());
        }
    }

}