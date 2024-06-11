import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws IOException {

        Servicios servicios = new Servicios("src/ArchivosOrigen/Procesadores.csv", "src/ArchivosOrigen/Tareas.csv");

        System.out.println("//////////////////////////////////");

        System.out.println("TP Especial Prog3 - Parte II: ");

        // Solucion Backtracking.
        Solucion solucionConBacktracking = servicios.backtracking(100);
        System.out.println("\n Backtracking: \n");

        System.out.println("  Procesadores: " + solucionConBacktracking.getProcesadores());
        System.out.println("  Tiempo máximo de ejecución = " + solucionConBacktracking.getTiempoMaximoEjecucion());
        System.out.println("  Cantidad de estados generados = " + solucionConBacktracking.getEstadosGenerados());
        System.out.println("  cantidad de casos evaluados en recursion = " + solucionConBacktracking.getAux());


        // Solucion Greedy.
        Solucion solucionConGreedy = servicios.greedy(100);
        System.out.println("\n Greedy: \n");

        System.out.println("  Procesadores: " + solucionConGreedy.getProcesadores());
        System.out.println("  Tiempo máximo de ejecución = " + solucionConGreedy.getTiempoMaximoEjecucion());
        System.out.println("  Cantidad candidatos considerados = " + solucionConGreedy.getCandidatosConsiderados());
//

    }

}

