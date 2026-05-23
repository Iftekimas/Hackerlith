package co.edu.unbosque.controller;

import co.edu.unbosque.model.*;

public class Controlador {

    private Juego juego;

    private java.util.ArrayList<String> historial = new java.util.ArrayList<>();
    private int turno = 0;

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

        if (celda.equals("FIREWALL") || celda.equals("PARED")) {
            return; // No mover si hay un firewall
        }

        if (celda.equals("PAQUETE")) {
            int paqNuevaFila = nuevaFila + df;
            int paqNuevaColumna = nuevaColumna + dc;

            if (!juego.getTablero().estaEnRango(paqNuevaFila, paqNuevaColumna))
                return; // No mover si el paquete va a salir del tablero

            if (juego.getTablero().getCelda(paqNuevaFila, paqNuevaColumna).equals("FIREWALL")
                    || juego.getTablero().getCelda(paqNuevaFila, paqNuevaColumna).equals("PARED"))
                return; // No mover si el paquete choca con un firewall

            juego.getPaquete().agregarRastro(nuevaFila, nuevaColumna);

            // Mover el paquete
            String celdaAnterior = "VACIO";
            for (Puerto p : juego.getPuertos()) {
                if (p.getFila() == nuevaFila && p.getColumna() == nuevaColumna && !p.isVisitado()) {
                    celdaAnterior = "PUERTO";
                    break;
                }
            }
            juego.getTablero().setCelda(nuevaFila, nuevaColumna, celdaAnterior);
            juego.getPaquete().setFila(paqNuevaFila);
            juego.getPaquete().setColumna(paqNuevaColumna);
            juego.getTablero().setCelda(paqNuevaFila, paqNuevaColumna, "PAQUETE");

            // Verificar si el paquete ha llegado a un puerto
            for (Puerto p : juego.getPuertos()) {
                int numEsperado = juego.isOrdenInverso()
                        ? (juego.getPuertos().length - juego.getPaquete().getPuertosVisitados())
                        : (juego.getPaquete().getPuertosVisitados() + 1);
                if (p.getFila() == paqNuevaFila && p.getColumna() == paqNuevaColumna
                        && !p.isVisitado()
                        && p.getNumero() == numEsperado) {
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

        turno++;
        historial.add("Turno " + turno + " | Dir: " + direccion
                + " | Pos: (" + nuevaFila + "," + nuevaColumna + ")"
                + " | Movimientos restantes: " + (juego.getJugador().getMovimientosRestantes() - 1)
                + " | Puertos visitados: " + juego.getPaquete().getPuertosVisitados());

        verificarNodos();
        verificarSandwichFirewall();
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
                    if (juego.getJugador().isModoSigilo())
                        juego.getJugador().setModoSigilo(false);
                    else
                        juego.setEstado(Juego.PERDIDO);
                } else if (a.getTipo().equals("ESCANER")) {
                    double pct;
                    if (juego.getDificultad().equals("ALTA")) {
                        pct = 0.15;
                    } else {
                        pct = 0.05;
                    }
                    int reduccion = (int) (juego.getJugador().getMovimientosRestantes() * pct);
                    if (reduccion < 1)
                        reduccion = 1;
                    juego.getJugador().setMovimientosRestantes(
                            juego.getJugador().getMovimientosRestantes() - reduccion);
                }
            }
        }
    }

    public void guardarLog() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("log_partida.txt");
            fw.write("=== LOG DE PARTIDA HACKERLITH ===\n");
            fw.write("Estado final:         " + juego.getEstado() + "\n");
            fw.write("Dificultad:           " + juego.getDificultad() + "\n");
            fw.write("Movimientos restantes: " + juego.getJugador().getMovimientosRestantes() + "\n");
            fw.write("Puertos visitados:    " + juego.getPaquete().getPuertosVisitados() + "\n");
            fw.write("Total de turnos:      " + turno + "\n");
            fw.write("\n=== HISTORIAL DE MOVIMIENTOS ===\n");
            for (String mov : historial) {
                fw.write(mov + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar log: " + e.getMessage());
        }
    }

    private void verificarSandwichFirewall() {
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        Tablero t = juego.getTablero();

        boolean sandwichH = t.estaEnRango(jf, jc - 1) && t.estaEnRango(jf, jc + 1)
                && t.getCelda(jf, jc - 1).equals("FIREWALL")
                && t.getCelda(jf, jc + 1).equals("FIREWALL");

        boolean sandwichV = t.estaEnRango(jf - 1, jc) && t.estaEnRango(jf + 1, jc)
                && t.getCelda(jf - 1, jc).equals("FIREWALL")
                && t.getCelda(jf + 1, jc).equals("FIREWALL");

        if (sandwichH || sandwichV) {
            int penalizacion = Math.max(1, jf + jc);
            juego.getJugador().setMovimientosRestantes(
                    juego.getJugador().getMovimientosRestantes() - penalizacion);
        }
    }

    private void verificarNodos() {
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        for (NodoEnergia n : juego.getNodos()) {
            if (n.getFila() == jf && n.getColumna() == jc && n.isActivo()) {
                int bonus = (int) (juego.getMovimientosMax() * 0.10);
                if (bonus < 1)
                    bonus = 1;
                juego.getJugador().setMovimientosRestantes(juego.getJugador().getMovimientosRestantes() + bonus);
                n.setActivo(false);
                juego.getTablero().setCelda(jf, jc, "VACIO");
            }
        }
    }

    // constructor
    public Controlador(String dificultad, boolean ordenInverso, int filas, int columnas, int numPuertos) {
        int movMax = filas * columnas;
        juego = new Juego(filas, columnas, numPuertos, movMax, ordenInverso);
        juego.inicializar(dificultad);
    }
}