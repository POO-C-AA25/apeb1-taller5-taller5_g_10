import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Problema4_EjecutorAppFiscalía {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String casosCorrupcion[] = {"Caso Odebrecht", "Caso Sobornos 2012-2016",
            "Caso Petroecuador", "Caso Singue", "Caso Isspol", "Caso Encuentro"};
        String nombres[] = {"Valeria", "Andrés", "Diego", "Mariana", "José", "Lucía"};
        String apellidos[] = {"Cueva", "González", "Maldonado", "Espinosa", "Zambrano", "Villacís"};
        String ocupaciones[] = {"Ministro de Salud", "Viceministro de Economía",
            "Asambleísta Nacional", "Gobernador Provincial", "Secretario de Educación", "Director de Obras Públicas"};

        ArrayList<CasoCorrupcion> listaCasos = new ArrayList<>();

        String agregarCaso = "S";
        while (agregarCaso.equalsIgnoreCase("S")) {            
            // Mostrar casos antes de agregar uno nuevo            
            String nombreCaso = casosCorrupcion[(int) (Math.random() * casosCorrupcion.length)];

            LocalDate fechaLocal = LocalDate.now().minusDays((int) (Math.random() * 20));
            Date fechaInicio = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

            double dano = 100000 + (Math.random() * 500000);
            CasoCorrupcion caso = new CasoCorrupcion(nombreCaso, fechaInicio, "Iniciado", dano);

            for (int i = 0; i < 2; i++) {
                String nombre = nombres[(int) (Math.random() * nombres.length)] + " " +
                                apellidos[(int) (Math.random() * apellidos.length)];
                String ocupacion = ocupaciones[(int) (Math.random() * ocupaciones.length)];
                String[] roles = {"Acusado", "Testigo", "Víctima"};
                String rol = roles[(int) (Math.random() * roles.length)];
                int sentencia = (int)(Math.random() * 3);
                boolean colabora = Math.random() > 0.5;
                caso.agregarPersona(new Persona(nombre, 30 + (int)(Math.random() * 20), ocupacion, rol, sentencia, colabora));
            }

            caso.actualizarEstado();
            listaCasos.add(caso);
            
            if (!listaCasos.isEmpty()) {
                System.out.println("\n=== Caso registrado===");
                System.out.println(caso);
                for (Persona p : caso.implicados) {
                    if (p != null && p.nivelImplicacion.equalsIgnoreCase("Acusado")) {
                        System.out.println(p.nombre);
                        System.out.println("- ¿Reducción de pena?: " + p.reduccionDePena());
                        System.out.println("- ¿Puede pagar fianza?: " + p.pagarFianza(caso.danoEconomico) +
                                " - Fianza total: " + p.fianza);
                    }
                }
                System.out.println("====================================\n");
            }

            System.out.print("¿Desea agregar otro caso? (S/N): ");
            agregarCaso = scanner.nextLine();
        }

        // Mostrar todos los casos registrados
        for (CasoCorrupcion c : listaCasos) {
            System.out.println("\n+--------------------------------------------------------+");
            System.out.println(c);
            for (Persona p : c.implicados) {
                if (p != null && p.nivelImplicacion.equalsIgnoreCase("Acusado")) {
                    System.out.println(p.nombre);
                    System.out.println("- ¿Reducción de pena?: " + p.reduccionDePena());
                    System.out.println("- ¿Puede pagar fianza?: " + p.pagarFianza(c.danoEconomico) +
                                       " - Fianza total: " + p.fianza);
                }                
            }
            System.out.println("+--------------------------------------------------------+\n");
        }
    }
}

class CasoCorrupcion {
    public String nombreCaso;
    public Date fechaInicio;
    public String estado;
    public double danoEconomico;
    public Persona[] implicados = new Persona[2];
    public int cantidadImplicados = 0;

    public CasoCorrupcion() {
        // Constructor Vacío
    }    
    
    public CasoCorrupcion(String nombreCaso, Date fechaInicio, String estado, double danoEconomico) {
        this.nombreCaso = nombreCaso;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.danoEconomico = danoEconomico;

    }

    public void agregarPersona(Persona persona) {
        if (cantidadImplicados < implicados.length) {
            implicados[cantidadImplicados++] = persona;
        } else {
            System.out.println("No se pueden agregar más personas al caso: " + nombreCaso);
        }
    }

    public void actualizarEstado() {
        long diasTranscurridos = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - fechaInicio.getTime());
        if (diasTranscurridos > 14) {
            estado = "Urgente";
        } else if (diasTranscurridos > 7) {
            estado = "Alerta";
        }
    }

    @Override
    public String toString() {
        String implicadosStr = "";
        for (int i = 0; i < cantidadImplicados; i++) {
            if (implicados[i] != null) {
                // Indenta cada línea de la representación de Persona
                implicadosStr += "    - " + implicados[i].toString().replace("\n", "\n      ") + "\n";
            }
        }

        return String.format(
            "CasoCorrupcion:\n" +
            "  Nombre del Caso: %s\n" +
            "  Fecha de Inicio: %s\n" +
            "  Estado: %s\n" +
            "  Daño Económico: $%.2f\n" +
            "  Cantidad de Implicados: %d\n" +
            "  Implicados:\n%s",
            nombreCaso,
            fechaInicio,
            estado,
            danoEconomico,
            cantidadImplicados,
            implicadosStr
        );
    }
}

class Persona {
    public String nombre;
    public int edad;
    public String ocupacion;
    public String nivelImplicacion;
    public int sentencia;
    public boolean colabora;
    public double fianza;

    public Persona() {
        // Constructor vacío
    }    

    public Persona(String nombre, int edad, String ocupacion, String nivelImplicacion, int sentencia, boolean colabora) {
        this.nombre = nombre;
        this.edad = edad;
        this.ocupacion = ocupacion;
        this.nivelImplicacion = nivelImplicacion;
        this.sentencia = sentencia;
        this.colabora = colabora;
    }

    public boolean reduccionDePena() {
        return "Acusado".equalsIgnoreCase(nivelImplicacion) && colabora;
    }

    public boolean pagarFianza(double danoEconomico) {
        boolean puedePagar = "Acusado".equalsIgnoreCase(nivelImplicacion)
                && sentencia < 1
                && colabora
                && (danoEconomico * 0.5 > 0);

        if (puedePagar) {
            this.fianza = danoEconomico * 0.5;
        } else {
            this.fianza = 0; 
        }

        return puedePagar;
    }

    @Override
    public String toString() {
        String text = "";
        text += "Persona:\n";
        text += "  Nombre: " + nombre + "\n";
        text += "  Edad: " + edad + "\n";
        text += "  Ocupación: " + ocupacion + "\n";
        text += "  Nivel de Implicación: " + nivelImplicacion + "\n";

        if (!"Víctima".equals(nivelImplicacion)) {
            text += "  Sentencia: " + sentencia + " años\n";
            text += "  Colabora: " + (colabora ? "Sí" : "No") + "\n";
        }

        return text;
    }
}