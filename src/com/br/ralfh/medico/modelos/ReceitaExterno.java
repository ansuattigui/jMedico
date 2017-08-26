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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "receita_nao_pacientes", schema = "docplus")
public class ReceitaExterno implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer receita_id;    
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    private String paciente;
    @OneToMany
    @Column(nullable = false)
    private List<PrescricaoExterno> prescricoes;
    
    public ReceitaExterno() {
        this.prescricoes = new ArrayList<>();
    }

    /**
     * @return the receita_id
     */
    public Integer getReceita_id() {
        return receita_id;
    }

    /**
     * @param receita_id the receita_id to set
     */
    public void setReceita_id(Integer receita_id) {
        this.receita_id = receita_id;
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
     * @return the prescricoes
     */
    public List<PrescricaoExterno> getPrescricoes() {
        return prescricoes;
    }

    /**
     * @param prescricoes the prescricoes to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
    
    public void setPrescricoes(List<PrescricaoExterno> prescricoes) throws CampoNuloException {
        if (!prescricoes.isEmpty()) {
            this.prescricoes = prescricoes;
        } else {
            throw new CampoNuloException("Prescreva ao menos um medicamento");
        }
    }         

    /**
     * @return the paciente
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
    
}
