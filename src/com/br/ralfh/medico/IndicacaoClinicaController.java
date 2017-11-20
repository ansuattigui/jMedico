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
public class IndicacaoClinicaController extends Controller {
    
    @FXML public Button btnApagaNome;
    @FXML public Button btnConfirmaNome;
    @FXML public Button btnCancelaNome;
    @FXML public TextField indicacaoClinica;    

    private String indClinica;
    
    public IndicacaoClinicaController() {
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
        indClinica = "";
        this.getStage().close();
    }
    
    
    public void btnConfirmaNomeFired(ActionEvent event) {                        
        indClinica = indicacaoClinica.getText();
        this.getStage().close();
    }

    public void btnApagaNomeFired(ActionEvent event) {
        indClinica = "";
        indicacaoClinica.clear();
    }

    
    public String getIndClinica() {
        return indClinica;
    }

    /**
     * @param indClinica
     */
    public void setIndClinica(String indClinica) {
        this.indClinica = indClinica;
        this.indicacaoClinica.setText(this.indClinica);
    }

}
