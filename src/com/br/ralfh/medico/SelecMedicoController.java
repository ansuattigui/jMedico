package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Medico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class SelecMedicoController extends Controller {

    ObservableList<Medico> sopMedicos;
    Boolean closeModal;
    @FXML private Button btnConf;
    @FXML private Button btnCanc;
    @FXML public TableView<Medico> tabelaMedicos;
    @FXML private TableColumn<Medico,String> medicoCol;
    @FXML private TableColumn<Medico,String> nomeCol;        
    @FXML
    private Insets x1;
    
    public SelecMedicoController() {   
        sopMedicos = FXCollections.observableArrayList(); 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableMedicos();
        addMedicosListener();
    }    
    
    public void setMedico(ObservableList<Medico> medicos) {
        this.sopMedicos.setAll(medicos);
    }
    
    @FXML
    public void btnConfFired(ActionEvent event) {
        this.closeModal = true;
        this.stage.close();
    }
    @FXML
    public void btnCancFired(ActionEvent event) {
        this.closeModal = false;
        this.stage.close();
    }
    
    
    private void initTableMedicos() {
        medicoCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
    }
    
    private void addMedicosListener() { 
        sopMedicos.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaMedicos.setItems(sopMedicos);
        }
        });
    }   
    
//    agenda: rotina para criar botões baseados nos médicos do cadastro
//    verificar como sALVAR NO REGISTRO OU EM INI
}
