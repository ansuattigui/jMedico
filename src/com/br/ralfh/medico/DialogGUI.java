package com.br.ralfh.medico;

import com.br.ralfh.medico.dlg.AlertDlgController;
import com.br.ralfh.medico.dlg.AutorizaDlgController;
import com.br.ralfh.medico.dlg.ExceptionDialogController;
import com.br.ralfh.medico.modelos.HorarioAgenda;
import java.io.IOException;
import java.time.Duration;
import java.util.Timer;
import javafx.animation.PauseTransition;
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
public class DialogGUI {

    private String guiFile;
    private String guiTitle;
    private final Stage stage;
    private final Scene scene;
    private final FXMLLoader loader;
    private final Parent root;
    private final Controller controller;
    private String mensagem;
    private Object o;
    private Timer timer;

    public DialogGUI(String tipoDlg, String msg, Object obj) throws IOException {
        this.mensagem = msg;
        this.o = obj;
        switch (tipoDlg) {
            case "INFO":
                guiFile = "fxml/InformDialog.fxml";
                guiTitle = "Informação do Sistema";
                break;
            case "EPAC":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Paciente";
                break;
            case "ECS":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Consulta";
                break;
            case "EAG":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Agendamento";
                break;
            case "EMAT":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Modelo de Atestado";
                break;
            case "EAT":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Atestado";
                break;
            case "EX": 
                guiFile = "dlg/ExceptionDialog.fxml";
                guiTitle = "Mensagem de erro";
                break;
            case "ECON":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Convênio";
                break;
            case "EGR":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir um grupo";
                break;
            case "EP":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir uma prescricao";
                break;
            case "EPO":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir uma posologia";
                break;
            case "MED":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir um medicamento";
                break;
            case "PRO":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir um procedimento";
                break;
            case "ER":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Excluir uma receita";
                break;
            case "S":
                guiFile = "fxml/InformDialog.fxml";    
                guiTitle = "Informação";
                break;
            case "AU":
                guiFile = "dlg/AutorizaDlg.fxml";
                guiTitle = "Autorização de Entrada";
                break;
            case "ATBS":
                guiFile = "fxml/InformDialog.fxml";    
                guiTitle = "Atualização de registro";  
                addTime();
                break;
            case "EMRC":
                guiFile = "dlg/AlertDlg.fxml";
                guiTitle = "Exclusão de Modelo de Recibo";
                break;

        }
        
        this.stage = new Stage();
        this.stage.setTitle(this.guiTitle);
        this.loader = new FXMLLoader(getClass().getResource(guiFile));
        this.root = (Parent)loader.load();
        
        
        this.controller = loader.getController(); 
        this.scene = new Scene(root);
        this.stage.setScene(scene);  
        
        this.stage.sizeToScene();        
        
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initStyle(StageStyle.UTILITY);
        
        this.controller.setStage(stage); 
        
        addListeners(tipoDlg);
    }
    
    public void addTime() {
        PauseTransition delay = new PauseTransition(javafx.util.Duration.millis(3000));
        delay.setOnFinished( event -> this.stage.close() );
        delay.play();    
    }

    private void addListeners(String tipoDlg) {
        switch (tipoDlg) {
            case "EX":
                ((ExceptionDialogController) controller).setMensagem(this.mensagem);
                break;
            case "ECS": case "EAG": case "ECON": case "EAT": case "EMAT": case "EGR": case "EPO": case "MED": case "EPAC": case "ER": case "EP": case "PRO": case "EMRC":
                ((AlertDlgController) controller).configProperties(TipoDialogo.EXCLUSÃO);
                break;
            case "S": case "INFO": case "ATBS":
                ((InformDialogController) controller).initDialog(mensagem, "","greenExclamation"); 
                break;
            case "AU": 
                ((AutorizaDlgController) controller).setHorario((HorarioAgenda) this.o);
                break;   
//            case "ATBS":
//                ((InformDialogController) controller).initDialog(mensagem, "", "greenExclamation"); 
//                break;
            case "ATMS":
                ((InformDialogController) controller).initDialog(mensagem, "", "redExclamation"); 
                break;
        }
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
    
}
