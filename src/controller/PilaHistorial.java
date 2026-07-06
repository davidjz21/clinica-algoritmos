package controller;

public class PilaHistorial {

    private static class NodoPila {
        String accion;
        NodoPila siguiente;
        NodoPila(String accion) { this.accion = accion; }
    }

    private NodoPila tope;

    public void apilar(String accion) {
        NodoPila nuevo = new NodoPila(accion);
        nuevo.siguiente = tope;
        tope = nuevo;
    }

    public String desapilar() {
        if (tope == null) return null;
        String accion = tope.accion;
        tope = tope.siguiente;
        return accion;
    }

    public String listar() {
        StringBuilder sb = new StringBuilder();
        NodoPila actual = tope;
        while (actual != null) {
            sb.append("- ").append(actual.accion).append("\n");
            actual = actual.siguiente;
        }
        return sb.length() == 0 ? "(Sin acciones registradas)" : sb.toString();
    }
}