package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatSocketsClient;
import com.br.ralfh.medico.chat.ChatSocketsServer;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    
    private String target;
    
    private ChatSocketsServer ss;
    private ChatSocketsClient sc;
    private Thread tSC;
    
    public ChatController() {
        ss = new ChatSocketsServer();
        sc = new ChatSocketsClient(target);
//        tSS = new Thread(ss);
//        tSS.start();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListeners();
        getDestinos();
        setToolTips();
        setButtons(false);        
    }    
        
    public void initListeners() {
        addListenerCbDestino();
    }

    private void setToolTips() {
        btnEnviar.setTooltip(new Tooltip("Clique para enviar texto"));
    }
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    private void setButtons(Boolean chave) {
        btnEnviar.setDisable(chave);    
    }
    
    private void getDestinos() {        
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
            target = InetAddress.getByName(this.target);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sc.  implementar send and receive...
        
        sc = new SocketsClient(1, cxSaida.getText(), target);
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
                target = cbDestino.getSelectionModel().getSelectedItem().substring(att1+1,att2).trim();
            }        
        });
    }    
    
    
}