import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Locale;
public class ConsolaView {
    private Scanner scanner;
    public ConsolaView() { scanner = new Scanner(System.in); }
    public void mostrarMenu() {
        System.out.println("\nENVÍA GT\n1. Cotizar y registrar envío\n2. Registrar vehículo\n3. Crear despacho"
                + "\n4. Asignar envío\n5. Retirar envío\n6. Hacer salir despacho\n7. Registrar resultado"
                + "\n8. Consultar envíos\n9. Consultar vehículos\n10. Conteo por categoría y estado"
                + "\n11. Reporte de ingresos\n12. Despachos no cerrados\n0. Salir");
    }
    public int leerEntero(String mensaje) {
        while (true) {
            try { return Integer.parseInt(leerTexto(mensaje)); }
            catch (NumberFormatException e) { mostrarError("Ingrese un número entero válido."); }
        }
    }
    public double leerDecimal(String mensaje) {
        while (true) {
            try {
                double numero = Double.parseDouble(leerTexto(mensaje));
                if (!Double.isFinite(numero)) throw new NumberFormatException();
                return numero;
            } catch (NumberFormatException e) { mostrarError("Ingrese un número finito; use punto para los decimales."); }
        }
    }
    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            mostrarError("El texto no puede estar vacío.");
        }
    }
    public boolean leerSiNo(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje + " (s/n):");
            if (respuesta.equalsIgnoreCase("s")) return true;
            if (respuesta.equalsIgnoreCase("n")) return false;
            mostrarError("Responda s o n.");
        }
    }
    public void mostrarCotizacion(Envio envio) {
        System.out.printf(Locale.US, "%s | Costo: Q%.2f | Tiempo estimado: %d días hábiles%n",
                envio.categoria(), envio.calcularCosto(), envio.tiempoEstimadoDias());
    }
    public void mostrarEnvio(Envio envio) { System.out.println(envio); }
    public void mostrarEnvios(List<Envio> envios) {
        if (envios.isEmpty()) mostrarMensaje("No hay envíos.");
        for (Envio envio : envios) mostrarEnvio(envio);
    }
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (vehiculos.isEmpty()) mostrarMensaje("No hay vehículos.");
        for (Vehiculo vehiculo : vehiculos) System.out.println(vehiculo);
    }
    public void mostrarDespacho(Despacho despacho) {
        System.out.println(despacho);
        mostrarEnvios(despacho.getEnvios());
    }
    public void mostrarConteo(Map<String, Map<EstadoEnvio, Integer>> conteo) {
        if (conteo.isEmpty()) mostrarMensaje("No hay envíos.");
        for (Map.Entry<String, Map<EstadoEnvio, Integer>> categoria : conteo.entrySet()) {
            mostrarMensaje(categoria.getKey());
            for (Map.Entry<EstadoEnvio, Integer> estado : categoria.getValue().entrySet())
                mostrarMensaje("  " + estado.getKey() + ": " + estado.getValue());
        }
    }
    public void mostrarIngresos(Ingresos ingresos) {
        System.out.printf(Locale.US, "Registro: Q%.2f%nReintentos: Q%.2f%nTotal: Q%.2f%n",
                ingresos.getTotalPorRegistro(), ingresos.getTotalPorReintentos(), ingresos.getTotal());
        mostrarMensaje("Ingresos por categoría (registro y reintentos):");
        if (ingresos.getPorCategoria().isEmpty()) mostrarMensaje("Todas las categorías: Q0.00");
        for (Map.Entry<String, Double> categoria : ingresos.getPorCategoria().entrySet())
            System.out.printf(Locale.US, "  %s: Q%.2f%n", categoria.getKey(), categoria.getValue());
        mostrarMensaje("Las categorías sin cobros tienen ingreso Q0.00.");
    }
    public void mostrarDespachosNoCerrados(List<Despacho> despachos) {
        if (despachos.isEmpty()) mostrarMensaje("No hay despachos sin cerrar.");
        for (Despacho despacho : despachos) mostrarDespacho(despacho);
    }
    public void mostrarMensaje(String mensaje) { System.out.println(mensaje); }
    public void mostrarError(String mensaje) { System.out.println("Error: " + mensaje); }
}
