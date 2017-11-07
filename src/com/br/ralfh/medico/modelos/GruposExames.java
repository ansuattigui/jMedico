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
public class GruposExames {
    
    public GruposExames() {
    }
    
    public static Boolean novoGrupo(GrupoExames grupo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.persist(grupo);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    public static Boolean atualizaGrupo(GrupoExames grupo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(grupo);  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static Boolean excluiGrupo(GrupoExames grupo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(GrupoExames.class, grupo.getId()));  
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
    
/*    
    public static ArrayList<Receita> getLista() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from Receita r order by r.dataEmissao desc";
        TypedQuery<Receita> query = manager.createQuery(jpql,Receita.class);
        ArrayList<Receita> receitas = (ArrayList) query.getResultList();
        manager.close();        
        
        return receitas;                
    }
    
    public static ObservableList<Receita> getObsLista() {
        
        return FXCollections.observableArrayList(GruposExames.getLista());
    }    

*/
    
    public static ArrayList<GrupoExames> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select r from GrupoExames r order by r.nome desc";
        TypedQuery<GrupoExames> query = manager.createQuery(jpql,GrupoExames.class);
        ArrayList<GrupoExames> grupos = (ArrayList) query.getResultList();
        manager.close();        
        
        return grupos;
    }        
    
    public static ObservableList<GrupoExames> getObsLista() {    
        return FXCollections.observableArrayList(GruposExames.getLista());
    }              
    
}
