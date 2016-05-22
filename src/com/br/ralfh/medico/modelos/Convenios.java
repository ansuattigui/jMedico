/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class Convenios {
    
    public Convenios() {
    }
    
    public static Boolean novoConvenio(Convenio conv) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(conv);  
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
    
    public static Boolean atualizaConvenio(Convenio conv) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(conv);  
//            manager.merge(manager.getReference(Atestado.class, atestado.getId()));  
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
    
    public static Boolean excluiConvenio(Convenio conv) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Convenio.class, conv.getId()));  
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
    
    
    public static ArrayList<Convenio> getLista() {        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Convenio c where not substring(c.nome,2,1) in :numeros order by c.nome";      
        TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
        
        List<String> numeros = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
        query.setParameter("numeros", numeros);
        
        ArrayList<Convenio> convenios = (ArrayList) query.getResultList();
        manager.close();        
        
        return convenios;                
    }

    public static ArrayList<String> getListaNomes() {        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c.nome from Convenio c where not substring(c.nome,2,1) in :numeros order by c.nome";
        TypedQuery<String> query = manager.createQuery(jpql,String.class);
        List<String> numeros = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
        query.setParameter("numeros", numeros);
        ArrayList<String> convenios = (ArrayList) query.getResultList();
        manager.close();        
        
        return convenios;                
    }
    
    public static Convenio getConvenioWithCod(Integer codcon) {
        Convenio convenio = null;
        if ((codcon!=null)&&(codcon>0)) {
            EntityManager manager = JPAUtil.getEntityManager();
            String jpql = "select c from Convenio c where c.id = :pcod";
            TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
            query.setParameter("pcod",codcon);
            convenio = query.getSingleResult();
            manager.close();        
        }         
        return convenio;
    }        

    public static ArrayList<Convenio> getListaWithCnpj(String convenio) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Convenio c where c.cnpj like :pcnpj order by c.nome";
        TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
        query.setParameter("pcnpj",convenio);
        ArrayList<Convenio> convenios = (ArrayList) query.getResultList();
        manager.close();        
        
        return convenios;
    }        
    
    public static ArrayList<Convenio> getListaWithcodANS(String convenio) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Convenio c where c.codigoANS like :pans order by c.nome";
        TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
        query.setParameter("pans",convenio);
        ArrayList<Convenio> convenios = (ArrayList) query.getResultList();
        manager.close();        
        
        return convenios;
    }        

    public static Convenio getConvenioWithNome(String nome) {
        Convenio convenio = null;
        if (!nome.isEmpty()) {
            EntityManager manager = JPAUtil.getEntityManager();
            String jpql = "select c from Convenio c where c.nome = :pnome order by c.nome";
            TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
            query.setParameter("pnome",nome);
            convenio = query.getSingleResult();
            manager.close();        
        }
        return convenio;
    }            
    
    public static List<Convenio> getListaWithNome(String convenio) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select c from Convenio c where c.nome LIKE :pnome order by c.nome";
        TypedQuery<Convenio> query = manager.createQuery(jpql,Convenio.class);
        query.setParameter("pnome",convenio+'%');
        List<Convenio> convenios = query.getResultList();
        manager.close();        
        
        return convenios;
    }        
    
    public static ObservableList<Convenio> getObsLista() {        
        return FXCollections.observableArrayList(Convenios.getLista());
    }    
    public static ObservableList<Convenio> getObsListaWithNome(String convenio) {        
        return FXCollections.observableArrayList(Convenios.getListaWithNome(convenio));
    }              
    public static ObservableList<Convenio> getObsListaWithCnpj(String convenio) {        
        return FXCollections.observableArrayList(Convenios.getListaWithCnpj(convenio));
    }  
    public static ObservableList<Convenio> getObsListaWithcodANS(String convenio) {        
        return FXCollections.observableArrayList(Convenios.getListaWithcodANS(convenio));
    }  
            
}
