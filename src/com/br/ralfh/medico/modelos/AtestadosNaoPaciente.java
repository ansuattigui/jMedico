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
public class AtestadosNaoPaciente {
    
    public AtestadosNaoPaciente() {
    }

    public static Boolean novoAtestado(AtestadoNaoPaciente atestado) {
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

    public static Boolean atualizaAtestado(AtestadoNaoPaciente atestado) {
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
    
    
    public static Boolean excluiAtestado(AtestadoNaoPaciente atestado) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(AtestadoNaoPaciente.class, atestado.getId()));  
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
    
    
    public static ArrayList<AtestadoNaoPaciente> getLista(String paciente) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select atnp from AtestadoNaoPaciente atnp where atnp.paciente = :parmPaciente order by at.data desc";
        TypedQuery<AtestadoNaoPaciente> query = manager.createQuery(jpql,AtestadoNaoPaciente.class);
        query.setParameter("parmPaciente", paciente);
        ArrayList atestados = (ArrayList) query.getResultList();
        manager.close();                
        return atestados;                
    }
    
        
    public static ObservableList<AtestadoNaoPaciente> getObsLista(String paciente) {        
        return FXCollections.observableArrayList(AtestadosNaoPaciente.getLista(paciente));
    }    
    
}
