package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.ExamesGrupo;
import com.br.ralfh.medico.modelos.GrupoExames;
import com.br.ralfh.medico.modelos.GruposExames;
import com.br.ralfh.medico.modelos.Receita;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class GrupoExamesController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private GrupoExames grupoExames;
    private ExamesGrupo exame;
    private SimpleObjectProperty<GrupoExames> sopGrupo;
    private ObservableList<GrupoExames> sopGrupos ;    
    private ObservableList<ExamesGrupo> sopExames = FXCollections.observableArrayList() ;    
    private StatusBtn status;
    private GUIFactory exameGUI;
    
    @FXML public Button btnConfirmaNome;
    @FXML public Button btnCancelaNome;
    
    @FXML public TableView<GrupoExames> tabelaGrupos;
    @FXML public TableColumn<GrupoExames,String> ordemCol;
    @FXML public TableColumn<GrupoExames,String> indicacaoClinica;    
    
    @FXML public TableView<ExamesGrupo> tabelaExames;
    @FXML public TableColumn<ExamesGrupo,String> exameCol;
    @FXML public TableColumn<ExamesGrupo,String> materialCol;    
    
    @FXML public Button btnSair;
    @FXML public Button btnNovoExame;
    @FXML public Button btnAlteraExame;
    @FXML public Button btnExcluiExame;
    
    @FXML Button btnNovoGrupo; @FXML public MenuItem miNovoGrupo;
    @FXML Button btnAtualizaGrupo; @FXML public MenuItem miAtualizaGrupo;
    @FXML Button btnSalvaGrupo; @FXML public MenuItem miSalvaGrupo;
    @FXML Button btnCancelaGrupo; @FXML public MenuItem miCancelaGrupo;
    @FXML Button btnExcluiGrupo; @FXML public MenuItem miExcluiGrupo;
    @FXML Button btnDuplicaGrupo; @FXML public MenuItem miDuplicaGrupo;
    @FXML SplitMenuButton btnPrintGrupos;    
    @FXML public MenuItem miOpcaoCarta; @FXML public MenuItem miOpcaoCartaT;
    @FXML public MenuItem miOpcaoA4; @FXML public MenuItem miOpcaoA4T;
    @FXML public MenuItem miOpcaoGaveta; @FXML public MenuItem miOpcaoGavetaT;
    @FXML public MenuItem miOpcaoMeioA4; @FXML public MenuItem miOpcaoMeioA4T;
    @FXML public MenuItem miOpcaoReduzido; @FXML public MenuItem miOpcaoReduzidoT;


    public GrupoExamesController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sopGrupo = new SimpleObjectProperty<>();
        sopGrupos = FXCollections.observableArrayList();
        sopExames = FXCollections.observableArrayList();

        initTabelas();
        initListeners();
        setToolTips();

        sopGrupos.setAll(GruposExames.getLista());
        
        status = StatusBtn.IDLE;
        setButtons();  
        habilEdicaoFired();
    }    
    
    public void initListeners() {
        AddSelecGrupoListener();
        addGrupoListener();
        addGruposListener();
        addExamesListener();   
        addSelecExameListener();
    }

    private void initTabelas() {
        initTabelaGrupos();
        initTabelaExames();
    }
    
    private void setToolTips() {
        btnNovoGrupo.setTooltip(new Tooltip("Criar novo grupo"));
        btnAtualizaGrupo.setTooltip(new Tooltip("Atualizar o Grupo selecionado"));        
        btnSalvaGrupo.setTooltip(new Tooltip("Gravar receita"));
        btnExcluiGrupo.setTooltip(new Tooltip("Excluir o grupo selecionado"));
        btnDuplicaGrupo.setTooltip(new Tooltip("Duplicar o grupo selecionado"));
        btnPrintGrupos.setTooltip(new Tooltip("Imprime a receita selecionada"));       
        btnNovoExame.setTooltip(new Tooltip("Inclui um exame"));        
        btnExcluiExame.setTooltip(new Tooltip("Exclui o exame selecionado"));
//        btnAlteraExame.setTooltip(new Tooltip("Atualiza o medicamento selecionado"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }

/*    
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPaciente.get() != null) {
                try {
                    indicacaoClinica.setText(sopPaciente.get().getNome());
                    sopGrupos.setAll(PedidosExames.getLista(sopPaciente.get()));
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        });
    }   
*/
    
    private void addGrupoListener() { 
        sopGrupo.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (sopGrupo.get()!=null) {
                    sopExames.setAll(sopGrupo.get().getExames());
                    setButtons();
                    habilEdicaoFired();
                }
            }
        });
    }       
    
    private void addGruposListener() { 
        sopGrupos.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {                
//                if (sopGrupos.size()>0) {
                    tabelaGrupos.getItems().setAll(sopGrupos);
//                } else {
//                    tabelaGrupos.getItems().clear();
//                }
            }
        });        
    }        
    public void AddSelecGrupoListener() {
        tabelaGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (status==StatusBtn.IDLE | status==StatusBtn.SHOWING) {
                    status = StatusBtn.SHOWING;
                    grupoExames = tabelaGrupos.getSelectionModel().getSelectedItem();
                    sopGrupo.set(grupoExames); 
                }
            }
        }); 
    }
    
    private void addExamesListener() { 
        sopExames.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!sopExames.isEmpty()) {
                    tabelaExames.setItems(sopExames); 
                }
            }
        });
    }
       
    private void addSelecExameListener() { 
        tabelaExames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {                
                exame = tabelaExames.getSelectionModel().getSelectedItem();
            }
        }); 
    }

    
    public void initTabelaGrupos() {
        ordemCol.setCellValueFactory(new Callback<CellDataFeatures<GrupoExames, String>, ObservableValue<String>>() {
          @Override public ObservableValue<String> call(CellDataFeatures<GrupoExames, String> p) {
            return new ReadOnlyObjectWrapper(tabelaGrupos.getItems().indexOf(p.getValue())+1 + "");
          }
        });           

        indicacaoClinica.setCellValueFactory(new Callback<CellDataFeatures<GrupoExames,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoExames,String> rec) {
                return new SimpleObjectProperty<>(rec.getValue().getIndicacaoClinica());
            }
        });  
        //nomeGrupo.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }           
    
    public void initTabelaExames() {
        exameCol.setCellValueFactory(new PropertyValueFactory<>("exame"));
        materialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
    }      
    
    public boolean checaGrupo() {
        boolean resultado = Boolean.FALSE;
        //grupoExames.setIndicacaoClinica(indicacaoClinica.getText());
        if ((grupoExames.getExames()==null)||(grupoExames.getExames().isEmpty())) {
            ShowDialog("EX", "Prescreva ao menos um exame", null,this.getStage());
    /*    } else if ((grupoExames.getIndicacaoClinica()==null)||(grupoExames.getIndicacaoClinica().isEmpty())) {
            ShowDialog("EX", "Informe a Indicação Clínica", null,this.getStage());
        */
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
    
    @FXML
    public void btnNovoGrupoFired(ActionEvent ae) throws IOException, CampoEmBrancoException {
        status = StatusBtn.INSERTING;
        grupoExames = new GrupoExames();
        
        String fxmlGUI = "dlg/IndicacaoClinica.fxml"; 
        StageStyle fxmlStyle = StageStyle.UTILITY;
        String fxmlTitle = "Indicação Clínica";

        GUIFactory nomeGUI;
        nomeGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        
        IndicacaoClinicaController controller = (IndicacaoClinicaController) nomeGUI.getController();
        controller.addStageCloseListener();
        
        nomeGUI.showAndWait();       
        
        if (!controller.getIndClinica().isEmpty()) {
            setButtons();
            habilEdicaoFired();
            grupoExames.setIndicacaoClinica(controller.getIndClinica());
            sopGrupo.set(grupoExames);
            sopGrupos.add(grupoExames);
        }
    }
    
    @FXML
    public void btnDuplicaGrupoFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
/*        
        grupoExames = new PedidoExames();
        grupoExames.setPaciente(sopPaciente.get());
        grupoExames.setDataEmissao(Util.ldHoje());
        grupoExames.setIndicacaoClinica(sopGrupo.get().getIndicacaoClinica());
        grupoExames.setComData(sopGrupo.get().getComData());
        
        for (Exame ex : sopGrupo.get().getExames()) {
            Exame p = new Exame();
            try {
                p.setExame(ex.getExame());
                p.setMaterial(ex.getMaterial());
                p.setPedidoExames(grupoExames);
                grupoExames.getExames().add(p);
            } catch (CampoEmBrancoException cbex) {
                Logger.getLogger(GrupoExamesController.class.getName()).log(Level.SEVERE, null, cbex);
            }
        }
//        grupoExames=recnew;   detached ex;
        sopGrupo.set(grupoExames);
        
        if (PedidosExames.novoPedido(grupoExames)) {
            status = StatusBtn.SHOWING;

            sopGrupos.setAll(PedidosExames.getLista(sopPaciente.get()));
            
            //sopPedidos.add(grupoExames);

            ShowDialog("S", "O pedido foi salvo com sucesso", null,this.getStage());
        } else {
            ShowDialog("EX", "Não foi possível salvar o pedido", null,this.getStage());
        }
*/
    }

    @FXML
    public void btnAtualizaGrupoFired(ActionEvent ae) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    @FXML
    public void btnExcluiGrupoFired(ActionEvent event) {        
        if (ExcluiRegistroDlg("ER", "", null,this.getStage())) {
            if (GruposExames.excluiGrupo(sopGrupo.get())) {
                sopGrupos.remove(sopGrupo.get());
                tabelaExames.getItems().clear();
            }
        }
    }    
    
    @FXML
    public void btnSalvaGrupoFired(ActionEvent event) {
        if (checaGrupo()) {
            if (status==StatusBtn.INSERTING) {            
                if (GruposExames.novoGrupo(grupoExames)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "O grupo foi salvo com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível salvar o grupo", null,this.getStage());
                }
            } else if (status==StatusBtn.UPDATING) {
                if (GruposExames.atualizaGrupo(grupoExames)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "O grupo foi atualizado com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível atualizar o grupo", null,this.getStage());
                }
            }
            setButtons();
            habilEdicaoFired();
        }
    }   
    
    @FXML
    public void btnCancelaGrupoFired(ActionEvent ae) {
        try {
            if (status==StatusBtn.INSERTING) {
                sopGrupos.remove(grupoExames);
                status = StatusBtn.IDLE;
            } else {
                status = StatusBtn.SHOWING;
            }        
        } catch (NullPointerException e) {
            status = StatusBtn.IDLE;
        }
        setButtons();      
        habilEdicaoFired();
    }
    
    @FXML
    public void btnNovoExameFired(ActionEvent ae) throws IOException {        
        String fxmlGUI = "fxml/ExameGrupo.fxml"; 
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Pedido de Exame";
        
        exameGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        exameGUI.initialize();
        ExameGrupoController controller = (ExameGrupoController) exameGUI.getController();        
        exameGUI.showAndWait();       
        
        if (controller.getExames()!=null) {    
            List<ExamesGrupo> exames = controller.getExames();            
            for(ExamesGrupo exam :exames) {
                exam.setGrupoExames(grupoExames);
                grupoExames.getExames().add(exam);
                sopExames.addAll(exam);
            }            
        }        
    }
    
    public void btnAlteraExameFired(ActionEvent ae)  throws IOException {
        String fxmlGUI = "fxml/ExameGrupo.fxml"; 
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Atualização de exame";
        
        exameGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        exameGUI.initialize();
        ExameGrupoController controller = (ExameGrupoController) exameGUI.getController();        
        controller.initExame(exame);
        int pos = grupoExames.getExames().indexOf(exame);
        List<ExamesGrupo> exames = grupoExames.getExames();
        exameGUI.showAndWait();       
        
        if (controller.getExame()!=null) {            
            ExamesGrupo exam = controller.getExames().get(0);
            exames.set(pos, exam);
            sopExames.setAll(exames);
        }        
    }

    public void btnExcluiExameFired(ActionEvent ae)  throws IOException {
        if (ExcluiRegistroDlg("EP", "", null,this.getStage())) { 
            grupoExames.getExames().remove(exame);
            sopExames.setAll(grupoExames.getExames());
//            status = StatusBtn.DELETINGPRESC;
        }
    }
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
    }    
    
        
    @FXML
    public void miOpcaoCartaFired(ActionEvent ev) {
        String fileName = "relatorios/pedidos/JPedidoCarta.jasper";
        PrintPedido(fileName);
    }
    @FXML
    public void miOpcaoA4Fired(ActionEvent ev) {
        String fileName = "relatorios/pedidos/JPedidoA4.jasper";
        PrintPedido(fileName);
    }
    @FXML
    public void miOpcaoGavetaFired(ActionEvent ev) {
        String fileName = "relatorios/pedidos/JPedidoG.jasper";
        PrintPedido(fileName);
    }
    @FXML
    public void miOpcaoMeioA4Fired(ActionEvent ev) {
        String fileName = "relatorios/pedidos/JPedidoMeioA4.jasper";
        PrintPedido(fileName);
    }
    @FXML
    public void miOpcaoReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/pedidos/JPedidoReduz.jasper";
        PrintPedido(fileName);
    }
    
    public void PrintPedido(String file) {
/*  
        HashMap hm = new HashMap();
        hm.put("idPedido", sopGrupo.get().getPedido_id());
        hm.put("nomePaciente", sopPaciente.get().getNome());
        hm.put("dataPedido", Util.formataDataExtenso(sopGrupo.get().getDataEmissao()));    
        hm.put("indicacaoClinica", sopGrupo.get().getIndicacaoClinica());    
        hm.put("comData", sopGrupo.get().getComData());    
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif"));
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif"));         
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            relat.openReport( "Pedido",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
*/
    }
     
    private void setButtons() {
        btnNovoGrupo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualizaGrupo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnSalvaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancelaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiGrupo.setDisable(status!=StatusBtn.SHOWING);
        btnPrintGrupos.setDisable(status!=StatusBtn.SHOWING);
        btnDuplicaGrupo.setDisable(status!=StatusBtn.SHOWING);
        
        miNovoGrupo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        miAtualizaGrupo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miSalvaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miCancelaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miExcluiGrupo.setDisable(status!=StatusBtn.SHOWING);
        miDuplicaGrupo.setDisable(status!=StatusBtn.SHOWING);

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
        
        btnNovoExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnAlteraExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    public void habilEdicaoFired() {
//        this.indicacaoClinica.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
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
}
