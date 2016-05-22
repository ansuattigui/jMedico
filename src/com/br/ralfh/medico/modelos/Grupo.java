/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Grupo implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String grupo;
    @OneToMany(mappedBy = "grupo")
    private List<Medicamento> medicamentos;
    
    public Grupo() {    
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
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the medicamentos
     */
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    /**
     * @param medicamentos the medicamentos to set
     */
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
        
}
