package controller;

import model.NodoArbol;
import model.Paciente;

public class ArbolBinario {
    private NodoArbol raiz;

    public void insertar(Paciente p) {
        raiz = insertarRec(raiz, p);
    }

    private NodoArbol insertarRec(NodoArbol actual, Paciente p) {
        if (actual == null) return new NodoArbol(p);
        int cmp = p.getDni().compareTo(actual.getPaciente().getDni());
        if (cmp < 0) actual.setIzquierdo(insertarRec(actual.getIzquierdo(), p));
        else if (cmp > 0) actual.setDerecho(insertarRec(actual.getDerecho(), p));
        return actual; // si el DNI ya existe, no se duplica
    }

    public Paciente buscar(String dni) {
        return buscarRec(raiz, dni);
    }

    private Paciente buscarRec(NodoArbol actual, String dni) {
        if (actual == null) return null;
        int cmp = dni.compareTo(actual.getPaciente().getDni());
        if (cmp == 0) return actual.getPaciente();
        return cmp < 0 ? buscarRec(actual.getIzquierdo(), dni) : buscarRec(actual.getDerecho(), dni);
    }

    public String listarInorden() {
        StringBuilder sb = new StringBuilder();
        listarInordenRec(raiz, sb);
        return sb.length() == 0 ? "(Arbol vacio)" : sb.toString();
    }

    private void listarInordenRec(NodoArbol actual, StringBuilder sb) {
        if (actual == null) return;
        listarInordenRec(actual.getIzquierdo(), sb);
        Paciente p = actual.getPaciente();
        sb.append(p.getDni()).append(" - ").append(p.getNombre()).append("\n");
        listarInordenRec(actual.getDerecho(), sb);
    }
}