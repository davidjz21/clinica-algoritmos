package controller;

import model.Nodo;
import model.Paciente;

public class ColaAtencion {
    private Nodo frente;
    private Nodo final_;

    public void encolar(Paciente p) {
        Nodo nuevo = new Nodo(p);
        if (frente == null) {
            frente = nuevo;
            final_ = nuevo;
        } else {
            final_.setSiguiente(nuevo);
            final_ = nuevo;
        }
    }

    public Paciente atenderSiguiente() {
        if (frente == null) return null;
        Paciente p = frente.getPaciente();
        frente = frente.getSiguiente();
        if (frente == null) final_ = null;
        return p;
    }

    public String listar() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = frente;
        int i = 1;
        while (actual != null) {
            Paciente p = actual.getPaciente();
            sb.append(i++).append(". ").append(p.getDni())
              .append(" - ").append(p.getNombre()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.length() == 0 ? "(Sala de espera vacia)" : sb.toString();
    }
}