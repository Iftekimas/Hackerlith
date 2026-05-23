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
    private JPanel panelJuegoCompleto;

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
                } else if (code == KeyEvent.VK_M) {
                    mostrarMenuPausa();
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
        controlador = new Controlador(panelMenu.getDificultad(), panelMenu.isOrdenInverso());
        panelJuego = new PanelJuego(controlador.getJuego());

        panelJuegoCompleto = new JPanel(new BorderLayout());
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

    private void mostrarMenuPausa() {
        JDialog dialogo = new JDialog(this, true);
        dialogo.setUndecorated(true);
        dialogo.setSize(280, 270);
        dialogo.setLocationRelativeTo(this);
        dialogo.getContentPane().setBackground(new Color(10, 10, 10));
        dialogo.setLayout(null);

        JLabel titulo = new JLabel("MENÚ DE PAUSA", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 280, 30);
        dialogo.add(titulo);

        JButton btnContinuar = crearBotonDialogo("CONTINUAR");
        JButton btnReiniciar = crearBotonDialogo("REINICIAR NIVEL");
        JButton btnObjetivos = crearBotonDialogo("VER OBJETIVOS");
        JButton btnMenu = crearBotonDialogo("VOLVER AL MENÚ");

        btnContinuar.setBounds(40, 65, 200, 36);
        btnReiniciar.setBounds(40, 110, 200, 36);
        btnObjetivos.setBounds(40, 155, 200, 36);
        btnMenu.setBounds(40, 200, 200, 36);

        dialogo.add(btnContinuar);
        dialogo.add(btnReiniciar);
        dialogo.add(btnObjetivos);
        dialogo.add(btnMenu);

        btnContinuar.addActionListener(e -> dialogo.dispose());
        btnReiniciar.addActionListener(e -> {
            dialogo.dispose();
            reiniciarJuego();
        });
        btnObjetivos.addActionListener(e -> verObjetivos(dialogo));
        btnMenu.addActionListener(e -> {
            dialogo.dispose();
            volverAlMenu();
        });

        dialogo.setVisible(true);
    }

    private JButton crearBotonDialogo(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(25, 25, 25));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        btn.setFocusPainted(false);
        return btn;
    }

    private void reiniciarJuego() {
        contenedor.remove(panelJuegoCompleto);
        iniciarJuego();
    }

    private void volverAlMenu() {
        contenedor.remove(panelJuegoCompleto);
        controlador = null;
        panelJuego = null;
        cardLayout.show(contenedor, "MENU");
    }

    private void verObjetivos(JDialog dialogo) {
        int visitados = controlador.getJuego().getPaquete().getPuertosVisitados();
        int total = controlador.getJuego().getPuertos().length;
        String orden = controlador.getJuego().isOrdenInverso() ? "P3 → P2 → P1" : "P1 → P2 → P3";
        JOptionPane.showMessageDialog(dialogo,
                "Orden: " + orden + "\nProgreso: " + visitados + " / " + total + " puertos visitados",
                "OBJETIVOS", JOptionPane.PLAIN_MESSAGE);
    }

}
