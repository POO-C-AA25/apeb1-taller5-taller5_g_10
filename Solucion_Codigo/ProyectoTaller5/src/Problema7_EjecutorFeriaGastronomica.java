import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Problema7_EjecutorFeriaGastronomica {
    public static void main(String[] args) {
        Random rand = new Random();   // Solo se usa para true/false aleatorios en la disponibilidad de un plato
        ArrayList<Plato> platos1 = new ArrayList<>(Arrays.asList(
            new Plato("Encebollado", new String[]{"Pescado", "Yuca", "Cebolla"}, 3.5, rand.nextBoolean(), 50),
            new Plato("Ceviche", new String[]{"Camarón", "Limón", "Tomate"}, 4.0, rand.nextBoolean(), 30)
        ));

        ArrayList<Plato> platos2 = new ArrayList<>(Arrays.asList(
            new Plato("Tacos", new String[]{"Carne", "Tortilla", "Salsa"}, 2.5, true, 60),
            new Plato("Quesadillas", new String[]{"Queso", "Tortilla", "Verduras"}, 3.0, true, 40)
        ));

        ArrayList<Plato> platos3 = new ArrayList<>(Arrays.asList(
            new Plato("Sushi", new String[]{"Arroz", "Pescado", "Alga"}, 5.0, true, 60),
            new Plato("Ramen", new String[]{"Fideos", "Caldo", "Huevo"}, 4.5, false, 35)
        ));

        // Crear expositores
        ArrayList<Expositor> expositores = new ArrayList<>(Arrays.asList(
            new Expositor("Ecuador", platos1),
            new Expositor("México", platos2),
            new Expositor("Japón", platos3)
        ));
        System.out.println("FERIA GASTRONÓMICA DE LOJA");
        System.out.println("===========================\n");

        mostrarDetallesExpositores(expositores);
        calculoEstadisticas(expositores);
    }

    public static void mostrarDetallesExpositores(ArrayList<Expositor> expositores) {
        System.out.println("=== Detalles de Expositores y Platos ===");
        for (Expositor expositor : expositores) {
            System.out.println(expositor);
        }
    }

    public static void calculoEstadisticas(ArrayList<Expositor> expositores) {
        System.out.println("=== Estadísticas Feria Gastronómica ===");

        ArrayList<Plato> platosMasVendidos = new ArrayList<>();
        Expositor expositorConMasIngresos = null;
        double mayorIngreso = 0;
        int maxCantidadVendida = 0;

        for (Expositor expositor : expositores) {
            double ingresos = expositor.calcularIngresos();
            System.out.println("Stand: " + expositor.nombre + " - Ingresos: $" + ingresos);

            if (ingresos > mayorIngreso) {
                mayorIngreso = ingresos;
                expositorConMasIngresos = expositor;
            }

            for (Plato plato : expositor.platos) {
                if (plato.cantidadVendida > maxCantidadVendida) {
                    maxCantidadVendida = plato.cantidadVendida;
                    platosMasVendidos.clear();
                    platosMasVendidos.add(plato);
                } else if (plato.cantidadVendida == maxCantidadVendida) {
                    platosMasVendidos.add(plato);
                }
            }
        }

        System.out.println("\nPlato(s) más vendido(s) (" + maxCantidadVendida + " unidades):");
        for (Plato plato : platosMasVendidos) {
            System.out.println("- " + plato.nombre + " (Stand: " + obtenerNombreExpositor(plato, expositores) + ")");
        }
        System.out.println("\nStand con mayor movimiento: " + expositorConMasIngresos.nombre + " ($" + mayorIngreso + ")");
    }

    public static String obtenerNombreExpositor(Plato plato, ArrayList<Expositor> expositores) {
        for (Expositor expositor : expositores) {
            if (expositor.platos.contains(plato)) {
                return expositor.nombre;
            }
        }
        return "Desconocido";
    }
}

class Expositor {
    public String nombre;
    public ArrayList<Plato> platos;

    public Expositor() {
        // Constructor vacío
    }   

    public Expositor(String nombre, ArrayList<Plato> platos) {
        this.nombre = nombre;
        this.platos = platos;
    }

    public double calcularIngresos() {
        double total = 0;
        for (Plato plato : platos) {
            if (plato.disponible) {
                total += plato.precio * plato.cantidadVendida;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stand: ").append(nombre).append("\n");
        for (Plato plato : platos) {
            sb.append(plato).append("\n");
        }
        return sb.toString();
    }
}

class Plato {
    public String nombre;
    public String[] ingredientes;
    public double precio;
    public boolean disponible;
    public int cantidadVendida;
    
    public Plato() {
        // Constructor vacío
    }

    public Plato(String nombre, String[] ingredientes, double precio, boolean disponible, int cantidadVendida) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.disponible = disponible;
        this.cantidadVendida = cantidadVendida;
    }

    @Override
    public String toString() {
        return "  Plato: " + nombre +
               "\n    Ingredientes: " + String.join(", ", ingredientes) +
               "\n    Precio: $" + precio +
               "\n    Disponible: " + (disponible ? "Sí" : "No") +
               "\n    Cantidad Vendida: " + cantidadVendida;
    }
}
