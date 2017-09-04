package com.br.ralfh.medico;

import com.br.ralfh.medico.dlg.ControladaDlgController;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.PrescricaoExterno;
import com.br.ralfh.medico.modelos.PrescricoesExterno;
import com.br.ralfh.medico.modelos.Receita;
import com.br.ralfh.medico.modelos.ReceitaExterno;
import com.br.ralfh.medico.modelos.ReceitasExterno;
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
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ReceitaExternoController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private ReceitaExterno receita;
    private PrescricaoExterno prescricao;
    private SimpleObjectProperty<ReceitaExterno> sopReceita;
    private ObservableList<ReceitaExterno> sopReceitas ;    
    private ObservableList<PrescricaoExterno> sopPrescricoes = FXCollections.observableArrayList() ;    
    private StatusBtn status;
    private GUIFactory prescricaoGUI;
    private GUIFactory controladaGUI;
            
    @FXML TextField nomePaciente;

    @FXML TableView<ReceitaExterno> tabelaReceitas;
    @FXML TableColumn<ReceitaExterno,String> ordemCol;
    @FXML TableColumn<ReceitaExterno,String> dataCol;        
    @FXML TableView<PrescricaoExterno> tablePrescricoes;
    @FXML TableColumn<PrescricaoExterno,String> medicamentoCol;
    @FXML TableColumn<PrescricaoExterno,String> posologiaCol;    
    @FXML TableColumn<PrescricaoExterno,String> viaAdminCol;
    @FXML TableColumn<PrescricaoExterno,String> quantidadeCol;
    @FXML TableColumn<PrescricaoExterno,Boolean>  excluiPrescricaoCol;   
    
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


    public ReceitaExternoController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelas();
        setToolTips();
//        sopPaciente = new SimpleObjectProperty<>();
        sopReceita = new SimpleObjectProperty<>();
        sopReceitas = FXCollections.observableArrayList();
        sopPrescricoes = FXCollections.observableArrayList();
        initListeners();
        
        status = StatusBtn.SHOWING;
        setButtons();        
    }    
    
    @FXML            
    public void btnProcNomeFired(ActionEvent ae) {
        if (!nomePaciente.getText().isEmpty()) {
            try {
                sopReceitas.setAll(ReceitasExterno.getLista(nomePaciente.getText()));
                nomePaciente.setText(nomePaciente.getText().toUpperCase());
            } catch (Exception ex) {
                Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        
    public void initListeners() {
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
        ordemCol.setCellValueFactory(new Callback<CellDataFeatures<ReceitaExterno, String>, ObservableValue<String>>() {
          @Override public ObservableValue<String> call(CellDataFeatures<ReceitaExterno, String> p) {
            return new ReadOnlyObjectWrapper(tabelaReceitas.getItems().indexOf(p.getValue())+1 + "");
          }
        });           
        dataCol.setCellValueFactory(new Callback<CellDataFeatures<ReceitaExterno,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ReceitaExterno,String> rec) {
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
        if (nomePaciente.getText().isEmpty()) {
            ShowDialog("EX", "Informe o nome do paciente", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        receita.setPaciente(nomePaciente.getText());
        if (receita.getPrescricoes().isEmpty()) {
            ShowDialog("EX", "Prescreva ao menos um medicamento", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
    
    @FXML
    public void btnNovaReceitaFired(ActionEvent ae) {
        if (nomePaciente.getText().isEmpty()) {
            ShowDialog("EX", "Informe o nome do paciente", null,this.getStage());
        } else {
            status = StatusBtn.INSERTING;
            receita = new ReceitaExterno();
            receita.setPaciente(nomePaciente.getText());
            receita.setDataEmissao(Util.ldHoje());
            sopReceita.set(receita);
            sopReceitas.add(receita);
            setButtons();
        }
    }
    
    @FXML
    public void btnDuplicaReceitaFired(ActionEvent event) {
        status = StatusBtn.INSERTING;        
        receita = new ReceitaExterno();
        receita.setPaciente(nomePaciente.getText());
        receita.setDataEmissao(Util.ldHoje());
        
        for (PrescricaoExterno prescr : sopReceita.get().getPrescricoes()) {
            PrescricaoExterno p = new PrescricaoExterno();
            try {
                p.setMedicamento(prescr.getMedicamento());
                p.setPosologia(prescr.getPosologia());
                p.setQuantidade(prescr.getQuantidade());
                p.setViaAdmin(prescr.getViaAdmin());
                p.setReceita(receita);
                receita.getPrescricoes().add(p);
            } catch (CampoEmBrancoException ex) {
                Logger.getLogger(ReceitaExternoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        receita=recnew;   detached prescricao;
        sopReceita.set(receita);
        sopReceitas.add(receita);
        
        if (ReceitasExterno.novaReceita(receita)) {
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
            if (ReceitasExterno.excluiReceita(sopReceita.get())) {
                sopReceitas.remove(sopReceita.get());
                tablePrescricoes.getItems().clear();
            }
        }
    }    
    
    @FXML
    public void btnSalvaReceitaFired(ActionEvent event) {
        if (checaReceita()) {
            if (status==StatusBtn.INSERTING) {            
                if (ReceitasExterno.novaReceita(receita)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "A receita foi salva com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível salvar a receita", null,this.getStage());
                }
            } else if (status==StatusBtn.UPDATING) {
                if (ReceitasExterno.atualizaReceita(receita)) {
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
        String fxmlGUI = "fxml/PrescricaoExterno.fxml";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Prescrição de medicamento";
        
        prescricaoGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        prescricaoGUI.initialize();
        PrescricaoExternoController controller = (PrescricaoExternoController) prescricaoGUI.getController(); 
        controller.addStageCloseListener();
        prescricaoGUI.showAndWait();       
        
        if (controller.getPrescricao()!=null) {   
            List<PrescricaoExterno> prescricoes = controller.getPrescricoes();
            for(PrescricaoExterno prescr :prescricoes) {
                prescr.setReceita(receita);
                receita.getPrescricoes().add(prescr);
                sopPrescricoes.addAll(prescr);
            }        
        }
    }
    
    public void btnAtualizaMedicamentoFired(ActionEvent ae)  throws IOException {
        String fxmlGUI = "fxml/PrescricaoExterno.fxml";
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Prescrição de medicamento";
        
        prescricaoGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        prescricaoGUI.initialize();
        PrescricaoExternoController controller = (PrescricaoExternoController) prescricaoGUI.getController();        
        controller.initExame(prescricao);
        controller.addStageCloseListener();
        int pos = receita.getPrescricoes().indexOf(prescricao);
        List<PrescricaoExterno> prescricoes = receita.getPrescricoes();
        prescricaoGUI.showAndWait();       
        
        if (controller.getPrescricao()!=null) {            
            PrescricaoExterno prescr = controller.getPrescricoes().get(0);
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
            if (PrescricoesExterno.atualizaPrescricao(prescricao)) {
                String fileName = "relatorios/controladas/JControladaMeioA4.jasper";
                PrintReceitaControlada(fileName);
            }
        }        
                
    }

    
    @FXML
    public void miOpcaoCartaFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaExternoCarta.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoA4Fired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaExternoA4.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoGavetaFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaExternoG.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoMeioA4Fired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaExternoMeioA4.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaExternoReduz.jasper";
        PrintReceita(fileName);
    }
    
    public void PrintReceita(String file) {
        HashMap hm = new HashMap();
        hm.put("idReceita", sopReceita.get().getReceita_id());
        hm.put("nomePaciente", nomePaciente.getText());
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
        hm.put("idReceita", sopReceita.get().getReceita_id());
        hm.put("nomePaciente", sopReceita.get().getPaciente());
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
        btnSair.setDisable(status!=StatusBtn.SHOWING);
        
//        nomePaciente.setEditable(status!=StatusBtn.IDLE);
        
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