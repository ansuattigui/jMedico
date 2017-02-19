package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatConexao;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
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
public class ChatController extends Controller implements Observer {

    /**
     * Initializes the controller class.
     */
    
    @FXML Label lblCliente;
    @FXML TextField cxSaida;
    @FXML TextArea cxConversa;
    @FXML ChoiceBox<String> cbDestino;
    @FXML Button btnEnviar; 
        
    private final int PORT = 8521;
    private String ip;
    private String destino;
    private ChatConexao conexao;
        
    public ChatController() {
        //super("Chat Simples em Java by @pcollares");
        this.conexao = new ChatConexao(ip, PORT);
        //initComponents();
        conexao.addObserver(this);
        escreve("Chat iniciado com " + conexao.getIp() + ":" + conexao.getPorta());
        cxSaida.requestFocus();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initListeners();
        getDestinos();
        setToolTips();
//        setButtons(false);        
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
    
    public void conectar() throws IOException {
    }    
    
    public void addListenerCbDestino() {
        cbDestino.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {                
                Integer at = cbDestino.getSelectionModel().getSelectedItem().indexOf("-");
                ip = cbDestino.getSelectionModel().getSelectedItem().substring(0,at).trim();

                Integer att1 = cbDestino.getSelectionModel().getSelectedItem().indexOf("(");
                Integer att2 = cbDestino.getSelectionModel().getSelectedItem().indexOf(")");
                destino = cbDestino.getSelectionModel().getSelectedItem().substring(att1+1,att2).trim();
            }        
        });
    }    
    
    
     public void btnEnviarFired(ActionEvent event) {    
       if (!cxSaida.getText().isEmpty()) {
            conexao.envia(cxSaida.getText());
            escreve("Você disse: "+cxSaida.getText());
            cxSaida.setText("");
        }
    }

    private void escreve(String texto){
        cxConversa.appendText(texto+"\n");
         if (!cxConversa.getText().isEmpty() && !cxConversa.isFocused()) {
//                cxConversa.setCaretPosition(cxConversa.getText().length() - 1);
            }
        
    }    

    @Override
    public void update(Observable o, Object arg) {
        escreve(conexao.getMensagem());
    }
    
    
}