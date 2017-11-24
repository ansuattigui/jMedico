package com.br.ralfh.medico;

import com.br.ralfh.medico.dlg.ControladaDlgController;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Medicamento;
import com.br.ralfh.medico.modelos.Medicamentos;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Posologia;
import com.br.ralfh.medico.modelos.Posologias;
import com.br.ralfh.medico.modelos.Prescricao;
import com.br.ralfh.medico.modelos.Prescricoes;
import com.br.ralfh.medico.modelos.Receita;
import com.br.ralfh.medico.modelos.Receitas;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ReceitaController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private Receita receita;
    private Prescricao prescricao;
    private SimpleObjectProperty<Receita> sopReceita;
    private ObservableList<Receita> sopReceitas ;    
    private SimpleObjectProperty<Paciente> sopPaciente;        
    private ObservableList<Prescricao> sopPrescricoes = FXCollections.observableArrayList() ;    
    private StatusBtn status;
    private GUIFactory prescricaoGUI;
    private GUIFactory controladaGUI;
            
    @FXML TextField nomePaciente;

    @FXML TableView<Receita> tabelaReceitas;
    @FXML TableColumn<Receita,String> ordemCol;
    @FXML TableColumn<Receita,String> dataCol;    
    
    @FXML TableView<Prescricao> tablePrescricoes;
    @FXML TableColumn<Prescricao,String> medicamentoCol;
    @FXML TableColumn<Prescricao,String> posologiaCol;    
    @FXML TableColumn<Prescricao,String> viaAdminCol;
    @FXML TableColumn<Prescricao,String> quantidadeCol;
    @FXML TableColumn<Prescricao,Boolean>  excluiPrescricaoCol;   
    
    @FXML public Button btnSair;
    @FXML Button btnNovoMedicamento;
    @FXML Button btnAtualizaMedicamento;
    @FXML Button btnExcluiMedicamento;
    
    @FXML Button btnNovaReceita; @FXML public MenuItem miNovaReceita;
    @FXML Button btnAtualizaReceita; @FXML public MenuItem miAtualizaReceita;
    @FXML Button btnSalvaReceita; @FXML public MenuItem miSalvaReceita;
    @FXML Button btnCancelaReceita; @FXML public MenuItem miCancelaReceita;
    @FXML Button btnExcluiReceita; @FXML public MenuItem miExcluiReceita;
    @FXML Button btnDuplicaReceita; @FXML public MenuItem miDuplicaReceita;
    @FXML SplitMenuButton btnPrintReceita;    
    @FXML public MenuItem miOpcaoCarta; @FXML public MenuItem miOpcaoCartaT;
    @FXML public MenuItem miOpcaoA4; @FXML public MenuItem miOpcaoA4T;
    @FXML public MenuItem miOpcaoGaveta; @FXML public MenuItem miOpcaoGavetaT;
    @FXML public MenuItem miOpcaoMeioA4; @FXML public MenuItem miOpcaoMeioA4T;
    @FXML public MenuItem miOpcaoReduzido; @FXML public MenuItem miOpcaoReduzidoT;


    public ReceitaController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelas();
        setToolTips();
        sopPaciente = new SimpleObjectProperty<>();
        sopReceita = new SimpleObjectProperty<>();
        sopReceitas = FXCollections.observableArrayList();
        sopPrescricoes = FXCollections.observableArrayList();
        initListeners();
        
        status = StatusBtn.IDLE;
        setButtons();        
    }    
    
    public void setPaciente(Paciente paciente) {
        this.sopPaciente.set(paciente);  
    }    
        
    public void initListeners() {
        addPacienteListener();
        AddSelecReceitaListener();
        addReceitaListener();
        addReceitasListener();
        addPrescricoesListener();   
        addSelecPrescricaoListener();
    }

    private void initTabelas() {
        initTabelaReceitas();
        initTablePrescricoes();
    }
    
    private void setToolTips() {
        btnNovaReceita.setTooltip(new Tooltip("Criar nova receita"));
        btnAtualizaReceita.setTooltip(new Tooltip("Atualizar a Receita selecionada"));        
        btnSalvaReceita.setTooltip(new Tooltip("Gravar receita"));
        btnExcluiReceita.setTooltip(new Tooltip("Excluir receita selecionada"));
        btnDuplicaReceita.setTooltip(new Tooltip("Duplicar a receita selecionada"));
        btnPrintReceita.setTooltip(new Tooltip("Imprime a receita selecionada"));       
        btnNovoMedicamento.setTooltip(new Tooltip("Prescreve um medicamento"));        
        btnExcluiMedicamento.setTooltip(new Tooltip("Exclui o medicamento selecionado"));
        btnAtualizaMedicamento.setTooltip(new Tooltip("Atualiza o medicamento selecionado"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }
        
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
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
        });
    }   
    private void addReceitaListener() { 
        sopReceita.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (sopReceita.get()!=null) {
                    sopPrescricoes.setAll(sopReceita.get().getPrescricoes());
                    setButtons();
                }
            }
        });
    }       
    private void addReceitasListener() { 
        sopReceitas.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {                
                if (sopReceitas.size()>0) {
                    tabelaReceitas.getItems().setAll(sopReceitas);
                } else {
                    tabelaReceitas.getItems().clear();
                }
            }
        });        
    }        
    public void AddSelecReceitaListener() {
        tabelaReceitas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (status==StatusBtn.IDLE | status==StatusBtn.SHOWING) {
                    status = StatusBtn.SHOWING;
                    receita = tabelaReceitas.getSelectionModel().getSelectedItem();
                    sopReceita.set(receita); 
                }
            }
        }); 
    }
    
    private void addPrescricoesListener() { 
        sopPrescricoes.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!sopPrescricoes.isEmpty()) {
                    tablePrescricoes.setItems(sopPrescricoes);
                }
            }
        });
    }
       
    private void addSelecPrescricaoListener() { 
        tablePrescricoes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {                
                prescricao = tablePrescricoes.getSelectionModel().getSelectedItem();
            }
        });
    }

    
    public void initTabelaReceitas() {
        ordemCol.setCellValueFactory(new Callback<CellDataFeatures<Receita, String>, ObservableValue<String>>() {
          @Override 
          public ObservableValue<String> call(CellDataFeatures<Receita, String> p) {
            return new ReadOnlyObjectWrapper(tabelaReceitas.getItems().indexOf(p.getValue())+1 + "");
          }
        });           
        dataCol.setCellValueFactory(new Callback<CellDataFeatures<Receita,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Receita,String> rec) {
                return new SimpleObjectProperty<>(Util.formataDataExtenso(rec.getValue().getDataEmissao()));
            }
        });   
    }           
    
    public void initTablePrescricoes() {
        medicamentoCol.setCellValueFactory(new PropertyValueFactory<>("medicamento"));
        posologiaCol.setCellValueFactory(new PropertyValueFactory<>("posologia"));
        viaAdminCol.setCellValueFactory(new PropertyValueFactory<>("viaAdmin"));      
        quantidadeCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));   
    }      
    
    public boolean checaReceita() {
        boolean resultado = Boolean.FALSE;
        if (receita.getPrescricoes().isEmpty()) {
            ShowDialog("EX", "Prescreva ao menos um medicamento", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
    
    @FXML
    public void btnNovaReceitaFired(ActionEvent ae) {
        status = StatusBtn.INSERTING;
        receita = new Receita();
        receita.setPaciente(sopPaciente.get());
        receita.setDataEmissao(Util.ldHoje());
        sopReceita.set(receita);
        sopReceitas.add(receita);
    }
    
    @FXML
    public void btnDuplicaReceitaFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        
        receita = new Receita();
        receita.setPaciente(sopPaciente.get());
        receita.setDataEmissao(Util.ldHoje());
        
        for (Prescricao prescr : sopReceita.get().getPrescricoes()) {
            Prescricao p = new Prescricao();
            try {
                p.setMedicamento(prescr.getMedicamento());
                p.setPosologia(prescr.getPosologia());
                p.setQuantidade(prescr.getQuantidade());
                p.setViaAdmin(prescr.getViaAdmin());
                p.setReceita(receita);
                receita.getPrescricoes().add(p);
            } catch (CampoEmBrancoException ex) {
                Logger.getLogger(ReceitaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        receita=recnew;   detached prescricao;
        sopReceita.set(receita);
        sopReceitas.add(receita);
        
        if (Receitas.novaReceita(receita)) {
            status = StatusBtn.SHOWING;
            ShowDialog("S", "A receita foi salva com sucesso", null,this.getStage());
        } else {
            ShowDialog("EX", "Não foi possível salvar a receita", null,this.getStage());
        }
        setButtons();
    }

    @FXML
    public void btnAtualizaReceitaFired(ActionEvent ae) {
        status = StatusBtn.UPDATING;
        setButtons();
    }
    
    @FXML
    public void btnExcluiReceitaFired(ActionEvent event) {        
        if (ExcluiRegistroDlg("ER", "", null,this.getStage())) {
            if (Receitas.excluiReceita(sopReceita.get())) {
                sopReceitas.remove(sopReceita.get());
                tablePrescricoes.getItems().clear();
            }
        }
    }    
    
    @FXML
    public void btnSalvaReceitaFired(ActionEvent event) {
        if (checaReceita()) {
            if (status==StatusBtn.INSERTING) {            
                if (Receitas.novaReceita(receita)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "A receita foi salva com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível salvar a receita", null,this.getStage());
                }
            } else if (status==StatusBtn.UPDATING) {
                if (Receitas.atualizaReceita(receita)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "A receita foi atualizada com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível atualizar a receita", null,this.getStage());
                }
            }
            setButtons();
        }
    }   
    
    @FXML
    public void btnCancelaReceitaFired(ActionEvent ae) {
        try {
            if (status==StatusBtn.INSERTING) {
                sopReceitas.remove(receita);
                status = StatusBtn.IDLE;
            } else {
                status = StatusBtn.SHOWING;
            }        
        } catch (NullPointerException e) {
            status = StatusBtn.IDLE;
        }
        setButtons();      
    }
    
    @FXML
    public void btnNovoMedicamentoFired(ActionEvent ae) throws IOException {        
        String fxmlGUI = "fxml/PrescricaoNova.fxml";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Prescrição de medicamento";
        
        prescricaoGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        prescricaoGUI.initialize();
        PrescricaoNovaController controller = (PrescricaoNovaController) prescricaoGUI.getController(); 
        controller.addStageCloseListener();
        prescricaoGUI.showAndWait();       
        
        if (controller.getPrescricao()!=null) {   
            List<Prescricao> prescricoes = controller.getPrescricoes();
            for(Prescricao prescr :prescricoes) {
                prescr.setReceita(receita);
                receita.getPrescricoes().add(prescr);
                sopPrescricoes.addAll(prescr);
            }        
        }
    }
    
    public void btnAtualizaMedicamentoFired(ActionEvent ae)  throws IOException {
        String fxmlGUI = "fxml/PrescricaoNova.fxml";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Prescrição de medicamento";
        
        prescricaoGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        prescricaoGUI.initialize();
        PrescricaoNovaController controller = (PrescricaoNovaController) prescricaoGUI.getController();        
        controller.initExame(prescricao);
        controller.addStageCloseListener();
        int pos = receita.getPrescricoes().indexOf(prescricao);
        List<Prescricao> prescricoes = receita.getPrescricoes();
        prescricaoGUI.showAndWait();       
        
        if (controller.getPrescricao()!=null) {            
            Prescricao prescr = controller.getPrescricoes().get(0);
            prescricoes.set(pos, prescr);
            sopPrescricoes.setAll(prescricoes);
        }        
    }

    public void btnExcluiMedicamentoFired(ActionEvent ae)  throws IOException {
        if (ExcluiRegistroDlg("EP", "", null,this.getStage())) { 
            receita.getPrescricoes().remove(prescricao);
            sopPrescricoes.setAll(receita.getPrescricoes());
//            status = StatusBtn.DELETINGPRESC;
        }
    }
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    

    public void btnReceitaControladaFired(ActionEvent ae) throws IOException {
        
        String fxmlGUI = "dlg/ControladaDlg.fxml";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "RECEITUARIO CONTROLE ESPECIAL";
        
        controladaGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        controladaGUI.initialize();
        ControladaDlgController controller = (ControladaDlgController) controladaGUI.getController();        
        controller.setMedicamento(prescricao.getMedicamento());
        
        controller.setPrescricao(prescricao.getPosologia()+" - "+prescricao.getQuantidade());
//        controller.setPrescricao(prescricao.getControlada());
        
        controladaGUI.showAndWait();               
        
        if (controller.getPrescricao()!="") {                        
            prescricao.setControlada(controller.getPrescricao());
            if (Prescricoes.atualizaPrescricao(prescricao)) {
                String fileName = "relatorios/controladas/JControladaMeioA4.jasper";
                PrintReceitaControlada(fileName);
            }
        }        
                
    }
    
    private void atualizaCadastros() {
        
        EntityManager manager = JPAUtil.getEntityManager();       
        Grupo grupo = manager.find(Grupo.class,0);
        Posologias posologias = new Posologias();
        Medicamentos medicamentos = new Medicamentos();
                
        manager.getTransaction().begin();
        for(Prescricao itemReceita: sopPrescricoes) {            
            Medicamento medicamento = medicamentos.getMedicamentoPeloNome(itemReceita.getMedicamento());
            if (medicamento == null) {
                medicamento = new Medicamento();
                medicamento.setPrincipio(itemReceita.getMedicamento());
                medicamento.setGrupo(grupo);                
                manager.persist(medicamento);
            }

            Posologia posologia = posologias.getModoPeloNome(itemReceita.getPosologia());
            if (posologia == null) {                
                posologia = new Posologia();                
                posologia.setPosologia(itemReceita.getPosologia());
                manager.persist(posologia);
            }            
        }
        manager.getTransaction().commit();
        manager.close();
    }    
        
    @FXML
    public void miOpcaoCartaFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaCarta.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoA4Fired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaA4.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoGavetaFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaG.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoMeioA4Fired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaMeioA4.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaReduz.jasper";
        PrintReceita(fileName);
    }
    
    public void PrintReceita(String file) {
        HashMap hm = new HashMap();
        hm.put("idReceita", sopReceita.get().getReceita_id());
        hm.put("nomePaciente", sopPaciente.get().getNome());
        hm.put("dataReceita", Util.formataDataExtenso(sopReceita.get().getDataEmissao()));    
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif"));
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif"));         
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            relat.openReport( "Receita",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
     
    public void PrintReceitaControlada(String file) {
        HashMap hm = new HashMap();
        hm.put("idPaciente", sopPaciente.get().getId());
        hm.put("dataReceita", Util.formataDataExtenso(sopReceita.get().getDataEmissao()));    
        hm.put("prescricao", tablePrescricoes.getSelectionModel().getSelectedItem().getControlada());
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif"));
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif"));         
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            relat.openReport( "Receita",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setButtons() {
        btnNovaReceita.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualizaReceita.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnSalvaReceita.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancelaReceita.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiReceita.setDisable(status!=StatusBtn.SHOWING);
        btnPrintReceita.setDisable(status!=StatusBtn.SHOWING);
        btnDuplicaReceita.setDisable(status!=StatusBtn.SHOWING);
        btnSair.setDisable(status==StatusBtn.SHOWING);
        
        miNovaReceita.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        miAtualizaReceita.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miSalvaReceita.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miCancelaReceita.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miExcluiReceita.setDisable(status!=StatusBtn.SHOWING);
        miDuplicaReceita.setDisable(status!=StatusBtn.SHOWING);

        miOpcaoCarta.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoA4.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoGaveta.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoMeioA4.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoReduzido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoCartaT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoA4T.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoGavetaT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoMeioA4T.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoReduzidoT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        
        btnNovoMedicamento.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnAtualizaMedicamento.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiMedicamento.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    
    public class TextCell extends TableCell<Receita, String> {
        
        final Label txt = new Label();
        
        public TextCell(String campo) {
            txt.setFont(Font.font("System", FontWeight.BOLD, 12));
            txt.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            txt.setPadding(new Insets(2));
        }
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
                txt.setText(item.trim().isEmpty()?"":item);
                txt.setTextAlignment(TextAlignment.CENTER);
                setGraphic(txt);
            } else {
                setGraphic(null);
            }
        }
    }
    
    @Override
    public void addStageCloseListener() {        
        getController().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              btnSalvaReceita.fire();
          }
        });
    }
    
    
}