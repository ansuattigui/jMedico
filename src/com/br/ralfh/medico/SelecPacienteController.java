package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Paciente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class SelecPacienteController extends Controller {

    ObservableList<Paciente> sopPacientes;
    Boolean closeModal;
    @FXML private Button btnConfPac;
    @FXML private Button btnCancPac;
    @FXML public TableView<Paciente> tabelaPacientes;
    @FXML private TableColumn<Paciente,Integer> codCol;
    @FXML private TableColumn<Paciente,Integer> codAntCol;
    @FXML private TableColumn<Paciente,String> nomeCol;        
    
    public SelecPacienteController() {   
        sopPacientes = FXCollections.observableArrayList(); 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTablePacientes();
        addPacientesListener();
    }    
    
    public void setPaciente(ObservableList<Paciente> pacientes) {
        this.sopPacientes.setAll(pacientes);
    }
    
    public void btnConfPacFired(ActionEvent event) {
        this.closeModal = true;
        this.stage.close();
    }
    public void btnCancPacFired(ActionEvent event) {
        this.closeModal = false;
        this.stage.close();
    }
    
    
    private void initTablePacientes() {
        codCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        codAntCol.setCellValueFactory(new PropertyValueFactory<>("codAntigo"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }
    
    private void addPacientesListener() { 
        sopPacientes.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaPacientes.setItems(sopPacientes);
        }
        });
    }   
    
    
}
