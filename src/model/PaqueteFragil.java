package model;
import java.util.Locale;
public class PaqueteFragil extends Paquete {
    private final double valorDeclarado;
    private static final double EMBALAJE = 15, PORCENTAJE_SEGURO = 0.03;
    public PaqueteFragil(String codigo, String remitente, String destinatario, double pesoReal, Destino destino,
            double largo, double ancho, double alto, double valorDeclarado) {
        super(codigo, remitente, destinatario, pesoReal, destino, largo, ancho, alto);
        if (!Double.isFinite(valorDeclarado) || valorDeclarado <= 0)
            throw new IllegalArgumentException("El valor declarado debe ser mayor que cero y finito.");
        this.valorDeclarado = valorDeclarado;
        if (!Double.isFinite(calcularCosto())) throw new IllegalArgumentException("El costo excede el rango calculable.");
    }
    @Override public double calcularCosto() { return super.calcularCosto() + EMBALAJE + PORCENTAJE_SEGURO * valorDeclarado; }
    @Override public int tiempoEstimadoDias() { return super.tiempoEstimadoDias() + 1; }
    @Override public int maximoIntentos() { return 2; }
    @Override public String categoria() { return "Paquete frágil"; }
    public double getValorDeclarado() { return valorDeclarado; }
    @Override public String toString() { return super.toString() + String.format(Locale.US, " | Valor declarado: Q%.2f", valorDeclarado); }
}
