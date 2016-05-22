package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
public class GruposController extends Controller {
    
    private GUIFactory egrupos;
    private ObservableList<Grupo> olGrupos;
    private SimpleObjectProperty<Grupo> grupo;
    
    
    @FXML private Button btnSair;
    @FXML private Button btnNovo;
    @FXML private Button btnExcluir;
    @FXML private Button btnAlterar;    
    @FXML private TableView<Grupo> tableGrupos;
    @FXML private TableColumn<Grupo, String> gruposCol;
    
    public GruposController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grupo = new SimpleObjectProperty<>(null);
        olGrupos = FXCollections.observableArrayList();
        initTableGrupos();
        addGruposListener();
        AddListenerSelecGrupo();
        initToolTips();
        initGruposData();
    }  
    
    private void addGruposListener() { 
        olGrupos.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {                
                if (olGrupos.size()>0) {
                    tableGrupos.getItems().setAll(olGrupos);
                } else {
                    tableGrupos.getItems().clear();
                }
            }
        });        
    }        
    
    
    public void btnNovoFired(ActionEvent event) throws Exception {        
        String fxmlGUI = "fxml/EditGrupos.fxml";
        String fxmlTitle = "Novo Grupo";
        StageStyle fxmlStyle = StageStyle.UTILITY;        
        egrupos = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
        EditGruposController controller = (EditGruposController) egrupos.getController();
        controller.setAcao(TipoOper.INCLUSÃO);
        egrupos.showAndWait();
        initGruposData();
    }
    
    
    public void btnExcluirFired(ActionEvent event) throws Exception {
        if (grupo.get()!=null) {
            if (ExcluiRegistroDlg("EGR", "", null)) {
                if (Grupos.excluiGrupo(grupo.get())) {
                    initGruposData();
                }            
            }        
        } else ShowDialog("INFO", "Selecione um Grupo", null);   
    }
 
    //Altera um medicamento/modo de uso no cadastro
    public void btnAlterarFired(ActionEvent event) throws Exception {
//        grupo = tableGrupos.getSelectionModel().getSelectedItem();
        if (grupo.get()!=null) {
            String fxmlGUI = "fxml/EditGrupos.fxml";
            String fxmlTitle = "Alterar um Grupo";
            StageStyle fxmlStyle = StageStyle.UTILITY;        
            egrupos = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
            EditGruposController controller = (EditGruposController) egrupos.getController();
            controller.setAcao(TipoOper.ALTERAÇÃO);
            controller.setGrupo(grupo.get());
            egrupos.showAndWait();
            initGruposData();
        } else {
            ShowDialog("INFO", "Selecione um Grupo", null);
        } 
    }
    
    //Atribui e exibe tooltips aos botões e itens de menu
    public void initToolTips() {
        btnNovo.setTooltip(new Tooltip("Criar grupo"));
        btnExcluir.setTooltip(new Tooltip("Excluir o grupo selecionado"));
        btnAlterar.setTooltip(new Tooltip("Atualizar o grupo selecionado"));
    }
    
    public void initTableGrupos() {
        gruposCol.setCellValueFactory(new PropertyValueFactory<Grupo, String>("grupo"));            
//        initGruposData();
    }

    public void initGruposData() {
        olGrupos.setAll(Grupos.getObsLista());
//        tableGrupos.setItems(null);
//        tableGrupos.setItems(olGrupos);        
    }
    
    public void AddListenerSelecGrupo() {
        tableGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            grupo.set(tableGrupos.getSelectionModel().getSelectedItem());
        }
        }); 
    }
    
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
}
