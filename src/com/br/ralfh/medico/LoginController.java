/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import static com.br.ralfh.medico.MedicoController.setEntityManagerFactory;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Servidor;
import com.br.ralfh.medico.modelos.Usuario;
import com.br.ralfh.medico.modelos.Usuarios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class LoginController extends Controller {
    
    @FXML TextField usuario;
    @FXML TextField senha;    
    @FXML Button btnEntrar;
    @FXML Button btnSair;
    @FXML Button btnSalvaConfig;
    
    @FXML TextField driver;
    @FXML TextField endereco;
    @FXML TextField porta;
    @FXML TextField bancodedados;
    @FXML TextField usuariobd;
    @FXML TextField senhabd;
    
    @FXML public TabPane tabLogin;
    
    @FXML HBox hboxServidor;
    @FXML TextField servidor;
    
    public Boolean login;
    private Usuario user;
    private Integer count;
    
    private final Servidor server;
    
    public LoginController() {
        server = new Servidor();
        server.getServidor();

    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tabLogin.getSelectionModel().select(0);        
        tabLogin.getTabs().get(1).setDisable(true);
        
        if (!MedicoController.getConn().checkConnection(server)) {
            openConfig();
        } 
        
        count = 0;
        user = null;
    }  
    
    public void openConfig() {
        tabLogin.getTabs().get(1).setDisable(false);
        tabLogin.getSelectionModel().select(1);        
    }
    
    @FXML
    public void btnSalvaConfigFired(ActionEvent ae) {
        try {
            if (driver.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o Driver de acesso ao BD");
            } else {
                server.setDriver(driver.getText());
            }
            if (endereco.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o endereço de acesso ao BD");
            } else {
                server.setAddress(endereco.getText());
            }
            if (porta.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o porta de acesso ao BD");
            } else {
                server.setPort(porta.getText());
            }
            if (bancodedados.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o nome do banco de dados do sistema");
            } else {
                server.setDbName(bancodedados.getText());
            }
            if (usuariobd.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o usuário com acesso ao BD");
            } else {
                server.setUser(usuariobd.getText());
            }
            if (senhabd.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe a senha de acesso ao BD");
            } else {
                server.setPassword(senhabd.getText());
            }

            server.setServidor();
            this.initialize(null, null);
            
        } catch (CampoEmBrancoException ceb) {
            Controller.ShowDialog("EX", ceb.getMessage(), null);
        }
    }
    
    
    public void btnEntrarFired(ActionEvent ae) {
        if (!usuario.getText().trim().isEmpty()) {            
            while (count < 3) {
                if (Usuarios.isUsuario(usuario.getText())) {
                    if (Usuarios.checaSenha(usuario.getText(), senha.getText())) {
                        user = Usuarios.getUsuarioWithNome(usuario.getText());                    
                        break;
                    } else {
                        count++;
                        ShowDialog("EX", "Dados incorretos!", null);
                        return;
                    }
                } else {
                    count++;
                    ShowDialog("EX", "Dados incorretos!", null);
                    return;
                }
            }            
        }
        this.stage.close();
    }
    
    public void btnSairFired(ActionEvent ae) {
        this.stage.close();
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }
    
}
