package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Usuario;
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
public class SelecUsuarioController extends Controller {

    ObservableList<Usuario> sopUsuarios;
    Boolean closeModal;
    @FXML private Button btnConf;
    @FXML private Button btnCanc;
    @FXML public TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario,String> usuarioCol;
    @FXML private TableColumn<Usuario,String> nomeCol;        
    
    public SelecUsuarioController() {   
        sopUsuarios = FXCollections.observableArrayList(); 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableUsuarios();
        addUsuariosListener();
    }    
    
    public void setUsuario(ObservableList<Usuario> usuarios) {
        this.sopUsuarios.setAll(usuarios);
    }
    
    public void btnConfFired(ActionEvent event) {
        this.closeModal = true;
        this.stage.close();
    }
    public void btnCancFired(ActionEvent event) {
        this.closeModal = false;
        this.stage.close();
    }
    
    
    private void initTableUsuarios() {
        usuarioCol.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
    }
    
    private void addUsuariosListener() { 
        sopUsuarios.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaUsuarios.setItems(sopUsuarios);
        }
        });
    }   
    
    
}
