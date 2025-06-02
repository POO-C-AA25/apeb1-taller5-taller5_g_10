import java.util.ArrayList;
import java.util.Arrays;

public class Problema6_EjecutorActividades {
    public static ArrayList<Participante> listaParc;
    public static void main(String[] args) {
        listaParc = new ArrayList<Participante>();
        
        ArrayList <Participante> participantes1 = new ArrayList<>(
                                                        Arrays.asList(new Participante("P1", 2, 10),
                                                                      new Participante("P2", 3, 5 ),
                                                                      new Participante("P3", 5, 7 )));
        ArrayList <Participante> participantes2 = new ArrayList<>(
                                                        Arrays.asList(new Participante("P1", 2, 10),
                                                                      new Participante("P4", 3, 5 ),
                                                                      new Participante("P5", 5, 7 )));
        ArrayList <Participante> participantes3 = new ArrayList<>(
                                                        Arrays.asList(new Participante("P6", 2, 10),
                                                                      new Participante("P7", 3, 9 ),
                                                                      new Participante("P1", 5, 10)));
        
        // Crear disciplinas
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina("Fútbol", participantes1));
        disciplinas.add(new Disciplina("Atletismo", participantes2));
        disciplinas.add(new Disciplina("Natación", participantes3));
        
        calculoEstaditicas(disciplinas);
    }

    public static void calculoEstaditicas(ArrayList<Disciplina> disciplinas) {
        System.out.println("\n===== ESTADÍSTICAS GENERALES DEL EVENTO =====");
        ArrayList<String> nombresUnicos = new ArrayList<>();
        ArrayList<Participante> resumen = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            System.out.println("\n--- Disciplina: " + d.nombreAct + " ---");
            int totalPuntaje = 0;
            int totalAsistencia = 0;
            for (Participante p : d.participantes) {
                totalPuntaje += p.puntaje;
                totalAsistencia += p.asistencia;
                
                int pos = -1;
                for (int i = 0; i < nombresUnicos.size(); i++) {
                    if (nombresUnicos.get(i).equals(p.nombrePar)) {
                        pos = i;
                        break;
                    }
                }

                if (pos == -1) {
                    Participante nuevo = new Participante(p.nombrePar, p.asistencia, p.puntaje);
                    if (p.puntaje >= 6) {
                        nuevo.numeroPruebasSuperadas = 1;
                    }
                    resumen.add(nuevo);
                    nombresUnicos.add(p.nombrePar);
                } else {
                    Participante existente = resumen.get(pos);
                    existente.asistencia += p.asistencia;
                    existente.puntaje += p.puntaje;
                    if (p.puntaje >= 6) {
                        existente.numeroPruebasSuperadas++;
                    }
                }

                if (p.puntaje >= 9) {
                    d.participantesDestacados.add(p);
                }
            }

            double promedioPuntaje = (d.participantes.size() > 0) ? (double) totalPuntaje / d.participantes.size() : 0;
            double promedioAsistencia = (d.participantes.size() > 0) ? (double) totalAsistencia / d.participantes.size() : 0;
            System.out.printf("Promedio de puntaje: %.2f\n", promedioPuntaje);
            System.out.printf("Promedio de asistencia: %.2f\n", promedioAsistencia);

            System.out.println("Participantes destacados:");
            for (Participante destacado : d.participantesDestacados) {
                System.out.println("- " + destacado.nombrePar + " con " + destacado.puntaje + " puntos");
            }
        }

        System.out.println("\n--- RESUMEN POR PARTICIPANTE ---");
        for (Participante p : resumen) {
            if (p.numeroPruebasSuperadas > 0) {
                p.promedioRendimiento = (double) p.puntaje / p.numeroPruebasSuperadas;
            } else {
                p.promedioRendimiento = 0;
            }
            System.out.println(p);
        }
    }
}

class Participante {
    public String nombrePar;
    public int asistencia;
    public int puntaje;
    public double promedioRendimiento;
    public int numeroPruebasSuperadas;
    
    public Participante() {
        // Constructor vacío
    }

    public Participante(String nombrePar, int asistencia, int puntaje) {
        this.nombrePar = nombrePar;
        this.asistencia = asistencia;
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Participante: " + nombrePar +
               " | Asistencia total: " + asistencia +
               " | Puntaje total: " + puntaje +
               " | Pruebas superadas: " + numeroPruebasSuperadas +
               " | Promedio rendimiento: " + String.format("%.2f", promedioRendimiento);
    }
}

class Disciplina {
    public String nombreAct;
    public ArrayList<Participante> participantes;
    public ArrayList<Participante> participantesDestacados;
    
    public Disciplina() {
        // Constructor vacío
    }

    public Disciplina(String nombreAct, ArrayList<Participante> participantes) {
        this.nombreAct = nombreAct;
        this.participantes = participantes;
        this.participantesDestacados = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Disciplina: " + nombreAct + 
                " | Participantes: " + participantes.size() +
                " | Destacados: " + participantesDestacados.size();
    }
}