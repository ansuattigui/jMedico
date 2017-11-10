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
public class Tusss {
        
    public Tusss() {
    }
    
    public static Boolean excluiProcedimento(Tuss procedimento) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Tuss.class, procedimento.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    
    
    public static ArrayList<Tuss> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select t from Tuss t order by t.codigo";
        TypedQuery<Tuss> query = manager.createQuery(jpql,Tuss.class);
        ArrayList<Tuss> procedimentos = (ArrayList) query.getResultList();
        manager.close();        
        
        return procedimentos;
    }
    public static ArrayList<Tuss> getLista(Grupo grupo) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select t from Tuss t where t.grupo=:pgrupo order by t.codigo";
        TypedQuery<Tuss> query = manager.createQuery(jpql,Tuss.class);
        query.setParameter("pgrupo",grupo);
        ArrayList<Tuss> procedimentos = (ArrayList) query.getResultList();
        manager.close();        
        
        return procedimentos;
    }        
    
    public static ObservableList<Tuss> getObsLista() {        
        return FXCollections.observableArrayList(Tusss.getLista());
    }    
    public static ObservableList<Tuss> getObsLista(Grupo grupo) {        
        return FXCollections.observableArrayList(Tusss.getLista(grupo));
    }    

    
    public static ArrayList<String> getSProcedimento() {
        ArrayList<Tuss> listaT = Tusss.getLista();
        ArrayList<String> listaS = new ArrayList<>();
        for (Tuss proced :listaT) {
            listaS.add(proced.getProcedimento());
        }
        return listaS;
    }    
    public static ArrayList<String> getSProcedimento(Grupo grupo) {
        ArrayList<Tuss> listaT = Tusss.getLista(grupo);
        ArrayList<String> listaS = new ArrayList<>();
        for (Tuss proced :listaT) {
            listaS.add(proced.getProcedimento());
        }
        return listaS;
    }

    public static ObservableList<String> getObsSListaProcedimento() {        
        return FXCollections.observableArrayList(Tusss.getSProcedimento());
    }    
    public static ObservableList<String> getObsSListaProcedimento(Grupo grupo) {        
        return FXCollections.observableArrayList(Tusss.getSProcedimento(grupo));
    }        
    
    public static Tuss getProcedimentoPeloNome(String sprocedimento) {        
        EntityManager manager = JPAUtil.getEntityManager();        
        String jpql = "select t from Tuss t where t.procedimento = :pprocedimento";
        TypedQuery<Tuss> query = manager.createQuery(jpql,Tuss.class);  
        query.setParameter("pprocedimento",sprocedimento);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {            
            return null;
        }
    }        
}
