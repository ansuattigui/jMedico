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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class Medicos {
    
    public Medicos() {
    }

    public static ArrayList<Medico> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medico m order by m.nome";
        TypedQuery<Medico> query = manager.createQuery(jpql,Medico.class);
        ArrayList medicos = (ArrayList) query.getResultList();
        manager.close();                
        return medicos;                
    }

    public static Medico getMedicoWithId(int id) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medico m where m.id=:id ";
        TypedQuery<Medico> query = manager.createQuery(jpql,Medico.class);
        query.setParameter("id",id);
        Medico medico = query.getSingleResult();
        manager.close();        
        
        return medico;                
    }
    
    public static Medico getMedicoWithNome(String nome) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medico m where m.nome=:pmedico ";
        TypedQuery<Medico> query = manager.createQuery(jpql,Medico.class);
        query.setParameter("pmedico",nome);
        Medico medico = query.getSingleResult();
        manager.close();        
        
        return medico;                
    }
    
    public static Boolean isMedico(String nome) {
        Boolean resultado;
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medico m where m.nome=:pmedico";
        TypedQuery<Medico> query = manager.createQuery(jpql,Medico.class);
        query.setParameter("pmedico",nome);
        try {
            Medico med = query.getSingleResult();
            resultado = Boolean.TRUE;
        } catch (NoResultException re) {
            resultado = Boolean.FALSE;
        }
        manager.close();                
        return resultado;                
    }
    
    public static ObservableList<Medico> getObsLista() {        
        return FXCollections.observableArrayList(Medicos.getLista());
    }    
    
    public static ObservableList<Medico> getObsMedicoWithNome(String medico) {        
        return FXCollections.observableArrayList(Medicos.getMedicoWithNome(medico));
    }              
    
            
}
