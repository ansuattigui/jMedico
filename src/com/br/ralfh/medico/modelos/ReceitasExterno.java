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
public class ReceitasExterno {
    
    public ReceitasExterno() {
    }
    
    public static Boolean novaReceita(ReceitaExterno rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(rec);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    public static Boolean atualizaReceita(ReceitaExterno rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(rec);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static Boolean excluiPrescricao(ReceitaExterno rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            //manager.merge(rec);  
            manager.merge(manager.getReference(ReceitaExterno.class, rec.getReceita_id()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    
    
    public static Boolean excluiReceita(ReceitaExterno rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(ReceitaExterno.class, rec.getReceita_id()));  
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
    
    
    public static ArrayList<ReceitaExterno> getLista() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from ReceitaExterno r order by r.dataEmissao desc";
        TypedQuery<ReceitaExterno> query = manager.createQuery(jpql,ReceitaExterno.class);
        ArrayList<ReceitaExterno> receitas = (ArrayList) query.getResultList();
        manager.close();        
        
        return receitas;                
    }
    
    public static ArrayList<ReceitaExterno> getLista(String paciente) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from ReceitaExterno r where r.paciente=:ppaciente order by r.dataEmissao desc";
        TypedQuery<ReceitaExterno> query = manager.createQuery(jpql,ReceitaExterno.class);
        query.setParameter("ppaciente",paciente);
        ArrayList<ReceitaExterno> receitas = (ArrayList) query.getResultList();
        manager.close();        
        
        return receitas;
    }        
    
    public static ObservableList<ReceitaExterno> getObsLista() {
        
        return FXCollections.observableArrayList(ReceitasExterno.getLista());
    }    
    public static ObservableList<ReceitaExterno> getObsLista(String paciente) {
        
        return FXCollections.observableArrayList(ReceitasExterno.getLista(paciente));
    }              
    
}
