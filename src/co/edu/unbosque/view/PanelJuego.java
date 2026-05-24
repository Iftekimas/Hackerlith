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

/**
 * Panel que dibuja el tablero, el jugador, las amenazas y el HUD durante la partida.
 * @author Santiago Forero Garibello
 * @author Alejandra Fernandez Espinosa
 * @author Michael Andres Sanchez
 * @version 1.0
 */
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
    private VentanaPrincipal ventana;

    /**
     * Crea el panel de juego cargando las imágenes según el skin elegido.
     * @param juego estado del juego a dibujar
     * @param skin nombre del skin del personaje: MARCIANITO o TITA
     * @param ventana referencia a la ventana principal para volver al menú
     */
    public PanelJuego(Juego juego, String skin, VentanaPrincipal ventana) {
        this.juego = juego;
        this.ventana = ventana;
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
        imgPuerto4 = new ImageIcon(getClass().getResource("/resources/Partida/Puerto_4.png")).getImage();
        imgPuerto5 = new ImageIcon(getClass().getResource("/resources/Partida/Puerto_5.png")).getImage();
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
        btnVolverMenu.addActionListener(e -> ventana.volverAlMenu());
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
        int tamCelda = getWidth() / columnas;
        if ((getHeight() - HUD_HEIGHT) / filas < tamCelda)
            tamCelda = (getHeight() - HUD_HEIGHT) / filas;
        if (tamCelda > 64)
            tamCelda = 64;

        int gridW = columnas * tamCelda;
        int gridH = filas * tamCelda;

        int offsetX = (getWidth() - gridW) / 2;
        if (offsetX < 0) offsetX = 0;
        int offsetY = HUD_HEIGHT + (getHeight() - HUD_HEIGHT - gridH) / 2;
        if (offsetY < HUD_HEIGHT) offsetY = HUD_HEIGHT;

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
        int sig;
        if (juego.isOrdenInverso())
            sig = juego.getPuertos().length - visitados;
        else
            sig = visitados + 1;
        String puertoText;
        if (visitados >= juego.getPuertos().length)
            puertoText = "¡COMPLETADO!";
        else
            puertoText = "PUERTO: P" + sig;
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
            g.drawString("¡MISIÓN COMPLETADA!", px + 135, py + 38);

            int is = 72;
            int gap = 24;
            int totalW = 3 * is + 2 * gap;
            int ix = px + (pw - totalW) / 2;
            int iy = py + 55;
            g.drawImage(imgMarcianito, ix, iy, is, is, null);
            g.drawImage(imgComputadora, ix + is + gap, iy, is, is, null);
            g.drawImage(imgBandera, ix + 2 * (is + gap), iy, is, is, null);

            g.setColor(new Color(170, 170, 170));
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            g.drawString("Has visitado todos los puertos en el orden correcto.", px + 65, iy + is + 18);

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
            g.drawString("MISIÓN FALLIDA", px + 118, py + 40);

            int is = 72;
            g.drawImage(imgDead, px + (pw - is) / 2, py + 55, is, is, null);

            g.setColor(new Color(170, 170, 170));
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            g.drawString("Has sido detectado o te quedaste sin movimientos.", px + 35, py + 150);

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

}
