import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
public class Despacho {
    private final int numero;
    private final Vehiculo vehiculo;
    private List<Envio> envios;
    private List<Envio> sinResultado;
    private EstadoDespacho estado;
    public Despacho(int numero, Vehiculo vehiculo) {
        if (numero <= 0 || vehiculo == null) throw new IllegalArgumentException("Número o vehículo inválido.");
        this.numero = numero; this.vehiculo = vehiculo;
        envios = new ArrayList<>(); sinResultado = new ArrayList<>();
        estado = EstadoDespacho.EN_PREPARACION;
        vehiculo.ocupar();
    }
    public void agregarEnvio(Envio envio) {
        if (estado != EstadoDespacho.EN_PREPARACION) throw new IllegalStateException("El despacho no está en preparación.");
        if (envio == null) throw new IllegalArgumentException("Debe indicar un envío.");
        if (envio.getEstado() != EstadoEnvio.EN_BODEGA) throw new IllegalStateException("El envío no está en bodega.");
        if (!envio.esCompatibleCon(vehiculo)) throw new IllegalArgumentException("El envío requiere un vehículo refrigerado.");
        if (pesoCargado() + envio.getPesoReal() > vehiculo.getCapacidadMaxima())
            throw new IllegalArgumentException("Se excede la capacidad del vehículo.");
        envio.marcarAsignado();
        envios.add(envio);
    }
    public void retirarEnvio(Envio envio) {
        if (estado != EstadoDespacho.EN_PREPARACION) throw new IllegalStateException("El despacho no está en preparación.");
        if (!envios.contains(envio)) throw new IllegalArgumentException("El envío no pertenece al despacho.");
        envio.liberar(); envios.remove(envio);
    }
    public void salir(Ingresos ingresos) {
        if (estado != EstadoDespacho.EN_PREPARACION) throw new IllegalStateException("El despacho no está en preparación.");
        if (envios.isEmpty()) throw new IllegalStateException("Un despacho vacío no puede salir.");
        if (ingresos == null) throw new IllegalArgumentException("Falta el registro de ingresos.");
        for (Envio envio : envios) {
            if (envio.getEstado() != EstadoEnvio.ASIGNADO) throw new IllegalStateException("Hay un envío que no está asignado.");
        }
        for (Envio envio : envios) {
            double cargo = envio.registrarSalida();
            if (cargo > 0) ingresos.registrarPorReintento(envio.categoria(), cargo);
        }
        sinResultado.addAll(envios);
        estado = EstadoDespacho.EN_RUTA;
    }
    public void registrarResultado(Envio envio, boolean entregado) {
        if (estado != EstadoDespacho.EN_RUTA) throw new IllegalStateException("El despacho no está en ruta.");
        if (!sinResultado.contains(envio)) throw new IllegalArgumentException("El envío no pertenece al despacho o ya tiene resultado.");
        envio.registrarResultado(entregado);
        sinResultado.remove(envio);
        if (sinResultado.isEmpty()) cerrar();
    }
    private void cerrar() { estado = EstadoDespacho.CERRADO; vehiculo.liberar(); }
    public double pesoCargado() {
        double peso = 0;
        for (Envio envio : envios) peso += envio.getPesoReal();
        return peso;
    }
    public double capacidadDisponible() { return vehiculo.getCapacidadMaxima() - pesoCargado(); }
    public boolean estaCerrado() { return estado == EstadoDespacho.CERRADO; }
    public int getNumero() { return numero; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public EstadoDespacho getEstado() { return estado; }
    public List<Envio> getEnvios() { return Collections.unmodifiableList(new ArrayList<>(envios)); }
    @Override public String toString() {
        return String.format(Locale.US, "Despacho %d | Vehículo: %s | %s | Cargado: %.2f kg | Disponible: %.2f kg",
                numero, vehiculo.getPlaca(), estado, pesoCargado(), capacidadDisponible());
    }
}
