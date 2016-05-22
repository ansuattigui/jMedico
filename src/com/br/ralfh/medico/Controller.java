
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Paciente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class Controller implements Initializable {
    
    protected Stage stage;
    protected Boolean optChoosen; 
    protected SimpleBooleanProperty opcaoCancelar; 
    private Controller caller;
    private Boolean closeModal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setStage(Stage pStage) {
        this.stage = pStage;
    }   
    
    public Stage getStage() {
        return this.stage;
    }

    public Controller getController() {
        return this;
    }
    
    public void setCaller(Controller controller) {
        this.caller = controller;
    }
    
    public static void ShowDialog(String tipoDlg, String msg, Object obj) {        
        DialogGUI dialog;
        try {
            dialog = new DialogGUI(tipoDlg,msg, obj);
            dialog.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean ExcluiRegistroDlg(String tipoDlg, String msg, Object obj) {    
        DialogGUI dialog = null;
        try {
            dialog = new DialogGUI(tipoDlg,msg, obj);
            dialog.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }        
        return dialog.getController().getCloseModal();
    }

        
/*        if (((AlertDlgController) dialog.getController()).optChoosen) {
            
            EntityManager manager = new JPAUtil().getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(Convenio.class, convenio.getId()));  
            manager.getTransaction().commit();
            manager.close();
            
        }
*/
    
    @FXML
    public void actionFecharConfirmar(ActionEvent event) {
        closeModal = Boolean.TRUE;
        stage.close();
    }
    @FXML
    public void actionFecharCancelar(ActionEvent event) {
        closeModal = Boolean.FALSE;
        stage.close();
    }
    
    public Boolean getCloseModal() {
        return closeModal;
    }
    
    
    public void setTitle(String title) {
        stage.setTitle(title);
    }
    
    
    /**
     * @param pac*
     * @param horario******************************************************************************/
    public void setPaciente(Integer pac, Integer horario) {
    }
    
    public void setPaciente(Paciente pac, Integer horario) {
    }

    public void addStageCloseListener() {
    }
    
    public void addStageHidingListener() {                
    }
    /********************************************************************************/
    
    
    
}
