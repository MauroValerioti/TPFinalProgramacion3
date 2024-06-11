public class Tarea {
    private String id;
    private String nombre;
    private int tiempoEjecucion;
    private boolean critica;
    private int nivelPrioridad;

    public Tarea(String id, String nombre, int tiempoEjecucion, boolean critica, int nivelPrioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempoEjecucion = tiempoEjecucion;
        this.critica = critica;
        this.nivelPrioridad = nivelPrioridad;
    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public String getNombre() {
        return nombre;
    }

//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public void setTiempoEjecucion(int tiempoEjecucion) {
//        this.tiempoEjecucion = tiempoEjecucion;
//    }
//
//    public void setCritica(boolean critica) {
//        this.critica = critica;
//    }
//
//    public void setNivelPrioridad(int nivelPrioridad) {
//        this.nivelPrioridad = nivelPrioridad;
//    }

    public String getId() {
        return id;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public boolean isCritica() {
        return critica;
    }

    public int getNivelPrioridad() {
        return nivelPrioridad;
    }
}
