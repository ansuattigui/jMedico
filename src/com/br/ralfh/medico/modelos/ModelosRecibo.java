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
public class ModelosRecibo {
    
    public ModelosRecibo() {
    }

    public static Boolean novoModeloRecibo(ModeloRecibo modelo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(modelo);  
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
    
    public static Boolean atualizaModeloRecibo(ModeloRecibo modelo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(modelo);  
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
        
    public static Boolean excluiModeloRecibo(ModeloRecibo modelo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(ModeloRecibo.class, modelo.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }             
        return resultado;        
    }
    
    
    public static ArrayList<ModeloRecibo> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select mr from ModeloRecibo mr order by mr.nomeModelo";
        TypedQuery<ModeloRecibo> query = manager.createQuery(jpql,ModeloRecibo.class);
        ArrayList modelos = (ArrayList) query.getResultList();
        manager.close();                
        return modelos;                
    }
    
    public static ModeloRecibo getModeloWithNome(String modelo) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select mr from ModeloRecibo mr where ma.nomeModelo=:pmodelo";
        TypedQuery<ModeloRecibo> query = manager.createQuery(jpql,ModeloRecibo.class);
        query.setParameter("pmodelo",modelo);
        ModeloRecibo modeloRecibo = query.getSingleResult();
        manager.close();        
        
        return modeloRecibo;                
    }
        
    public static ObservableList<ModeloRecibo> getObsLista() {        
        return FXCollections.observableArrayList(ModelosRecibo.getLista());
    }    
    
    public static ObservableList<ModeloRecibo> getObsModeloWithNome(String modelo) {        
        return FXCollections.observableArrayList(ModelosRecibo.getModeloWithNome(modelo));
    }              
    
}
