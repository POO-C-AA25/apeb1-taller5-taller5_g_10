    import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Problema3_EjecutorCategorizacionDepartamentos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        String empresas[] = {"TecnoSoft", "GlobalVision", "InnovaTech", "AlphaCore", "NextGen", "CyberWorks"};
        String tiposDepartamentos[] = {"Programación", "Marketing", "Recursos Humanos", "Ventas", "Finanzas", 
            "Diseño", "Logística", "Atención al Cliente", "Investigación y Desarrollo", "Producción"};
        String dirRUCS[] = {"0992845671001", "1724589634002", "1102938475003", 
            "0601827364004", "1809473621005", "0918374652006"};
        String direcciones[] = {"Av. Universitaria y Calle Lourdes",
            "Calle Bolívar entre Azuay y Mercadillo", "Av. Emiliano Ortega y Av. Manuel Agustín Aguirre",
            "Calle Sucre y 10 de Agosto",  "Av. Pío Jaramillo Alvarado y Rocafuerte",
            "Calle Bernardo Valdivieso y José Antonio Eguiguren"};
        String nombEmpresa = empresas[(int) Math.floor(Math.random() * empresas.length)]; 
        String direccion = direcciones[(int) Math.floor(Math.random() * direcciones.length)];
        String RUC = dirRUCS[(int) Math.floor(Math.random() * dirRUCS.length)];
        Empresa empresa = new Empresa(nombEmpresa, RUC, direccion);
        char continuar = 'S';
        while (continuar == 'S') {            
            String departamentos = tiposDepartamentos[random.nextInt(tiposDepartamentos.length)];
            int empleados = 5 + random.nextInt(61); 
            int produccion = (int) (100000 + (random.nextDouble() * 10000000));

            Departamento departamento = new Departamento(departamentos, empleados, produccion);
            departamento.determinarCategoria();
            empresa.agregarDepartamento(departamento);

            System.out.println("Departamento añadido:");
            System.out.println(departamento);
            do {
                System.out.print("\n¿Desea agregar otro departamento? (S/N): ");
                continuar = sc.next().charAt(0);
                if (continuar != 'S' && continuar != 'N') {
                    System.err.println("Error. Debe digitar S para si o N para no.");
                }
            } while(continuar != 'S' && continuar != 'N');            
        }
        System.out.println("\n--- Detalles Empresa ---");
        System.out.println(empresa.toString());
    }
}

class Departamento {
    public String nombre;
    public int numeroEmpleados;
    public int produccionAnual;
    public char categoria;

    public Departamento() {
        // Constructor vacío
    } 

    public Departamento(String nombre, int numeroEmpleados, int produccionAnual) {
        this.nombre = nombre;
        this.numeroEmpleados = numeroEmpleados;
        this.produccionAnual = produccionAnual;

    }

    public char determinarCategoria() {
        if (numeroEmpleados > 20 && produccionAnual > 1000000) {
            categoria = 'A';
            return categoria;
        } else if (numeroEmpleados == 20 && produccionAnual == 1000000) {
            categoria = 'B';
            return categoria;
        } else if (numeroEmpleados == 10 && produccionAnual == 500000) {
            categoria = 'C';
            return categoria;
        } else {
            categoria = 'C';
            return categoria;
        }
    }

    @Override
    public String toString() {
        return "\nDepartamento\n    Nombre = " + nombre + 
                ",\n    Numero de empleados = " + numeroEmpleados + 
                ",\n    ProduccionAnual = " + produccionAnual + 
                ",\n    Categoria = " + categoria;
    }

}

class Empresa {
    public String nombEmpresa;
    public String RUC;
    public String direccion;
    public ArrayList<Departamento> departamentos;

    public Empresa() {
        // Constructor
    }    

    public Empresa(String nombEmpresa, String RUC, String direccion) {
        this.nombEmpresa = nombEmpresa;
        this.RUC = RUC;
        this.direccion = direccion;
        this.departamentos = new ArrayList();
    }

    public void agregarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }

    @Override
    public String toString() {
        return "Nombre = " + nombEmpresa + 
                ",\nRUC = " + RUC + 
                ",\nDirección = " + direccion + 
                departamentos;
    }

}