package com.br.ralfh.medico;

import com.br.ralfh.medico.chat.ChatConexao;
import com.br.ralfh.medico.jdbc.ConnectionFactory;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Conexao;
import com.br.ralfh.medico.modelos.Conexoes;
import com.br.ralfh.medico.modelos.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
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
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;

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
    @FXML Button btnGruposExames;
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
    @FXML Button btnReceitaNaoPaciente;    
    @FXML Button btnReceitaControlada;
    @FXML Button btnReceitaA4;
    @FXML Button btnReceitaMeioA4;
    @FXML Button btnEnvelope162;
    @FXML Button btnEnvelope229;
    @FXML ImageView ivFundo;
    @FXML Line lineFundo;    
    @FXML StackPane spTitle;
    
    public MedicoController() {
        chat = null;
        perfilUsuario = null;
        conexao = null;
        connFact = new ConnectionFactory();
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
        btnGruposExames.setTooltip(new Tooltip("Criar Grupos de Exames"));
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
        ChatController controller = (ChatController) chat.getController();
//        controller.addStageCloseListener();
        chat.showAndWait();
        chat = null;
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
    
    public void btnGruposExamesFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/GrupoExames.fxml";
        String titleGUI = "Grupos de Exames";
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
    
    @FXML
    public void btnReceitaNaoPacienteFired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/ReceitaExterno.fxml";
        StageStyle fxmlStyle = StageStyle.UTILITY;        
        GUIFactory receitas = new GUIFactory(fxmlGUI,null,fxmlStyle,this.getStage());
        receitas.showAndWait();
    }
    
    @FXML
    public void btnEnvelope162Fired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/envelope_162_114.fxml";
        StageStyle fxmlStyle = StageStyle.UTILITY;        
        GUIFactory receitas = new GUIFactory(fxmlGUI,null,fxmlStyle,this.getStage());
        receitas.showAndWait();
    }
    
    @FXML
    public void btnEnvelope229Fired(ActionEvent event) throws Exception {
        String fxmlGUI = "fxml/envelope_229_114.fxml";
        StageStyle fxmlStyle = StageStyle.UTILITY;        
        GUIFactory receitas = new GUIFactory(fxmlGUI,null,fxmlStyle,this.getStage());
        receitas.showAndWait();
    }

    @FXML
    public void btnEnvelopeFired(ActionEvent event) throws Exception {
        String fileName = "";
        if (event.getSource().equals(btnEnvelope162)) {
            fileName = "relatorios/Envelopes/envelope_162_114.jasper";
        } else if (event.getSource().equals(btnEnvelope229)) {
            fileName = "relatorios/Envelopes/envelope_229_114.jasper";
        }
        HashMap hm = new HashMap();        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/logoJHTC-Envelope.gif"));
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/inforJHTC-Envelope.gif"));         
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            relat.openReport( "Envelope",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    
    @FXML
    public void btnReceitaEmBrancoFired(ActionEvent event) throws Exception {
        String fileName = "";
        if (event.getSource().equals(btnReceitaControlada)) {
            fileName = "relatorios/controladas/JControladaMeioA4EmBranco.jasper";
        } else if (event.getSource().equals(btnReceitaA4)) {
            fileName = "relatorios/receitas/JReceitaA4EmBranco.jasper";
        } else if (event.getSource().equals(btnReceitaMeioA4)) {
            fileName = "relatorios/receitas/JReceitaMeioA4EmBranco.jasper";
        }
        HashMap hm = new HashMap();
        hm.put("idPaciente", null);
        hm.put("dataReceita", null);    
        hm.put("prescricao", null);
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif"));
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif"));         
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            relat.openReport( "Receita",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
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
            chat(conexaoChat.getMensagem());
        }
    }

/*        } else {
            if (conexaoChat.getMensagem().toUpperCase()=="SAIR") {
                chat.close();
            }
*/
    
    
    private void chat(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String fxmlGUI = "fxml/Chat.fxml";
                String titleGUI = "Bate Papo";
                StageStyle fxmlStyle = StageStyle.DECORATED;
                try {
                    chat = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,JDocplus.getMainStage());
                    ChatController cont = (ChatController) chat.getController();
//                    cont.addStageCloseListener();                    
                    cont.escreve(msg);
                    chat.showAndWait();
                    chat = null;
                } catch (IOException ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
}
