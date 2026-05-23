package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import co.edu.unbosque.model.Juego;

public class PanelInfo extends JPanel {

    private Juego juego;
    private Image imgJugador, imgPuerto, imgFirewall, imgAntivirus, imgEscaner, imgCasilla;

    public PanelInfo(Juego juego) {
        this.juego = juego;
        setBackground(new Color(15, 15, 15));
        setPreferredSize(new Dimension(220, 0));

        imgJugador = new ImageIcon(getClass().getResource("/resources/Marcianito/Mirando al frente.png")).getImage();
        imgPuerto = new ImageIcon(getClass().getResource("/resources/Partida/puerto 1.png")).getImage();
        imgFirewall = new ImageIcon(getClass().getResource("/resources/Partida/Firewall.png")).getImage();
        imgAntivirus = new ImageIcon(getClass().getResource("/resources/Partida/Antivirus proactivo.png")).getImage();
        imgEscaner = new ImageIcon(getClass().getResource("/resources/Partida/Escaner de latencia.png")).getImage();
        imgCasilla = new ImageIcon(getClass().getResource("/resources/Partida/Punto guia.png")).getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));

        // leyenda

        g.setColor(Color.WHITE);
        g.drawString("LEYENDA", 20, 40);
        g.setColor(new Color(100, 100, 100));
        g.drawLine(10, 48, 210, 48);

        int iconSize = 28;
        int x = 14, y = 90;
        int paso = 38;

        dibujarFila(g, imgJugador, "JUGADOR", x, y, iconSize);
        y += paso;
        dibujarFila(g, imgPuerto, "PUERTO DE ENLACE", x, y, iconSize);
        y += paso;
        dibujarFila(g, imgFirewall, "FIREWALL", x, y, iconSize);
        y += paso;
        dibujarFila(g, imgAntivirus, "ANTIVIRUS PROACTIVO", x, y, iconSize);
        y += paso;
        dibujarFila(g, imgEscaner, "ESCANER DE LATENCIA", x, y, iconSize);
        y += paso;
        dibujarFila(g, imgCasilla, "CASILLA LIBRE", x, y, iconSize);
        y += paso + 10;

        // Objetivo

        g.setColor(new Color(100, 100, 100));
        g.drawLine(10, y - 6, 210, y - 6);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        g.drawString("OBJETIVO", 20, y + 10);
        g.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g.setColor(new Color(200, 200, 200));
        g.drawString("Visita todos los", 14, y + 30);
        g.drawString("puertos en orden.", 14, y + 45);
        g.drawString("P1 -> P2 -> P3", 14, y + 65);
    }

    private void dibujarFila(Graphics g, Image img, String texto, int x, int y, int size) {
        g.drawImage(img, x, y - size + 6, size, size, null);
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Monospaced", Font.PLAIN, 11));

        if (texto.length() > 16) {
            String[] partes = texto.split(" ", 2);
            g.drawString(partes[0], x + size + 8, y - 8);
            g.drawString(partes[1], x + size + 8, y + 6);

        } else {
            g.drawString(texto, x + size + 8, y - 2);
        }
    }
}
