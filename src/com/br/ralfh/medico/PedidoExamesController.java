package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Exame;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.PedidoExames;
import com.br.ralfh.medico.modelos.PedidosExames;
import com.br.ralfh.medico.modelos.Receita;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
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
import javafx.util.Callback;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PedidoExamesController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private PedidoExames pedido;
    private Exame exame;
    private SimpleObjectProperty<PedidoExames> sopPedido;
    private ObservableList<PedidoExames> sopPedidos ;    
    private SimpleObjectProperty<Paciente> sopPaciente;        
    private ObservableList<Exame> sopExames = FXCollections.observableArrayList() ;    
    private StatusBtn status;
    private GUIFactory exameGUI;
            
    @FXML public TextField nomePaciente;

    @FXML public TableView<PedidoExames> tabelaPedidos;
    @FXML public TableColumn<PedidoExames,String> ordemCol;
    @FXML public TableColumn<PedidoExames,String> dataCol;    
    
    @FXML public TableView<Exame> tableExames;
    @FXML public TableColumn<Exame,String> exameCol;
    @FXML public TableColumn<Exame,String> materialCol;    
    
    @FXML public Button btnSair;
    @FXML public Button btnNovoExame;
    @FXML public Button btnAtualizaExame;
    @FXML public Button btnExcluiExame;
    
    @FXML Button btnNovoPedido; @FXML public MenuItem miNovoPedido;
    @FXML Button btnAtualizaPedido; @FXML public MenuItem miAtualizaPedido;
    @FXML Button btnSalvaPedido; @FXML public MenuItem miSalvaPedido;
    @FXML Button btnCancelaPedido; @FXML public MenuItem miCancelaPedido;
    @FXML Button btnExcluiPedido; @FXML public MenuItem miExcluiPedido;
    @FXML Button btnDuplicaPedido; @FXML public MenuItem miDuplicaPedido;
    @FXML SplitMenuButton btnPrintPedido;    
    @FXML public MenuItem miOpcaoCarta; @FXML public MenuItem miOpcaoCartaT;
    @FXML public MenuItem miOpcaoA4; @FXML public MenuItem miOpcaoA4T;
    @FXML public MenuItem miOpcaoGaveta; @FXML public MenuItem miOpcaoGavetaT;
    @FXML public MenuItem miOpcaoPrateleira; @FXML public MenuItem miOpcaoPrateleiraT;
    @FXML public MenuItem miOpcaoReduzido; @FXML public MenuItem miOpcaoReduzidoT;


    public PedidoExamesController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelas();
        setToolTips();
        sopPaciente = new SimpleObjectProperty<>();
        sopPedido = new SimpleObjectProperty<>();
        sopPedidos = FXCollections.observableArrayList();
        sopExames = FXCollections.observableArrayList();
        initListeners();
        
        status = StatusBtn.IDLE;
        setButtons();        
    }    
    
    public void setPaciente(Paciente paciente) {
        this.sopPaciente.set(paciente);  
    }    
        
    public void initListeners() {
        addPacienteListener();
        AddSelecPedidoListener();
        addPedidoListener();
        addPedidosListener();
        addPrescricoesListener();   
        addSelecExameListener();
    }

    private void initTabelas() {
        initTabelaPedidos();
        initTabelaExames();
    }
    
    private void setToolTips() {
        btnNovoPedido.setTooltip(new Tooltip("Criar nova receita"));
        btnAtualizaPedido.setTooltip(new Tooltip("Atualizar a Receita selecionada"));        
        btnSalvaPedido.setTooltip(new Tooltip("Gravar receita"));
        btnExcluiPedido.setTooltip(new Tooltip("Excluir receita selecionada"));
        btnDuplicaPedido.setTooltip(new Tooltip("Duplicar a receita selecionada"));
        btnPrintPedido.setTooltip(new Tooltip("Imprime a receita selecionada"));       
        btnNovoExame.setTooltip(new Tooltip("Prescreve um medicamento"));        
        btnExcluiExame.setTooltip(new Tooltip("Exclui o medicamento selecionado"));
        btnAtualizaExame.setTooltip(new Tooltip("Atualiza o medicamento selecionado"));
        btnSair.setTooltip(new Tooltip("Fechar esta janela"));
    }
        
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPaciente.get() != null) {
                try {
                    nomePaciente.setText(sopPaciente.get().getNome());
                    sopPedidos.setAll(PedidosExames.getLista(sopPaciente.get()));
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        });
    }   
    private void addPedidoListener() { 
        sopPedido.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (sopPedido.get()!=null) {
                    sopExames.setAll(sopPedido.get().getExames());
                    setButtons();
                }
            }
        });
    }       
    private void addPedidosListener() { 
        sopPedidos.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {                
                if (sopPedidos.size()>0) {
                    tabelaPedidos.getItems().setAll(sopPedidos);
                } else {
                    tabelaPedidos.getItems().clear();
                }
            }
        });        
    }        
    public void AddSelecPedidoListener() {
        tabelaPedidos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (status==StatusBtn.IDLE | status==StatusBtn.SHOWING) {
                    status = StatusBtn.SHOWING;
                    pedido = tabelaPedidos.getSelectionModel().getSelectedItem();
                    sopPedido.set(pedido); 
                }
            }
        }); 
    }
    
    private void addPrescricoesListener() { 
        sopExames.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!sopExames.isEmpty()) {
                    tableExames.setItems(sopExames);
                }
            }
        });
    }
       
    private void addSelecExameListener() { 
        tableExames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {                
                exame = tableExames.getSelectionModel().getSelectedItem();
            }
        });
    }

    
    public void initTabelaPedidos() {
        ordemCol.setCellValueFactory(new Callback<CellDataFeatures<PedidoExames, String>, ObservableValue<String>>() {
          @Override public ObservableValue<String> call(CellDataFeatures<PedidoExames, String> p) {
            return new ReadOnlyObjectWrapper(tabelaPedidos.getItems().indexOf(p.getValue())+1 + "");
          }
        });           
        dataCol.setCellValueFactory(new Callback<CellDataFeatures<PedidoExames,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PedidoExames,String> rec) {
                return new SimpleObjectProperty<>(Util.formataDataExtenso(rec.getValue().getDataEmissao()));
            }
        });   
    }           
    
    public void initTabelaExames() {
        exameCol.setCellValueFactory(new PropertyValueFactory<>("medicamento"));
        materialCol.setCellValueFactory(new PropertyValueFactory<>("posologia"));
    }      
    
    public boolean checaPedido() {
        boolean resultado = Boolean.FALSE;
        if (pedido.getExames().isEmpty()) {
            ShowDialog("EX", "Prescreva ao menos um exame", null,this.getStage());
        } else {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }
    
    @FXML
    public void btnNovoPedidoFired(ActionEvent ae) {
        status = StatusBtn.INSERTING;
        pedido = new PedidoExames();
        pedido.setPaciente(sopPaciente.get());
        pedido.setDataEmissao(Util.ldHoje());
        sopPedido.set(pedido);
        sopPedidos.add(pedido);
    }
    
    @FXML
    public void btnDuplicaPedidoFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        
        pedido = new PedidoExames();
        pedido.setPaciente(sopPaciente.get());
        pedido.setDataEmissao(Util.ldHoje());
        
        for (Exame ex : sopPedido.get().getExames()) {
            Exame p = new Exame();
            try {
                p.setExame(ex.getExame());
                p.setMaterial(ex.getMaterial());
                p.setPedidoExames(pedido);
                pedido.getExames().add(p);
            } catch (CampoEmBrancoException cbex) {
                Logger.getLogger(PedidoExamesController.class.getName()).log(Level.SEVERE, null, cbex);
            }
        }
//        pedido=recnew;   detached ex;
        sopPedido.set(pedido);
        sopPedidos.add(pedido);
        
        if (PedidosExames.novoPedido(pedido)) {
            status = StatusBtn.SHOWING;
            ShowDialog("S", "O pedido foi salvo com sucesso", null,this.getStage());
        } else {
            ShowDialog("EX", "Não foi possível salvar o pedido", null,this.getStage());
        }
    }

    @FXML
    public void btnAtualizaPedidoFired(ActionEvent ae) {
        status = StatusBtn.UPDATING;
        setButtons();
    }
    
    @FXML
    public void btnExcluiPedidoFired(ActionEvent event) {        
        if (ExcluiRegistroDlg("ER", "", null,this.getStage())) {
            if (PedidosExames.excluiPedido(sopPedido.get())) {
                sopPedidos.remove(sopPedido.get());
                tableExames.getItems().clear();
            }
        }
    }    
    
    @FXML
    public void btnSalvaPedidoFired(ActionEvent event) {
        if (checaPedido()) {
            if (status==StatusBtn.INSERTING) {            
                if (PedidosExames.novoPedido(pedido)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "O pedido foi salvo com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível salvar o pedido", null,this.getStage());
                }
            } else if (status==StatusBtn.UPDATING) {
                if (PedidosExames.atualizaPedido(pedido)) {
                    status = StatusBtn.SHOWING;
                    ShowDialog("S", "O pedido foi atualizado com sucesso", null,this.getStage());
                } else {
                    ShowDialog("EX", "Não foi possível atualizar o pedido", null,this.getStage());
                }
            }
            setButtons();
        }
    }   
    
    @FXML
    public void btnCancelaPedidoFired(ActionEvent ae) {
        try {
            if (status==StatusBtn.INSERTING) {
                sopPedidos.remove(pedido);
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
    public void btnNovoExameFired(ActionEvent ae) throws IOException {        
        String fxmlGUI = "fxml/Exame.fxml"; 
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Pedido de Exame";
        
        exameGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        exameGUI.initialize();
        ExameController controller = (ExameController) exameGUI.getController();        
        exameGUI.showAndWait();       
        
        if (controller.getExame()!=null) {            
            Exame exam = controller.getExame();
            exam.setPedidoExames(pedido);
            pedido.getExames().add(exam);                                
            sopExames.add(exam);
        }        
    }
    
    public void btnAtualizaExameFired(ActionEvent ae)  throws IOException {
        String fxmlGUI = "fxml/Exame.fxml"; 
        StageStyle fxmlStyle = StageStyle.DECORATED;
        String fxmlTitle = "Atualização de exame";
        
        exameGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        exameGUI.initialize();
        ExameController controller = (ExameController) exameGUI.getController();        
        exameGUI.showAndWait();       
        
        if (controller.getExame()!=null) {            
            Exame exam = controller.getExame();
            exam.setPedidoExames(pedido);
            pedido.getExames().add(exam);                                
            sopExames.add(exam);
        }        
    }

    public void btnExcluiExameFired(ActionEvent ae)  throws IOException {
        if (ExcluiRegistroDlg("EP", "", null,this.getStage())) { 
            pedido.getExames().remove(exame);
            sopExames.remove(exame);
//            status = StatusBtn.DELETINGPRESC;
        }
    }
        
    public void sairFired(ActionEvent event) {
        this.stage.close();
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
    public void miOpcaoPrateleiraFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaP.jasper";
        PrintReceita(fileName);
    }
    @FXML
    public void miOpcaoReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/receitas/JReceitaReduz.jasper";
        PrintReceita(fileName);
    }
    
    public void PrintReceita(String file) {
        HashMap hm = new HashMap();
        hm.put("idReceita", sopPedido.get().getPedido_id());
        hm.put("nomePaciente", sopPaciente.get().getNome());
        hm.put("dataReceita", Util.formataDataExtenso(sopPedido.get().getDataEmissao()));    
        
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
        btnNovoPedido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualizaPedido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnSalvaPedido.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancelaPedido.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiPedido.setDisable(status!=StatusBtn.SHOWING);
        btnPrintPedido.setDisable(status!=StatusBtn.SHOWING);
        btnDuplicaPedido.setDisable(status!=StatusBtn.SHOWING);
        
        miNovoPedido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        miAtualizaPedido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miSalvaPedido.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miCancelaPedido.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miExcluiPedido.setDisable(status!=StatusBtn.SHOWING);
        miDuplicaPedido.setDisable(status!=StatusBtn.SHOWING);

        miOpcaoCarta.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoA4.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoGaveta.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoPrateleira.setDisable(true);  //((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoReduzido.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoCartaT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoA4T.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoGavetaT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoPrateleiraT.setDisable(true);  //((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miOpcaoReduzidoT.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        
        btnNovoExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnAtualizaExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnExcluiExame.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
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