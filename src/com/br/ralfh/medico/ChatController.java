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
    
    private ArrayList<Conexao> conexoes;
    private final int PORT = 8521;
    private String ip;
    private String destino;
    private ChatConexao conexao;
        
    public ChatController() {
        this.conexao = null;
        this.conexoes = new ArrayList<>();
//        cxSaida.requestFocus();
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
        conexoes = Conexoes.getListaOutros(MedicoController.getConexao());
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
    
    public void addListenerCbDestino() {
        cbDestino.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {  
                if (!conexoes.isEmpty()) {
                    Integer at = cbDestino.getSelectionModel().getSelectedItem().indexOf("-");
                    ip = cbDestino.getSelectionModel().getSelectedItem().substring(0,at).trim();
                    
                    
                    

                    Integer att1 = cbDestino.getSelectionModel().getSelectedItem().indexOf("(");
                    Integer att2 = cbDestino.getSelectionModel().getSelectedItem().indexOf(")");
                    destino = cbDestino.getSelectionModel().getSelectedItem().substring(att1+1,att2).trim();
                } else {
                    setButtons(true);
                }
            }        
        });
    }    
    
    
     public void btnEnviarFired(ActionEvent event) throws IOException {   
        if (conexao == null) {
            conectar();
        }
         
       if (!cxSaida.getText().isEmpty()) {
            conexao.envia(cxSaida.getText(),ip);
            escreve(destino+": "+cxSaida.getText());
            cxSaida.setText("");
        }
    }
     
    public void conectar() throws IOException {
        conexao = MedicoController.conexaoChat;
        conexao.addObserver(this);
        escreve("Chat iniciado com " + ip + ":" + conexao.getPorta());
    }    
         

    public void escreve(String texto){
        cxConversa.appendText(texto+"\n");
         if (!cxConversa.getText().isEmpty() && !cxConversa.isFocused()) {
//                cxConversa.set   .setCaretPosition(cxConversa.getText().length() - 1);
        }        
    }    

    @Override
    public void update(Observable o, Object arg) {
        escreve(conexao.getMensagem());
    }
    
}