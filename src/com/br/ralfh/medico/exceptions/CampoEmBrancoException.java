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
public class CampoEmBrancoException extends Exception {

    /**
     * Creates a new instance of <code>CampoEmBrancoException</code> without
     * detail message.
     */
    public CampoEmBrancoException() {
    }

    /**
     * Constructs an instance of <code>CampoEmBrancoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CampoEmBrancoException(String msg) {
        super(msg);
    }
}
