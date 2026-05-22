package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import co.edu.unbosque.model.Juego;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PanelJuego extends JPanel {

    private Juego juego;
    private int tamCelda = 50;

    private Image imgAntivirus;
    private Image imgEscaner;
    private Image imgFirewall;
    private Image imgPaquete;
    private Image imgCasilla;
    private Image imgPuerto1;
    private Image imgPuerto2;
    private Image imgPuerto3;

    public PanelJuego(Juego juego) {
        this.juego = juego;
        setBackground(Color.BLACK);

        imgAntivirus = new ImageIcon(getClass().getResource("/resources/Partida/Antivirus proactivo.png")).getImage();
        imgEscaner = new ImageIcon(getClass().getResource("/resources/Partida/Escaner de latencia.png")).getImage();
        imgFirewall = new ImageIcon(getClass().getResource("/resources/Partida/Firewall.png")).getImage();
        imgPaquete = new ImageIcon(getClass().getResource("/resources/Partida/Pared.png")).getImage();
        imgCasilla = new ImageIcon(getClass().getResource("/resources/Partida/Punto.png")).getImage();
        imgPuerto1 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 1.png")).getImage();
        imgPuerto2 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 2.png")).getImage();
        imgPuerto3 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 3.png")).getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el tablero
        int filas = juego.getTablero().getFilas();
        int columnas = juego.getTablero().getColumnas();
        // Dibujar fondo
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int x = j * tamCelda;
                int y = i * tamCelda;
                String celda = juego.getTablero().getCelda(i, j);

                // fondo negro para todas las celdas
                g.setColor(Color.BLACK);
                g.fillRect(x, y, tamCelda, tamCelda);

                // punto pequeño solo en celdas vacías
                if (celda.equals("VACIO")) {
                    int dotSize = 8;
                    int dotX = x + (tamCelda - dotSize) / 2;
                    int dotY = y + (tamCelda - dotSize) / 2;
                    g.drawImage(imgCasilla, dotX, dotY, dotSize, dotSize, null);
                }

                // dibujar imágenes según el contenido de la celda
                if (celda.equals("FIREWALL")) {
                    g.drawImage(imgFirewall, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("PAQUETE")) {
                    g.drawImage(imgPaquete, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("PUERTO")) {
                    int num = 1;
                    for (co.edu.unbosque.model.Puerto p : juego.getPuertos()) {
                        if (p.getFila() == i && p.getColumna() == j) {
                            num = p.getNumero();
                            break;
                        }
                    }
                    // Dibujar el puerto con su número específico
                    if (num == 1)
                        g.drawImage(imgPuerto1, x, y, tamCelda, tamCelda, null);
                    else if (num == 2)
                        g.drawImage(imgPuerto2, x, y, tamCelda, tamCelda, null);
                    else
                        g.drawImage(imgPuerto3, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("ANTIVIRUS")) {
                    g.drawImage(imgAntivirus, x, y, tamCelda, tamCelda, null);

                } else if (celda.equals("ESCANER")) {
                    g.drawImage(imgEscaner, x, y, tamCelda, tamCelda, null);
                }
            }
        }
        // Dibujar el jugador
        g.setColor(Color.GREEN);
        // fondo del jugador
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        g.setColor(Color.GREEN);
        g.fillRect(jc * tamCelda + 5, jf * tamCelda + 5, tamCelda - 10, tamCelda - 10);
        g.setColor(Color.WHITE);
        g.drawRect(jc * tamCelda + 5, jf * tamCelda + 5, tamCelda - 10, tamCelda - 10);

        int[][] rastro = juego.getPaquete().getRastro();
        int cantidad = juego.getPaquete().getCantidadRastro();

        for (int r = 0; r < cantidad; r++) {
            int rx = rastro[r][1] * tamCelda + 15;
            int ry = rastro[r][0] * tamCelda + 15;
            g.setColor(new Color(255, 255, 0, 80));
            g.fillOval(rx, ry, tamCelda - 30, tamCelda - 30);
        }
        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        g.drawString("Movimientos: " + juego.getJugador().getMovimientosRestantes(), 10,
                juego.getTablero().getFilas() * tamCelda + 20);

        int siguiente = juego.getPaquete().getPuertosVisitados() + 1;
        if (siguiente <= juego.getPuertos().length) {
            g.drawString("Puerto objetivo: P" + siguiente, 250, juego.getTablero().getFilas() * tamCelda + 20);
        } else {
            g.drawString("¡Todos los puertos visitados!", 250, juego.getTablero().getFilas() * tamCelda + 20);
        }

        // mensaje de fin de juego
        if (juego.getEstado().equals(Juego.GANADO)) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.GREEN);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
            g.drawString("¡MISION COMPLETADA!", 80, getHeight() / 2);
        } else if (juego.getEstado().equals(Juego.PERDIDO)) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
            g.drawString("MISION FALLIDA", 120, getHeight() / 2);
        }
    }

    public void actualizar() {
        repaint();
    }

}
