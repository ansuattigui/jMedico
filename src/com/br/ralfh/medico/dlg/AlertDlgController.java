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
import com.br.ralfh.medico.TipoDialogo;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public class AlertDlgController extends Controller {
    
    @FXML public Label messageLabel;
    @FXML public Label detailsLabel;
    @FXML public HBox actionParent;
    @FXML public Button actionButton;
    @FXML public Button cancelButton;
    @FXML public HBox okParent;
    @FXML public Button okButton;
    @FXML public ImageView imDlg;

    private Properties propriedades; 
//    public Boolean optChoosen;
    private String mensagem;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propriedades = new Properties();
    }  
    
    public void setMensagem(String msg) {
        mensagem = msg;
    }

    
    public void configProperties(TipoDialogo td) {        
        switch (td) {
            case EXCLUSÃO:
                configExclusaoDlg();
                break;
            case INCLUSÃO:
//                configInclusaoDlg();
                break;
            case ATUALIZAÇÃO:
//                configAtualizacaoDlg();
                break;
            case AUTORIZAÇÃO:
                configAutorizacaoDlg();
                break;
            default:
                System.out.println("Opção default!!");
        }
    }
    
    
    public void configExclusaoDlg() {        
        try {
            propriedades.load(getClass().getResourceAsStream("ExcluirDlg.properties"));
        } catch (IOException ioe) {
            System.out.println("Arquivo não encontrado");
        }
        
        okButton.setText(propriedades.getProperty("button.ok"));
        cancelButton.setText(propriedades.getProperty("button.cancel"));
        messageLabel.setText(propriedades.getProperty("label.message"));
        detailsLabel.setText(propriedades.getProperty("label.details"));
        actionButton.setVisible(false);
        Image im = new Image(getClass().getResourceAsStream("Interrogacao.png"));
        imDlg.setImage(im);
    }    

    public void configAutorizacaoDlg() {        
        try {
            propriedades.load(getClass().getResourceAsStream("AutorizarDlg.properties"));
        } catch (IOException ioe) {
            System.out.println("Arquivo não encontrado");
        }
        
        okButton.setText(propriedades.getProperty("button.ok"));
        cancelButton.setVisible(Boolean.FALSE);
//        actionButton.setVisible(Boolean.FALSE);
        messageLabel.setText(propriedades.getProperty("label.message"));
        detailsLabel.setText(mensagem);
        Image im = new Image(getClass().getResourceAsStream("Autorizacao.jpg"));
        imDlg.setImage(im);
    }    
    
    
    public void btnOkFired(ActionEvent event) {
        optChoosen = Boolean.TRUE;
        this.getStage().close();
    }

    public void btnCancelFired(ActionEvent event) {
        this.optChoosen = Boolean.FALSE;
        this.getStage().close();
    }    
    
}
