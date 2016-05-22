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
public class Atestados {
    
    public Atestados() {
    }

    public static Boolean novoAtestado(Atestado atestado) {
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

    public static Boolean atualizaAtestado(Atestado atestado) {
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
    
    
    public static Boolean excluiAtestado(Atestado atestado) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Atestado.class, atestado.getId()));  
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
    
    
    public static ArrayList<Atestado> getLista(Paciente paciente) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select at from Atestado at where at.paciente = :parmPaciente order by at.data desc";
        TypedQuery<Atestado> query = manager.createQuery(jpql,Atestado.class);
        query.setParameter("parmPaciente", paciente);
        ArrayList atestados = (ArrayList) query.getResultList();
        manager.close();                
        return atestados;                
    }
    
        
    public static ObservableList<Atestado> getObsLista(Paciente paciente) {        
        return FXCollections.observableArrayList(Atestados.getLista(paciente));
    }    
    
}
