package Modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class DrogaVisita implements Serializable {

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
    @Column(name = "idDrogaVisita")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Visita visita;
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
        if (!(object instanceof DrogaVisita)) {
            return false;
        }
        DrogaVisita other = (DrogaVisita) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.DrogaVisita[ id=" + getId() + " ]";
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
     * @return the visita
     */
    public Visita getVisita() {
        return visita;
    }

    /**
     * @param visita the visita to set
     */
    public void setVisita(Visita visita) {
        this.visita = visita;
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
