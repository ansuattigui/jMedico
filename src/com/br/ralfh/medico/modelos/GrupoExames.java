/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
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
import javax.persistence.OneToMany;

@Entity
public class GrupoExames implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    private String nome;
    private String indicacaoClinica;    
    private String material;
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "grupo")
    @Column(nullable = false)
    private List<ExamesGrupo> exames;
    
    public GrupoExames() {
        this.exames = new ArrayList<>();
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    

    /**
     * @return the exames
     */
    public List<ExamesGrupo> getExames() {
        return exames;
    }

    /**
     * @param exames the exames to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
    
    public void setExames(List<ExamesGrupo> exames) throws CampoNuloException {
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
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setIndicacaoClinica(String indicacaoClinica) throws CampoEmBrancoException {
        if (indicacaoClinica.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe a indicação clínica");
        } else {
            this.indicacaoClinica = indicacaoClinica;
        }
    }
    
    
    /**
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setMaterial(String material) throws CampoEmBrancoException {
        if (material.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe o material do exame");
        } else {
            this.material = material;
        }
    }
    
}
