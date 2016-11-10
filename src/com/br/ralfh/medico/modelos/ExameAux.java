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
 * @author ralfh
 */
public class ExameAux {

    private StringProperty nomeExame;
    
    public ExameAux(String nome) {
        this.nomeExame = new SimpleStringProperty(nome);
    }

    /**
     * @return the nomeExame
     */
    public StringProperty nomeExameProperty() {
        return nomeExame;
    }

    public String getNomeExame() {
        return nomeExame.get();
    }
    
    /**
     * @param nomeExame the nomeExame to set
     */
    public void setNomeExame(StringProperty nomeExame) {
        this.nomeExame = nomeExame;
    }

    
}
