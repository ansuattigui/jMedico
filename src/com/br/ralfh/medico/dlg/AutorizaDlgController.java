/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.dlg;

import com.br.ralfh.medico.Controller;
import com.br.ralfh.medico.MedicoController;
import com.br.ralfh.medico.SocketsClient;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import com.br.ralfh.medico.modelos.HorarioAgenda;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public final class AutorizaDlgController extends Controller {
    
    @FXML public Label messageLabel;
    @FXML public Label detailsLabel;
    @FXML public HBox actionParent;
    @FXML public Button autorizarButton;
    @FXML public Button cancelButton;
    @FXML public HBox okParent;
    @FXML public Button okButton;
    @FXML public ImageView imDlg;
    @FXML public ComboBox<String> cbDestinos;

    private Properties propriedades; 
    public Boolean optChoosen;
    private HorarioAgenda hor;
    
    private SocketsClient sc;
    private Thread tSC;

    public AutorizaDlgController() {
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propriedades = new Properties();        
    }  
    
    public void configProperties() {        
        try {
            propriedades.load(getClass().getResourceAsStream("AutorizarDlg.properties"));
        } catch (IOException ioe) {
            System.out.println("Arquivo não encontrado");
        }
        
        okButton.setText(propriedades.getProperty("button.ok"));
        okButton.setDisable(Boolean.TRUE);
        
        cancelButton.setVisible(Boolean.FALSE);
        
        autorizarButton.setText(propriedades.getProperty("button.action"));
        autorizarButton.setVisible(Boolean.TRUE);
        
        messageLabel.setText(propriedades.getProperty("label.message"));
        detailsLabel.setText(hor.getPaciente());
        
        Image im = new Image(getClass().getResourceAsStream("Autorizacao.jpg"));
        imDlg.setImage(im);
           
        addStageCloseListener();
        initComboConexoes();

    }    

    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                if ((tSC!=null) && (tSC.isAlive())) {
                    tSC.interrupt();
                }   
            }
        });     
    }
    
    
    public void btnOkFired(ActionEvent event) {
        this.getStage().close();
    }

    public void btnCancelFired(ActionEvent event) {
//        this.optChoosen = Boolean.FALSE;
//        this.getStage().close();
    }
    
    
    public void btnAutorizarFired(ActionEvent event) {                        
        Integer at = cbDestinos.getSelectionModel().getSelectedItem().indexOf("-");
        byte[] ip = cbDestinos.getSelectionModel().getSelectedItem().substring(0,at).trim().getBytes();

        Integer att1 = cbDestinos.getSelectionModel().getSelectedItem().indexOf("(");
        Integer att2 = cbDestinos.getSelectionModel().getSelectedItem().indexOf(")");
        String host = cbDestinos.getSelectionModel().getSelectedItem().substring(att1+1,att2).trim();
        
        InetAddress target = null;
        try {
            target = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(AutorizaDlgController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sc = new SocketsClient(0, hor.getId(),target);
        tSC = new Thread(sc);
        tSC.start();       
        autorizarButton.setDisable(Boolean.TRUE);
        okButton.setDisable(Boolean.FALSE);        
    }
    
    public void getLocalHostIP() {
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            System.out.println(address);
        } catch (UnknownHostException uhEx) {
            System.out.println("Could not find local address!");
        }        
    }

    /**
     * @param horario the hor to set
     */
    public void setHorario(HorarioAgenda horario) {
        this.hor = horario;
        configProperties();
    }
    
    private void initComboConexoes() {        
        ArrayList<Conexao> conexoes = Conexoes.getListaOutros(MedicoController.getConexao());
        ArrayList<String> strConexoes = new ArrayList<>();
        
        for (Conexao item : conexoes) {
            String s = item.getIp()+ " - " + item.getUsuario().getNomeCompleto()+" em " + "("+item.getMachine()+")";
            boolean add = strConexoes.add(s);
        }        
                
        if (strConexoes.isEmpty()) {
            strConexoes.add("Não há outros usuários conectados");
            autorizarButton.setDisable(Boolean.TRUE);
            okButton.setDisable(Boolean.FALSE);
        }
        ObservableList<String> options = FXCollections.observableArrayList(strConexoes);    
        cbDestinos.getItems().setAll(options);
        cbDestinos.getSelectionModel().selectFirst();
    }
    
    
}
