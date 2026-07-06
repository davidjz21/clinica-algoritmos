package controller;

import model.Nodo;
import model.Paciente;

public class ListaEnlazada {
    private Nodo inicio;
    private Nodo fin;

    public void agregar(Paciente p) {
        Nodo nuevo = new Nodo(p);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public String listar() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = inicio;
        int i = 1;
        while (actual != null) {
            Paciente p = actual.getPaciente();
            sb.append(i++).append(". ").append(p.getDni())
              .append(" - ").append(p.getNombre()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.length() == 0 ? "(Historial vacio)" : sb.toString();
    }
}