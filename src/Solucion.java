import java.util.List;

//clase creada para presentar la solucion final de los algoritmos de Backtracking y Greedy.
public class Solucion {

    private List<Procesador> procesadores;
    private int tiempoMaximoEjecucion;

    //para backtracking
    private int estadosGenerados;
    //para greedy
    private int candidatosConsiderados;

    private int cantCasos;

    //CONSTRUCTOR
    public Solucion() {
        this.tiempoMaximoEjecucion = -1;
        this.estadosGenerados = 0;
        this.candidatosConsiderados = 0;
        this.cantCasos = 0;
    }

    public List<Procesador> getProcesadores() {
        return procesadores;
    }

    public void setProcesadores(List<Procesador> procesadores) {
        this.procesadores = procesadores;
    }

    public int getTiempoMaximoEjecucion() {
        return tiempoMaximoEjecucion;
    }

    public void setTiempoMaximoEjecucion(int tiempoMaximoEjecucion) {
        this.tiempoMaximoEjecucion = tiempoMaximoEjecucion;
    }

    public int getEstadosGenerados() {
        return estadosGenerados;
    }

    public void setEstadosGenerados(int estadosGenerados) {
        this.estadosGenerados = estadosGenerados;
    }

    public int getCandidatosConsiderados() {
        return candidatosConsiderados;
    }

    public void setCandidatosConsiderados(int candidatosConsiderados) {
        this.candidatosConsiderados = candidatosConsiderados;
    }

    public int getAux() {
        return cantCasos;
    }

    public void setAux(int aux) {
        this.cantCasos = aux;
    }
}
