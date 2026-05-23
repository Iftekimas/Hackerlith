package co.edu.unbosque.view;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import co.edu.unbosque.controller.Controlador;

public class VentanaPrincipal extends JFrame {

    private PanelJuego panelJuego;

    public VentanaPrincipal(Controlador controlador) {
        setTitle("Hackerlith");
        setSize(1220, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelJuego = new PanelJuego(controlador.getJuego());
        add(panelJuego, BorderLayout.CENTER);

        PanelInfo panelInfo = new PanelInfo(controlador.getJuego());
        add(panelInfo, BorderLayout.EAST);

        // panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3));
        JButton btnArriba = new JButton("↑");
        JButton btnAbajo = new JButton("↓");
        JButton btnIzquierda = new JButton("←");
        JButton btnDerecha = new JButton("→");

        // Agregar botones al panel

        panelBotones.add(new JPanel());
        panelBotones.add(btnArriba);
        panelBotones.add(new JPanel());
        panelBotones.add(btnIzquierda);
        panelBotones.add(btnAbajo);
        panelBotones.add(btnDerecha);

        add(panelBotones, BorderLayout.SOUTH);

        // Acción de los botones
        btnArriba.addActionListener(e -> {
            panelJuego.setDireccionJugador("ATRAS");
            controlador.moverJugador("W");
            panelJuego.actualizar();
        });

        btnAbajo.addActionListener(e -> {
            panelJuego.setDireccionJugador("FRENTE");
            controlador.moverJugador("S");
            panelJuego.actualizar();
        });
        btnIzquierda.addActionListener(e -> {
            panelJuego.setDireccionJugador("IZQUIERDA");
            controlador.moverJugador("A");
            panelJuego.actualizar();
        });
        btnDerecha.addActionListener(e -> {
            panelJuego.setDireccionJugador("DERECHA");
            controlador.moverJugador("D");
            panelJuego.actualizar();
        });

        // teclado
        addKeyListener(new KeyAdapter() {
            // Manejar teclas W, A, S, D para mover al jugador y E para activar modo sigilo
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                String tecla = "";
                if (code == KeyEvent.VK_W) {
                    tecla = "W";
                    panelJuego.setDireccionJugador("ATRAS");
                } else if (code == KeyEvent.VK_E) {
                    controlador.getJuego().getJugador().setModoSigilo(true);
                    panelJuego.actualizar();
                } else if (code == KeyEvent.VK_S) {
                    tecla = "S";
                    panelJuego.setDireccionJugador("FRENTE");
                } else if (code == KeyEvent.VK_A) {
                    tecla = "A";
                    panelJuego.setDireccionJugador("IZQUIERDA");
                } else if (code == KeyEvent.VK_D) {
                    tecla = "D";
                    panelJuego.setDireccionJugador("DERECHA");
                }

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
