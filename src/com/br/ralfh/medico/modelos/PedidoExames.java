/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.CampoNuloException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PedidoExames implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer pedido_id;    
    @ManyToOne
    private Paciente paciente;
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(nullable = false)
    private Boolean comData;
    private Boolean comRQE;
    private String indicacaoClinica;
    
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "pedido")
    @Column(nullable = false)
    private List<Exame> exames;
    
    public PedidoExames() {
        this.exames = new ArrayList<>();
        comData = Boolean.FALSE;
    }

    /**
     * @return the pedido_id
     */
    public Integer getPedido_id() {
        return pedido_id;
    }

    /**
     * @param pedido_id the pedido_id to set
     */
    public void setPedido_id(Integer pedido_id) {
        this.pedido_id = pedido_id;
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
     * @return the dataEmissao
     */
    public LocalDate getDataEmissao() {
        Instant instant = Instant.ofEpochMilli(dataEmissao.getTime());
        LocalDate dataEm = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        
        return dataEm;
    }

    /**
     * @param dataEmissao the dataEmissao to set
     */
    public void setDataEmissao(LocalDate dataEmissao) {
        
        Instant instant = dataEmissao.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date dataEm = Date.from(instant);        

        this.dataEmissao = dataEm;
    }

    /**
     * @return the exames
     */
    public List<Exame> getExames() {
        return exames;
    }

    /**
     * @param exames the exames to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
    
    public void setExames(List<Exame> exames) throws CampoNuloException {
        if (!exames.isEmpty()) {
            this.exames = exames;
        } else {
            throw new CampoNuloException("Prescreva ao menos um exame");
        }
    }         

    /**
     * @return the indicacaoClinica
     */
    public String getIndicacaoClinica() {
        return indicacaoClinica;
    }

    /**
     * @param indicacaoClinica the indicacaoClinica to set
     */
    public void setIndicacaoClinica(String indicacaoClinica) {
        this.indicacaoClinica = indicacaoClinica;
    }

    /**
     * @return the comData
     */
    public Boolean getComData() {
        return comData;
    }

    /**
     * @param comData the comData to set
     */
    public void setComData(Boolean comData) {
        this.comData = comData;
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
