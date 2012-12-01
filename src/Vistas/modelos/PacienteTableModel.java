package Vistas.modelos;

import Controladores.PacientesController;
import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.Paciente;
import clinica.Controladores;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class PacienteTableModel extends AbstractTableModel implements Observer {

    private String[] columns = {"DNI","Apellido","Nombre","Dirección","Teléfono"};
    private PacientesController controlador;
    private Paciente[] pacientes;

    public PacienteTableModel() {
        controlador = Controladores.pacientesController;
        controlador.addObserver(this);
        pacientes = controlador.obtenerPacientes();
    }

    @Override
    public int getRowCount() {
        return pacientes.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return pacientes[rowIndex].getDni();
            case 1 :
                return pacientes[rowIndex].getApellido();
            case 2 :
                return pacientes[rowIndex].getNombre();
            case 3 :
                return pacientes[rowIndex].getDomicilio();
            case 4 :
                return pacientes[rowIndex].getTelefono();
            default :
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return Long.class;
            case 1 :
                return String.class;
            case 2 :
                return String.class;
            case 3 :
                return String.class;
            case 4 :
                return String.class;
            default :
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Paciente paciente = pacientes[rowIndex];
            switch(columnIndex) {
                case 0 :
                    paciente.setDni(Long.valueOf(String.valueOf(aValue)));
                    break;
                case 1 :
                    paciente.setApellido(String.valueOf(aValue));
                    break;
                case 2 :
                    paciente.setNombre(String.valueOf(aValue));
                    break;
                case 3 :
                    paciente.setDomicilio(String.valueOf(aValue));
                    break;
                case 4 :
                    paciente.setTelefono(String.valueOf(aValue));
                    break;
                default :
                    break;
            }
            controlador.editarPaciente(paciente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PacienteTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PacienteTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Paciente obtenerPaciente(int index) {
        return pacientes[index];
    }
 
    @Override
    public void update(Observable o, Object arg) {
        pacientes = controlador.obtenerPacientes();
        fireTableDataChanged();
    }

}
