package view;

import controller.ArbolBinario;
import controller.ColaAtencion;
import controller.ListaEnlazada;
import controller.ListaPrioridad;
import controller.PilaHistorial;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Nodo;
import model.Paciente;


public class FrmPrincipal extends javax.swing.JFrame {
    
    ListaPrioridad listaAtencion = new ListaPrioridad();
    ListaEnlazada historialGeneral = new ListaEnlazada();
    ColaAtencion colaEspera = new ColaAtencion();
    PilaHistorial pilaAcciones = new PilaHistorial();
    ArbolBinario arbolBusqueda = new ArbolBinario();
    
    private javax.swing.JTextField txtDni = new javax.swing.JTextField();
    private javax.swing.JTextField txtNombre = new javax.swing.JTextField();
    private javax.swing.JComboBox<String> cboPrioridad = new javax.swing.JComboBox<>();
    private javax.swing.JTable tblPacientes = new javax.swing.JTable();
    
    private javax.swing.JButton btnRegistrar = new javax.swing.JButton("Registrar Paciente");
    private javax.swing.JButton btnEncolar   = new javax.swing.JButton("Encolar en Espera");
    private javax.swing.JButton btnAtender   = new javax.swing.JButton("Atender Siguiente (Cola)");
    private javax.swing.JButton btnDeshacer  = new javax.swing.JButton("Deshacer Ultima Accion");
    private javax.swing.JButton btnVerHistorial = new javax.swing.JButton("Ver Historial");
    
    private javax.swing.JTextField txtBuscarDni = new javax.swing.JTextField(10);
    private javax.swing.JButton btnBuscar    = new javax.swing.JButton("Buscar por DNI");
    private javax.swing.JTextArea txtResultados = new javax.swing.JTextArea(10, 35);

    public FrmPrincipal() {
        setTitle("Sistema de Triaje - Clínica Internacional");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new java.awt.FlowLayout());

        add(new javax.swing.JLabel("DNI:"));
        txtDni.setColumns(10); 
        add(txtDni);

        add(new javax.swing.JLabel("Nombre:"));
        txtNombre.setColumns(15);
        add(txtNombre);

        add(new javax.swing.JLabel("Prioridad:"));
        cboPrioridad.addItem("1 - Emergencia");
        cboPrioridad.addItem("2 - Urgencia");
        cboPrioridad.addItem("3 - Estable");
        add(cboPrioridad);

        add(btnRegistrar);

        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"DNI", "Nombre", "Prioridad"}
        ));
        
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tblPacientes);
        scroll.setPreferredSize(new java.awt.Dimension(400, 200));
        add(scroll);
        
     
        add(btnEncolar);
        add(btnAtender);
        add(btnDeshacer);
        add(new javax.swing.JLabel("Buscar DNI:"));
        add(txtBuscarDni);
        add(btnBuscar);
        add(btnVerHistorial);


        txtResultados.setEditable(false);
        javax.swing.JScrollPane scrollResultados = new javax.swing.JScrollPane(txtResultados);
        scrollResultados.setPreferredSize(new java.awt.Dimension(400, 150));
        add(scrollResultados);

        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        
        
        btnEncolar.addActionListener(evt -> btnEncolarActionPerformed(evt));
        btnAtender.addActionListener(evt -> btnAtenderActionPerformed(evt));
        btnDeshacer.addActionListener(evt -> btnDeshacerActionPerformed(evt));
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        btnVerHistorial.addActionListener(evt -> btnVerHistorialActionPerformed(evt));
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
        modelo.setRowCount(0);

        Nodo aux = listaAtencion.getInicio();
        while (aux != null) {
            Paciente p = aux.getPaciente();
            Object[] fila = {p.getDni(), p.getNombre(), p.getPrioridadTexto()};
            modelo.addRow(fila);
            aux = aux.getSiguiente();
        }
    }

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            int prioridad = cboPrioridad.getSelectedIndex() + 1; 

            Paciente nuevo = new Paciente(dni, nombre, prioridad);
            listaAtencion.insertar(nuevo);
            arbolBusqueda.insertar(nuevo);
            historialGeneral.agregar(nuevo);
            pilaAcciones.apilar("Registrado: " + dni);

            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Paciente en cola de espera.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en el registro.");
        }
    }
    
    // nuevos
    
    private void btnVerHistorialActionPerformed(java.awt.event.ActionEvent evt) {
        txtResultados.setText(historialGeneral.listar());
    }

    private void btnEncolarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Paciente p = new Paciente(txtDni.getText(), txtNombre.getText(), cboPrioridad.getSelectedIndex() + 1);
            colaEspera.encolar(p);
            pilaAcciones.apilar("Encolado en espera: " + p.getDni());
            txtResultados.setText(colaEspera.listar());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al encolar.");
        }
    }

    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {
        Paciente atendido = colaEspera.atenderSiguiente();
        if (atendido != null) {
            pilaAcciones.apilar("Atendido desde cola: " + atendido.getDni());
            txtResultados.setText("Atendido: " + atendido.getNombre() + " (" + atendido.getDni() + ")\n\n" + colaEspera.listar());
        } else {
            txtResultados.setText("No hay pacientes en la sala de espera.");
        }
    }

    private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {
        String accion = pilaAcciones.desapilar();
        if (accion != null) {
            txtResultados.setText("Se deshizo: " + accion + "\n\nHistorial de acciones:\n" + pilaAcciones.listar());
        } else {
            txtResultados.setText("No hay acciones para deshacer.");
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        Paciente encontrado = arbolBusqueda.buscar(txtBuscarDni.getText());
        if (encontrado != null) {
            txtResultados.setText("Encontrado:\n" + encontrado.getDni() + " - " + encontrado.getNombre()
                    + " (" + encontrado.getPrioridadTexto() + ")");
        } else {
            txtResultados.setText("No se encontro ningun paciente con DNI: " + txtBuscarDni.getText());
        }
    }
    
    
    
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }
}
