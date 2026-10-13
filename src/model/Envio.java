package model;
import java.util.Locale;

public abstract class Envio {
    private static final double CARGO_REINTENTO = 15;
    private static final double TARIFA_BASE_URBANA = 25, TARIFA_BASE_DEPARTAMENTAL = 40;
    private final String codigo, remitente, destinatario;
    private final double pesoReal;
    private final Destino destino;
    private EstadoEnvio estado;
    private int intentos;

    protected Envio(String codigo, String remitente, String destinatario, double pesoReal, Destino destino) {
        if (codigo == null || codigo.trim().isEmpty() || remitente == null || remitente.trim().isEmpty()
                || destinatario == null || destinatario.trim().isEmpty())
            throw new IllegalArgumentException("Código, remitente y destinatario no pueden estar vacíos.");
        if (!Double.isFinite(pesoReal) || pesoReal <= 0)
            throw new IllegalArgumentException("El peso debe ser un número finito mayor que cero.");
        if (destino == null) throw new IllegalArgumentException("Debe indicar un destino.");
        this.codigo = codigo.trim();
        this.remitente = remitente.trim();
        this.destinatario = destinatario.trim();
        this.pesoReal = pesoReal;
        this.destino = destino;
        estado = EstadoEnvio.EN_BODEGA;
        intentos = 0;
    }
    public abstract double calcularCosto();
    public abstract int tiempoEstimadoDias();
    public abstract int maximoIntentos();
    public abstract String categoria();
    public boolean esCompatibleCon(Vehiculo vehiculo) { return true; }
    protected double tarifaBase() { return destino == Destino.URBANO ? TARIFA_BASE_URBANA : TARIFA_BASE_DEPARTAMENTAL; }
    protected double cargoPorReintento() { return CARGO_REINTENTO; }
    public void marcarAsignado() {
        if (estado != EstadoEnvio.EN_BODEGA) throw new IllegalStateException("El envío no está en bodega.");
        estado = EstadoEnvio.ASIGNADO;
    }
    public void liberar() {
        if (estado != EstadoEnvio.ASIGNADO) throw new IllegalStateException("El envío no está asignado.");
        estado = EstadoEnvio.EN_BODEGA;
    }
    public double registrarSalida() {
        if (estado != EstadoEnvio.ASIGNADO) throw new IllegalStateException("El envío no está asignado.");
        estado = EstadoEnvio.EN_RUTA;
        intentos++;
        return intentos == 1 ? 0 : cargoPorReintento();
    }
    public void registrarResultado(boolean entregado) {
        if (estado != EstadoEnvio.EN_RUTA) throw new IllegalStateException("El envío no está en ruta.");
        estado = entregado ? EstadoEnvio.ENTREGADO
                : intentos >= maximoIntentos() ? EstadoEnvio.DEVUELTO_AL_REMITENTE : EstadoEnvio.EN_BODEGA;
    }
    public String getCodigo() { return codigo; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public double getPesoReal() { return pesoReal; }
    public Destino getDestino() { return destino; }
    public EstadoEnvio getEstado() { return estado; }
    public int getIntentos() { return intentos; }
    @Override public String toString() {
        return String.format(Locale.US, "%s | %s | %s -> %s | %.2f kg | %s | %s | Q%.2f | %d días hábiles | Intentos: %d/%d",
                codigo, categoria(), remitente, destinatario, pesoReal, destino, estado,
                calcularCosto(), tiempoEstimadoDias(), intentos, maximoIntentos());
    }
}
