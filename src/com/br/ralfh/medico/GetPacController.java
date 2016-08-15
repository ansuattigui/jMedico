package com.br.ralfh.medico;

import com.br.ralfh.medico.libs.DateUtil;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Pacientes;
import com.br.ralfh.medico.modelos.Receita;
import com.br.ralfh.medico.modelos.Receitas;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class GetPacController extends Controller {
    
    ObservableList<Paciente> olPacientes = FXCollections.observableArrayList();
    Receita receita;
    Paciente paciente;
    
    @FXML
    private TextField chaveCod;
    @FXML
    private TextField chaveCodAnt;
    @FXML
    private TextField chaveNome;
    @FXML
    public Button btnEditarReceita;
    @FXML
    public Button btnNovaReceita;
    @FXML
    public Button btnDuplicaReceita;
    @FXML 
    public Button btnCadPac;
    
    @FXML
    private TableView<Paciente> tablePacientes;
    @FXML
    private TableColumn<Paciente,Integer> codCol;
    @FXML
    private TableColumn<Paciente,Integer> codAntCol;
    @FXML 
    private TableColumn<Paciente,String> nomeCol;
    
    @FXML
    private TableView<Receita> tableReceitas;
    @FXML
    public TableColumn<Receita,String> viewDataCol;
    @FXML
    public TableColumn<Receita,Boolean> viewReceitaCol;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePacientes();
        initTableReceitas();
        setToolTips();
//        addPacienteListener();
//        configButtons();
    }  

    public void btnCadPacFired(ActionEvent event) throws Exception {
        if (!tablePacientes.getSelectionModel().isEmpty()) {
            String fxmlGUI = "fxml/Paciente.fxml";
            String fxmlTitle = "Cadastro do Paciente";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            GUIFactory cadPaciente = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle, this.getStage());
            PacienteController controller = (PacienteController) cadPaciente.getController();
            cadPaciente.showAndWait();
        }
    }
    
    public void btnEditarReceitaFired(ActionEvent event) throws Exception {
        if (!tableReceitas.getSelectionModel().isEmpty()) {
            String fxmlGUI = "fxml/Receita.fxml";
            String fxmlTitle = "Nova Receita";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            GUIFactory novaReceita = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle, this.getStage());
            ReceitaController controller = (ReceitaController) novaReceita.getController();
            controller.initListeners();
//            controller.initVars(paciente,receita);
            novaReceita.show();
        }
    }

    public void btnNovaReceitaFired(ActionEvent event) throws Exception {
        receita = new Receita();
        String fxmlGUI = "fxml/Receita.fxml";
        String fxmlTitle = "Nova Receita";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory novaReceita = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        ReceitaController controller = (ReceitaController) novaReceita.getController();
        controller.initListeners();
//        controller.initVars(paciente,receita);
        novaReceita.show();
    }
    
    public void btnDuplicaReceitaFired(ActionEvent event) throws Exception {
        if (!tableReceitas.getSelectionModel().isEmpty()) {
            receita.setReceita_id(null);
            receita.setDataEmissao(LocalDate.now());
            String fxmlGUI = "fxml/Receita.fxml";
            String fxmlTitle = "Copiar Receita";
            StageStyle fxmlStyle = StageStyle.UTILITY;
            GUIFactory novaReceita = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle, this.getStage());
            ReceitaController controller = (ReceitaController) novaReceita.getController();
            controller.initListeners();
//            controller.initVars(paciente,receita);
            novaReceita.show();
        }
    }
    
    private void initTablePacientes() {
        codCol.setCellValueFactory(new PropertyValueFactory<Paciente,Integer>("id"));
        codAntCol.setCellValueFactory(new PropertyValueFactory<Paciente,Integer>("codAntigo"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
    }

    private void initTableReceitas() {
        
        viewDataCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Receita, String> p) {
                return new SimpleObjectProperty<>(DateUtil.formataData(p.getValue().getDataEmissao()));
            }
         });
        
        viewReceitaCol.setSortable(false);
        viewReceitaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita,Boolean>, ObservableValue<Boolean>>() {
            @Override 
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Receita,Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        viewReceitaCol.setCellFactory(new Callback<TableColumn<Receita,Boolean>, TableCell<Receita, Boolean>>() {
              @Override public TableCell<Receita,Boolean> call(TableColumn<Receita,Boolean> receitaBooleanTableColumn) {
                return new ViewReceitaCell(stage, tableReceitas);
              }
        });            
    }    

    
    public void onPacientesSelect(MouseEvent event) { 
        if (!tablePacientes.getSelectionModel().isEmpty()) {
            paciente = tablePacientes.getSelectionModel().getSelectedItem();
            Receitas receitas = new Receitas();
            tableReceitas.setItems(receitas.getObsLista(paciente));
        }
    }        

    public void onReceitasSelect(MouseEvent event) { 
        if (!tableReceitas.getSelectionModel().isEmpty()) {
            receita = tableReceitas.getSelectionModel().getSelectedItem();
            configButtons();
        }
    }
    
    public void chaveCodFired(ActionEvent event) {
        
        Pacientes pacientes = new Pacientes();
        olPacientes.setAll(pacientes.getObsListaWithCod(Integer.parseInt(chaveCod.getText())));
        tablePacientes.setItems(olPacientes);
    }
    public void chaveCodAntFired(ActionEvent event) {

        Pacientes pacientes = new Pacientes();
        olPacientes.setAll(pacientes.getObsListaWithCodAnt(Integer.parseInt(chaveCodAnt.getText())));
        tablePacientes.setItems(olPacientes);
    }
    public void chaveNomeFired(ActionEvent event) {

        Pacientes pacientes = new Pacientes();
        String chave = chaveNome.getText();
        olPacientes.setAll(pacientes.getObsListaWithNome(chave));
        tablePacientes.setItems(olPacientes);
    }    

    private class ViewReceitaCell extends TableCell<Receita, Boolean> {
    final Button viewButton       = new Button("...");    
    final StackPane paddedButton = new StackPane();
    final DoubleProperty buttonY = new SimpleDoubleProperty();
    
    ViewReceitaCell(final Stage stage, final TableView table) {
      paddedButton.setPadding(new Insets(3));
      paddedButton.getChildren().add(viewButton);
      viewButton.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent mouseEvent) {
          buttonY.set(mouseEvent.getScreenY());
        }
      });
      viewButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent actionEvent) {  
            Double y = buttonY.get();
            try {
//                showAlertDlg(msg,dtl,'E');
            } catch (Exception ex) {
                Logger.getLogger(ReceitaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      });
    }
 
    /** places an add button in the row only if the row is not empty. */
    @Override protected void updateItem(Boolean item, boolean empty) {
      super.updateItem(item, empty);
      if (!empty) {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(paddedButton);
      }
    }
  }
    
    public void setToolTips() {
      btnEditarReceita.setTooltip(new Tooltip("Editar/Excluir/Imprimir Receita"));
      btnNovaReceita.setTooltip(new Tooltip("Nova receita"));
      btnDuplicaReceita.setTooltip(new Tooltip("Duplica a receita selecionada"));
  }
  
    public void configButtons() {
      btnEditarReceita.setDisable(receita.getReceita_id() == -1);
      btnNovaReceita.setDisable(olPacientes.isEmpty()); 
      btnDuplicaReceita.setDisable(receita.getReceita_id() == -1);
  }

  
    private void addPacienteListener() { 
        olPacientes.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            configButtons();
        }
        });
    }   
  
  
}
