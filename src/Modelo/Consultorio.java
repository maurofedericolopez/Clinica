package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class Consultorio implements Serializable {

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
    @Column(name = "idConsultorio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long numero;
    private BigDecimal longitud;
    private Long ventanas;
    private Boolean baño;
    private Boolean cama;
    private Boolean closet;
    @Temporal(TemporalType.DATE)
    private Date ultimaFechaRemodelacion;
    private Double valor;
    @ManyToOne
    private Piso piso;
    @ManyToOne
    private Propietario propietario;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultorio)) {
            return false;
        }
        Consultorio other = (Consultorio) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Consultorio[ id=" + getId() + " ]";
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
     * @return the longitud
     */
    public BigDecimal getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the ventanas
     */
    public Long getVentanas() {
        return ventanas;
    }

    /**
     * @param ventanas the ventanas to set
     */
    public void setVentanas(Long ventanas) {
        this.ventanas = ventanas;
    }

    /**
     * @return the baño
     */
    public Boolean getBaño() {
        return baño;
    }

    /**
     * @param baño the baño to set
     */
    public void setBaño(Boolean baño) {
        this.baño = baño;
    }

    /**
     * @return the cama
     */
    public Boolean getCama() {
        return cama;
    }

    /**
     * @param cama the cama to set
     */
    public void setCama(Boolean cama) {
        this.cama = cama;
    }

    /**
     * @return the closet
     */
    public Boolean getCloset() {
        return closet;
    }

    /**
     * @param closet the closet to set
     */
    public void setCloset(Boolean closet) {
        this.closet = closet;
    }

    /**
     * @return the ultimaFechaRemodelacion
     */
    public Date getUltimaFechaRemodelacion() {
        return ultimaFechaRemodelacion;
    }

    /**
     * @param ultimaFechaRemodelacion the ultimaFechaRemodelacion to set
     */
    public void setUltimaFechaRemodelacion(Date ultimaFechaRemodelacion) {
        this.ultimaFechaRemodelacion = ultimaFechaRemodelacion;
    }

    /**
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the piso
     */
    public Piso getPiso() {
        return piso;
    }

    /**
     * @param piso the piso to set
     */
    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    /**
     * @return the propietario
     */
    public Propietario getPropietario() {
        return propietario;
    }

    /**
     * @param propietario the propietario to set
     */
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

}
