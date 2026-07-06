package controller;

import model.Nodo;
import model.Paciente;


public class ListaPrioridad {
    private Nodo inicio;

    public ListaPrioridad() {
        this.inicio = null;
    }

    public void insertar(Paciente nuevoPaciente) {
        Nodo nuevoNodo = new Nodo(nuevoPaciente);

        if (inicio == null || nuevoPaciente.getPrioridad() < inicio.getPaciente().getPrioridad()) {
            nuevoNodo.setSiguiente(inicio);
            inicio = nuevoNodo;
        } else {
            Nodo actual = inicio;
            while (actual.getSiguiente() != null && 
                   actual.getSiguiente().getPaciente().getPrioridad() <= nuevoPaciente.getPrioridad()) {
                actual = actual.getSiguiente();
            }
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
        }
    }

    public Nodo getInicio() {
        return inicio;
    }
    
    public void eliminarPrimero() {
        if (inicio != null) {
            inicio = inicio.getSiguiente();
        }
    }
}
