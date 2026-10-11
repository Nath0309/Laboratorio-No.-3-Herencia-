public class EnviaGTController {
    private final EnviaGT empresa;
    private final ConsolaView vista;
    public EnviaGTController(EnviaGT empresa, ConsolaView vista) { this.empresa = empresa; this.vista = vista; }
    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerEntero("Opción:");
            procesarOpcion(opcion);
        } while (opcion != 0);
    }
    private void procesarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 0: vista.mostrarMensaje("Hasta pronto."); break;
                case 1: cotizarYRegistrarEnvio(); break;
                case 2: registrarVehiculo(); break;
                case 3: crearDespacho(); break;
                case 4: asignarEnvio(); break;
                case 5: retirarEnvio(); break;
                case 6: salirDespacho(); break;
                case 7: registrarResultados(); break;
                case 8: consultarEnvios(); break;
                case 9: consultarVehiculos(); break;
                case 10: reporteConteo(); break;
                case 11: reporteIngresos(); break;
                case 12: reporteDespachosNoCerrados(); break;
                default: vista.mostrarError("Opción fuera del menú.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) { vista.mostrarError(e.getMessage()); }
    }
    private void cotizarYRegistrarEnvio() {
        Envio envio = leerEnvio();
        vista.mostrarCotizacion(envio);
        if (vista.leerSiNo("¿Acepta la cotización y desea registrar el envío?")) {
            empresa.registrarEnvio(envio);
            vista.mostrarMensaje("Envío registrado y cobrado.");
        } else vista.mostrarMensaje("Cotización finalizada sin registro ni cobro.");
    }
    private Envio leerEnvio() {
        vista.mostrarMensaje("1. Sobre | 2. Paquete | 3. Paquete frágil | 4. Carga refrigerada");
        int categoria = vista.leerEntero("Categoría:");
        if (categoria < 1 || categoria > 4) throw new IllegalArgumentException("Categoría inválida.");
        String codigo = vista.leerTexto("Código:");
        String remitente = vista.leerTexto("Remitente:");
        String destinatario = vista.leerTexto("Destinatario:");
        double peso = vista.leerDecimal("Peso real (kg):");
        int opcionDestino = vista.leerEntero("Destino (1 urbano, 2 departamental):");
        if (opcionDestino != 1 && opcionDestino != 2) throw new IllegalArgumentException("Destino inválido.");
        Destino destino = opcionDestino == 1 ? Destino.URBANO : Destino.DEPARTAMENTAL;
        // La selección por categoría solo construye el objeto; las reglas son polimórficas.
        switch (categoria) {
            case 1: return new Sobre(codigo, remitente, destinatario, peso, destino);
            case 2:
            case 3:
                double largo = vista.leerDecimal("Largo (cm):");
                double ancho = vista.leerDecimal("Ancho (cm):");
                double alto = vista.leerDecimal("Alto (cm):");
                if (categoria == 2) return new Paquete(codigo, remitente, destinatario, peso, destino, largo, ancho, alto);
                return new PaqueteFragil(codigo, remitente, destinatario, peso, destino, largo, ancho, alto,
                        vista.leerDecimal("Valor declarado (Q):"));
            case 4: return new CargaRefrigerada(codigo, remitente, destinatario, peso, destino,
                    vista.leerDecimal("Temperatura requerida (°C):"));
            default: throw new IllegalArgumentException("Categoría inválida.");
        }
    }
    private void registrarVehiculo() {
        Vehiculo vehiculo = new Vehiculo(vista.leerTexto("Placa:"), vista.leerDecimal("Capacidad máxima (kg):"),
                vista.leerSiNo("¿Tiene refrigeración?"));
        empresa.registrarVehiculo(vehiculo);
        vista.mostrarMensaje("Vehículo registrado.");
    }
    private void crearDespacho() { vista.mostrarDespacho(empresa.crearDespacho(vista.leerTexto("Placa del vehículo:"))); }
    private void asignarEnvio() {
        int numero = vista.leerEntero("Número de despacho:");
        empresa.asignarEnvio(numero, vista.leerTexto("Código del envío:"));
        vista.mostrarDespacho(empresa.buscarDespacho(numero));
    }
    private void retirarEnvio() {
        int numero = vista.leerEntero("Número de despacho:");
        empresa.retirarEnvio(numero, vista.leerTexto("Código del envío:"));
        vista.mostrarDespacho(empresa.buscarDespacho(numero));
    }
    private void salirDespacho() {
        int numero = vista.leerEntero("Número de despacho:");
        empresa.salirDespacho(numero);
        vista.mostrarDespacho(empresa.buscarDespacho(numero));
    }
    private void registrarResultados() {
        int numero = vista.leerEntero("Número de despacho:");
        String codigo = vista.leerTexto("Código del envío:");
        empresa.registrarResultado(numero, codigo, vista.leerSiNo("¿Fue entregado?"));
        vista.mostrarEnvio(empresa.buscarEnvio(codigo));
        vista.mostrarDespacho(empresa.buscarDespacho(numero));
    }
    private void consultarEnvios() { vista.mostrarEnvios(empresa.listarEnvios()); }
    private void consultarVehiculos() { vista.mostrarVehiculos(empresa.listarVehiculos()); }
    private void reporteConteo() { vista.mostrarConteo(empresa.contarPorCategoriaYEstado()); }
    private void reporteIngresos() { vista.mostrarIngresos(empresa.getIngresos()); }
    private void reporteDespachosNoCerrados() { vista.mostrarDespachosNoCerrados(empresa.despachosNoCerrados()); }
}
