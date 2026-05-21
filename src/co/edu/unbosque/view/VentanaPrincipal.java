package co.edu.unbosque.view;

import javax.swing.JFrame;

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

        setVisible(true);
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }
}
