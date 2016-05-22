/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import com.br.ralfh.medico.Util;
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
public class Usuarios {
    
    public Usuarios() {
    }

    public static ArrayList<Usuario> getLista() {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select u from Usuario u order by u.usuario";
        TypedQuery<Usuario> query = manager.createQuery(jpql,Usuario.class);
        ArrayList usuarios = (ArrayList) query.getResultList();
        manager.close();                
        return usuarios;                
    }
    
    public static Usuario getUsuarioWithNome(String nome) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select u from Usuario u where u.usuario=:pusuario ";
        TypedQuery<Usuario> query = manager.createQuery(jpql,Usuario.class);
        query.setParameter("pusuario",nome);
        Usuario usuario = query.getSingleResult();
        manager.close();        
        
        return usuario;                
    }
    
    public static Boolean isUsuario(String nome) {
        Boolean resultado;
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select u from Usuario u where u.usuario=:pusuario";
        TypedQuery<Usuario> query = manager.createQuery(jpql,Usuario.class);
        query.setParameter("pusuario",nome);
        try {
            Usuario usu = query.getSingleResult();
            resultado = Boolean.TRUE;
        } catch (NoResultException re) {
            resultado = Boolean.FALSE;
        }
        manager.close();                
        return resultado;                
    }

    public static Boolean checaSenha(String user, String passw) {
        Boolean resultado=Boolean.FALSE;
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select u from Usuario u where u.usuario=:pusuario";
        TypedQuery<Usuario> query = manager.createQuery(jpql,Usuario.class);
        query.setParameter("pusuario",user);
        try {
            Usuario usu = query.getSingleResult();
            if(Util.decriptografa(usu.getSenha()).equals(passw)) {
                resultado = Boolean.TRUE;
            } 
        } catch (NoResultException re) {
            resultado = Boolean.FALSE;
        }
        manager.close();                
        return resultado;                
    }
    
    
    public static ObservableList<Usuario> getObsLista() {        
        return FXCollections.observableArrayList(Usuarios.getLista());
    }    
    
    public static ObservableList<Usuario> getObsUsuarioWithNome(String usuario) {        
        return FXCollections.observableArrayList(Usuarios.getUsuarioWithNome(usuario));
    }                  
}
