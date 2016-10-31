package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Medicamento implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;
    private String principio;
    @Column(unique = true)
    private String nomecomercial;
    @ManyToOne
    private Grupo grupo;
    
    public Medicamento() {
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
     * @return the principio
     */
    public String getPrincipio() {
        return principio;
    }

    /**
     * @param principio the principio to set
     */
    public void setPrincipio(String principio) {
        this.principio = principio;
    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the nomecomercial
     */
    public String getNomecomercial() {
        return nomecomercial;
    }

    /**
     * @param nomecomercial the nomecomercial to set
     */
    public void setNomecomercial(String nomecomercial) {
        this.nomecomercial = nomecomercial;
    }
    
}
