package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import co.edu.unbosque.controller.Controlador;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private PanelMenu panelMenu;
    private PanelJuego panelJuego;
    private Controlador controlador;

    public VentanaPrincipal() {
        setTitle("Hackerlith");
        setSize(1220, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelMenu = new PanelMenu();
        panelMenu.getBtnNuevaPartida().addActionListener(e -> iniciarJuego());
        contenedor.add(panelMenu, "MENU");

        add(contenedor);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (controlador == null)
                    return;
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

    private void iniciarJuego() {
        controlador = new Controlador(panelMenu.getDificultad());
        panelJuego = new PanelJuego(controlador.getJuego());

        JPanel panelJuegoCompleto = new JPanel(new BorderLayout());
        panelJuegoCompleto.add(panelJuego, BorderLayout.CENTER);
        panelJuegoCompleto.add(new PanelInfo(controlador.getJuego(), panelMenu.getDificultad()), BorderLayout.EAST);

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
        panelJuegoCompleto.add(panelBotones, BorderLayout.SOUTH);

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

        contenedor.add(panelJuegoCompleto, "JUEGO");
        cardLayout.show(contenedor, "JUEGO");
        requestFocusInWindow();
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }
}
