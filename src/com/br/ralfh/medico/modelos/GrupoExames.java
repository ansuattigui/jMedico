/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.exceptions.CampoNuloException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String sexo;
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
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
        
}
