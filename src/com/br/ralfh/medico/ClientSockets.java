/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

/**
 *
 * @author ralfh
 */
public class ClientSockets extends Thread {
    
    private String mensagem;
    
    public ClientSockets (String msg) {
        mensagem = msg;
    }
    
    @Override
    public void run() {
        
    }

}
