package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import co.edu.unbosque.controller.Controlador;
import co.edu.unbosque.model.Jugador;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private PanelMenu panelMenu;
    private PanelJuego panelJuego;
    private Controlador controlador;
    private JPanel panelJuegoCompleto;
    private GestorAudio audio;

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
                    Jugador j = controlador.getJuego().getJugador();
                    if (!j.isSigiloUsado()) {
                        j.setModoSigilo(true);
                        j.setSigiloUsado(true);
                        panelJuego.actualizar();
                    }
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
        audio = new GestorAudio(); // ← agregar aquí
        SwingUtilities.invokeLater(() -> audio.reproducir("/resources/Audio/Intro 1.wav", true));

    }

    private void iniciarJuego() {
        controlador = new Controlador(panelMenu.getDificultad(), panelMenu.isOrdenInverso(), panelMenu.getFilas(), panelMenu.getColumnas(), panelMenu.getNumPuertos());
        panelJuego = new PanelJuego(controlador.getJuego(), panelMenu.getSkin());
        panelJuego.setOnVolverMenu(() -> volverAlMenu());

        audio.reproducir("/resources/Audio/Intro 2.wav", true);

        panelJuegoCompleto = new JPanel(new BorderLayout());
        panelJuegoCompleto.add(panelJuego, BorderLayout.CENTER);
        panelJuegoCompleto.add(new PanelInfo(controlador.getJuego(), panelMenu.getDificultad()), BorderLayout.EAST);

        panelJuegoCompleto = new JPanel(new BorderLayout());
        panelJuegoCompleto.add(panelJuego, BorderLayout.CENTER);
        panelJuegoCompleto.add(new PanelInfo(controlador.getJuego(), panelMenu.getDificultad()), BorderLayout.EAST);

        contenedor.add(panelJuegoCompleto, "JUEGO");
        cardLayout.show(contenedor, "JUEGO");
        requestFocusInWindow();

        contenedor.add(panelJuegoCompleto, "JUEGO");
        cardLayout.show(contenedor, "JUEGO");
        if (panelMenu.getDificultad().equals("ALTA")) {
            audio.reproducir("/resources/Audio/Gamelay.wav", true);
        } else {
            audio.reproducir("/resources/Audio/Intro 2.wav", true);
        }

        requestFocusInWindow();
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }

    private void mostrarMenuPausa() {
        JDialog dialogo = new JDialog(this, true);
        dialogo.setUndecorated(true);
        dialogo.setSize(280, 315);
        dialogo.setLocationRelativeTo(this);
        dialogo.getContentPane().setBackground(new Color(10, 10, 10));
        dialogo.setLayout(null);

        JLabel titulo = new JLabel("MENÚ DE PAUSA", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 280, 30);
        dialogo.add(titulo);

        JButton btnContinuar    = crearBotonDialogo("CONTINUAR");
        JButton btnReiniciar    = crearBotonDialogo("REINICIAR NIVEL");
        JButton btnObjetivos    = crearBotonDialogo("VER OBJETIVOS");
        JButton btnInstrucciones = crearBotonDialogo("INSTRUCCIONES  (-5 MOV)");
        JButton btnMenu         = crearBotonDialogo("VOLVER AL MENÚ");

        btnContinuar.setBounds(40, 65, 200, 36);
        btnReiniciar.setBounds(40, 110, 200, 36);
        btnObjetivos.setBounds(40, 155, 200, 36);
        btnInstrucciones.setBounds(40, 200, 200, 36);
        btnMenu.setBounds(40, 245, 200, 36);

        dialogo.add(btnContinuar);
        dialogo.add(btnReiniciar);
        dialogo.add(btnObjetivos);
        dialogo.add(btnInstrucciones);
        dialogo.add(btnMenu);

        btnContinuar.addActionListener(e -> dialogo.dispose());
        btnReiniciar.addActionListener(e -> {
            dialogo.dispose();
            reiniciarJuego();
        });
        btnObjetivos.addActionListener(e -> verObjetivos(dialogo));
        btnInstrucciones.addActionListener(e -> verInstruccionesConPenalizacion(dialogo));
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
        audio.reproducir("/resources/Audio/Intro 1.wav", true);

        contenedor.remove(panelJuegoCompleto);
        controlador = null;
        panelJuego = null;

        cardLayout.show(contenedor, "MENU");
    }

    private void verInstruccionesConPenalizacion(JDialog dialogo) {
        int penalizacion = 5;
        int actuales = controlador.getJuego().getJugador().getMovimientosRestantes();
        int nuevos = actuales - penalizacion;
        if (nuevos < 0) nuevos = 0;
        controlador.getJuego().getJugador().setMovimientosRestantes(nuevos);
        panelJuego.actualizar();
        JOptionPane.showMessageDialog(dialogo,
                "OBJETIVO\nEmpuja el paquete a todos los puertos en orden\nantes de quedarte sin movimientos.\n\n" +
                        "CONTROLES\n  W / ↑  Arriba\n  S / ↓  Abajo\n  A / ←  Izquierda\n  D / →  Derecha\n" +
                        "  E       Modo Sigilo\n  M       Pausa\n\n" +
                        "AMENAZAS\n  Antivirus  — te elimina al tocarte\n  Escáner    — reduce tus movimientos\n  Firewall   — bloquea el paso\n\n" +
                        "⚠ Penalización aplicada: -" + penalizacion + " movimientos",
                "INSTRUCCIONES", JOptionPane.PLAIN_MESSAGE);
    }

    private void verObjetivos(JDialog dialogo) {
        int visitados = controlador.getJuego().getPaquete().getPuertosVisitados();
        int total = controlador.getJuego().getPuertos().length;
        String orden = "";
        if (controlador.getJuego().isOrdenInverso()) {
            for (int i = total; i >= 1; i--) {
                orden += "P" + i;
                if (i > 1) orden += " → ";
            }
        } else {
            for (int i = 1; i <= total; i++) {
                orden += "P" + i;
                if (i < total) orden += " → ";
            }
        }
        JOptionPane.showMessageDialog(dialogo,
                "Orden: " + orden + "\nProgreso: " + visitados + " / " + total + " puertos visitados",
                "OBJETIVOS", JOptionPane.PLAIN_MESSAGE);
    }

}
