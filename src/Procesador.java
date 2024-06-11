import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Procesador {
    private String id;
   // private String codigo;
    private boolean refrigerado;
    //private int anoFuncionamiento;
    private List<Tarea> tareasAsignadas;
    protected int tiempoEjecucionAcumulado;

    public Procesador(String id, String codigo, boolean refrigerado, int anoFuncionamiento) {
        this.id = id;
        //this.codigo = codigo;
        this.refrigerado = refrigerado;
       //this.anoFuncionamiento = anoFuncionamiento;
        this.tareasAsignadas = new ArrayList<>();
        this.tiempoEjecucionAcumulado = 0;
    }

    public String getId() {
        return id;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }

    public void asignarTarea(Tarea tarea) {
        tareasAsignadas.add(tarea);
        tiempoEjecucionAcumulado += tarea.getTiempoEjecucion();
    }

    public int getTiempoEjecucionAcumulado() {
        return tiempoEjecucionAcumulado;
    }

    public List<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    @Override
    public String toString() {
        return "\n" + id + ": \n" +
                " tareasAsignadas=" + tareasAsignadas.stream()
                .map(Tarea::getId)  // Obtener el ID de cada tarea
                .collect(Collectors.joining(", ")) ;  // Unir los IDs con comas
    }

}
