package model;
import java.util.Locale;

public class Vehiculo {
    private final String placa;
    private final double capacidadMaxima;
    private final boolean refrigerado;
    private boolean ocupado;

    public Vehiculo(String placa, double capacidadMaxima, boolean refrigerado) {
        if (placa == null || placa.trim().isEmpty()) throw new IllegalArgumentException("La placa no puede estar vacía.");
        if (!Double.isFinite(capacidadMaxima) || capacidadMaxima <= 0)
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero y finita.");
        this.placa = placa.trim(); 
        this.capacidadMaxima = capacidadMaxima; 
        this.refrigerado = refrigerado;
        ocupado = false;
    }

    public String getPlaca() { 
        return placa; 
    }

    public double getCapacidadMaxima() { 
        return capacidadMaxima; 
    }
    public boolean esRefrigerado() { 
        return refrigerado; 
    }

    public boolean estaLibre() { 
        return !ocupado; 
    }

    public void ocupar() {
        if (ocupado) throw new IllegalStateException("El vehículo ya tiene un despacho sin cerrar.");
        ocupado = true;
    }

    public void liberar() { 
        ocupado = false; 
    }
    
    @Override public String toString() {
        return String.format(Locale.US, "%s | Capacidad: %.2f kg | Refrigerado: %s | %s",
                placa, capacidadMaxima, refrigerado ? "sí" : "no", ocupado ? "Ocupado" : "Libre");
    }
}
