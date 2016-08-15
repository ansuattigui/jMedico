package com.br.ralfh.medico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ralfh
 */
public class JDocplus extends Application {
    
    private static Stage mainStage;
    
    @Override
    public void start(Stage principalStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        mainStage = principalStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Medico.fxml"));        
        Parent root = (Parent)loader.load();
        
        MedicoController controller = (MedicoController) loader.getController();
        controller.setStage(getMainStage());
       
        controller.addStageCloseListener();
        controller.getStage().setMaxWidth(230);
        controller.getStage().setMaximized(true);
        Scene scene = new Scene(root);
        
        getMainStage().setScene(scene);        
        getMainStage().setTitle("JDocplus");
        getMainStage().getIcons().add(new Image(getClass().getResourceAsStream("imagens/icons/stethoscope_no_sh.png")));
        getMainStage().initStyle(StageStyle.UTILITY);
        getMainStage().show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return the mainStage
     */
    public static Stage getMainStage() {
        return mainStage;
    }
}