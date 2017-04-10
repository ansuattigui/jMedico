package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoNuloException;
import com.br.ralfh.medico.modelos.CEP;
import com.br.ralfh.medico.modelos.CEPs;
import com.br.ralfh.medico.modelos.Convenios;
import com.br.ralfh.medico.modelos.FichaMedica;
import com.br.ralfh.medico.modelos.HorariosAgenda;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Pacientes;
import com.br.ralfh.medico.modelos.UF;
import com.br.ralfh.medico.modelos.UFs;
import com.br.ralfh.medico.modelos.Usuario;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PacienteController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private Paciente paciente;
    private StatusBtn status;
    private Integer OrigemExterna;
    private SimpleObjectProperty<Integer> sopIdade;
    private SimpleObjectProperty<Paciente> sopPaciente;
    private ObservableList<Paciente> sopPacientes;    
    private static Usuario perfilUsuario;
    private ObservableList<UF> ufs;
    private UF uf;
    private String cep1;
    private String cep2;
    private CEP cep;
    
    @FXML Button btnCriarPaciente;                @FXML Button btnAtualPaciente;
    @FXML Button btnDelPaciente;                  @FXML Button btnConfPaciente;
    @FXML Button btnCancPaciente;                 @FXML Button btnSair;
    @FXML Button btnFichaMedica;                  @FXML Button btnAtestados;
    @FXML Button btnReceitas;                     @FXML Button btnRecibos;
    @FXML Button btnPedidos;
    @FXML Button btnProcCodAnt;                   @FXML Button btnProcNome;
    @FXML Button btnProcurar;                     @FXML Button btnFotografar;
    @FXML ImageView imageFotografia;              @FXML TextField codPaciente;
    @FXML TextField codAntPaciente;               @FXML TextField nomePaciente;
    @FXML ChoiceBox<Sexo> sexoPaciente;           @FXML DateField nascPaciente;
    @FXML TextField idade;                        @FXML ComboBox naturPaciente;
    @FXML ComboBox nacionPaciente;                @FXML ChoiceBox<String> estCivilPaciente;
    @FXML ChoiceBox<Etnia> etniaPaciente;         @FXML TextField rgPaciente;
    @FXML TextField cpfPaciente;                  @FXML TextField profPaciente;
    @FXML TextField enderPaciente;                @FXML TextField numEndPaciente;
    @FXML TextField compEndPaciente;              @FXML TextField bairroPaciente;    
    @FXML FormattedTextField fmtCEP;              @FXML TextField cidadePaciente;
    @FXML ComboBox ufPaciente;                    @FXML TextField telResPaciente;
    @FXML TextField telComPaciente;               @FXML TextField celularPaciente;
    @FXML TextField emailPaciente;                @FXML ComboBox convPaciente;
    @FXML TextField matConvPaciente;
    
    @FXML TextField indicacao;                    @FXML TextField dataPrimConsulta;    
    @FXML ComboBox<StatusPaciente> statusPac;     @FXML ComboBox<SitCadastro> sitCadastro;
    
    private byte[] bFotografia; 

    public PacienteController() {
        this.status = StatusBtn.IDLE;
        this.sopPacientes = FXCollections.observableArrayList();
        this.sopPaciente = new SimpleObjectProperty<>();
        this.sopIdade = new SimpleObjectProperty<>(0);
        this.OrigemExterna = 0;
        perfilUsuario = MedicoController.getPerfilUsuario();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToolTips();
        habilEdicaoFired();
        initCombos();
        initFmtCep();
        addPacienteListener();
        addPacientesListener();
        bindDataNascIdade();
        addListenerDataNasc();
        addListenerUF();
        setButtons();
    } 
    
    @Override
    public void setPaciente(Integer pac, Integer horario) {
        sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(pac)));
        OrigemExterna = horario;
    }
    
    @Override
    public void setPaciente(Paciente pac, Integer horario) {
        paciente = pac;
        sopPaciente.set(paciente);
        OrigemExterna = horario;
    }

    @Override
    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              if (OrigemExterna > 0) {
                HorariosAgenda.atualizaPacienteHorario(paciente, OrigemExterna);
              }
          }
    });     
    }
    
    public void addListenerDataNasc() {
        nascPaciente.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (!Objects.isNull(newVal)) {
                Integer Idade = Period.between(Util.ld(nascPaciente.valueProperty().get()), LocalDate.now()).getYears();
                sopIdade.set(Idade);
            } else {
                sopIdade.set(0);
            }
            
        }
    });
    }
    
    private void bindDataNascIdade() {
        idade.textProperty().bind(sopIdade.asString());
    }                    
    
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPaciente.get() != null) {
                if (sopPaciente.get().getId() == -1) {
                    status = StatusBtn.INSERTING;
                } else {
                    status = StatusBtn.SHOWING;
                }
                setButtons();
                habilEdicaoFired();
                mostraPaciente();
            }
        }
    });                
    }
        
    private void addPacientesListener() { 
        sopPacientes.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                apagaPaciente();
                if (sopPacientes.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecPaciente.fxml";
                        String titleGUI = "Selecionar Paciente";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecPaciente = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,getStage());
                        selecPaciente.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecPacienteController controller = (SelecPacienteController) selecPaciente.getController();
                        controller.setPaciente(sopPacientes);
                        selecPaciente.showAndWait();
                        if (controller.closeModal) {
                            paciente = controller.tabelaPacientes.getSelectionModel().getSelectedItem();
                            sopPaciente.set(paciente);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopPacientes.size() == 1) {
                    paciente = sopPacientes.iterator().next();
                    sopPaciente.set(paciente);
                }
            }
        });        
    }  
        
    private void initCombos() {
        initComboUfPaciente();
        initComboSexoPaciente();
        initComboEstadoCivil();
        initComboEtnia();
        initComboConvenio();
        initComboStatus();
        initComboSitCadastro();
        initComboNaturalidade();
        initComboNacionalidade();
    }

    private void initComboStatus() {
        statusPac.getItems().clear();
        ObservableList<StatusPaciente> options = 
            FXCollections.observableArrayList(
                StatusPaciente.values()
            );        
        statusPac.getItems().addAll(options);
        statusPac.getSelectionModel().selectFirst();    //  CRIAR ENTRADAS EM STATUS  datafield em pacientes  mascara dados datafield
    }

    private void initComboSitCadastro() {
        sitCadastro.getItems().clear();
        ObservableList<SitCadastro> options = 
            FXCollections.observableArrayList(
                SitCadastro.values()
            );        
        sitCadastro.getItems().addAll(options);
    }
    
    
    private void initComboUfPaciente() {            
        ufs = UFs.getObsLista();        
        for (UF uf:ufs) {
            ufPaciente.getItems().add(uf.getUf());
        }
    }
    public void addListenerUF() {
        ufPaciente.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (!Objects.isNull(newVal)) {
                uf = UFs.getUFPelaSigla((String) newVal);
            } else {
                uf = null;
            }            
        }
    });
    }
    
    private void initComboSexoPaciente() {       
        ObservableList<Sexo> options = 
            FXCollections.observableArrayList(
                Sexo.values()
            );        
        sexoPaciente.getItems().addAll(options);        
    }
    
    private void initComboEstadoCivil() {
        ObservableList<EstadoCivil> options = 
            FXCollections.observableArrayList(
                EstadoCivil.values()
            );    
        ObservableList<String> lista=FXCollections.observableArrayList();
        
        for (EstadoCivil item : options) {
            lista.add(item.estadocivil());
        }
        
        estCivilPaciente.getItems().addAll(lista);        
    }
                    
    private void initComboEtnia() {
        ObservableList<Etnia> options = 
            FXCollections.observableArrayList(
                Etnia.values()
            );    
        etniaPaciente.getItems().addAll(options);  
    }

    private void initComboNaturalidade() {
        ObservableList<String> options = 
            FXCollections.observableArrayList(UFs.getNaturalidades());    
        naturPaciente.getItems().addAll(options);        
    }
    
    private void initComboNacionalidade() {
        ArrayList nacion = new ArrayList();
        nacion.add(0, "BRASILEIRA");
        nacionPaciente.getItems().addAll(nacion);        
    }
    
    
    private void initComboConvenio() {
        ObservableList<String> options = 
            FXCollections.observableArrayList(Convenios.getListaNomes());    
        convPaciente.getItems().addAll(options);        
    }

    /// Melhorar mascara com string 5 pos - 3 pos
    private void initFmtCep() {        
        fmtCEP.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0,99999));
        fmtCEP.getPatternVerifiers().put("g", new IntegerRangePatternVerifier(0,999));
        fmtCEP.setPattern("h-g"); 
        fmtCEP.setClearButtonVisible(true);
    }
    
    public void criaPacienteFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        apagaPaciente();
        codAntPaciente.setText(String.valueOf(Pacientes.getProcCodAnt()));
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaPacienteFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnDelPacienteFired(ActionEvent event) {
        if (ExcluiRegistroDlg("EPAC", "", null,this.getStage())) {
            if (!Pacientes.excluiPaciente(paciente)) {
                ShowDialog("EX", "Não foi possível excluir o paciente", null,this.getStage());
            } else {
                ShowDialog("S", "Paciente excluido com sucesso", null,this.getStage());
                status = StatusBtn.IDLE;
                apagaPaciente();
                setButtons();
            }
        }
    }
            
    public void confPacienteFired(ActionEvent event) {          
        if (status==StatusBtn.INSERTING) {            
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            this.paciente = new Paciente();
            if (preenchePaciente()) {   
                if (Pacientes.novoPaciente(this.paciente,manager)) {
                    manager.getTransaction().commit();
                    manager.close();     
                    ShowDialog("S", "Paciente incluido com sucesso", null,this.getStage());                    
                    status = StatusBtn.SHOWING;
                } else {
                    ShowDialog("EX", "Não foi possível incluir o paciente", null,this.getStage());
                    manager.getTransaction().rollback();
                    manager.close();     
                    //status = StatusBtn.INSERTING;
                }    
            }
        } else {
            if (preenchePaciente()) {
                if (Pacientes.atualizaPaciente(paciente)) {
                    ShowDialog("S", "Paciente atualizado com sucesso", null,this.getStage());                    
                    status = StatusBtn.SHOWING;
                } else {
                    ShowDialog("EX", "Não foi possível atualizar o paciente", null,this.getStage());
                    //status = StatusBtn.INSERTING;
                }
                status = StatusBtn.SHOWING;
            } else return;
        }
        
        setButtons();      
        habilEdicaoFired();
    }
    
    public void cancPacienteFired(ActionEvent event) {
        try {
            if (status==StatusBtn.INSERTING) {
                status = StatusBtn.IDLE;
                apagaPaciente();
            } else {
                status = StatusBtn.SHOWING;
                mostraPaciente();
            }        
        } catch (NullPointerException e) {
            status = StatusBtn.IDLE;
        }        
        setButtons();      
        habilEdicaoFired();
    }    
    
    @FXML
    public void fichaMedicaFired(ActionEvent event){
        String fxmlGUI = "fxml/FichaMedica.fxml";
        String titleGUI = "Ficha médica de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory fichamedica;   
        try {
            fichamedica = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            FichaMedicaController controller = (FichaMedicaController) fichamedica.getController();
            controller.setPaciente(paciente);
            controller.addStageCloseListener();
            fichamedica.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    @FXML
    public void btnAtestadosFired(ActionEvent event){
        String fxmlGUI = "fxml/Atestado.fxml";
        String titleGUI = "Atestados de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory atestados;   
        try {
            atestados = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            AtestadoController controller = (AtestadoController) atestados.getController();
            controller.setPaciente(paciente);
            atestados.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    
    @FXML
    public void btnReceitasFired(ActionEvent event){
        String fxmlGUI = "fxml/Receita.fxml";
        String titleGUI = "Receitas de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory receitas;   
        try {
            receitas = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            ReceitaController controller = (ReceitaController) receitas.getController();
            controller.setPaciente(paciente);
            receitas.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @FXML
    public void btnRecibosFired(ActionEvent event){
        String fxmlGUI = "fxml/Recibo.fxml";
        String titleGUI = "Recibos de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory recibos;   
        try {
            recibos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            ReciboController controller = (ReciboController) recibos.getController();
            controller.setPaciente(paciente);
            recibos.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @FXML
    public void btnPedidosFired(ActionEvent event){
        String fxmlGUI = "fxml/PedidosNovo.fxml";
        String titleGUI = "Pedidos de Exames de " + paciente.getNome() + " / " + paciente.getConvenio().getNome();
        StageStyle fxmlStyle = StageStyle.DECORATED;
        GUIFactory pedidos;   
        try {
            pedidos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            PedidoExamesNovoController controller = (PedidoExamesNovoController) pedidos.getController();
            controller.setPaciente(paciente);
            pedidos.showAndWait(); 
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void btnProcurarFired(ActionEvent event) {
        apagaPaciente();
        status = StatusBtn.IDLE;
        setButtons();
        habilEdicaoFired();
    }
    

    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
    public void btnFotografarFired(ActionEvent event) throws Exception {
        CameraController controller;
        GUIFactory camera;
                
        StageStyle fxmlStyle = StageStyle.UTILITY;
        String gui = "fxml/Camera.fxml";
        String titleGUI = "Fotografar visitante";

        try {
            camera = new GUIFactory(gui,titleGUI,fxmlStyle,this.getStage());
//            camera.initialize();
            controller = (CameraController) camera.getController();
            camera.showAndWait();
            if (controller.closeModal) {
                Image foto = new Image(controller.file.toURI().toString());
                imageFotografia.setImage(foto);
                bFotografia = controller.bFile;
            } 
        } catch (Exception ex) {
            Logger.getLogger(CameraController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }   
    
    private void mostraPaciente() {
        try {
            codAntPaciente.setText(String.valueOf(paciente.getCodAntigo()));
            nomePaciente.setText(paciente.getNome()); 
            sexoPaciente.getSelectionModel().clearSelection();
            sexoPaciente.getSelectionModel().select(paciente.getSexo());
            nascPaciente.setValue(paciente.getNascimento());
            naturPaciente.getEditor().setText(paciente.getNaturalidade());
            nacionPaciente.getSelectionModel().select(paciente.getNacionalidade());
            estCivilPaciente.getSelectionModel().select(paciente.getEstadoCivil());
            etniaPaciente.getSelectionModel().select(paciente.getEtnia());
            profPaciente.setText(paciente.getProfissao());
            rgPaciente.setText(paciente.getIdentidade());
            cpfPaciente.setText(paciente.getCpf());
            enderPaciente.setText(paciente.getEndereco());
            numEndPaciente.setText(paciente.getNumero());
            compEndPaciente.setText(paciente.getComplemento());
            bairroPaciente.setText(paciente.getBairro());
            fmtCEP.setText(paciente.getCep());
            cidadePaciente.setText(paciente.getCidade());
            ufPaciente.getSelectionModel().select(paciente.getEstado());
            telResPaciente.setText(paciente.getTelResidencial());
            telComPaciente.setText(paciente.getTelComercial());
            celularPaciente.setText(paciente.getCelular());
            emailPaciente.setText(paciente.getEmail());
            String conv = paciente.getConvenio().getNome();
            convPaciente.getSelectionModel().select(conv);
            matConvPaciente.setText(paciente.getNumConveniado());
            
            indicacao.setText(paciente.getIndicacao());
            //sitCadastro.getSelectionModel().select(SitCadastro.valueOf(paciente.get));
            statusPac.getSelectionModel().select(StatusPaciente.valueOf(paciente.getStatus()));
            dataPrimConsulta.setText(FichaMedica.getDataPrimCons(paciente));
            bFotografia = paciente.getFotografia();
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(bFotografia);
                Image image = new Image(in);            
                this.imageFotografia.setImage(image);
            } catch(NullPointerException e) {
                this.imageFotografia.setImage(null);
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    private Boolean preenchePaciente() {        
        Boolean resultado = Boolean.FALSE;
        try {
            paciente.setCodAntigo(Integer.parseInt(codAntPaciente.getText()));        
            paciente.setNome(nomePaciente.getText()); 
            paciente.setSexo(sexoPaciente.getSelectionModel().getSelectedItem());
            paciente.setNascimento(nascPaciente.getValue());
            paciente.setEtnia(etniaPaciente.getSelectionModel().getSelectedItem());
            paciente.setConvenio((String) convPaciente.getSelectionModel().getSelectedItem());
            paciente.setNumConveniado(matConvPaciente.getText());
            resultado = Boolean.TRUE;
//        } catch(CampoNuloException | CampoEmBrancoException cne) {
        } catch(CampoNuloException cne) {
            ShowDialog("EX", cne.getMessage(), null,this.getStage());
        }
        paciente.setNaturalidade(naturPaciente.getEditor().getText());
        paciente.setNacionalidade(nacionPaciente.getEditor().getText());
        paciente.setEstadoCivil(estCivilPaciente.getSelectionModel().getSelectedItem());
        paciente.setProfissao(profPaciente.getText());
        paciente.setIdentidade(rgPaciente.getText());
        paciente.setCpf(cpfPaciente.getText());
        paciente.setEndereco(enderPaciente.getText());
        paciente.setNumero(numEndPaciente.getText());
        paciente.setComplemento(compEndPaciente.getText());
        paciente.setBairro(bairroPaciente.getText());
        paciente.setCep(fmtCEP.getText());
        paciente.setCidade(cidadePaciente.getText());
        paciente.setEstado((String) ufPaciente.getSelectionModel().getSelectedItem());
        paciente.setTelComercial(telComPaciente.getText());
        paciente.setTelResidencial(telResPaciente.getText());
        paciente.setCelular(celularPaciente.getText());
        paciente.setEmail(emailPaciente.getText()); 
        
        paciente.setIndicacao(indicacao.getText());
        if (statusPac.getValue()!=null) {
            paciente.setStatus(statusPac.getValue().toString());
        } else {
            paciente.setStatus(statusPac.getItems().get(0).name());
        }
//        paciente.setSitCadastro(sitCadastro.getValue().toString());
        
        paciente.setFotografia(bFotografia);             
        return resultado;
    }
        
    public void btnProcCodFired(ActionEvent event) throws Exception {
//        Pacientes pacientes = new Pacientes();
        sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCod(Integer.parseInt(codPaciente.getText()))));
    }    
    
    public void btnProcCodAntFired(ActionEvent event) throws Exception {
//        Pacientes pacientes = new Pacientes();
        Integer codant = (codAntPaciente.getText().trim().isEmpty()?-1:Integer.parseInt(codAntPaciente.getText()));
        if (codant>0) {
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithCodAnt(codant)));
        }
    }
    
    public void btnProcNomeFired(ActionEvent event) throws Exception {
        String nome = nomePaciente.getText();
        if (!nome.isEmpty()) {
            nome = "%" + nome.replace(" ", "%");            
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithNome(nome)));
        }
    } 
    
    public void btnProcCepFired(ActionEvent event) {      
        if (!fmtCEP.getText().isEmpty()) {
            uf = UFs.getUFPeloCep(fmtCEP.getText());
            if (uf != null) {
                preencheCep(uf);
            }
        }
    }
    
    private void preencheCep(UF uf) {
        ufPaciente.getSelectionModel().select(uf.getUf());
        cep = CEPs.getCEPPeloNome(fmtCEP.getText(), uf.getUf());
        if (cep!=null) preencheEndereco();
    }
    
    private void preencheEndereco() {
        if (cep!=null) {
            enderPaciente.setText(cep.getTp_logradouro()+" "+cep.getLogradouro());
            bairroPaciente.setText(cep.getBairro());
            cidadePaciente.setText(cep.getCidade());
        }
    }
    
    
    public void apagaPaciente() {
        this.codAntPaciente.clear();
        this.nomePaciente.clear();
        this.sexoPaciente.getSelectionModel().select(-1);
        this.etniaPaciente.getSelectionModel().select(-1);
        this.nascPaciente.setValue(null);
        this.idade.clear();
        this.naturPaciente.getEditor().clear();
        this.nacionPaciente.getEditor().clear();
        this.estCivilPaciente.getSelectionModel().select(-1);
        this.profPaciente.clear();
        this.rgPaciente.clear();
        this.cpfPaciente.clear();
        this.enderPaciente.clear();
        this.numEndPaciente.clear();
        this.compEndPaciente.clear();
        this.bairroPaciente.clear();
        this.cidadePaciente.clear();
        this.fmtCEP.clear();
        this.ufPaciente.getSelectionModel().select(-1);
        this.telComPaciente.clear();
        this.telResPaciente.clear();
        this.celularPaciente.clear();
        this.emailPaciente.clear();
        this.convPaciente.getSelectionModel().select(-1);
        this.matConvPaciente.clear();  
        this.indicacao.clear();
        this.dataPrimConsulta.clear();
        this.statusPac.getSelectionModel().select(-1);
        this.sitCadastro.getSelectionModel().select(-1);
        this.imageFotografia.setImage(null);
    }
    
    public void habilEdicaoFired() {
        this.codAntPaciente.setEditable(((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING))|(status==StatusBtn.IDLE));  //setEditable(Boolean.FALSE);   //(((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING))|(status==StatusBtn.IDLE));
        this.nomePaciente.setEditable(((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING))|(status==StatusBtn.IDLE));
//        this.sexoPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
//        this.nascPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
//        this.nascPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
//        this.naturPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
//        this.nacionPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
//        this.estCivilPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        this.profPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.rgPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.cpfPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.enderPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.numEndPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.compEndPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.bairroPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.cidadePaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
//        this.fmtCEP.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
//        this.ufPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        this.telComPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.telResPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.celularPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        this.emailPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
//        this.convPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        this.matConvPaciente.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));              
        this.indicacao.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));     
        this.dataPrimConsulta.setEditable(false);
        
    }
    
    private void setButtons() {
        btnCriarPaciente.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualPaciente.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnDelPaciente.setDisable(status!=StatusBtn.SHOWING);
        btnConfPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancPaciente.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        
        btnFichaMedica.setDisable((status!=StatusBtn.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico"))) ;
        btnAtestados.setDisable((status!=StatusBtn.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
        btnReceitas.setDisable((status!=StatusBtn.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico")));
        btnRecibos.setDisable((status!=StatusBtn.SHOWING));       
        btnPedidos.setDisable((status!=StatusBtn.SHOWING) | (!perfilUsuario.getTipoUsuario().equals("Medico"))) ;
        
        btnProcurar.setDisable(status!=StatusBtn.SHOWING);
        btnProcCodAnt.setDisable(status!=StatusBtn.IDLE);
        btnProcNome.setDisable(status!=StatusBtn.IDLE);
        btnSair.setDisable((status!=StatusBtn.IDLE)&(status!=StatusBtn.SHOWING));
        btnFotografar.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    private void setToolTips() {
        btnCriarPaciente.setTooltip(new Tooltip("Criar novo Paciente"));
        btnAtualPaciente.setTooltip(new Tooltip("Atualizar o Paciente selecionado"));        
        btnConfPaciente.setTooltip(new Tooltip("Gravar Paciente"));
        btnDelPaciente.setTooltip(new Tooltip("Excluir Paciente selecionado"));
        btnCancPaciente.setTooltip(new Tooltip("Cancelar as alterações"));        
        btnFichaMedica.setTooltip(new Tooltip("Abrir a Ficha Médica do Paciente"));
        btnReceitas.setTooltip(new Tooltip("Emitir/Acesssar Receitas do Paciente"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnAtestados.setTooltip(new Tooltip("Emitir/Acesssar Atestados do Paciente"));
        btnProcCodAnt.setTooltip(new Tooltip("Procurar Paciente pelo codigo"));
        btnProcNome.setTooltip(new Tooltip("Procurar Paciente pelo nome"));
        btnRecibos.setTooltip(new Tooltip("Emitir/Acesssar Recibos do Paciente"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }
    
    
}