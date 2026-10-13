package model;
public class Sobre extends Envio {
    private static final double PESO_MAXIMO = 0.5;
    private static final double TARIFA_URBANA = 20, TARIFA_DEPARTAMENTAL = 35;
    public Sobre(String codigo, String remitente, String destinatario, double pesoReal, Destino destino) {
        super(codigo, remitente, destinatario, pesoReal, destino);
        if (pesoReal > PESO_MAXIMO) throw new IllegalArgumentException("El sobre no puede superar 0.5 kg.");
    }
    @Override public double calcularCosto() { return getDestino() == Destino.URBANO ? TARIFA_URBANA : TARIFA_DEPARTAMENTAL; }
    @Override public int tiempoEstimadoDias() { return getDestino() == Destino.URBANO ? 1 : 2; }
    @Override public int maximoIntentos() { return 3; }
    @Override public String categoria() { return "Sobre"; }
}
