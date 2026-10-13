package main;

import model.EnviaGT;
import view.ConsolaView;
import controller.EnviaGTController;
import model.DatosIniciales;

public class Main {
    public static void main(String[] args) {
        EnviaGT empresa = new EnviaGT();
        DatosIniciales.cargar(empresa);
        ConsolaView vista = new ConsolaView();
        EnviaGTController controlador = new EnviaGTController(empresa, vista);
        controlador.iniciar();
    }
}
