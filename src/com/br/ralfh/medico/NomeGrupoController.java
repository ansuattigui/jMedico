/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import com.br.ralfh.medico.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public class NomeGrupoController extends Controller {
    
    @FXML public Button btnApagaNome;
    @FXML public Button btnConfirmaNome;
    @FXML public Button btnCancelaNome;
    @FXML public TextField nomeGrupoExames;    

    private String nomeGrupo;
    
    public NomeGrupoController() {
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }      
    
    public void btnCancelaNomeFired(ActionEvent event) {
        nomeGrupo = "";
        this.getStage().close();
    }
    
    
    public void btnConfirmaNomeFired(ActionEvent event) {                        
        nomeGrupo = nomeGrupoExames.getText();
        this.getStage().close();
    }

    public void btnApagaNomeFired(ActionEvent event) {
        nomeGrupo = "";
        nomeGrupoExames.clear();
    }

    
    public String getNomeGrupo() {
        return nomeGrupo;
    }

    /**
     * @param nomeGrupo
     */
    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
        this.nomeGrupoExames.setText(this.nomeGrupo);
    }

}
