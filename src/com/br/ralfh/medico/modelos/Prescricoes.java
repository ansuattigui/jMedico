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
public class Prescricoes {
    
    public Prescricoes() {
    }

    public static Boolean excluiPrescricao(Prescricao prescr) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Prescricao.class, prescr.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;        
    }
    
    
    
    public ArrayList<Prescricao> getLista(Receita receita) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select p from Prescricao p where p.receita = :preceita order by p.medicamento";
        TypedQuery<Prescricao> query = manager.createQuery(jpql,Prescricao.class);
        query.setParameter("preceita", receita);
        ArrayList<Prescricao> prescricoes = (ArrayList) query.getResultList();
        manager.close();        
        
        return prescricoes;
    }
    
    public ObservableList<Prescricao> getObsLista(Receita receita) { 
        
        return FXCollections.observableArrayList(this.getLista(receita));
    }    

    public void removeLista(Receita receita) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "delete p from Prescricao p where p.receita = :preceita";
        TypedQuery<Prescricao> query = manager.createQuery(jpql,Prescricao.class);
        query.setParameter("preceita", receita);
        query.executeUpdate();
        manager.close();        
    }
    
    
}
