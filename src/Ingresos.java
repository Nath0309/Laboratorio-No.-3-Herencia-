import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;
public class Ingresos {
    private double totalPorRegistro, totalPorReintentos;
    private Map<String, Double> porCategoria;
    public Ingresos() {
        totalPorRegistro = 0; totalPorReintentos = 0;
        porCategoria = new LinkedHashMap<>();
    }
    void registrarPorRegistro(String categoria, double monto) {
        if (!Double.isFinite(monto) || monto <= 0) throw new IllegalArgumentException("El ingreso debe ser positivo y finito.");
        totalPorRegistro += monto;
        porCategoria.put(categoria, porCategoria.getOrDefault(categoria, 0.0) + monto);
    }
    void registrarPorReintento(String categoria, double monto) {
        if (!Double.isFinite(monto) || monto <= 0) throw new IllegalArgumentException("El ingreso debe ser positivo y finito.");
        totalPorReintentos += monto;
        porCategoria.put(categoria, porCategoria.getOrDefault(categoria, 0.0) + monto);
    }
    public double getTotalPorRegistro() { return totalPorRegistro; }
    public double getTotalPorReintentos() { return totalPorReintentos; }
    public double getTotal() { return totalPorRegistro + totalPorReintentos; }
    public Map<String, Double> getPorCategoria() { return Collections.unmodifiableMap(new LinkedHashMap<>(porCategoria)); }
}
