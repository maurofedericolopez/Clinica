package clinica;

import Vistas.PrincipalUI;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mauro Federico Lopez
 */
public class Clinica {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicaPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrincipalUI ventanaPrincipal = new PrincipalUI();
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.pack();
    }

}
