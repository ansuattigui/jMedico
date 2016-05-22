/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Medicamento;
import com.br.ralfh.medico.modelos.Medicamentos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class MedicamentosController extends Controller {
    
    private GUIFactory emedicamentos;
    private SimpleObjectProperty<Medicamento> medicamento;    
    
    @FXML public Button btnNovo;
    @FXML public Button btnSair;
    @FXML public Button btnExcluir;
    @FXML public Button btnAlterar;
    @FXML public Menu menuMedicamentos;
    @FXML public TableView<Medicamento> tableMedicamentos;
    @FXML public TableColumn<Medicamento,String> grupoCol;
    @FXML public TableColumn<Medicamento,String> nomeComercialCol;
    @FXML public TableColumn<Medicamento,String> principioCol;
    
    @FXML
    private ComboBox<String> cbGrupo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        medicamento = new SimpleObjectProperty<>();
        initTableMedicamentos();
        initMedicamentosData();
        initToolTips();
        initChGrupo();
        AddListenerToChoiceBoxGrupo();
        AddListenerSelecPosologia();
    }  

    
    // Criar novo medicam/modo de uso no cadastro
    public void btnNovoFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/EditMedicamentos.fxml";
        String fxmlTitle = "Novo Medicamento";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        emedicamentos = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
        EditMedicamentoController controller = (EditMedicamentoController) emedicamentos.getController();
        controller.setAcao(TipoOper.INCLUSÃO);
        if (cbGrupo.getSelectionModel().getSelectedItem()!=null) {
            controller.setGrupo(Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().getSelectedItem()));
        }
        emedicamentos.showAndWait();

        Integer index = cbGrupo.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
            initMedicamentosData(grupo);
        } else {
            initMedicamentosData();
        }
    }    
    
    //Excluir um medicam/modo de uso do cadastro
    public void btnExcluirFired(ActionEvent event) throws Exception {
        if (medicamento.get()!=null) {
            if (ExcluiRegistroDlg("MED", "", null)) {
                if (Medicamentos.excluiMedicamento(medicamento.get())) {
                    initMedicamentosData();
                }
            }        
        } else ShowDialog("INFO", "Selecione um Medicamento", null);
    }
    
    //Altera um medicam/modo de uso no cadastro
    public void btnAlterarFired(ActionEvent event) throws Exception {
        if (medicamento.get()!=null) {
            String fxmlGUI = "fxml/EditMedicamentos.fxml";
            String fxmlTitle = "Alterar um Medicamento";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            emedicamentos = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
            EditMedicamentoController controller = (EditMedicamentoController) emedicamentos.getController();
            controller.setAcao(TipoOper.ALTERAÇÃO);
            controller.setMedicamento(medicamento.get());
            emedicamentos.showAndWait();

            Integer index = cbGrupo.getSelectionModel().getSelectedIndex();
            if (index > 0) {
                Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
                initMedicamentosData(grupo);
            } else {
                initMedicamentosData();
            }
        } else {
            ShowDialog("INFO", "Selecione um Medicamento", null);
        }
    }
    
    //Atribui e exibe tooltips aos botões e itens de menu
    public void initToolTips() {
            btnNovo.setTooltip(new Tooltip("Inserir novo medicamento no cadastro"));
            btnExcluir.setTooltip(new Tooltip("Excluir um medicamento do cadastro"));
            btnAlterar.setTooltip(new Tooltip("Atualizar um medicamento no cadastro"));
    }

    public void initTableMedicamentos() {
        nomeComercialCol.setCellValueFactory(new PropertyValueFactory<>("nomecomercial"));
        principioCol.setCellValueFactory(new PropertyValueFactory<>("principio"));
        grupoCol.setCellValueFactory((CellDataFeatures<Medicamento, String> p) -> new SimpleStringProperty(p.getValue().getGrupo().getGrupo()));          
    }
    
    public void initMedicamentosData() {
        tableMedicamentos.setItems(Medicamentos.getObsLista());        
    }
    
    public void initMedicamentosData(Grupo grupo) {
        tableMedicamentos.setItems(null);
        tableMedicamentos.setItems(Medicamentos.getObsLista(grupo));        
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
                    initMedicamentosData();
                } else {
                    Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
                    initMedicamentosData(grupo);                
                }
            }        
        });
    }

    public void AddListenerSelecPosologia() {
        tableMedicamentos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            medicamento.set(tableMedicamentos.getSelectionModel().getSelectedItem());
        }
        }); 
    }
    
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
}
