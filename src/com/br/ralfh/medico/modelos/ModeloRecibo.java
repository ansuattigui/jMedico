package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "modelorecibo", schema = "docplus")
public class ModeloRecibo implements Serializable {    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name="nomeModelo")
    private String nomeModelo;
    @Column(name = "cabecalho")
    private String cabecalho;
    @Column(name = "corpo")
    private String corpo;
    @Column(name = "rodape")
    private String rodape;
    
    
    public ModeloRecibo() {
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
     * @return the nomeModelo
     */
    public String getNomeModelo() {
        return nomeModelo;
    }

    /**
     * @param nomeModelo the nomeModelo to set
     */
    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
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


        
}
