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
public class Exames {
    
    public Exames() {
    }

    public static Boolean excluiExame(Exame exam) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Exame.class, exam.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;        
    }
    public static Boolean atualizaExame(Exame exam) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(exam);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;        
    }
    
    public static ArrayList<Exame> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select e from Exame e order by e.exame";
        TypedQuery<Exame> query = manager.createQuery(jpql,Exame.class);
        ArrayList<Exame> exames = (ArrayList) query.getResultList();
        manager.close();        

        ArrayList<Exame> uniques = new ArrayList<Exame>();
        for (Exame element : exames) {
          if (!uniques.contains(element)) {
            uniques.add(element);
          }
        }        
        
        return uniques;
    }
        
    public static ArrayList<Exame> getLista(PedidoExames pedido) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select e from Exame e where e.PedidoExames = :ppedido order by e.exame";
        TypedQuery<Exame> query = manager.createQuery(jpql,Exame.class);
        query.setParameter("ppedido", pedido);
        ArrayList<Exame> exames = (ArrayList) query.getResultList();
        manager.close();        
        
        return exames;
    }

    public static ArrayList<Exame> getListaPorMaterial(String material) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql;
        if (!"Outros".equals(material)) {
            jpql = "select distinct(e) from Exame e where e.material = :pmaterial order by e.material, e.exame";
        } else {
            jpql = "select distinct(e) from Exame e where e.material not in ('Fezes','Sangue','Urina','Imagem') order by e.material, e.exame";
        }
        TypedQuery<Exame> query = manager.createQuery(jpql,Exame.class);
        query.setParameter("pmaterial", material);
        ArrayList<Exame> exames = (ArrayList) query.getResultList();
        manager.close();    

        ArrayList<Exame> uniques = new ArrayList<Exame>();
        for (Exame element : exames) {
          if (!uniques.contains(element)) {
            uniques.add(element);
          }
        }        
        
        return uniques;
    }
    
    public static ObservableList<Exame> getObsLista(Exames pedido) {         
        return FXCollections.observableArrayList(Exames.getLista());
    }    

    public static ObservableList<Exame> getObsLista(PedidoExames pedido) {         
        return FXCollections.observableArrayList(Exames.getLista(pedido));
    }    

    public void removeLista(PedidoExames pedido) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "delete e from Exame e where e.pedido = :ppedido";
        TypedQuery<Exame> query = manager.createQuery(jpql,Exame.class);
        query.setParameter("ppedido", pedido);
        query.executeUpdate();
        manager.close();        
    }
    
    
}
