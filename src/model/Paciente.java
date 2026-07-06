package model;

public class Paciente {
    private String dni;
    private String nombre;
    private int prioridad;

    public Paciente(String dni, String nombre, int prioridad) {
        this.dni = dni;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }
    
    public String getPrioridadTexto() {
        switch(prioridad) {
            case 1: 
                return "1 - Emergencia";
            case 2: 
                return "2 - Urgencia";
            default: 
                return "3 - Estable";
        }
    }
}
