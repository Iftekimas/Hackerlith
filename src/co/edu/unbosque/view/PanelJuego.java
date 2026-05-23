package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import co.edu.unbosque.model.Juego;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.FontMetrics;

public class PanelJuego extends JPanel {

    private Juego juego;
    private static final int HUD_HEIGHT = 40; // Altura del área de HUD debajo del tablero

    private String direccionJugador = "FRENTE";

    // Imágenes para los elementos del juego
    private Image imgAntivirus;
    private Image imgEscaner;
    private Image imgFirewall;
    private Image imgPaquete;
    private Image imgCasilla;
    private Image imgPuerto1;
    private Image imgPuerto2;
    private Image imgPuerto3;
    private Image imgPuerto4;
    private Image imgPuerto5;
    private Image imgMarcianito;
    private Image imgMarcianitoDer;
    private Image imgMarcianitIzq;
    private Image imgMarcianitAtras;
    private Image imgPilaRecarga;
    private Image imgBorde;
    private Image imgDead;
    private Image imgComputadora;
    private Image imgBandera;
    private JButton btnVolverMenu;
    private Runnable onVolverMenu;

    public PanelJuego(Juego juego, String skin) {
        this.juego = juego;
        setBackground(Color.BLACK);
        // Cargar imágenes
        imgAntivirus = new ImageIcon(getClass().getResource("/resources/Partida/Antivirus proactivo.png")).getImage();
        imgEscaner = new ImageIcon(getClass().getResource("/resources/Partida/Escaner de latencia.png")).getImage();
        imgFirewall = new ImageIcon(getClass().getResource("/resources/Partida/Firewall.png")).getImage();
        imgPaquete = new ImageIcon(getClass().getResource("/resources/Partida/archivo.png")).getImage();
        imgCasilla = new ImageIcon(getClass().getResource("/resources/Partida/borde.png")).getImage();
        imgPuerto1 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 1.png")).getImage();
        imgPuerto2 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 2.png")).getImage();
        imgPuerto3 = new ImageIcon(getClass().getResource("/resources/Partida/puerto 3.png")).getImage();
        java.net.URL url4 = getClass().getResource("/resources/Partida/puerto 4.png");
        imgPuerto4 = (url4 != null) ? new ImageIcon(url4).getImage() : imgPuerto3;
        java.net.URL url5 = getClass().getResource("/resources/Partida/puerto 5.png");
        imgPuerto5 = (url5 != null) ? new ImageIcon(url5).getImage() : imgPuerto3;
        imgComputadora = new ImageIcon(getClass().getResource("/resources/Nivel completado/Compu feli.png")).getImage();
        imgBandera = new ImageIcon(getClass().getResource("/resources/Nivel completado/Banderita.png")).getImage();

        setLayout(null);
        btnVolverMenu = new JButton("♥  VOLVER AL MENÚ");
        btnVolverMenu.setBackground(new Color(25, 25, 25));
        btnVolverMenu.setForeground(Color.WHITE);
        btnVolverMenu.setFont(new Font("Monospaced", Font.BOLD, 13));
        btnVolverMenu.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.setVisible(false);
        btnVolverMenu.addActionListener(e -> {
            if (onVolverMenu != null)
                onVolverMenu.run();
        });
        add(btnVolverMenu);

        if (skin.equals("TITA")) {
            String c = "/resources/Personajes/Tita/";
            imgMarcianito = new ImageIcon(getClass().getResource(c + "mirando al frente.png")).getImage();
            imgMarcianitoDer = new ImageIcon(getClass().getResource(c + "mirando a la derecha.png")).getImage();
            imgMarcianitIzq = new ImageIcon(getClass().getResource(c + "caminando a la izquierda.png")).getImage();
            imgMarcianitAtras = new ImageIcon(getClass().getResource(c + "mirando atras.png")).getImage();
            imgDead = new ImageIcon(getClass().getResource(c + "dead.png")).getImage();
        } else {
            String c = "/resources/Personajes/Marcianito/";
            imgMarcianito = new ImageIcon(getClass().getResource(c + "Mirando al frente.png")).getImage();
            imgMarcianitoDer = new ImageIcon(getClass().getResource(c + "Mirando a la derecha.png")).getImage();
            imgMarcianitIzq = new ImageIcon(getClass().getResource(c + "mirando a la izquierda.png")).getImage();
            imgMarcianitAtras = new ImageIcon(getClass().getResource(c + "mirando atras.png")).getImage();
            imgDead = new ImageIcon(getClass().getResource(c + "dead.png")).getImage();
        }

        imgPilaRecarga = new ImageIcon(getClass().getResource("/resources/Partida/pila de recarga.png")).getImage();
        imgBorde = new ImageIcon(getClass().getResource("/resources/Partida/borde.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int filas = juego.getTablero().getFilas();
        int columnas = juego.getTablero().getColumnas();
        int tamCelda = Math.min(64, Math.min(getWidth() / columnas, (getHeight() - HUD_HEIGHT) / filas));
        int gridW = columnas * tamCelda;
        int gridH = filas * tamCelda;

        // Centrar la cuadrícula en el panel
        int offsetX = Math.max(0, (getWidth() - gridW) / 2);
        int offsetY = HUD_HEIGHT + Math.max(0, (getHeight() - HUD_HEIGHT - gridH) / 2);

        // Fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, HUD_HEIGHT, getWidth(), getHeight());

        // Dibujar borde.png cubriendo toda la cuadrícula
        g.drawImage(imgBorde, offsetX, offsetY, gridW, gridH, null);

        // Dibujar celdas (saltando PARED, ya las cubre borde.png)
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String celda = juego.getTablero().getCelda(i, j);
                if (celda.equals("PARED"))
                    continue;

                int x = offsetX + j * tamCelda;
                int y = offsetY + i * tamCelda;

                g.setColor(Color.BLACK);
                g.fillRect(x, y, tamCelda, tamCelda);

                if (celda.equals("VACIO")) {
                    int dotSize = 70;
                    g.drawImage(imgCasilla, x + (tamCelda - dotSize) / 2, y + (tamCelda - dotSize) / 2, dotSize,
                            dotSize, null);
                } else if (celda.equals("FIREWALL")) {
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
                    if (num == 1)
                        g.drawImage(imgPuerto1, x, y, tamCelda, tamCelda, null);
                    else if (num == 2)
                        g.drawImage(imgPuerto2, x, y, tamCelda, tamCelda, null);
                    else if (num == 3)
                        g.drawImage(imgPuerto3, x, y, tamCelda, tamCelda, null);
                    else if (num == 4)
                        g.drawImage(imgPuerto4, x, y, tamCelda, tamCelda, null);
                    else
                        g.drawImage(imgPuerto5, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("ANTIVIRUS")) {
                    g.drawImage(imgAntivirus, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("ESCANER")) {
                    g.drawImage(imgEscaner, x, y, tamCelda, tamCelda, null);
                } else if (celda.equals("NODO")) {
                    g.drawImage(imgPilaRecarga, x, y, tamCelda, tamCelda, null);
                }
            }
        }

        // Jugador
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        Image imgJugadorActual;
        if (juego.getEstado().equals(Juego.PERDIDO))
            imgJugadorActual = imgDead;
        else if (direccionJugador.equals("DERECHA"))
            imgJugadorActual = imgMarcianitoDer;
        else if (direccionJugador.equals("IZQUIERDA"))
            imgJugadorActual = imgMarcianitIzq;
        else if (direccionJugador.equals("ATRAS"))
            imgJugadorActual = imgMarcianitAtras;
        else
            imgJugadorActual = imgMarcianito;
        g.drawImage(imgJugadorActual, offsetX + jc * tamCelda, offsetY + jf * tamCelda, tamCelda, tamCelda, null);

        // Rastro del paquete
        int[][] rastro = juego.getPaquete().getRastro();
        int cantidad = juego.getPaquete().getCantidadRastro();
        for (int r = 0; r < cantidad; r++) {
            int rx = offsetX + rastro[r][1] * tamCelda + 15;
            int ry = offsetY + rastro[r][0] * tamCelda + 15;
            g.setColor(new Color(255, 255, 0, 80));
            g.fillOval(rx, ry, tamCelda - 30, tamCelda - 30);
        }

        // HUD
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, getWidth(), HUD_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 16));
        g.drawString("❤ " + juego.getJugador().getMovimientosRestantes() + "/" + juego.getMovimientosMax(), 15, 28);
        g.drawString("|", 180, 28);
        int visitados = juego.getPaquete().getPuertosVisitados();
        int sig = juego.isOrdenInverso() ? (juego.getPuertos().length - visitados) : (visitados + 1);
        String puertoText = visitados >= juego.getPuertos().length ? "¡COMPLETADO!" : "PUERTO: P" + sig;
        g.drawString(puertoText, 200, 28);
        if (juego.getJugador().isModoSigilo()) {
            g.setColor(Color.CYAN);
            g.drawString("| SIGILO", 380, 28);
        }

        // Mensaje fin de juego
        // Mensaje fin de juego
        if (juego.getEstado().equals(Juego.GANADO)) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            int pw = 500, ph = 290, px = (getWidth() - pw) / 2, py = (getHeight() - ph) / 2;
            g.setColor(new Color(18, 18, 18));
            g.fillRect(px, py, pw, ph);
            g.setColor(new Color(90, 90, 90));
            g.drawRect(px, py, pw, ph);

            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 20));
            FontMetrics fm = g.getFontMetrics();
            String t1 = "¡MISIÓN COMPLETADA!";
            g.drawString(t1, px + (pw - fm.stringWidth(t1)) / 2, py + 38);

            int is = 72, gap = 24, totalW = 3 * is + 2 * gap;
            int ix = px + (pw - totalW) / 2, iy = py + 55;
            g.drawImage(imgMarcianito, ix, iy, is, is, null);
            g.drawImage(imgComputadora, ix + is + gap, iy, is, is, null);
            g.drawImage(imgBandera, ix + 2 * (is + gap), iy, is, is, null);

            g.setColor(new Color(170, 170, 170));
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            String sub = "Has visitado todos los puertos en el orden correcto.";
            fm = g.getFontMetrics();
            g.drawString(sub, px + (pw - fm.stringWidth(sub)) / 2, iy + is + 18);

            int movUsados = juego.getMovimientosMax() - juego.getJugador().getMovimientosRestantes();
            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 13));
            g.drawString("MOVIMIENTOS USADOS:  " + movUsados, px + 60, iy + is + 45);

        } else if (juego.getEstado().equals(Juego.PERDIDO)) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            int pw = 420, ph = 270, px = (getWidth() - pw) / 2, py = (getHeight() - ph) / 2;
            g.setColor(new Color(18, 18, 18));
            g.fillRect(px, py, pw, ph);
            g.setColor(new Color(90, 90, 90));
            g.drawRect(px, py, pw, ph);

            g.setColor(Color.RED);
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 22));
            FontMetrics fm = g.getFontMetrics();
            String t2 = "MISIÓN FALLIDA";
            g.drawString(t2, px + (pw - fm.stringWidth(t2)) / 2, py + 40);

            int is = 72;
            g.drawImage(imgDead, px + (pw - is) / 2, py + 55, is, is, null);

            g.setColor(new Color(170, 170, 170));
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            String msg = "Has sido detectado o te quedaste sin movimientos.";
            fm = g.getFontMetrics();
            g.drawString(msg, px + (pw - fm.stringWidth(msg)) / 2, py + 150);

            int movUsados = juego.getMovimientosMax() - juego.getJugador().getMovimientosRestantes();
            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 13));
            g.drawString("MOVIMIENTOS USADOS:  " + movUsados, px + 60, py + 178);
        }

    }

    public void actualizar() {
        repaint();
        if (!juego.getEstado().equals(Juego.JUGANDO) && !btnVolverMenu.isVisible()) {
            int bw = 210, bh = 38;
            btnVolverMenu.setBounds(getWidth() / 2 - bw / 2, getHeight() / 2 + 105, bw, bh);
            btnVolverMenu.setVisible(true);
        }
    }

    public void setDireccionJugador(String dir) {
        this.direccionJugador = dir;
    }

    public void setOnVolverMenu(Runnable r) {
        this.onVolverMenu = r;
    }

}
