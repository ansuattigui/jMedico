package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.AtestadoExterno;
import com.br.ralfh.medico.modelos.AtestadosExterno;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class SelecAtestadoExternoController extends Controller {
    
    private AtestadoExterno atestado;
    private Boolean closeModal;
    @FXML private Button btnConfirma;
    @FXML private Button btnCancela;
    @FXML public TableView<AtestadoExterno> tabelaAtestados;
    @FXML private TableColumn<AtestadoExterno,String> colPaciente;
    @FXML private TableColumn<AtestadoExterno,String> colAtestado;
    @FXML private TableColumn<AtestadoExterno,String> colData;
    
    public SelecAtestadoExternoController() {   
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelaAtestados();
        initDados();
        addTabelaListener();
    }    
    
    public void btnConfirmaFired(ActionEvent event) {
        this.closeModal = true;
        this.stage.close();
    }
    public void btnCancelaFired(ActionEvent event) {
        this.closeModal = false;
        this.stage.close();
    }
    
    
    private void initTabelaAtestados() {
        
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AtestadoExterno,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AtestadoExterno,String> h) {
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                return new SimpleObjectProperty<>(dt.format(h.getValue().getData()));
            }
        });
//        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colAtestado.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
    
    private void initDados() { 
        tabelaAtestados.setItems(AtestadosExterno.getObsLista());
    } 
    
    
    private void addTabelaListener() { 
        tabelaAtestados.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            atestado = tabelaAtestados.getSelectionModel().getSelectedItem();
        }
        }); 
    }   

    /**
     * @return the atestado
     */
    public AtestadoExterno getAtestado() {
        return atestado;
    }

    /**
     * @param atestado the atestado to set
     */
    public void setAtestado(AtestadoExterno atestado) {
        this.atestado = atestado;
    }

    /**
     * @return the closeModal
     */
    public Boolean getCloseModal() {
        return closeModal;
    }
    
    
    
}
