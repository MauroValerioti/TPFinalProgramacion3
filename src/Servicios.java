import java.io.*;
import java.util.*;

public class Servicios {
    private List<Procesador> procesadores;
    private List<Tarea> tareas;
    private Map<String, Tarea> tareaMap;

//////////////CONSTRUCTORES Y PROCESOS DE CARGA

    /*
    * la complejidad temporal del constructor es O(p + t), siendo p la cantidad de Procesadores y
    * t la cantidad de tareas. Ya que debe recorrer todos los registros que tenga cada uno de
    * los datasets y cargarlos.
    * */
    public Servicios(String pathProcesadores, String pathTareas) throws IOException {
        this.procesadores = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.tareaMap = new HashMap<>();

       cargarProcesadores(pathProcesadores);
       cargarTareas(pathTareas);
    }

    private void cargarProcesadores(String pathProcesadores) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathProcesadores));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            Procesador p = new Procesador(parts[0], parts[1], Boolean.parseBoolean(parts[2]), Integer.parseInt(parts[3]));
            procesadores.add(p);
        }
        br.close();
    }

    private void cargarTareas(String pathTareas) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathTareas));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            Tarea t = new Tarea(parts[0], parts[1], Integer.parseInt(parts[2]), Boolean.parseBoolean(parts[3]), Integer.parseInt(parts[4]));
            tareas.add(t);
            tareaMap.put(t.getId(), t);
        }
        br.close();
    }
//////////////FIN DE CONSTRUCTORES Y PROCESOS DE CARGA

//////////////SERVICIOS DE PARTE I

    /*
    * la complejidad temporal del servicio 1 es O(1) ya que solo debe acceder al valor
    * pasado por ID.
    * */
    public Tarea servicio1(String ID) {
        return tareaMap.get(ID);
    }

    /*
    * la complejidad temporal del servicio 2 es O(t) siendo t la cantidad de tareas que
    * recorre y asigna a la lista de resultados.
    * */
    public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> result = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.isCritica() == esCritica) {
                result.add(t);
            }
        }
        return result;
    }

    /*
    * la complejidad temporal del servicio 3 es O(t) siendo t la cantidad de registros que recorre
    * y luego asigna al resultado si cumple la condicion.
    * */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> result = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getNivelPrioridad() >= prioridadInferior && t.getNivelPrioridad() <= prioridadSuperior) {
                result.add(t);
            }
        }
        return result;
    }

//////////////FIN DE SERVICIOS DE PARTE I.

//////////////INICIO DE SERVICIOS DE PARTE II(Backtraking y Greedy).

    /*
    * este metodo me permite evaluar si se cumplen las condiciones para asignar la tarea al tipo de
    * porcesador dependiendo si es refrigerado o no refrigerado.
    * */
    private boolean puedeAsignarTarea(Procesador procesador, Tarea tarea, int maxTimeNoRefrigerado) {

        //si el procesador es no refrigerado pregunto si el tiempo de ejecucion acumulado supera el maximo permitido.
        if (!procesador.isRefrigerado() && procesador.getTiempoEjecucionAcumulado() + tarea.getTiempoEjecucion() > maxTimeNoRefrigerado) {
            return false;
        }

        //cuento la cantidad de tareas criticas que ese procesador tiene asignado en su lista de tareas.
        long tareasCriticas = procesador.getTareasAsignadas().stream().filter(Tarea::isCritica).count();

        //si el limite de tareas criticas maxima se supera o la tarea es critica retorna false F v F = F
        return !tarea.isCritica() || tareasCriticas < 2;
    }


    //////////////INICIO DE SOLUCION POR BACKTRACKING.

    /*
    * este es el metodo ppal de la solucion por backtracking. paso por parametro el
    * maximo timepo para procesadores no refrigerados e inicializo la solucion que retornare
    * luego llamo al metodo BacktrackingImpl para que genere la mejor solucion.
    * */
    public Solucion backtracking(int maximoTiempoNoRefrigerado) {
        Solucion mejorSolucion = new Solucion();

        int[] cantCasos = {0}; // Uso un array para contar las iteraciones
        //llamo al metodo ppal
        backtrackingImpl(0, new ArrayList<>(procesadores), new ArrayList<>(), maximoTiempoNoRefrigerado, mejorSolucion, cantCasos);

        //retorno el mejor tiempo para la solucion.
        return mejorSolucion;
    }

    /*
    * implementacion del metodo por backtracking, este metodo se autollama recursivamente arrastrando los parametros de mejor solucion.
    * recorre todas las tareas y cuando llega al final retorna la solucion. Para cada tarea, por la que entro en la recursion, busco cual de
    * los procesadores es el mas adecuado para asignarle la tarea. finalmente busco de manera recursiva la mejor combinacion tarea/procesador
    * con el mejor tiempo para restornar en la solucion.
    * */
    private void backtrackingImpl(int tareaIndex, List<Procesador> procesadores, List<Tarea> tareasAsignadas, int maximoTiempoNoRefrigerado, Solucion mejorSolucion, int[] cantCasos) {

        //si la tarea es la ultima de la lista de tareas.
        if (tareaIndex == tareas.size()) {

            //obtengo el tiempo de ejecucion acumulado del procesador con mayor tiempo acumulado.
            int tiempoMaximo = procesadores.stream().mapToInt(Procesador::getTiempoEjecucionAcumulado).max().orElse(0);

            if (mejorSolucion.getTiempoMaximoEjecucion() == -1 || tiempoMaximo < mejorSolucion.getTiempoMaximoEjecucion()) {
                mejorSolucion.setProcesadores(new ArrayList<>(procesadores));
                mejorSolucion.setTiempoMaximoEjecucion(tiempoMaximo);
                mejorSolucion.setEstadosGenerados(mejorSolucion.getEstadosGenerados() + 1);
                mejorSolucion.setAux(cantCasos[0]);

            }
            //termino el backtracking y retorno los parametros con los valores generados
            return;
        }

        Tarea tarea = tareas.get(tareaIndex);
        //recorro los procesadores con el objetivo de asignar la tarea actual al mas viable
        for (Procesador procesador : procesadores) {
            //si el procesador cumple las condiciones para agreggar la tarea la asigna.
            if (puedeAsignarTarea(procesador, tarea, maximoTiempoNoRefrigerado)) {
                //System.out.println("entro al if");

                cantCasos[0]++;

                procesador.asignarTarea(tarea);

                //llamo a recursion con el metodo
                backtrackingImpl(tareaIndex + 1, procesadores, tareasAsignadas, maximoTiempoNoRefrigerado, mejorSolucion, cantCasos);
                //remuevo la tarea asginada

                procesador.getTareasAsignadas().remove(tarea);
                //quito el tiempo de ejecucion de la ultima tarea asignada al procesador.
                procesador.tiempoEjecucionAcumulado -= tarea.getTiempoEjecucion();
            }
        }
    }
//////////////FIN DE SOLUCION POR BACKTRAKING.


//////////////INICIO DE SOLUCION POR GREEDY.

    /*
    * metodo Greedy, en primer lugar ordeno las tareas de mayor a menor tiempo de ejecucion,
    * luego recorro la lista de tareas ordenadas y para cada tarea recorro la lista de procesadores
    * para ubicar el mas adecuado para asignar esta tarea(verifico previamente que el procesador cumpla con
    * los requisitos para asignar la tarea). Finalmente agrego a la solucion la lista de procesadores resultantes
    * con sus respectivas tareas asignadas y el tiempo maximo de ejecucion.
    *
    * */
    public Solucion greedy(int maximoTiempoNoRefrigerado) {

        //ordena las tareas por tiempo de ejecucion de mayor a menor
        tareas.sort(Comparator.comparingInt(Tarea::getTiempoEjecucion).reversed());
        //inicializo la solucion que luego voy a retornar
        Solucion solucion = new Solucion();

        //recorro la lista de tareas
        for (Tarea tarea : tareas) {
            //vuelvo null el mejor procesador
            Procesador mejorProcesador = null;

            //busco el procesador mas adecuado para la trea actual
            for (Procesador procesador : procesadores) {
                //pregunto si puedo asignar la tarea a ese procesador llamando al metodo.
                if (puedeAsignarTarea(procesador, tarea, maximoTiempoNoRefrigerado)) {
                    //si no se asigno otro procesador antes y el tiempo del procesador es mejor que el ultimo asignado en mejores lo asigno como el mejor.
                    if (mejorProcesador == null || procesador.getTiempoEjecucionAcumulado() < mejorProcesador.getTiempoEjecucionAcumulado()) {
                        mejorProcesador = procesador;
                    }
                }
            }

            //asigno la tarea al mejor procesador encontrado.
            if (mejorProcesador != null) {
                mejorProcesador.asignarTarea(tarea);
                solucion.setCandidatosConsiderados(solucion.getCandidatosConsiderados() + 1);
            }
        }
        //asigno las correspondientes soluciones.
        solucion.setProcesadores(procesadores);
        //obtengo el tiempo de ejecucion acumulado del procesador con mayor tiempo acumulado.
        solucion.setTiempoMaximoEjecucion(procesadores.stream().mapToInt(Procesador::getTiempoEjecucionAcumulado).max().orElse(0));
        return solucion;
    }

//////////////FIN DE SOLUCION POR GREEDY.

//////////////FIN DE SERVICIOS DE PARTE II.
}
