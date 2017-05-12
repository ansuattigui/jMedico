/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.dlg;

import com.br.ralfh.medico.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public class ControladaDlgController extends Controller {
    
    @FXML public Label tituloLabel;
    @FXML public Label medicamentoLabel;
    @FXML public Button salvarButton;
    @FXML public Button cancelarButton;
    @FXML public TextArea textoPrescricao;    
    @FXML public ImageView imDlg;

    private String medicamento;
    private String prescricao;
    
    public ControladaDlgController() {
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }      
    
    public void btnCancelFired(ActionEvent event) {
        prescricao = "";
        this.getStage().close();
    }
    
    
    public void btnSalvarFired(ActionEvent event) {                        
        prescricao = medicamento + " - " + textoPrescricao.getText();
        this.getStage().close();
    }

    /**
     * @return the medicamento
     */
    public String getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
        this.medicamentoLabel.setText(this.medicamento);
    }

    /**
     * @return the prescricao
     */
    public String getPrescricao() {
        return prescricao;
    }

    /**
     * @param prescricao the prescricao to set
     */
    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

}
