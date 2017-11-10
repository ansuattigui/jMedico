/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.exceptions;

/**
 *
 * @author ralfh
 */
public class CampoNuloException extends Exception {

    /**
     * Creates a new instance of <code>CampoNuloException</code> without detail
     * message.
     */
    public CampoNuloException() {
    }

    /**
     * Constructs an instance of <code>CampoNuloException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CampoNuloException(String msg) {
        super(msg);
    }
        
}
