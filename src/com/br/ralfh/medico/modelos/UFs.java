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
public class UFs {
        
    public UFs() {
    }
    
    
    public static ArrayList<UF> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select u from UF u order by u.uf";
        TypedQuery<UF> query = manager.createQuery(jpql,UF.class);
        ArrayList<UF> ufs = (ArrayList) query.getResultList();
        manager.close();                
        return ufs;
    }
    public static ObservableList<UF> getObsLista() {        
        return FXCollections.observableArrayList(UFs.getLista());
    }            
    
    public static UF getUFPelaSigla(String suf) {        
        EntityManager manager = JPAUtil.getEntityManager();        
        String jpql = "select uf from UF uf where uf.uf = :puf";
        TypedQuery<UF> query = manager.createQuery(jpql,UF.class);  
        query.setParameter("puf",suf);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {            
            return null;
        }
    }
    
    public static UF getUFPeloCep(String cep) {
        String pcep = cep.substring(0,4);
        EntityManager manager = JPAUtil.getEntityManager();        
        String jpql = "select uf from UF uf where :pcep between uf.cep1 and uf.cep2";
        TypedQuery<UF> query = manager.createQuery(jpql,UF.class);  
        query.setParameter("pcep",pcep);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {            
            return null;
        }
    }
    
    public static ArrayList<String> getNaturalidades() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select upper(u.nome) from UF u order by u.nome";
        TypedQuery<String> query = manager.createQuery(jpql,String.class);
        ArrayList<String> nat = (ArrayList) query.getResultList();
        manager.close();                
        return nat;
    }
    
    
    
}
