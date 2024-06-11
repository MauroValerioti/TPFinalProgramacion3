import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Servicios servicios = new Servicios("src/ArchivosOrigen/Procesadores.csv", "src/ArchivosOrigen/Tareas.csv");

            System.out.println("//////////////////////////////////");

            System.out.println("TP Especial Prog3 - Parte I: ");

            // Servicio 1: Obtener toda la info de la Tarea asociada a un ID
            String ID = "T2";
            Tarea tarea = servicios.servicio1(ID);
            if (tarea != null) {
                System.out.println(" Servicio 1: Datos de la tarea solicitada.");
                System.out.println("  Id tarea: " + tarea.getId());
                System.out.println("  Nombre Tarea: " + tarea.getNombre());
                System.out.println("  Nivel de prioridad de Tarea: " + tarea.getNivelPrioridad());
                System.out.println("  Tiempo de ejecucion de tarea: " + tarea.getTiempoEjecucion());
            } else {
                System.out.println("Servicio 1: Tarea no encontrada");
            }

            // Servicio 2: Ver tareas criticas o no criticas?
            boolean esCritica = false;
            List<Tarea> tareasCriticas = servicios.servicio2(esCritica);
            String titulo = (esCritica) ? "tareas críticas." : "tareas no criticas.";
            System.out.println(" Servicio 2: listar " + titulo);
            for (Tarea t : tareasCriticas) {
                System.out.println("  " + t.getNombre());
            }

            // Servicio 3: listar tareas entre un minimo y un maximo valor.
            int prioridadInferior = 30;
            int prioridadSuperior = 90;
            System.out.println(" Servicio 3: Tareas con prioridad entre "+ prioridadInferior +" y "+ prioridadSuperior + " .");
            List<Tarea> tareasPrioridad = servicios.servicio3(prioridadInferior, prioridadSuperior);
            for (Tarea t : tareasPrioridad) {
                System.out.println("  " + t.getNombre());
            }

            System.out.println("//////////////////////////////////");


            //por si el archivo viene con errores o si falla la apertura.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}