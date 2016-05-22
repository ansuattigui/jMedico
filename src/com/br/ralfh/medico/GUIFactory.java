package com.br.ralfh.medico;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author ralfh
 */

public final class GUIFactory {

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private Parent root;
    private final String guiFile;
    private final StageStyle style;
    private Controller controller;    
    
    public GUIFactory(String gui, String title, StageStyle sty) throws IOException {         
        this.guiFile = gui;
        this.style = sty;
//        GUIFactory guifact = MedicoController.getMapaJanelas(gui);
//        if (guifact!=null) {
//            guifact.getStage().toFront();            
//        } else {
            this.stage = new Stage();
            this.stage.initModality(Modality.APPLICATION_MODAL);      
            this.stage.initStyle(this.style);
            this.stage.setTitle("JDocplus - "+title);  
            
            this.initialize();
        //}
    }

    public void initialize() {
        this.loader = new FXMLLoader(getClass().getResource(this.guiFile));
        try {
            this.root = (Parent)loader.load();
        } catch (IOException ex) {
            Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.controller = loader.getController(); 
        this.scene = new Scene(root);
        
        this.stage.setScene(scene);                  
        this.controller.setStage(stage);             
        addWindowCloseListener();
        MedicoController.setEntradaMapaJanelas(guiFile, this);
//        this.showAndWait();
    }
    
    public void show() {                
        this.stage.show();
    }
   
    public void showAndWait() {
        this.stage.showAndWait();
    }
    
    public Controller getController() {
        return this.controller;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public void close() {
        this.stage.close();
    }
    
    private void addWindowCloseListener() {
        this.stage.onHidingProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            MedicoController.apagaEntradaMapaJanelas(guiFile);
        }
    });
    }

}
