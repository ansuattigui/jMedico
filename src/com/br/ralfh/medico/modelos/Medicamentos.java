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
public class Medicamentos {
        
    public Medicamentos() {
    }
    
    public static Boolean excluiMedicamento(Medicamento medicamento) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Medicamento.class, medicamento.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    
    
    public static ArrayList<Medicamento> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medicamento m order by m.principio";
        TypedQuery<Medicamento> query = manager.createQuery(jpql,Medicamento.class);
        ArrayList<Medicamento> medicamentos = (ArrayList) query.getResultList();
        manager.close();        
        
        return medicamentos;
    }
    public static ArrayList<Medicamento> getLista(Grupo grupo) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medicamento m where m.grupo=:pgrupo order by m.principio";
        TypedQuery<Medicamento> query = manager.createQuery(jpql,Medicamento.class);
        query.setParameter("pgrupo",grupo);
        ArrayList<Medicamento> medicamentos = (ArrayList) query.getResultList();
        manager.close();        
        
        return medicamentos;
    }        
    
    public static ObservableList<Medicamento> getObsLista() {
        
        return FXCollections.observableArrayList(Medicamentos.getLista());
    }    
    public static ObservableList<Medicamento> getObsLista(Grupo grupo) {
        
        return FXCollections.observableArrayList(Medicamentos.getLista(grupo));
    }    

    
    public static ArrayList<String> getSPrincipio() {
        ArrayList<Medicamento> listaM = Medicamentos.getLista();
        ArrayList<String> listaS = new ArrayList<>();
        for (Medicamento medic :listaM) {
            listaS.add(medic.getPrincipio());
        }
        return listaS;
    }    
    public static ArrayList<String> getSPrincipio(Grupo grupo) {
        ArrayList<Medicamento> listaM = Medicamentos.getLista(grupo);
        ArrayList<String> listaS = new ArrayList<>();
        for (Medicamento medic :listaM) {
            listaS.add(medic.getPrincipio());
        }
        return listaS;
    }

    public static ObservableList<String> getObsSListaPrincipio() {
        
        return FXCollections.observableArrayList(Medicamentos.getSPrincipio());
    }    
    public static ObservableList<String> getObsSListaPrincipio(Grupo grupo) {
        
        return FXCollections.observableArrayList(Medicamentos.getSPrincipio(grupo));
    }    

    public static ArrayList<String> getSNomeComercial() {
        ArrayList<Medicamento> listaM = Medicamentos.getLista();
        ArrayList<String> listaS = new ArrayList<>();
        for (Medicamento medic :listaM) {
            listaS.add(medic.getNomecomercial());
        }
        return listaS;
    }    
    public static ArrayList<String> getSNomeComercial(Grupo grupo) {
        ArrayList<Medicamento> listaM = Medicamentos.getLista(grupo);
        ArrayList<String> listaS = new ArrayList<>();
        for (Medicamento medic :listaM) {
            listaS.add(medic.getNomecomercial());
        }
        return listaS;
    }

    public static ObservableList<String> getObsSListaNomeComercial() {
        
        return FXCollections.observableArrayList(Medicamentos.getSNomeComercial());
    }    
    public static ObservableList<String> getObsSListaNomeComercial(Grupo grupo) {
        
        return FXCollections.observableArrayList(Medicamentos.getSNomeComercial(grupo));
    }    
    
    
    public static Medicamento getMedicamentoPeloNome(String smedicamento) {
        
        EntityManager manager = JPAUtil.getEntityManager();
        
        String jpql = "select m from Medicamento m where m.principio = :pmedicamento";
        TypedQuery<Medicamento> query = manager.createQuery(jpql,Medicamento.class);  
        query.setParameter("pmedicamento",smedicamento);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {            
            return null;
        }
    }
    
    
}
