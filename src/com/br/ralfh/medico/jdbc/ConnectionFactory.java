/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.jdbc;

import com.br.ralfh.medico.Controller;
import com.br.ralfh.medico.JDocplus;
import com.br.ralfh.medico.MedicoController;
import com.br.ralfh.medico.modelos.Servidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ralfh
 */
public class ConnectionFactory {
    
    private String address;
    private String driver;
    private String port;
    private String dbName;
    private String user;
    private String password;
    private String connString;

    private final Map prt;
    
    public Connection conn;
    
    public ConnectionFactory() {
        conn = null;
        prt = new HashMap();
    }
    
    public Boolean checkConnection(Servidor servidor) {
        Boolean resultado = Boolean.FALSE;
        
        this.address = servidor.getAddress();
        this.driver = servidor.getDriver();
        this.port = servidor.getPort();
        this.dbName = servidor.getDbName();
        this.user = servidor.getUser();
        this.password = servidor.getPassword();
        
        this.connString = driver+"//"+address+":"+port+"/"+dbName;
        
        try {                
            conn = DriverManager.getConnection(connString, user, password);     
            resultado = Boolean.TRUE;
            
            prt.put("javax.persistence.jdbc.url", connString);
            prt.put("javax.persistence.jdbc.user", user);
            prt.put("javax.persistence.jdbc.password", password);            
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(dbName,prt);
            MedicoController.setEntityManagerFactory(emf);
            
            conn.close();
        } catch (SQLException e) {
            Controller.ShowDialog("EX", "Problemas com a conexão ao Banco de Dados", null,JDocplus.getMainStage());            
        }
        return resultado;
    }   
            
    
    public Connection getConnection() {
         try {
             return DriverManager.getConnection(connString, user, password);
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }    
    
    
}
