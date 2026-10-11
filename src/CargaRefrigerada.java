import java.util.Locale;
public class CargaRefrigerada extends Envio {
    private final double temperaturaRequerida;
    private static final double TEMP_MINIMA = -25, TEMP_MAXIMA = 8;
    private static final double TARIFA_POR_KG = 12, CARGO_CADENA_FRIO = 60;
    public CargaRefrigerada(String codigo, String remitente, String destinatario, double pesoReal, Destino destino,
            double temperaturaRequerida) {
        super(codigo, remitente, destinatario, pesoReal, destino);
        if (destino != Destino.URBANO) throw new IllegalArgumentException("La carga refrigerada solo admite destino urbano.");
        if (!Double.isFinite(temperaturaRequerida) || temperaturaRequerida < TEMP_MINIMA || temperaturaRequerida > TEMP_MAXIMA)
            throw new IllegalArgumentException("La temperatura debe estar entre -25 y 8 °C.");
        this.temperaturaRequerida = temperaturaRequerida;
        if (!Double.isFinite(calcularCosto())) throw new IllegalArgumentException("El peso excede el rango calculable.");
    }
    @Override public double calcularCosto() { return tarifaBase() + TARIFA_POR_KG * getPesoReal() + CARGO_CADENA_FRIO; }
    @Override public int tiempoEstimadoDias() { return 1; }
    @Override public int maximoIntentos() { return 1; }
    @Override public String categoria() { return "Carga refrigerada"; }
    @Override public boolean esCompatibleCon(Vehiculo vehiculo) { return vehiculo.esRefrigerado(); }
    public double getTemperaturaRequerida() { return temperaturaRequerida; }
    @Override public String toString() { return super.toString() + String.format(Locale.US, " | Temperatura: %.2f °C", temperaturaRequerida); }
}
