package model;
import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.EnumMap;
import java.util.ArrayList;
import java.util.Collections;
public class EnviaGT {
    private Map<String, Envio> envios;
    private Map<String, Vehiculo> vehiculos;
    private List<Despacho> despachos;
    private final Ingresos ingresos;
    private int siguienteNumeroDespacho;
    public EnviaGT() {
        envios = new LinkedHashMap<>(); vehiculos = new LinkedHashMap<>(); despachos = new ArrayList<>();
        ingresos = new Ingresos(); siguienteNumeroDespacho = 1;
    }
    public void registrarEnvio(Envio envio) {
        if (envio == null) throw new IllegalArgumentException("Debe indicar un envío.");
        if (envios.containsKey(envio.getCodigo())) throw new IllegalArgumentException("El código ya existe.");
        if (envio.getEstado() != EstadoEnvio.EN_BODEGA || envio.getIntentos() != 0)
            throw new IllegalStateException("Solo se pueden registrar envíos nuevos.");
        double costo = envio.calcularCosto();
        if (!Double.isFinite(costo) || costo <= 0) throw new IllegalArgumentException("Costo inválido.");
        envios.put(envio.getCodigo(), envio);
        ingresos.registrarPorRegistro(envio.categoria(), costo);
    }
    public void cargarEnvioInicial(Envio envio) {
        if (envio == null) throw new IllegalArgumentException("Debe indicar un envío.");
        if (envios.containsKey(envio.getCodigo())) throw new IllegalArgumentException("El código ya existe.");
        if (envio.getEstado() != EstadoEnvio.EN_BODEGA || envio.getIntentos() != 0)
            throw new IllegalStateException("Los envíos iniciales deben estar en bodega y sin intentos.");
        envios.put(envio.getCodigo(), envio);
    }
    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) throw new IllegalArgumentException("Debe indicar un vehículo.");
        if (vehiculos.containsKey(vehiculo.getPlaca())) throw new IllegalArgumentException("La placa ya existe.");
        if (!vehiculo.estaLibre()) throw new IllegalStateException("El vehículo debe estar libre.");
        vehiculos.put(vehiculo.getPlaca(), vehiculo);
    }
    public Despacho crearDespacho(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        Despacho despacho = new Despacho(siguienteNumeroDespacho, vehiculo);
        despachos.add(despacho); siguienteNumeroDespacho++;
        return despacho;
    }
    public void asignarEnvio(int numeroDespacho, String codigo) { buscarDespacho(numeroDespacho).agregarEnvio(buscarEnvio(codigo)); }
    public void retirarEnvio(int numeroDespacho, String codigo) { buscarDespacho(numeroDespacho).retirarEnvio(buscarEnvio(codigo)); }
    public void salirDespacho(int numeroDespacho) { buscarDespacho(numeroDespacho).salir(ingresos); }
    public void registrarResultado(int numeroDespacho, String codigo, boolean entregado) {
        buscarDespacho(numeroDespacho).registrarResultado(buscarEnvio(codigo), entregado);
    }
    public Envio buscarEnvio(String codigo) {
        Envio envio = envios.get(codigo);
        if (envio == null) throw new IllegalArgumentException("No existe ese envío.");
        return envio;
    }
    public Vehiculo buscarVehiculo(String placa) {
        Vehiculo vehiculo = vehiculos.get(placa);
        if (vehiculo == null) throw new IllegalArgumentException("No existe ese vehículo.");
        return vehiculo;
    }
    public Despacho buscarDespacho(int numero) {
        for (Despacho despacho : despachos) if (despacho.getNumero() == numero) return despacho;
        throw new IllegalArgumentException("No existe ese despacho.");
    }
    public List<Envio> listarEnvios() { return Collections.unmodifiableList(new ArrayList<>(envios.values())); }
    public List<Vehiculo> listarVehiculos() { return Collections.unmodifiableList(new ArrayList<>(vehiculos.values())); }
    public Map<String, Map<EstadoEnvio, Integer>> contarPorCategoriaYEstado() {
        Map<String, Map<EstadoEnvio, Integer>> conteo = new LinkedHashMap<>();
        for (Envio envio : envios.values()) {
            if (!conteo.containsKey(envio.categoria())) {
                Map<EstadoEnvio, Integer> estados = new EnumMap<>(EstadoEnvio.class);
                for (EstadoEnvio estado : EstadoEnvio.values()) estados.put(estado, 0);
                conteo.put(envio.categoria(), estados);
            }
            Map<EstadoEnvio, Integer> estados = conteo.get(envio.categoria());
            estados.put(envio.getEstado(), estados.get(envio.getEstado()) + 1);
        }
        return conteo;
    }
    public List<Despacho> despachosNoCerrados() {
        List<Despacho> abiertos = new ArrayList<>();
        for (Despacho despacho : despachos) if (!despacho.estaCerrado()) abiertos.add(despacho);
        return Collections.unmodifiableList(abiertos);
    }
    public Ingresos getIngresos() { return ingresos; }
}
