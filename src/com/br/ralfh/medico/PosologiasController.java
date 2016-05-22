/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Posologia;
import com.br.ralfh.medico.modelos.Posologias;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PosologiasController extends Controller {
    
    private GUIFactory eposologias;
    private ObservableList<Posologia> olPosologias;    
    private SimpleObjectProperty<Posologia> posologia;    
    
    @FXML private Button btnSair;
    @FXML private Button btnNovo;
    @FXML private Button btnExcluir;
    @FXML private Button btnAlterar;    
    @FXML private TableView<Posologia> tablePosologias;
    @FXML private TableColumn<Posologia, String> posologiasCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        posologia = new SimpleObjectProperty<>(null);
        initTableModos();
        initPosologiasData();
        initToolTips();
        AddListenerSelecPosologia();
    }  
    
    public void btnNovoFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/EditPosologias.fxml";
        String titleGUI = "Nova posologia";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        eposologias = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle);
        EditPosologiasController controller = (EditPosologiasController) eposologias.getController();
        controller.setAcao(TipoOper.INCLUSÃO);
        eposologias.showAndWait();
        initPosologiasData();
    }
        
    public void btnExcluirFired(ActionEvent event) throws Exception {
        if (posologia.get()!=null) {
            if (ExcluiRegistroDlg("EPO", "", null)) {
                if (Posologias.excluiPosologia(posologia.get())) {
                    initPosologiasData();
                }
            }        
        } else ShowDialog("INFO", "Selecione uma Posologia", null);
    }

    
    public void btnAlterarFired(ActionEvent event) throws Exception {
        if (posologia.get()!=null) {
            String fxmlGUI = "fxml/EditPosologias.fxml";
            String titleGUI = "Alterar uma posologia";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            eposologias = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle);
            EditPosologiasController controller = (EditPosologiasController) eposologias.getController();
            controller.setAcao(TipoOper.ALTERAÇÃO);
            controller.setPosologia(posologia.get());
            eposologias.showAndWait(); 
            initPosologiasData();
        } else {
            ShowDialog("INFO", "Selecione uma Posologia", null);
        }
    }
    
    public void initToolTips() {
        btnNovo.setTooltip(new Tooltip("Criar posologia"));
        btnExcluir.setTooltip(new Tooltip("Excluir a posologia selecionada"));
        btnAlterar.setTooltip(new Tooltip("Atualizar a posologia selecionada"));
    }
    
    
    public void initTableModos() {
        posologiasCol.setCellValueFactory(new PropertyValueFactory<Posologia, String>("posologia"));
        }            
    
    public void initPosologiasData() {
        Posologias posologias = new Posologias();            
        tablePosologias.setItems(null);
        tablePosologias.setItems(posologias.getObsLista());        
    }
    
    public void AddListenerSelecPosologia() {
        tablePosologias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            posologia.set(tablePosologias.getSelectionModel().getSelectedItem());
        }
        }); 
    }
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
}
