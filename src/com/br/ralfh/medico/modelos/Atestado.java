package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity @Table(name = "tab_atestados_paciente", schema = "docplus")
public class Atestado implements Serializable {    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAtestado")
    private Integer id;
    @Column(name="DataAtestado")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @Column(name="TipoAtestado")
    private String tipo;
    @Column(name = "cabecalho")
    private String cabecalho;
    @Column(name = "atestado")
    private String corpo;
    @Column(name = "rodape")
    private String rodape;
    @Column(name = "comRQE")
    private Boolean comRQE;
    @ManyToOne 
    @JoinColumn(name="IDPaciente", nullable=false, updatable=false)
    private Paciente paciente;
    
    public Atestado() {
        id = -1;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cabecalho
     */
    public String getCabecalho() {
        return cabecalho;
    }

    /**
     * @param cabecalho the cabecalho to set
     */
    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    /**
     * @return the corpo
     */
    public String getCorpo() {
        return corpo;
    }

    /**
     * @param corpo the corpo to set
     */
    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    /**
     * @return the rodape
     */
    public String getRodape() {
        return rodape;
    }

    /**
     * @param rodape the rodape to set
     */
    public void setRodape(String rodape) {
        this.rodape = rodape;
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

    /**
     * @return the comRQE
     */
    public Boolean getComRQE() {
        return comRQE;
    }

    /**
     * @param comRQE the comRQE to set
     */
    public void setComRQE(Boolean comRQE) {
        this.comRQE = comRQE;
    }

}
