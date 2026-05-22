package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import co.edu.unbosque.model.Juego;

public class PanelJuego extends JPanel {

    private Juego juego;
    private int tamCelda = 50;

    public PanelJuego(Juego juego) {
        this.juego = juego;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int filas = juego.getTablero().getFilas();
        int columnas = juego.getTablero().getColumnas();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String celda = juego.getTablero().getCelda(i, j);
                if (celda.equals("FIREWALL")) {
                    g.setColor(Color.GRAY);
                    g.fillRect(j * tamCelda, i * tamCelda, tamCelda, tamCelda);
                } else if (celda.equals("PUERTO")) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * tamCelda, i * tamCelda, tamCelda, tamCelda);
                } else if (celda.equals("PAQUETE")) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(j * tamCelda, i * tamCelda, tamCelda, tamCelda);
                }
                g.setColor(Color.DARK_GRAY);
                g.drawRect(j * tamCelda, i * tamCelda, tamCelda, tamCelda);
            }
        }

        g.setColor(Color.GREEN);
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        g.fillOval(jc * tamCelda + 10, jf * tamCelda + 10, tamCelda - 20, tamCelda - 20);
    }

    public void actualizar() {
        repaint();
    }

}
