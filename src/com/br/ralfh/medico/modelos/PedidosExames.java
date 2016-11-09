/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class PedidosExames {
    
    public PedidosExames() {
    }
    
    public static Boolean novoPedido(PedidoExames ped) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(ped);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    public static Boolean atualizaPedido(PedidoExames ped) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(ped);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static Boolean excluiPrescricao(Receita rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            //manager.merge(rec);  
            manager.merge(manager.getReference(Receita.class, rec.getReceita_id()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    
    
    public static Boolean excluiPedido(PedidoExames ped) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Receita.class, ped.getPedido_id()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        } finally {
            return resultado;
        }
    }
    
    
    public static ArrayList<Receita> getLista() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from Receita r order by r.dataEmissao desc";
        TypedQuery<Receita> query = manager.createQuery(jpql,Receita.class);
        ArrayList<Receita> receitas = (ArrayList) query.getResultList();
        manager.close();        
        
        return receitas;                
    }
    
    public static ArrayList<PedidoExames> getLista(Paciente paciente) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from PedidoExames r where r.paciente=:ppaciente order by r.dataEmissao desc";
        TypedQuery<PedidoExames> query = manager.createQuery(jpql,PedidoExames.class);
        query.setParameter("ppaciente",paciente);
        ArrayList<PedidoExames> pedidos = (ArrayList) query.getResultList();
        manager.close();        
        
        return pedidos;
    }        
    
    public static ObservableList<Receita> getObsLista() {
        
        return FXCollections.observableArrayList(PedidosExames.getLista());
    }    
    public static ObservableList<PedidoExames> getObsLista(Paciente paciente) {
        
        return FXCollections.observableArrayList(PedidosExames.getLista(paciente));
    }              
    
}
