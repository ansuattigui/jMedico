package com.br.ralfh.medico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ralfh
 */
public class JDocplus extends Application {
    
    public Stage mainStage;
    
    @Override
    public void start(Stage principalStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        mainStage = principalStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Medico.fxml"));        
        Parent root = (Parent)loader.load();
        
        MedicoController controller = (MedicoController) loader.getController();
        controller.setStage(mainStage);
        controller.addStageCloseListener();
        controller.getStage().setMaximized(true);
        Scene scene = new Scene(root);
        
        mainStage.setScene(scene);        
        mainStage.setTitle("JDocplus");
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/icons/stethoscope_no_sh.png")));
        mainStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}