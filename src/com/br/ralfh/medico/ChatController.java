package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatClient;
import com.br.ralfh.medico.chat.ChatServer;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Medicamento;
import com.br.ralfh.medico.modelos.Medicamentos;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Posologia;
import com.br.ralfh.medico.modelos.Posologias;
import com.br.ralfh.medico.modelos.Prescricao;
import com.br.ralfh.medico.modelos.Receita;
import com.br.ralfh.medico.modelos.Receitas;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ChatController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    @FXML Label lblCliente;
    @FXML TextArea cxEntrada;
    @FXML TextArea cxSaida;
    @FXML TextField textoSaida;
    @FXML Button btnEnviar; 
    
    private ChatServer server;
    private ChatClient client;

    public ChatController() {
        server = new ChatServer();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
        
    public void initListeners() {
        addPacienteListener();
    }

    private void setToolTips() {
        btnEnviar.setTooltip(new Tooltip("Clique para enviar texto"));
    }
        
    private void addPacienteListener() { 
/*        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPaciente.get() != null) {
                try {
                    nomePaciente.setText(sopPaciente.get().getNome());
                    sopReceitas.setAll(Receitas.getLista(sopPaciente.get()));
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }); */
    }   
        
    public boolean checaReceita() {
        boolean resultado = Boolean.FALSE;
        if (true) {
            ShowDialog("EX", "Prescreva ao menos um medicamento", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
    
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    private void setButtons() {
        btnEnviar.setDisable(true);    
    }
}