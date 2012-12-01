package Modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class DrogaServicio implements Serializable {

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
    @Column(name = "idDrogaServicio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100)
    private String dosis;
    @ManyToOne
    private Servicio servicio;
    @ManyToOne
    private Droga droga;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DrogaServicio)) {
            return false;
        }
        DrogaServicio other = (DrogaServicio) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.DrogaServicio[ id=" + getId() + " ]";
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
     * @return the dosis
     */
    public String getDosis() {
        return dosis;
    }

    /**
     * @param dosis the dosis to set
     */
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    /**
     * @return the servicio
     */
    public Servicio getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the droga
     */
    public Droga getDroga() {
        return droga;
    }

    /**
     * @param droga the droga to set
     */
    public void setDroga(Droga droga) {
        this.droga = droga;
    }

}
