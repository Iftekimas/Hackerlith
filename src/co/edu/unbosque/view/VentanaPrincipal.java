package co.edu.unbosque.view;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import co.edu.unbosque.controller.Controlador;

public class VentanaPrincipal extends JFrame {

    private PanelJuego panelJuego;

    public VentanaPrincipal(Controlador controlador) {
        setTitle("Hackerlith");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelJuego = new PanelJuego(controlador.getJuego());
        add(panelJuego);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                String tecla = "";
                if (code == KeyEvent.VK_W)
                    tecla = "W";
                else if (code == KeyEvent.VK_S)
                    tecla = "S";
                else if (code == KeyEvent.VK_A)
                    tecla = "A";
                else if (code == KeyEvent.VK_D)
                    tecla = "D";

                if (!tecla.isEmpty()) {
                    controlador.moverJugador(tecla);
                    panelJuego.actualizar();
                }
            }

        });
        setFocusable(true);
        setVisible(true);
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }
}
