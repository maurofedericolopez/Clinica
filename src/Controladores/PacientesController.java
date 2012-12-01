package Controladores;

import ControladoresJPA.PacienteJpaController;
import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.Paciente;
import clinica.Clinica;
import java.util.Observable;

/**
 *
 * @author Mauro Federico Lopez
 */
public class PacientesController extends Observable {

    private PacienteJpaController pacienteJpaController;

    public PacientesController() {
        pacienteJpaController = new PacienteJpaController(Clinica.emf);
    }

    public void registrarNuevoPaciente(Paciente paciente) {
        pacienteJpaController.create(paciente);
        notificarCambios();
    }

    public Paciente[] obtenerPacientes() {
        Object[] array = pacienteJpaController.findPacienteEntities().toArray();
        Paciente[] pacientes = null;
        for(int i = 0; i < array.length; i++)
            pacientes[i] = (Paciente) array[i];
        return pacientes;
    }

    public void editarPaciente(Paciente paciente) throws NonexistentEntityException, Exception {
        pacienteJpaController.edit(paciente);
        notificarCambios();
    }

    public void eliminarPaciente(Paciente paciente) throws NonexistentEntityException {
        pacienteJpaController.destroy(paciente.getId());
        notificarCambios();
    }

    private void notificarCambios() {
        setChanged();
        notifyObservers();
    }

}
