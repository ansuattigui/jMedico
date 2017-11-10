/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import javax.persistence.EntityManager;

/**
 *
 * @author Ralfh
 */
public class JPAUtil {
    
    public JPAUtil() {
    }
    
    public static EntityManager getEntityManager() {
        return MedicoController.getEntityManagerFactory().createEntityManager();
    }
    
}
