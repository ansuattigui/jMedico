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
public class Grupos {
    
    public Grupos() {        
    }
    
    public static Boolean excluiGrupo(Grupo grupo) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Grupo.class, grupo.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static ArrayList<Grupo> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Grupo> query = manager.createQuery("select g from Grupo g order by g.grupo",Grupo.class);
        ArrayList<Grupo> grupos = (ArrayList) query.getResultList();
        manager.close();        
        
        return grupos;
    }
    
    public static ObservableList<Grupo> getObsLista() {        
        return FXCollections.observableArrayList(Grupos.getLista());                        
    }       
    
    public static ArrayList<String> getS() {
        ArrayList<Grupo> listaM = Grupos.getLista();
        ArrayList<String> listaS = new ArrayList<>();
        listaS.add("");
        for (Grupo grupo :listaM) {
            listaS.add(grupo.getGrupo());
        }
        return listaS;
    }    
    public static ObservableList<String> getObsSLista() {        
        return FXCollections.observableArrayList(getS());
    }    
    
    public static Grupo getGrupoPeloNome(String sgrupo) {        
        EntityManager manager = JPAUtil.getEntityManager();        
        String jpql = "select g from Grupo g where g.grupo=:pgrupo";
        TypedQuery<Grupo> query = manager.createQuery(jpql,Grupo.class);  
        query.setParameter("pgrupo",sgrupo);
        Grupo grupo = query.getSingleResult();
        
        return grupo;
    }
    
}
