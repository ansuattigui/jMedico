/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public class InformDialogController extends Controller {
    
    private Controller caller;
    
    @FXML
    private ImageView ivIcone;
    @FXML
    private Label messageLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private HBox actionParent;
    @FXML
    private Button actionButton;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox okParent;
    @FXML
    private Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        
    public void initDialog(String mensagem, String dtl, String tipoIcone) {
        messageLabel.setText(mensagem);
        detailsLabel.setText(dtl);
        Image im;
        switch (tipoIcone) {
            case "greenExclamation":
                im = new Image(getClass().getResourceAsStream("greenexclamation.png"));
                ivIcone.setImage(im);
                break;
            case "redExclamation" :
                im = new Image(getClass().getResourceAsStream("redexclamation.png"));
                ivIcone.setImage(im);
                break;        
        }
        configDialog();
    }
    
    private void configDialog() {
//        stage.initOwner(caller.getStage());
//        stage.sizeToScene();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
    }
    
    public void okButtonFired(ActionEvent event) {
//        caller.optChoosen.set(true);
        stage.hide();
    }
    
    public void cancelButtonFired(ActionEvent event) {
//        caller.opcaoCancelar.set(true);
        stage.hide();
    }
    
    @Override
    public void setCaller(Controller controller) {
        this.caller = controller;
    }
    
    @Override
    public void setStage(Stage stag) {
        this.stage = stag;
    }
}
