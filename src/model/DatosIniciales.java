package model;
public class DatosIniciales {
    public static void cargar(EnviaGT empresa) {
        empresa.cargarEnvioInicial(new Sobre("S001", "Ana", "Luis", 0.3, Destino.URBANO));
        empresa.cargarEnvioInicial(new Sobre("S002", "María", "José", 0.5, Destino.DEPARTAMENTAL));
        empresa.cargarEnvioInicial(new Paquete("P001", "Carlos", "Elena", 3.2, Destino.URBANO, 40, 30, 20));
        empresa.cargarEnvioInicial(new Paquete("P002", "Sara", "Pedro", 5.2, Destino.DEPARTAMENTAL, 10, 10, 10));
        empresa.cargarEnvioInicial(new PaqueteFragil("F001", "Lucía", "Mario", 3.2, Destino.URBANO, 40, 30, 20, 1200));
        empresa.cargarEnvioInicial(new PaqueteFragil("F002", "Pablo", "Sofía", 2.1, Destino.DEPARTAMENTAL, 10, 10, 10, 500));
        empresa.cargarEnvioInicial(new CargaRefrigerada("R001", "Clínica", "Farmacia", 2.5, Destino.URBANO, -25));
        empresa.cargarEnvioInicial(new CargaRefrigerada("R002", "Mercado", "Restaurante", 10, Destino.URBANO, 8));
        empresa.registrarVehiculo(new Vehiculo("V001", 5, false));
        empresa.registrarVehiculo(new Vehiculo("V002", 30, false));
        empresa.registrarVehiculo(new Vehiculo("V003", 50, true));
    }
}
