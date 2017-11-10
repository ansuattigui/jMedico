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
public class FormatoNumericoInvalidoException extends NumberFormatException {

    /**
     * Creates a new instance of <code>FormatoNumericoInvalidoException</code> without
     * detail message.
     */
//    public FormatoNumericoInvalidoException() {
//    }

    /**
     * Constructs an instance of <code>FormatoNumericoInvalidoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FormatoNumericoInvalidoException(String msg) {
        super(msg);
    }
}
