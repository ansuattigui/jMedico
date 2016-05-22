package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class Procedimento_Convenio implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    @ManyToOne( fetch = FetchType.EAGER )
    private Convenio convenio;
    @ManyToOne ( fetch = FetchType.EAGER)
    private Tuss tuss;
    private BigDecimal valor;
    
    
    public Procedimento_Convenio() {
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
     * @return the convenio
     */
    public Convenio getConvenio() {
        return convenio;
    }

    /**
     * @param convenio the convenio to set
     */
    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    /**
     * @return the tuss
     */
    public Tuss getTuss() {
        return tuss;
    }

    /**
     * @param tuss the tuss to set
     */
    public void setTuss(Tuss tuss) {
        this.tuss = tuss;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    
}   