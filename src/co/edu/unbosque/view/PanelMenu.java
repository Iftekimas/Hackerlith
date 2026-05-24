package co.edu.unbosque.view;

import java.awt.*;
import javax.swing.*;

/**
 * Panel del menú principal. Permite configurar dificultad, tamaño y skin antes de jugar.
 */
public class PanelMenu extends JPanel {

    private JButton btnNuevaPartida, btnSalir;
    private JButton btnBaja, btnMedia, btnAlta;
    private String dificultad = "MEDIA";
    private JButton btnOrden;
    private boolean ordenInverso = false;
    private JButton btnConfiguracion, btnInstrucciones;
    private JButton btnSkin;
    private String skin = "MARCIANITO";
    private JButton btnFilas, btnColumnas, btnPuertos;
    private int filas = 9;
    private int columnas = 14;
    private int numPuertos = 3;

    public PanelMenu() {
        setBackground(new Color(10, 10, 10));
        setLayout(null);

        // crear botones

        btnBaja = crearBoton("BAJA", 430, 330, 100);
        btnMedia = crearBoton("MEDIA", 545, 330, 100);
        btnAlta = crearBoton("ALTA", 660, 330, 100);
        btnOrden = crearBoton("ORDEN: NORMAL", 430, 380, 330);
        btnSkin = crearBoton("SKIN: MARCIANITO", 430, 423, 330);
        btnFilas = crearBoton("FILAS: 9", 430, 466, 95);
        btnColumnas = crearBoton("COLS: 14", 530, 466, 100);
        btnPuertos = crearBoton("PUERTOS: 3", 635, 466, 125);
        btnNuevaPartida = crearBoton("NUEVA PARTIDA", 470, 509, 220);
        btnConfiguracion = crearBoton("CONFIGURACIÓN", 470, 552, 220);
        btnInstrucciones = crearBoton("INSTRUCCIONES", 470, 595, 220);
        btnSalir = crearBoton("SALIR", 470, 638, 220);

        // añadir botones

        add(btnOrden);
        add(btnBaja);
        add(btnMedia);
        add(btnAlta);
        add(btnNuevaPartida);
        add(btnConfiguracion);
        add(btnInstrucciones);
        add(btnSalir);
        add(btnSkin);
        add(btnFilas);
        add(btnColumnas);
        add(btnPuertos);

        marcarDificultad("MEDIA");

        btnOrden.addActionListener(e -> {
            ordenInverso = !ordenInverso;
            btnOrden.setText(ordenInverso ? "ORDEN: INVERSO" : "ORDEN: NORMAL");
            btnOrden.setForeground(ordenInverso ? Color.YELLOW : Color.WHITE);
        });

        btnConfiguracion.addActionListener(e -> mostrarConfiguracion());
        btnInstrucciones.addActionListener(e -> mostrarInstrucciones());
        btnSkin.addActionListener(e -> {
            skin = skin.equals("MARCIANITO") ? "TITA" : "MARCIANITO";
            btnSkin.setText("SKIN: " + skin);
        });

        btnBaja.addActionListener(e -> marcarDificultad("BAJA"));
        btnMedia.addActionListener(e -> marcarDificultad("MEDIA"));
        btnAlta.addActionListener(e -> marcarDificultad("ALTA"));
        btnSalir.addActionListener(e -> System.exit(0));

        btnFilas.addActionListener(e -> {
            filas = (filas >= 20) ? 5 : filas + 1;
            btnFilas.setText("FILAS: " + filas);
        });
        btnColumnas.addActionListener(e -> {
            columnas = (columnas >= 20) ? 5 : columnas + 1;
            btnColumnas.setText("COLS: " + columnas);
        });
        btnPuertos.addActionListener(e -> {
            numPuertos = (numPuertos >= 5) ? 2 : numPuertos + 1;
            btnPuertos.setText("PUERTOS: " + numPuertos);
        });
    }

    private void marcarDificultad(String d) {
        dificultad = d;
        btnBaja.setForeground(d.equals("BAJA") ? Color.YELLOW : Color.WHITE);
        btnMedia.setForeground(d.equals("MEDIA") ? Color.YELLOW : Color.WHITE);
        btnAlta.setForeground(d.equals("ALTA") ? Color.YELLOW : Color.WHITE);
    }

    private JButton crearBoton(String texto, int x, int y, int w) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, w, 38);
        btn.setBackground(new Color(25, 25, 25));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        g.drawString("HACKERLITH", 390, 180);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.setColor(new Color(150, 150, 150));
        g.drawString("INFILTRA. CONECTA. DOMINA.", 400, 215);
        g.setColor(new Color(80, 80, 80));
        g.drawLine(350, 290, 820, 290);
        g.setColor(new Color(180, 180, 180));
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.drawString("DIFICULTAD:", 340, 352);
        g.drawString("MATRIZ:", 340, 485);
    }

    public String getDificultad() {
        return dificultad;
    }

    public JButton getBtnNuevaPartida() {
        return btnNuevaPartida;
    }

    public boolean isOrdenInverso() {
        return ordenInverso;
    }

    private void mostrarConfiguracion() {
        JOptionPane.showMessageDialog(this,
                "Dificultad: " + dificultad + "\nOrden: " + (ordenInverso ? "INVERSO" : "NORMAL")
                        + "\nMatriz: " + filas + " filas × " + columnas + " columnas"
                        + "\nPuertos: " + numPuertos,
                "CONFIGURACIÓN", JOptionPane.PLAIN_MESSAGE);
    }

    private void mostrarInstrucciones() {
        JOptionPane.showMessageDialog(this,
                "OBJETIVO\nEmpuja el paquete a todos los puertos en orden\nantes de quedarte sin movimientos.\n\n" +
                        "CONTROLES\n  W / ↑  Arriba\n  S / ↓  Abajo\n  A / ←  Izquierda\n  D / →  Derecha\n" +
                        "  E       Modo Sigilo\n  M       Pausa\n\n" +
                        "AMENAZAS\n  Antivirus  — te elimina al tocarte\n  Escáner    — reduce tus movimientos\n  Firewall   — bloquea el paso",
                "INSTRUCCIONES", JOptionPane.PLAIN_MESSAGE);
    }

    public String getSkin() {
        return skin;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getNumPuertos() {
        return numPuertos;
    }

}
