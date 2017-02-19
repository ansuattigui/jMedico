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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ralfh
 */
public class ChatServer implements Runnable {
    
    private static ArrayList<BufferedWriter> clients;           
    private static ServerSocket server; 
    private String name;
    private Socket con;
    private InputStream is;  
    private InputStreamReader isr;  
    private BufferedReader bfr;       
    
    
    public ChatServer(Socket con){
        this.con = con;
        try {
            is  = con.getInputStream();
            isr = new InputStreamReader(is);
            bfr = new BufferedReader(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }                          
    }       
    
    public void run(){                      
        try{
            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw); 
            clients.add(bfw);
            name = msg = bfr.readLine();

            while(!"Sair".equalsIgnoreCase(msg) && msg != null)
              {           
               msg = bfr.readLine();
               sendToAll(bfw, msg);
               System.out.println(msg);                                              
               }

        }catch (Exception e) {
            e.printStackTrace();
        }                       
    }         
    
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException {
        BufferedWriter bwS;

        for(BufferedWriter bw : clients){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(name + " -> " + msg+"\r\n");
                bw.flush(); 
            }
        }          
    }    
    
}
