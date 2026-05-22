package co.edu.unbosque.model;

import co.edu.unbosque.controller.Controlador;
import co.edu.unbosque.view.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        VentanaPrincipal ventana = new VentanaPrincipal(controlador);
        ventana.setVisible(true);
    }
}
