package co.edu.unbosque.view;

import java.awt.*;
import javax.swing.*;

public class PanelMenu extends JPanel {

    private JButton btnNuevaPartida, btnSalir;
    private JButton btnBaja, btnMedia, btnAlta;
    private String dificultad = "MEDIA";
    private JButton btnOrden;
    private boolean ordenInverso = false;

    public PanelMenu() {
        setBackground(new Color(10, 10, 10));
        setLayout(null);

        btnBaja = crearBoton("BAJA", 430, 330, 100);
        btnMedia = crearBoton("MEDIA", 545, 330, 100);
        btnAlta = crearBoton("ALTA", 660, 330, 100);
        btnOrden = crearBoton("OREDEN: NORMAL", 430, 380, 330);

        btnNuevaPartida = crearBoton("NUEVA PARTIDA", 470, 400, 220);
        btnSalir = crearBoton("SALIR", 470, 455, 220);

        add(btnOrden);
        add(btnBaja);
        add(btnMedia);
        add(btnAlta);
        add(btnNuevaPartida);
        add(btnSalir);

        marcarDificultad("MEDIA");

        btnOrden.addActionListener(e -> {
            add(btnOrden);
            ordenInverso = !ordenInverso;
            btnOrden.setText(ordenInverso ? "ORDEN: INVERSO" : "ORDEN: NORMAL");
            btnOrden.SetForeground(ordenInverso ? Color.YELLOW : Color.WHITE);
        });
        btnBaja.addActionListener(e -> marcarDificultad("BAJA"));
        btnMedia.addActionListener(e -> marcarDificultad("MEDIA"));
        btnAlta.addActionListener(e -> marcarDificultad("ALTA"));
        btnSalir.addActionListener(e -> System.exit(0));
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
}
