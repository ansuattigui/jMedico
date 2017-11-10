/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author ralfh
 */
public class Conexoes {
    
    public static InetAddress getLocalHostIP() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address;
        } catch (UnknownHostException uhEx) {
            return null;
        }        
    }   
    
     public static ArrayList<Conexao> getListaOutros(Conexao conexao) {
        Usuario excecao = conexao.getUsuario();
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Conexao c where c.usuario != :excecao order by c.machine";
        TypedQuery<Conexao> query = manager.createQuery(jpql,Conexao.class);       
        query.setParameter("excecao", excecao);
        ArrayList<Conexao> conexoes = (ArrayList) query.getResultList();
        manager.close();        
        
        return conexoes;                
    }

     
    public static Conexao getConexao(String ip) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Conexao c where c.ip = :ip";
        TypedQuery<Conexao> query = manager.createQuery(jpql,Conexao.class);
        query.setParameter("ip", ip);
        Conexao conexao = query.getSingleResult();
        return conexao;
    }
   

}

