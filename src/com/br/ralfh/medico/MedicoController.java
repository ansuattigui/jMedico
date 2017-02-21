package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatConexao;
import com.br.ralfh.medico.jdbc.ConnectionFactory;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import com.br.ralfh.medico.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ralfh
 */
public class MedicoController extends Controller implements Observer {
    
    private static EntityManagerFactory entityManagerFactory;
//    private static HashMap<String,GUIFactory> mapaJanelas;

    
    /** Dados de conexão do usuário com o Sistema **/    
    /**
     * @return the conexao
     */
    public static Conexao getConexao() {
        return conexao;
    }

    /**
     * @return the perfilUsuario
     */
    
    private static Usuario perfilUsuario;
    public static Conexao conexao;
    private SocketsServer ss;
    private Thread tSS;
    private static ConnectionFactory connFact;
    //private DialogGUI dialog;
    private GUIFactory chat;

    public static ChatConexao conexaoChat;
    
    @FXML Accordion menuPrincipal;
    @FXML TitledPane tpaneAgenda;
    @FXML Button btnPacientes;
    @FXML Button btnAgenda;
    @FXML Button btnModelosAtestado;
    @FXML Button btnModelosRecibo;
    @FXML Button btnGrupos;
    @FXML Button btnModos;
    @FXML Button btnMedicamentos;
    @FXML Button btnReceita;
    @FXML Button btnConfig;
    @FXML Button btnConvenio;
    @FXML Button btnTuss;
    @FXML Button btnFaturConvenio;
    @FXML Button btnRecibos;
    @FXML Button btnUsuario;
    @FXML Button btnMedico;
    @FXML Button btnAtestadoNaoPaciente;
    @FXML ImageView ivFundo;
    @FXML Line lineFundo;    
    @FXML StackPane spTitle;
    
    public MedicoController() {
        chat = null;
        perfilUsuario = null;
        conexao = null;
        connFact = new ConnectionFactory();
//        dialog = null;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            if (loginShow()==null) {
                System.exit(0);
            } else {
                conectaSistema(perfilUsuario);
            }
        } catch (IOException ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        menuPrincipal.setExpandedPane(tpaneAgenda);
        configToolTips();
        configBtns();
    }   
    
    
    /** Cria uma entrada na tabela de conexoes com os dados do usuário, máquina e conexão **/
    private void conectaSistema(Usuario user) {        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        conexao = new Conexao();
        conexao.setHoraConexao(Date.from(Instant.now()));
        conexao.setUsuario(user);
        conexao.setMachine(Conexoes.getLocalHostIP().getHostName());
        conexao.setIp(Conexoes.getLocalHostIP().getHostAddress());   
        try {
            manager.persist(conexao);        
            manager.getTransaction().commit();
        } catch (Exception ex) {
            ShowDialog("EX",ex.getMessage(), null,this.getStage());
        } finally {
            manager.close();     
        }
        
        ss = new SocketsServer();
        tSS = new Thread(ss);
        tSS.start();
        
        conexaoChat = new ChatConexao(conexao.getIp(), 8521);
        conexaoChat.addObserver(this);
        
    }
    
    
    /** remove entrada da tabela de conexões **/    
    private void desconectaSistema() {        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        manager.remove(manager.getReference(Conexao.class, getConexao().getId()));  
        manager.getTransaction().commit();
        manager.close();      
        
        tSS.interrupt();
    }
    
    
    /** Define ações realizadas quando do fechamento do sistema **/
    @Override
    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              desconectaSistema();
                System.exit(0);
          }
    });     
    }
    
    
    /** retorna perfil do usuario conectado para configuração de opções do sistema **/
    public static Usuario getPerfilUsuario() {
        return perfilUsuario;
    }
    
    /** Configura os ToolTips dos botões da tela principal do sistema **/
    private void configToolTips() {
        btnAgenda.setTooltip(new Tooltip("Acessar a Agenda do Médico"));
        btnModelosAtestado.setTooltip(new Tooltip("Configurar Modelos de Atestado Médico"));
        btnModelosRecibo.setTooltip(new Tooltip("Configurar Modelos de Recibo de Pagamento"));
        btnConvenio.setTooltip(new Tooltip("Acessar o Cadastro de Convênios"));
        btnFaturConvenio.setTooltip(new Tooltip("Acessar o Faturamento de Convênios"));
        btnUsuario.setTooltip(new Tooltip("Acessar o Cadastro de Usuários do Sistema"));
        btnMedico.setTooltip(new Tooltip("Acessar o Cadastro de Médicos do Sistema"));
        btnMedicamentos.setTooltip(new Tooltip("Cadastro de Medicamentos"));
        btnGrupos.setTooltip(new Tooltip("Cadastro de Grupos de Medicamento"));
        btnModos.setTooltip(new Tooltip("Cadastro de posologias"));
        btnPacientes.setTooltip(new Tooltip("Cadastro de Pacientes"));
    }
    
    private void configBtns() {
        btnAtestadoNaoPaciente.setDisable((!perfilUsuario.getTipoUsuario().equals("Medico")));
        
    }


    /** Apresenta a tela de Login do Sistema **/
    private Usuario loginShow() throws IOException {
        String fxmlGUI = "fxml/Login.fxml";
        String titleGUI = "Autenticação";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory login = new GUIFactory(fxmlGUI, titleGUI,fxmlStyle,this.getStage());
        LoginController controller = (LoginController) login.getController();
        login.showAndWait();
        perfilUsuario = controller.getUser();
        return perfilUsuario;
    }

    
    public void btnPacientesFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Pacientes.fxml";
        String titleGUI = "Cadastro do Paciente";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory pacientes = new GUIFactory(fxmlGUI, titleGUI,fxmlStyle,this.getStage());
        Controller controller = pacientes.getController();

        controller = (PacienteController) controller;
        //controller.addStageCloseListener();
        pacientes.showAndWait();   
    }
    
    public void btnModosFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Posologias.fxml";
        String titleGUI = "Posologias";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory posologia = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        posologia.showAndWait();
    }
    
    
    public void btnGruposFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Grupos.fxml";
        String titleGUI = "Grupo de Medicamentos";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory grupos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        grupos.showAndWait();
    }
            
    public void btnMedicamentosFired(ActionEvent event) throws Exception {                        
        String fxmlGUI = "fxml/Medicamentos.fxml";
        String titleGUI = "Medicamentos";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory medicamentos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        medicamentos.showAndWait();
    }
    
    public void btnReceitaFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Receita.fxml";
        String titleGUI = "Receitas do Paciente";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory receita = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        receita.showAndWait();
    }

    public void btnAgendaFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Agenda.fxml";
        String titleGUI = "Agenda de Consultas";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory agenda = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,null);//  this.getStage());
        agenda.showAndWait();
    }
    
    public void btnChatFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Chat.fxml";
        String titleGUI = "Bate Papo";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        chat = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        chat.showAndWait();
    }
    

    public void btnModelosAtestadoFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/ModeloAtestado.fxml";
        String titleGUI = "Modelos de Atestados Médicos";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory modelo = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        modelo.showAndWait();
    }

    public void btnModelosReciboFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/ModeloRecibo.fxml";
        String titleGUI = "Modelos de Recibos";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory modelo = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        modelo.showAndWait();
    }
    
    public void btnConvenioFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Convenio.fxml";
        String titleGUI = "Cadastro de Convenios";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory convenio = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        convenio.showAndWait(); 
    }

    @FXML
    public void btnFaturConvenioFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/FaturConvenio.fxml";
        String titleGUI = "Faturamento de Convenios";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory faturconvenio = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        faturconvenio.showAndWait();        
    }
    
    @FXML
    public void btnRecibosFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/FaturRecibos.fxml";
        String titleGUI = "Recibos Emitidos";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory recibos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        recibos.showAndWait();        
    }

    
    public void btnUsuarioFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Usuarios.fxml";
        String titleGUI = "Cadastro de Usuarios";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory usuario = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        usuario.showAndWait(); 
       
    }

    public void btnMedicoFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/Medicos.fxml";
        String titleGUI = "Cadastro de Medicos";
        StageStyle fxmlStyle = StageStyle.UNIFIED;
        GUIFactory medico = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
        medico.showAndWait();        
    }
    
    @FXML
    public void btnAtestadoNaoPacienteFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/AtestadoNaoPaciente.fxml";
        StageStyle fxmlStyle = StageStyle.UTILITY;        
        GUIFactory atestados = new GUIFactory(fxmlGUI,null,fxmlStyle,this.getStage());
        atestados.showAndWait();
    }
    
    
    /**
     * @return the connFact
     */
    public static ConnectionFactory getConn() {
        return connFact;
    }

    /**
     * @return the entityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * @param entityManagerFactory the entityManagerFactory to set
     */
    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        MedicoController.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (chat == null) {
            chat("");
        }
    }
    
    private void chat(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                String fxmlGUI = "fxml/Chat.fxml";
                String titleGUI = "Bate Papo";
                StageStyle fxmlStyle = StageStyle.DECORATED;
                try {
                    chat = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,JDocplus.getMainStage());
                    chat.showAndWait();
                    chat = null;
                } catch (IOException ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }

/*
                try {
                    dialog = new DialogGUI("CT",msg, null,JDocplus.getMainStage());
                    dialog.showAndWait();
                    dialog = null;
                } catch (IOException ex) {
                    Logger.getLogger(ChatConexao.class.getName()).log(Level.SEVERE, null, ex);
                }                    
*/
            }
        });
    }
    
    
}
