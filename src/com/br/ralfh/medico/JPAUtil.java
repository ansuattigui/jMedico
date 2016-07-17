/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
