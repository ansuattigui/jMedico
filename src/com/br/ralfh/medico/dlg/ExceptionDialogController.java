/*
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ExceptionDialogController extends Controller {
    @FXML ImageView iconeDlg;
    @FXML Label messageLabel;
    @FXML HBox okParent;
    @FXML Button okButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Image im = new Image(getClass().getResourceAsStream("imagens/Exclamacao.png"));
//        iconeDlg.setImage(im);     
    }    
    
    public void setMensagem(String msg) {
        messageLabel.setText(msg);
    }
    
    public void okButtonPressed(ActionEvent ae) {
        this.stage.close();
    }
    
}
