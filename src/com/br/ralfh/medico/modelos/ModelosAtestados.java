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
public class ModelosAtestados {
    
    public ModelosAtestados() {
    }

    public static Boolean novoModeloAtestado(ModeloAtestado modelo) {
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
    
    public static Boolean atualizaModeloAtestado(ModeloAtestado modelo) {
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
        
    public static Boolean excluiModeloAtestado(ModeloAtestado modelo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(ModeloAtestado.class, modelo.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }             
        return resultado;        
    }
    
    
    public static ArrayList<ModeloAtestado> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select ma from ModeloAtestado ma order by ma.nomeModelo";
        TypedQuery<ModeloAtestado> query = manager.createQuery(jpql,ModeloAtestado.class);
        ArrayList modelos = (ArrayList) query.getResultList();
        manager.close();                
        return modelos;                
    }
    
    public static ModeloAtestado getModeloWithNome(String modelo) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select ma from ModeloAtestado ma where ma.nomeModelo=:pmodelo";
        TypedQuery<ModeloAtestado> query = manager.createQuery(jpql,ModeloAtestado.class);
        query.setParameter("pmodelo",modelo);
        ModeloAtestado modeloAtestado = query.getSingleResult();
        manager.close();        
        
        return modeloAtestado;                
    }
        
    public static ObservableList<ModeloAtestado> getObsLista() {        
        return FXCollections.observableArrayList(ModelosAtestados.getLista());
    }    
    
    public static ObservableList<ModeloAtestado> getObsModeloWithNome(String modelo) {        
        return FXCollections.observableArrayList(ModelosAtestados.getModeloWithNome(modelo));
    }              
    
}
