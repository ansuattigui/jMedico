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
public class Recibos {
    
    public Recibos() {
    }

    public static Boolean novoRecibo(Recibo recibo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(recibo);  
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

    public static Boolean atualizaRecibo(Recibo recibo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(recibo);  
//            manager.merge(manager.getReference(Atestado.class, atestado.getId()));  
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
    
    
    public static Boolean excluiRecibo(Recibo recibo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Recibo.class, recibo.getId()));  
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
    
    
    public static ArrayList<Recibo> getLista(Paciente paciente) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select rc from Recibo rc where rc.paciente = :parmPaciente order by rc.data desc";
        TypedQuery<Recibo> query = manager.createQuery(jpql,Recibo.class);
        query.setParameter("parmPaciente", paciente);
        ArrayList recibos = (ArrayList) query.getResultList();
        manager.close();                
        return recibos;                
    }
    
        
    public static ObservableList<Recibo> getObsLista(Paciente paciente) {        
        return FXCollections.observableArrayList(Recibos.getLista(paciente));
    }    
    
}
