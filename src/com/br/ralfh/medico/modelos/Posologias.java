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
public class Posologias {
    
    public Posologias() {        
    }
    
    public static Boolean excluiPosologia(Posologia posologia) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Posologia.class, posologia.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    
    public ArrayList<Posologia> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Posologia> query = manager.createQuery("select p from Posologia p order by p.posologia",Posologia.class);
        ArrayList<Posologia> posologias = (ArrayList) query.getResultList();
        manager.close();        
        
        return posologias;
    }    
    public ObservableList<Posologia> getObsLista() {
        
        return FXCollections.observableArrayList(this.getLista());
    } 
    
    public ArrayList<String> getS() {
        ArrayList<Posologia> listaM = this.getLista();
        ArrayList<String> listaS = new ArrayList<>();
        for (Posologia posologia :listaM) {
            listaS.add(posologia.getPosologia());
        }
        return listaS;
    }
    public ObservableList<String> getObsSLista() {
        
        return FXCollections.observableArrayList(this.getS());
    }
    
    public Posologia getModoPeloNome(String sposologia) {
        
        EntityManager manager = JPAUtil.getEntityManager();
        
        String jpql = "select p from Posologia p where p.posologia=:pposologia";
        TypedQuery<Posologia> query = manager.createQuery(jpql,Posologia.class);  
        query.setParameter("pposologia",sposologia);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
}
