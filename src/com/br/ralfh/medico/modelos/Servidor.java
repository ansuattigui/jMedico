/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.Controller;
import com.br.ralfh.medico.JDocplus;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author ralfh
 */
public final class Servidor {
    
    private String driver;
    private String address;
    private String port;
    private String dbname;
    private String user;
    private String password;
    private Properties propriedades; 
    private FileOutputStream fileout;
    private FileInputStream filein;
    
    public Servidor() {
        //getServidor();        
    }

    public void setServidor() {
        propriedades = new Properties();        
        propriedades.setProperty("servidor.driver",this.getDriver());
        propriedades.setProperty("servidor.address",this.getAddress());
        propriedades.setProperty("servidor.port",this.getPort());
        propriedades.setProperty("servidor.dbname",this.getDbName());        
        propriedades.setProperty("servidor.user",this.getUser());
        propriedades.setProperty("servidor.password",this.getPassword());
        
        try {
            fileout = new FileOutputStream("servidor.properties");
            propriedades.store(fileout, "Propriedades de acesso ao BD do sistema");
        } catch (IOException ioe) {
            Controller.ShowDialog("EX", "Arquivo de configurações do sistema não localizado", null,JDocplus.getMainStage());
        }
    }
    
    
    public void getServidor() {
        propriedades = new Properties();        
        try {
            filein = new FileInputStream("servidor.properties");
            propriedades.load(filein);
        } catch (IOException ioe) {
            Controller.ShowDialog("EX", "Arquivo de configurações do sistema não localizado", null,JDocplus.getMainStage());
        }
        
        driver = propriedades.getProperty("servidor.driver");
        address = propriedades.getProperty("servidor.address");
        port = propriedades.getProperty("servidor.port");
        dbname = propriedades.getProperty("servidor.dbname");        
        user = propriedades.getProperty("servidor.user");
        password = propriedades.getProperty("servidor.password");
    }
    
    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the dbname
     */
    public String getDbName() {
        return dbname;
    }

    /**
     * @param dbName the dbname to set
     */
    public void setDbName(String dbName) {
        this.dbname = dbName;
    }
    
    
    
}
