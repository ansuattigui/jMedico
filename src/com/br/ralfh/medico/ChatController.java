package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatClient;
import com.br.ralfh.medico.chat.ChatServer;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    @FXML TextField cxSaida;
    @FXML TextArea cxConversa;
    @FXML ChoiceBox<String> cbDestino;
    @FXML Button btnEnviar; 
    
    private String host;
    
    private SocketsClient sc;
    private Thread tSC;
    
    private HashMap<String,String> hmInChat;
    
    public ChatController() {
        hmInChat = new HashMap<>();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListeners();
        initConexoes();
        setToolTips();
        setButtons(false);        
    }    
        
    public void initListeners() {
        addPacienteListener();
        addListenerCbDestino();
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
/*        
    public boolean checaReceita() {
        boolean resultado = Boolean.FALSE;
        if (true) {
            ShowDialog("EX", "Prescreva ao menos um medicamento", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
*/    
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    private void setButtons(Boolean chave) {
        btnEnviar.setDisable(chave);    
    }
    
    private void initConexoes() {        
        ArrayList<Conexao> conexoes = Conexoes.getListaOutros(MedicoController.getConexao());
        ArrayList<String> strConexoes = new ArrayList<>();
        
        for (Conexao item : conexoes) {
            String s = item.getIp()+ " - " + item.getUsuario().getNomeCompleto()+" em " + "("+item.getMachine()+")";
            boolean add = strConexoes.add(s);
        }        
                
        if (strConexoes.isEmpty()) {
            strConexoes.add("Não há outros usuários conectados");
        }
        ObservableList<String> options = FXCollections.observableArrayList(strConexoes);    
        cbDestino.getItems().setAll(options);
        cbDestino.getSelectionModel().selectFirst();
    }
    
    public void btnEnviarFired(ActionEvent event) {    
        InetAddress target = null;
        try {
            target = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sc = new SocketsClient(0, cxSaida.getText(), target);
        tSC = new Thread(sc);
        tSC.start();       
//        autorizarButton.setDisable(Boolean.TRUE);    
    }
    
    public void addListenerCbDestino() {
        cbDestino.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {                
                Integer at = cbDestino.getSelectionModel().getSelectedItem().indexOf("-");
                byte[] ip = cbDestino.getSelectionModel().getSelectedItem().substring(0,at).trim().getBytes();

                Integer att1 = cbDestino.getSelectionModel().getSelectedItem().indexOf("(");
                Integer att2 = cbDestino.getSelectionModel().getSelectedItem().indexOf(")");
                host = cbDestino.getSelectionModel().getSelectedItem().substring(att1+1,att2).trim();
            }        
        });
    }    
    
    
}