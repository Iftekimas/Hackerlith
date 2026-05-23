package co.edu.unbosque.view;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import co.edu.unbosque.controller.Controlador;

public class VentanaPrincipal extends JFrame {

    private PanelJuego panelJuego;

    public VentanaPrincipal(Controlador controlador) {
        setTitle("Hackerlith");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelJuego = new PanelJuego(controlador.getJuego());
        add(panelJuego, BorderLayout.CENTER);

        // panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3));
        JButton btnArriba = new JButton("↑");
        JButton btnAbajo = new JButton("↓");
        JButton btnIzquierda = new JButton("←");
        JButton btnDerecha = new JButton("→");

        panelBotones.add(new JPanel());
        panelBotones.add(btnArriba);
        panelBotones.add(new JPanel());
        panelBotones.add(btnIzquierda);
        panelBotones.add(btnAbajo);
        panelBotones.add(btnDerecha);

        add(panelBotones, BorderLayout.SOUTH);

        btnArriba.addActionListener(e -> {
            controlador.moverJugador("W");
            panelJuego.actualizar();
        });

        btnAbajo.addActionListener(e -> {
            controlador.moverJugador("S");
            panelJuego.actualizar();
        });
        btnIzquierda.addActionListener(e -> {
            controlador.moverJugador("A");
            panelJuego.actualizar();
        });
        btnDerecha.addActionListener(e -> {
            controlador.moverJugador("D");
            panelJuego.actualizar();
        });

        // teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                String tecla = "";
                if (code == KeyEvent.VK_W)
                    tecla = "W";
                else if (code == KeyEvent.VK_E) {
                    controlador.getJuego().getJugador().setModoSigilo(true);
                    panelJuego.actualizar();
                } else if (code == KeyEvent.VK_S)
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
