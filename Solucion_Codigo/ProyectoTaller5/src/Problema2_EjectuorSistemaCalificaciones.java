import java.util.Scanner;

public class Problema2_EjectuorSistemaCalificaciones {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombres[] = {"Ian", "Elita", "Mateo", "Luis", "Samanta", "Camila"};
        String apellidos[] = {"Rivera", "Medinta", "Songor", "Montero", "Loja", "Lima"};
        String materias[] = {"Lengua y Literatura", "Matemática", "Ciudadanía", "Historia"};
        double notaAprobados[] = new double[35];
        double notaReprobados[] = new double[35];
        Estudiante[] aprobados = new Estudiante[35];
        Estudiante[] reprobados = new Estudiante[35];
        int a = 0, r = 0;
        char anadir = 'S';
        System.out.println("SISTEMA DE CALIFICACIONES");
        System.out.println("=========================\n");
        while (anadir == 'S') {
            if (a > 35 || r > 35) {
                System.out.println("No se puede añadir más estudiantes. Límite de estudiantes alcanzado.");
            } else {
                double acd = Math.floor(Math.random() * 36) / 10;
                double ape = Math.floor(Math.random() * 36) / 10;
                double aa = Math.floor(Math.random() * 31) / 10;
                Materia materia = new Materia(materias[(int) Math.floor(Math.random() * materias.length)], acd, ape, aa);
                String nombreEstudiante = nombres[(int) Math.floor(Math.random() * nombres.length)] + " "
                        + apellidos[(int) Math.floor(Math.random() * apellidos.length)];
                Estudiante estudiante = new Estudiante(nombreEstudiante, (int)(Math.random() * (64 - 18 + 1)) + 18, materia);
                System.out.println(estudiante.toString());
                System.out.print(materia);
                materia.calcularNotaFinal();                
                System.out.printf("     %nNota Final: %.2f", materia.notaFinal);
                if (estudiante.comprobarAprobacion()) {
                    System.out.println("\nEstudiante Aprobado");
                    aprobados[a] = estudiante;
                    notaAprobados[a] = materia.notaFinal;
                    a++;
                } else {
                    System.out.println("\nEstudiante Reprobado");
                    reprobados[r] = estudiante;
                    notaReprobados[r] = materia.notaFinal;
                    r++;
                }
                
                if (!estudiante.comprobarAprobacion()) {
                    System.out.println("     \nNo alcanza el puntaje necesario. "
                            + "Deberá rendir un examen de recuperación sobre 3.5/10 puntos,\n"
                            + "agregado al 60% acumulado de los componentes ACD, APE y AA.");
                }
                
                System.out.print("\nDesea añadir otro estudiante? (s/n): ");
                anadir = sc.next().charAt(0);
            }
        }
        
        System.out.println("\nAprobados");
        if (aprobados[0] == null) {
            System.out.println("No hay estudiantes aprobados.");
        } else {
            for (int i = 0; i < a; i++) {
                System.out.println(aprobados[i]);
                System.out.printf("Nota final: %.2f%n", notaAprobados[i]);
            }
        }  
        System.out.println("\nReprobados");
        if (reprobados[0] == null) {
            System.out.println("No hay estudiantes reprobados.");
        } else {
            for (int i = 0; i < r; i++) {
                System.out.println(reprobados[i]);
                System.out.printf("Nota final: %.2f%n", notaReprobados[i]);
            }
        }
    }
}

class Estudiante {
    public String nombreEst;
    public int edad;
    public Materia materia;
    public boolean aprobado;

    public Estudiante() {
        // Constructor vacío
    }

    public Estudiante(String nombreEst, int edad, Materia materia) {
        this.nombreEst = nombreEst;
        this.edad = edad;
        this.materia = materia;
    }
    
    public boolean comprobarAprobacion() {
        double total = this.materia.ACD + this.materia.APE + this.materia.AA;
        return this.aprobado = total >= 7;
    }  

    @Override
    public String toString() {
        return "Estudiante{" + "Nombre:" + nombreEst + 
                ", Edad: " + edad + 
                ", Materia: " + materia + 
                ", Aprobado:" + aprobado + '}';
    }
}

class Materia {
    public String nombreM;
    public double ACD;
    public double APE;
    public double AA;
    public double notaFinal;

    public Materia() {
        // Constructor vacío
    }

    public Materia(String nombreM, double ACD, double APE, double AA) {
        this.nombreM = nombreM;
        this.ACD = ACD;
        this.APE = APE;
        this.AA = AA;
    }
    public void calcularNotaFinal() {
        this.notaFinal = ACD + APE + AA;
    }
    
    @Override
    public String toString() {
        return "Materia{" + "Nombre de la Materia: " + nombreM + 
                ", Componente ACD: " + ACD + 
                ", Compontente APE: " + APE + 
                ", CompontenteAA:" + AA + '}';
    }
}