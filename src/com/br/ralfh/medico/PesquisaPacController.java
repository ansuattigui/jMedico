package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.HorarioAgenda;
import com.br.ralfh.medico.modelos.HorariosAgenda;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Pacientes;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PesquisaPacController extends Controller {
    
    ObservableList<Paciente> sopPacientes = FXCollections.observableArrayList();
//    Receita receita;
    Paciente paciente;
    
    @FXML public TextField chaveCod;
    @FXML public TextField chaveCodAnt;
    @FXML public TextField chaveNome;
    @FXML public Button btnBuscar;
    @FXML public Button btnAgenda;
    @FXML public Button btnLimpar;
    @FXML public Button btnSair;
    
    @FXML public TableView<Paciente> tablePacientes;
    @FXML public TableColumn<Paciente,Integer> codCol;
    @FXML public TableColumn<Paciente,Integer> codAntCol;
    @FXML public TableColumn<Paciente,String> nomeCol;
    
    @FXML public TableView<HorarioAgenda> tableDatas;
    @FXML public TableColumn<HorarioAgenda,String> dataCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePacientes();
        initTableDatas();
        setToolTips();
        addPacienteListener();
        onPacientesSelect();
//        configButtons();
    }  

    public void buscarFired(ActionEvent event) throws Exception {        
        if (!chaveCod.getText().isEmpty()) {
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCod(Integer.parseInt(chaveCod.getText()))));
        } else if (!chaveCodAnt.getText().isEmpty()) {
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(Integer.parseInt(chaveCodAnt.getText()))));
        } else if (!chaveNome.getText().isEmpty()) {            
            String nome = chaveNome.getText();
            nome = "%" + nome.replace(" ", "%");            
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithNome(nome)));
        } else {
            ShowDialog("INFO", "Informe uma chave para pesquisa", null,this.getStage());
        }
    }

    @FXML public void agendaFired(ActionEvent ae) {
        
    }
    
    
    @FXML public void limparFired(ActionEvent ae) {
        chaveCod.clear();
        chaveCodAnt.clear();
        chaveNome.clear();
        sopPacientes.clear();
    }
    
    
    @FXML
    public void sairFired(ActionEvent event) {
        getStage().close();
    }
    
    private void initTablePacientes() {
        codCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        codAntCol.setCellValueFactory(new PropertyValueFactory<>("codAntigo"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    
    private void initTableDatas() {
        
        dataCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HorarioAgenda,String>, ObservableValue<String>>() {            
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HorarioAgenda,String> p) {
                return new SimpleObjectProperty<>(Util.formataData(Util.ld(p.getValue().getData())));
            }
         });
        
    }    

    
    @FXML
    public void onPacientesSelect() { 
        tablePacientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            paciente = tablePacientes.getSelectionModel().getSelectedItem();
            tableDatas.setItems(FXCollections.observableArrayList(HorariosAgenda.getListaPaciente(paciente)));
        }
        }); 
/*        
        if (!tablePacientes.getSelectionModel().isEmpty()) {
            paciente = tablePacientes.getSelectionModel().getSelectedItem();
//            Receitas receitas = new Receitas();
//            tableReceitas.setItems(receitas.getObsLista(paciente));
        }
*/        
    }        

/*  
    @FXML
    public void onReceitasSelect(MouseEvent event) { 
        if (!tableReceitas.getSelectionModel().isEmpty()) {
            receita = tableReceitas.getSelectionModel().getSelectedItem();
            configButtons();
        }
    }
*/    

    
    public void setToolTips() {
      btnBuscar.setTooltip(new Tooltip("Localizar paciente com a chave fornecida"));
      btnSair.setTooltip(new Tooltip("Fechar esta janela"));
  }

    private void addPacienteListener() { 
        sopPacientes.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {            
            tablePacientes.getItems().setAll(sopPacientes);
            //configButtons();
        }
        });
    }   
}
