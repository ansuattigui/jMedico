package com.br.ralfh.medico.modelos;

import java.io.Serializable;

public class CEP implements Serializable {
    private Integer id;
    private String cidade;
    private String logradouro;
    private String bairro;
    private String cep;
    private String tp_logradouro;
    
    public CEP() {
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
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the tp_logradouro
     */
    public String getTp_logradouro() {
        return tp_logradouro;
    }

    /**
     * @param tp_logradouro the tp_logradouro to set
     */
    public void setTp_logradouro(String tp_logradouro) {
        this.tp_logradouro = tp_logradouro;
    }
    
}
