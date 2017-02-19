/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 *
 * @author ralfh
 */
public class ChatClient implements Runnable {
    

    

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void enviarMensagem(String msg) throws IOException{
       if(msg.equals("Sair")){
         bfw.write("Desconectado \r\n");
         texto.append("Desconectado \r\n");
       }else{
         bfw.write(msg+"\r\n");
         texto.append( txtNome.getText() + " diz -> " +         txtMsg.getText()+"\r\n");
       }
        bfw.flush();
        txtMsg.setText("");        
   }    
    
    public void escutar() throws IOException{
       InputStream in = socket.getInputStream();
       InputStreamReader inr = new InputStreamReader(in);
       BufferedReader bfr = new BufferedReader(inr);
       String msg = "";

        while(!"Sair".equalsIgnoreCase(msg))

           if(bfr.ready()){
             msg = bfr.readLine();
           if(msg.equals("Sair"))
             texto.append("Servidor caiu! \r\n");
            else
             texto.append(msg+"\r\n");         
            }
    }      
    
    public void sair() throws IOException{
        enviarMensagem("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }    
    
    
    
}
