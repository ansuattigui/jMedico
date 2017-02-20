/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.chat;

import com.br.ralfh.medico.DialogGUI;
import com.br.ralfh.medico.JDocplus;
import com.br.ralfh.medico.modelos.Conexao;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Ralfh
 */
public class ChatConexao extends Observable {
    
    private String ip;
    private int porta;
    private String mensagem;
    
    private DialogGUI dialog;

    public ChatConexao(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
        new Thread(new Recebe()).start();
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public void envia(String texto, String ipAddr) {
        if (dialog == null) {
            chat("");
        }
                
        new Thread(new Envia(texto,ipAddr)).start();
    }

    public void notifica(String mensagem) {
        this.mensagem = mensagem;
        setChanged();
        notifyObservers();
    }

    class Recebe implements Runnable {

        byte[] dadosReceber = new byte[255];
        boolean erro = false;
        DatagramSocket socket = null;

        @Override
        public void run() {
                        
            while (true) {
                try {
                    socket = new DatagramSocket(getPorta());
                } catch (SocketException ex) {
                    Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                }
                erro = false;
                while (!erro) {
                    DatagramPacket pacoteRecebido = new DatagramPacket(dadosReceber, dadosReceber.length);
                    try {
                        socket.receive(pacoteRecebido);
                        byte[] b = pacoteRecebido.getData();
                        String s = "";
                        for (int i = 0; i < b.length; i++) {
                            if (b[i] != 0) {
                                s += (char) b[i];
                            }
                        }
                        String nome = pacoteRecebido.getAddress().toString() + " disse:";
                        notifica(nome + s);
                    } catch (Exception e) {
                        System.out.println("erro");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        erro = true;
                        continue;
                    }
                }
            }
        }
    }

    class Envia implements Runnable {

        String texto;
        String ipAddr;

        public Envia(String texto, String ipAddr) {
            this.texto = texto;
            this.ipAddr = ipAddr;
        }

        @Override
        public void run() {

            byte[] dados = texto.getBytes();

            try {
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(ipAddr);
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, addr, getPorta());
                clientSocket.send(pacote);
                clientSocket.close();
            } catch (SocketException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void chat(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dialog = null;
                try {
                    dialog = new DialogGUI("CT",msg, null,JDocplus.getMainStage());
                    dialog.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(ChatConexao.class.getName()).log(Level.SEVERE, null, ex);
                }                    
            }
        });
    }
    
}    