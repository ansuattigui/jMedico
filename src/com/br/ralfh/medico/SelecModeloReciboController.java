package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.ModeloRecibo;
import com.br.ralfh.medico.modelos.ModelosRecibo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class SelecModeloReciboController extends Controller {

    ObservableList<ModeloRecibo> sopModelos;
    //Boolean getCloseModal;
    @FXML private Button btnConfirma;
    @FXML private Button btnCancela;
    @FXML public TableView<ModeloRecibo> tabelaModelos;
    @FXML private TableColumn<ModeloRecibo,Integer> ordemCol;
    @FXML private TableColumn<ModeloRecibo,String> modeloCol;
    @FXML public WebView wvModelo; 
    
    public SelecModeloReciboController() {   
        sopModelos = FXCollections.observableArrayList(); 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableModelos();
        addModelosListener();
        AddListenerSelecModelo();
        initDados();
    }   
    
    private void initDados() {
        sopModelos.setAll(ModelosRecibo.getObsLista());
    }

    private void initTableModelos() {
        ordemCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
    }
    
    private void addModelosListener() { 
        sopModelos.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaModelos.setItems(sopModelos);
        }
        });
    }   
    
    public String getNomeModelo(){
        return tabelaModelos.getSelectionModel().getSelectedItem().getNomeModelo();
    }
    public String getCabecalhoModelo() {
        return tabelaModelos.getSelectionModel().getSelectedItem().getCabecalho();
    }
    public String getCorpoModelo() {
        return tabelaModelos.getSelectionModel().getSelectedItem().getCorpo();
    }
    public String getRodapeModelo() {
        return tabelaModelos.getSelectionModel().getSelectedItem().getRodape();
    }
    
    public void AddListenerSelecModelo() {
        tabelaModelos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            String htmlCabecalho = getCabecalhoModelo();
            String htmlCorpo = getCorpoModelo();
            String htmlRodape = getRodapeModelo();            
            String htmlNew = htmlCabecalho+htmlCorpo+htmlRodape;
            wvModelo.getEngine().loadContent(htmlNew);
        }
        }); 
    }
    
    
    
}
