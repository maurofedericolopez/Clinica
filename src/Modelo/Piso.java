package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class Piso implements Serializable {

    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @Column(name = "idPiso")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long numero;
    @ManyToOne
    private Edificio edificio;
    @OneToOne
    private Enfermera enfermera;
    @OneToMany(mappedBy = "piso")
    private List<Habitacion> habitaciones;
    @OneToMany(mappedBy = "piso")
    private List<Consultorio> consultorios;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piso)) {
            return false;
        }
        Piso other = (Piso) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Piso[ id=" + getId() + " ]";
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * @return the edificio
     */
    public Edificio getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    /**
     * @return the enfermera
     */
    public Enfermera getEnfermera() {
        return enfermera;
    }

    /**
     * @param enfermera the enfermera to set
     */
    public void setEnfermera(Enfermera enfermera) {
        this.enfermera = enfermera;
    }

    /**
     * @return the habitaciones
     */
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * @param habitaciones the habitaciones to set
     */
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * @return the consultorios
     */
    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    /**
     * @param consultorios the consultorios to set
     */
    public void setConsultorios(List<Consultorio> consultorios) {
        this.consultorios = consultorios;
    }

}
