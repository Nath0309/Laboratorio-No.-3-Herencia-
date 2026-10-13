package model;
import java.util.Locale;
public class Paquete extends Envio {
    private final double largo, ancho, alto;
    private static final double TARIFA_POR_KG = 8, DIVISOR_VOLUMETRICO = 5000;
    public Paquete(String codigo, String remitente, String destinatario, double pesoReal, Destino destino,
            double largo, double ancho, double alto) {
        super(codigo, remitente, destinatario, pesoReal, destino);
        if (!Double.isFinite(largo) || !Double.isFinite(ancho) || !Double.isFinite(alto)
                || largo <= 0 || ancho <= 0 || alto <= 0)
            throw new IllegalArgumentException("Las medidas deben ser números finitos mayores que cero.");
        this.largo = largo; this.ancho = ancho; this.alto = alto;
        if (!Double.isFinite(tarifaBase() + TARIFA_POR_KG * Math.ceil(pesoFacturable())))
            throw new IllegalArgumentException("Las medidas o el peso exceden el rango calculable.");
    }
    public double pesoVolumetrico() { return largo * ancho * alto / DIVISOR_VOLUMETRICO; }
    public double pesoFacturable() { return Math.max(getPesoReal(), pesoVolumetrico()); }
    @Override public double calcularCosto() { return tarifaBase() + TARIFA_POR_KG * Math.ceil(pesoFacturable()); }
    @Override public int tiempoEstimadoDias() { return getDestino() == Destino.URBANO ? 2 : 4; }
    @Override public int maximoIntentos() { return 3; }
    @Override public String categoria() { return "Paquete"; }
    public double getLargo() { return largo; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
    @Override public String toString() {
        return super.toString() + String.format(Locale.US, " | %.2f x %.2f x %.2f cm | Volumétrico: %.2f kg | Facturable: %.2f kg",
                largo, ancho, alto, pesoVolumetrico(), pesoFacturable());
    }
}
