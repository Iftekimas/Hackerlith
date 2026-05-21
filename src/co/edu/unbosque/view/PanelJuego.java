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
                g.setColor(Color.DARK_GRAY);
                g.drawRect(j * tamCelda, i * tamCelda, tamCelda, tamCelda);
            }
        }

        // jugador
        g.setColor(Color.GREEN);
        int jf = juego.getJugador().getFila();
        int jc = juego.getJugador().getColumna();
        g.fillOval(jc * tamCelda + 5, jf * tamCelda + 5, tamCelda - 10, tamCelda - 10);
    }

    public void actualizar() {
        repaint();
    }

}
