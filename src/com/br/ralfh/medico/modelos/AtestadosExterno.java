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
public class AtestadosExterno {
    
    public AtestadosExterno() {
    }

    public static Boolean novoAtestado(AtestadoExterno atestado) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(atestado);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static Boolean atualizaAtestado(AtestadoExterno atestado) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(atestado);  
//            manager.merge(manager.getReference(Atestado.class, atestado.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    
    public static Boolean excluiAtestado(AtestadoExterno atestado) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(AtestadoExterno.class, atestado.getId()));  
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
    
    public static ArrayList<AtestadoExterno> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select atex from AtestadoExterno atex order by atex.paciente,atex.data desc";
        TypedQuery<AtestadoExterno> query = manager.createQuery(jpql,AtestadoExterno.class);
        ArrayList atestados = (ArrayList) query.getResultList();
        manager.close();                
        return atestados;                
    }
    public static ObservableList<AtestadoExterno> getObsLista() {        
        return FXCollections.observableArrayList(AtestadosExterno.getLista());
    }    
    
    
    public static ArrayList<AtestadoExterno> getLista(String paciente) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select atnp from AtestadoNaoPaciente atnp where atnp.paciente = :parmPaciente order by at.data desc";
        TypedQuery<AtestadoExterno> query = manager.createQuery(jpql,AtestadoExterno.class);
        query.setParameter("parmPaciente", paciente);
        ArrayList atestados = (ArrayList) query.getResultList();
        manager.close();                
        return atestados;                
    }    
        
    public static ObservableList<AtestadoExterno> getObsLista(String paciente) {        
        return FXCollections.observableArrayList(AtestadosExterno.getLista(paciente));
    }    
    
}
