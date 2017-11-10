package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UF implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;
    private String uf;
    private String nome;
    private String cep1;
    private String cep2;
    
    public UF() {
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
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
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
     * @return the cep1
     */
    public String getCep1() {
        return cep1;
    }

    /**
     * @param cep1 the cep1 to set
     */
    public void setCep1(String cep1) {
        this.cep1 = cep1;
    }

    /**
     * @return the cep2
     */
    public String getCep2() {
        return cep2;
    }

    /**
     * @param cep2 the cep2 to set
     */
    public void setCep2(String cep2) {
        this.cep2 = cep2;
    }

}
