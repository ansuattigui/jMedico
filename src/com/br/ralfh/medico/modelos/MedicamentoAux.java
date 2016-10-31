/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ralfh
 */
public class MedicamentoAux {
    
    private StringProperty nomeComercial;
    private StringProperty principioAtivo;
    
    public MedicamentoAux(String nome, String principio) {
        this.nomeComercial = new SimpleStringProperty(nome);
        this.principioAtivo = new SimpleStringProperty(principio);
    }

    /**
     * @return the nomeComercial
     */
    public StringProperty nomeComercialProperty() {
        return nomeComercial;
    }

    public String getNomeComercial() {
        return nomeComercial.get();
    }
    
    /**
     * @param nomeComercial the nomeComercial to set
     */
    public void setNomeComercial(StringProperty nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    /**
     * @return the principioAtivo
     */
    public StringProperty principioAtivoProperty() {
        return principioAtivo;
    }

    public String getPrincipioAtivo() {
        return principioAtivo.get();
    }
    
    /**
     * @param principioAtivo the principioAtivo to set
     */
    public void setPrincipioAtivo(StringProperty principioAtivo) {
        this.principioAtivo = principioAtivo;
    }
    
}
