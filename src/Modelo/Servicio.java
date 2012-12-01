package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class Servicio implements Serializable {

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
    @Column(name = "idServicio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fechaConsultaIngreso;
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Column(length = 45)
    private String obraSocial;
    private Double valor;
    @ManyToOne
    private TipoServicio tipoServicio;
    @Column(length = 45)
    private String tipoExamen;
    private Long maquinasExamen;
    @Column(length = 100)
    private String resultadoExamen;
    private Double valorRestauranteHospitalizacion;
    private Long diasHospitalizacion;
    private Long visitasHospitalizacion;
    @Column(length = 100)
    private String suministroHospitalizacion;
    private Double valorSuministroHospitalizacion;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Paciente paciente;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Servicio[ id=" + getId() + " ]";
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
     * @return the fechaConsultaIngreso
     */
    public Date getFechaConsultaIngreso() {
        return fechaConsultaIngreso;
    }

    /**
     * @param fechaConsultaIngreso the fechaConsultaIngreso to set
     */
    public void setFechaConsultaIngreso(Date fechaConsultaIngreso) {
        this.fechaConsultaIngreso = fechaConsultaIngreso;
    }

    /**
     * @return the fechaSalida
     */
    public Date getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * @return the obraSocial
     */
    public String getObraSocial() {
        return obraSocial;
    }

    /**
     * @param obraSocial the obraSocial to set
     */
    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
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
     * @return the tipoServicio
     */
    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    /**
     * @param tipoServicio the tipoServicio to set
     */
    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    /**
     * @return the tipoExamen
     */
    public String getTipoExamen() {
        return tipoExamen;
    }

    /**
     * @param tipoExamen the tipoExamen to set
     */
    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    /**
     * @return the maquinasExamen
     */
    public Long getMaquinasExamen() {
        return maquinasExamen;
    }

    /**
     * @param maquinasExamen the maquinasExamen to set
     */
    public void setMaquinasExamen(Long maquinasExamen) {
        this.maquinasExamen = maquinasExamen;
    }

    /**
     * @return the resultadoExamen
     */
    public String getResultadoExamen() {
        return resultadoExamen;
    }

    /**
     * @param resultadoExamen the resultadoExamen to set
     */
    public void setResultadoExamen(String resultadoExamen) {
        this.resultadoExamen = resultadoExamen;
    }

    /**
     * @return the valorRestauranteHospitalizacion
     */
    public Double getValorRestauranteHospitalizacion() {
        return valorRestauranteHospitalizacion;
    }

    /**
     * @param valorRestauranteHospitalizacion the valorRestauranteHospitalizacion to set
     */
    public void setValorRestauranteHospitalizacion(Double valorRestauranteHospitalizacion) {
        this.valorRestauranteHospitalizacion = valorRestauranteHospitalizacion;
    }

    /**
     * @return the diasHospitalizacion
     */
    public Long getDiasHospitalizacion() {
        return diasHospitalizacion;
    }

    /**
     * @param diasHospitalizacion the diasHospitalizacion to set
     */
    public void setDiasHospitalizacion(Long diasHospitalizacion) {
        this.diasHospitalizacion = diasHospitalizacion;
    }

    /**
     * @return the visitasHospitalizacion
     */
    public Long getVisitasHospitalizacion() {
        return visitasHospitalizacion;
    }

    /**
     * @param visitasHospitalizacion the visitasHospitalizacion to set
     */
    public void setVisitasHospitalizacion(Long visitasHospitalizacion) {
        this.visitasHospitalizacion = visitasHospitalizacion;
    }

    /**
     * @return the suministroHospitalizacion
     */
    public String getSuministroHospitalizacion() {
        return suministroHospitalizacion;
    }

    /**
     * @param suministroHospitalizacion the suministroHospitalizacion to set
     */
    public void setSuministroHospitalizacion(String suministroHospitalizacion) {
        this.suministroHospitalizacion = suministroHospitalizacion;
    }

    /**
     * @return the valorSuministroHospitalizacion
     */
    public Double getValorSuministroHospitalizacion() {
        return valorSuministroHospitalizacion;
    }

    /**
     * @param valorSuministroHospitalizacion the valorSuministroHospitalizacion to set
     */
    public void setValorSuministroHospitalizacion(Double valorSuministroHospitalizacion) {
        this.valorSuministroHospitalizacion = valorSuministroHospitalizacion;
    }

    /**
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
