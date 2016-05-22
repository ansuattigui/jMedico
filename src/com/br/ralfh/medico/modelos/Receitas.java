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
public class Receitas {
    
    public Receitas() {
    }
    
    public static Boolean novaReceita(Receita rec) {
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
    
    public static Boolean atualizaReceita(Receita rec) {
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
    
    
    
    public static Boolean excluiReceita(Receita rec) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Receita.class, rec.getReceita_id()));  
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
    
    public static ArrayList<Receita> getLista(Paciente paciente) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from Receita r where r.paciente=:ppaciente order by r.dataEmissao desc";
        TypedQuery<Receita> query = manager.createQuery(jpql,Receita.class);
        query.setParameter("ppaciente",paciente);
        ArrayList<Receita> receitas = (ArrayList) query.getResultList();
        manager.close();        
        
        return receitas;
    }        
    
    public static ObservableList<Receita> getObsLista() {
        
        return FXCollections.observableArrayList(Receitas.getLista());
    }    
    public static ObservableList<Receita> getObsLista(Paciente paciente) {
        
        return FXCollections.observableArrayList(Receitas.getLista(paciente));
    }              
    
}
