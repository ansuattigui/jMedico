/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Tuss;
import com.br.ralfh.medico.modelos.Tusss;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
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
public class TussController extends Controller {
    
    private GUIFactory etuss;
    private SimpleObjectProperty<Tuss> tuss;    
    
    @FXML public Button btnNovo;
    @FXML public Button btnSair;
    @FXML public Button btnExcluir;
    @FXML public Button btnAlterar;
    @FXML public Menu menuTuss;
    @FXML public TableView<Tuss> tableTuss;
    @FXML public TableColumn<Tuss,String> codigoCol;
    @FXML public TableColumn<Tuss,String> grupoCol;
    @FXML public TableColumn<Tuss,String> subgrupoCol;
    @FXML public TableColumn<Tuss,String> procedimentoCol;
    
    @FXML
    private ComboBox<String> cbGrupo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tuss = new SimpleObjectProperty<>();
        initTableTuss();
        TussController.this.initTussData();
        initToolTips();
        initChGrupo();
        AddListenerToChoiceBoxGrupo();
        AddListenerSelecProcedimento();
    }  

    
    // Criar novo procedimento no cadastro
    public void btnNovoFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/EditProcedimentos.fxml"; 
        String fxmlTitle = "Novo Procedimento";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        etuss = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
        EditProcedimentoController controller = (EditProcedimentoController) etuss.getController();
        controller.setAcao(TipoOper.INCLUSÃO);
        if (cbGrupo.getSelectionModel().getSelectedItem()!=null) {
            controller.setGrupo(Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().getSelectedItem()));
        }
        etuss.showAndWait();

        Integer index = cbGrupo.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
            initTussData(grupo);
        } else {
            initTussData();
        }
    }    
    
    //Excluir um procedimento do cadastro
    public void btnExcluirFired(ActionEvent event) throws Exception {
        if (tuss.get()!=null) {
            if (ExcluiRegistroDlg("MED", "", null)) {
                if (Tusss.excluiProcedimento(tuss.get())) {
                    initTussData();
                }
            }        
        } else ShowDialog("INFO", "Selecione um Procedimento", null);
    }
    
    //Altera um procedimento no cadastro
    public void btnAlterarFired(ActionEvent event) throws Exception {
        if (tuss.get()!=null) {
            String fxmlGUI = "fxml/EditProcedimentos.fxml";
            String fxmlTitle = "Alterar um Procedimento";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            etuss = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
            EditProcedimentoController controller = (EditProcedimentoController) etuss.getController();
            controller.setAcao(TipoOper.ALTERAÇÃO);
            controller.setProcedimento(tuss.get());
            etuss.showAndWait();

            Integer index = cbGrupo.getSelectionModel().getSelectedIndex();
            if (index > 0) {
                Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
                initTussData(grupo);
            } else {
                initTussData();
            }
        } else {
            ShowDialog("INFO", "Selecione um Procedimento", null);
        }
    }
    
    //Atribui e exibe tooltips aos botões e itens de menu
    public void initToolTips() {
            btnNovo.setTooltip(new Tooltip("Inserir novo procedimento no cadastro"));
            btnExcluir.setTooltip(new Tooltip("Excluir um procedimento do cadastro"));
            btnAlterar.setTooltip(new Tooltip("Atualizar um procedimento no cadastro"));
    }

    public void initTableTuss() {
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        grupoCol.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        subgrupoCol.setCellValueFactory(new PropertyValueFactory<>("subgrupo"));
        procedimentoCol.setCellValueFactory(new PropertyValueFactory<>("procedimento"));
//        grupoCol.setCellValueFactory((CellDataFeatures<Medicamento, String> p) -> new SimpleStringProperty(p.getValue().getGrupo().getGrupo()));          
    }
    
    public void initTussData() {
        tableTuss.setItems(Tusss.getObsLista());        
    }
    
    public void initTussData(Grupo grupo) {
        tableTuss.setItems(null);
        tableTuss.setItems(Tusss.getObsLista(grupo));        
    }

    public void initChGrupo(){
        if (!cbGrupo.getItems().isEmpty()) {
            cbGrupo.getItems().clear();
        }
        cbGrupo.setItems(Grupos.getObsSLista());                    
    }
    
    public void AddListenerToChoiceBoxGrupo() {
        cbGrupo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                if (cbGrupo.getSelectionModel().getSelectedIndex() == 0) {
                    TussController.this.initTussData();
                } else {
                    Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
                    initTussData(grupo);                
                }
            }        
        });
    }

    public void AddListenerSelecProcedimento() {
        tableTuss.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            tuss.set(tableTuss.getSelectionModel().getSelectedItem());
        }
        }); 
    }
    
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
}
