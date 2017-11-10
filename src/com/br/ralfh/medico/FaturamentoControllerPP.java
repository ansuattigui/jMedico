package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Convenio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class FaturamentoControllerPP extends Controller {

    ObservableList<Convenio> sopConvenios;
//    Boolean getCloseModal;
    @FXML public Button btnConf;
    @FXML public Button btnCanc;
    @FXML public TableView<Convenio> tabelaConvenios;
    @FXML public TableColumn<Convenio,String> codAnsCol;
    @FXML public TableColumn<Convenio,String> nomeCol;        
    @FXML public DatePicker dataInicio;
    @FXML public DatePicker dataFim;
    
    public FaturamentoControllerPP() {   
        sopConvenios = FXCollections.observableArrayList(); 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableConvenios();
        addConveniosListener();
    }    
    
    
    
    public void setConvenio(ObservableList<Convenio> convenios) {
        this.sopConvenios.setAll(convenios);
    }
    
    public void btnConfFired(ActionEvent event) {
//        this.getCloseModal = true;
//        this.stage.close();
        
        
    }
    public void btnCancFired(ActionEvent event) {
//        this.getCloseModal = false;
//        this.stage.close();
        
        
    }
    
    
    private void initTableConvenios() {
        codAnsCol.setCellValueFactory(new PropertyValueFactory<>("codANS"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }
    
    private void addConveniosListener() { 
        sopConvenios.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaConvenios.setItems(sopConvenios);
        }
        });
    }   
    
    
}
