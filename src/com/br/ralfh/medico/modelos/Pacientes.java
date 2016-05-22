/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import com.br.ralfh.medico.Util;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class Pacientes {
    
    public Pacientes() {
    }
    
    private static final String QUERY_LISTA_WITH_CODANT = "from Paciente as pac where pac.codAntigo like :codAnt order by pac.nome";
    private static final String QUERY_PROX_COD_ANT = "select max(codAntigo) from Paciente as ca";

    
    public static Integer getProcCodAnt() {

        EntityManager manager = JPAUtil.getEntityManager();
        Query query = manager.createQuery(QUERY_PROX_COD_ANT);
        Integer ca = (Integer) query.getSingleResult();
        manager.close();        
        
        return ++ca;                
    }
    
    
    public static Boolean novoPaciente(Paciente pac, EntityManager manager) {
        Boolean resultado = Boolean.FALSE;
        try {
            manager.persist(pac); 
            resultado = Boolean.TRUE;
            
            PrimeiraConsulta pc = new PrimeiraConsulta();
            pc.setId(pac);
            pc.setData(Util.dHoje());
            
            manager.persist(pc);
            
//            manager.getTransaction().commit();
//            manager.close();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    public static Boolean atualizaPaciente(Paciente pac) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.merge(pac);  
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

    public static Boolean excluiPaciente(Paciente pac) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Paciente.class, pac.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public ArrayList<Paciente> getLista() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select p from Paciente p order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class);
        ArrayList<Paciente> pacientes = (ArrayList) query.getResultList();
        manager.close();        
        
        return pacientes;                
    }

    public static ArrayList<Paciente> getListaWithCod(Integer paciente) {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select p from Paciente p where p.id like :pcod order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class);
        query.setParameter("pcod",paciente);
        ArrayList<Paciente> pacientes = (ArrayList) query.getResultList();
        manager.close();        
        
        return pacientes;
    }        

    public static ArrayList<Paciente> getListaWithCodAnt(Integer paciente) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select p from Paciente p where p.codAntigo like :pcodant order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class);
        query.setParameter("pcodant",paciente);
        ArrayList<Paciente> pacientes = (ArrayList) query.getResultList();
        manager.close();        
        
        return pacientes;
    }        
    
    public static ArrayList<Paciente> getListaWithNome(String paciente) {
        ArrayList<Paciente> pacs;
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select p from Paciente p where p.nome LIKE :pnome order by p.nome";
        TypedQuery<Paciente> query = manager.createQuery(jpql,Paciente.class); 
        query.setParameter("pnome",paciente+'%');
        try {
            pacs = (ArrayList) query.getResultList();
        } catch(EntityNotFoundException enf) {
            pacs = null;
        }
        manager.close();        
        
        return pacs;
    }        
    
    public ObservableList<Paciente> getObsLista() {        
        return FXCollections.observableArrayList(this.getLista());
    }    
    public static ObservableList<Paciente> getObsListaWithNome(String pac) {
        List<Paciente> pacientes = getListaWithNome(pac);
        if (pacientes != null) 
            return FXCollections.observableList(pacientes);
        else
            return null;
    }              
    public static ObservableList<Paciente> getObsListaWithCod(Integer paciente) {        
        return FXCollections.observableArrayList(getListaWithCod(paciente));
    }              
    public static ObservableList<Paciente> getObsListaWithCodAnt(Integer paciente) {        
        return FXCollections.observableArrayList(getListaWithCodAnt(paciente));
    }  
            
}
