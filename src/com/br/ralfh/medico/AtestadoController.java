package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Atestado;
import com.br.ralfh.medico.modelos.Atestados;
import com.br.ralfh.medico.modelos.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class AtestadoController extends Controller {
    
    private StatusBtn status;

    private Atestado atestado;
    private final SimpleObjectProperty<Atestado> sopAtestado;
    private final ObservableList<Atestado> sopAtestados;
    private final SimpleObjectProperty<Paciente> sopPaciente;
//    private Boolean getCloseModal;
    
    @FXML public Button btnNovoAtestado;
    @FXML public Button btnEditaAtestado;
    @FXML public Button btnExcluiAtestado;
    @FXML public Button btnConfirma;
    @FXML public Button btnCancela;
    @FXML public Button btnDuplicar;
    @FXML public SplitMenuButton btnPrintAtestado;
    @FXML public Button btnSair;
    
    @FXML public TabPane tpAtestado;
    
    @FXML public MenuItem miNovoAtestado;
    @FXML public MenuItem miEditaAtestado;
    @FXML public MenuItem miExcluiAtestado;
    @FXML public MenuItem miConfirma;
    @FXML public MenuItem miCancela;
    @FXML public MenuItem miDuplicar;
    @FXML public MenuItem miSair;    
    
    @FXML public MenuItem miAtestadoCarta;
    @FXML public MenuItem miAtestadoA4;
    @FXML public MenuItem miAtestadoGaveta;
    @FXML public MenuItem miAtestadoMeioA4;
    @FXML public MenuItem miAtestadoReduzido;
    @FXML public MenuItem miAtestadoCartaT;
    @FXML public MenuItem miAtestadoA4T;
    @FXML public MenuItem miAtestadoGavetaT;
    @FXML public MenuItem miAtestadoMeioA4T;
    @FXML public MenuItem miAtestadoReduzidoT;

    
    @FXML public TextField nomeAtestado;
    @FXML public DatePicker dataAtestado;
    @FXML public TableView<Atestado> tabelaAtestados;
    @FXML public TableColumn<Atestado,String> dataCol;
    @FXML public TableColumn<Atestado,String> atestadoCol;
    @FXML public HTMLEditor htmlEditorCabecalho;
    @FXML public HTMLEditor htmlEditorCorpo;
    @FXML public HTMLEditor htmlEditorRodape;
    
    public AtestadoController() {   
        this.status = StatusBtn.IDLE;
        sopPaciente = new SimpleObjectProperty<>();
        sopAtestados = FXCollections.observableArrayList(); 
        sopAtestado = new SimpleObjectProperty<>();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelaAtestados();
        addAtestadosListener();
        addListenerAtestado();
        AddListenerSelecModelo();
        AddListenerTabPage();
        setButtons();
        habilEdicaoFired();
    }    
    
    public void setPaciente(Paciente paciente) {
        this.sopPaciente.set(paciente);
        setAtestados(Atestados.getObsLista(paciente));
    }
    
    public void setAtestados(ObservableList<Atestado> atestados) {
        this.sopAtestados.setAll(atestados);
    }
    
    @FXML
    public void btnNovoAtestadoFired(ActionEvent event) {        
        tpAtestado.getSelectionModel().select(1);
        limpaAtestado();
        String fxmlGUI = "fxml/SelecModeloAtestado.fxml";
        String titleGUI = "Selecione um modelo de atestados";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory atestados;   
        SelecModeloController controller = null;
        try {
            atestados = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            controller = (SelecModeloController) atestados.getController();
            atestados.showAndWait(); 

            if (controller.getCloseModal()) {  
                dataAtestado.setValue(LocalDate.now());
                nomeAtestado.setText(controller.getNomeModelo());
                htmlEditorCabecalho.setHtmlText(controller.getCabecalhoModelo());
                htmlEditorCorpo.setHtmlText(trataTagsAtestado(controller.getCorpoModelo()));
                htmlEditorRodape.setHtmlText(controller.getRodapeModelo());
            } else {
            }
        } catch (IOException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
        status = StatusBtn.INSERTING;
        setButtons();
        habilEdicaoFired();        
    }
    
    @FXML
    public void btnEditaAtestadoFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
        tpAtestado.getSelectionModel().select(1);
    }
    
    @FXML
    public void btnExcluiAtestadoFired(ActionEvent event) {        
        if (ExcluiRegistroDlg("EAT", "", null,this.getStage())) {
            limpaAtestado();
            Atestados.excluiAtestado(atestado);
            setAtestados(Atestados.getObsLista(this.sopPaciente.get()));
        }
    }
    
    @FXML
    public void miAtestadoCartaFired(ActionEvent ev) {
        String fileName = "relatorios/atestados/AtestadosCarta.jasper";
        PrintAtestado(fileName);
    }
    @FXML
    public void miAtestadoA4Fired(ActionEvent ev) {
        String fileName = "relatorios/atestados/AtestadosA4.jasper";
        PrintAtestado(fileName);
    }
    @FXML
    public void miAtestadoGavetaFired(ActionEvent ev) {
        String fileName = "relatorios/atestados/AtestadosG.jasper";
        PrintAtestado(fileName);
    }
    @FXML
    public void miAtestadoMeioA4Fired(ActionEvent ev) {
        String fileName = "relatorios/atestados/JAtestadosMeioA4.jasper";
        PrintAtestado(fileName);
    }
    @FXML
    public void miAtestadoReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/atestados/AtestadosReduz.jasper";
        PrintAtestado(fileName);
    }
    
    public void PrintAtestado(String file) {
        HashMap hm = new HashMap();

        hm.put("dataAtestado", Util.formataDataExtenso(atestado.getData()));    
        hm.put("idAtestado", atestado.getId());   
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif")); //("imagens/formularioJHTC_03.png")); 
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif")); //("imagens/formularioJHTC_11.png")); 
        
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            relat.openReport( "Atestado",inputStream,hm);
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    public void btnConfirmaFired(ActionEvent event) {
        if (status==StatusBtn.INSERTING) {
            this.atestado = new Atestado();
            //this.atestado.setData(Util.udate(dataAtestado.getValue()));
            if (preencheAtestado(this.atestado)) {                        
                if (Atestados.novoAtestado(atestado)) {
                    ShowDialog("S", "Atestado criado com sucesso", null,this.getStage());                    
                    status = StatusBtn.SHOWING;
                } else {
                    ShowDialog("EX", "Não foi possível criar o atestado", null,this.getStage());
//                    status = StatusBtn.INSERTING;
                }    
            } else return;
        } else {
            if (preencheAtestado(this.atestado)) {
                if (Atestados.atualizaAtestado(this.atestado)) {
                    ShowDialog("S", "Atestado atualizado com sucesso", null,this.getStage());                    
                    status = StatusBtn.SHOWING;
                  } else {
                        ShowDialog("EX", "Não foi possível atualizar o atestado", null,this.getStage());
                }
            } else return;
        }
//        status = StatusBtn.SHOWING;
        //setAtestados(Atestados.getObsLista(this.sopPaciente.get()));
        setButtons();
        habilEdicaoFired();
    }

    public void btnCancelaFired(ActionEvent event) {
        try {
            if (status==StatusBtn.INSERTING) {
                limpaAtestado();
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
    
    public void btnDuplicarFired(ActionEvent ae) {
        Atestado clone = new Atestado();
        clone.setData(Util.dHoje());
        if (preencheAtestado(clone)) {        
            Atestados.novoAtestado(clone);
        } else return;
        setAtestados(Atestados.getObsLista(this.sopPaciente.get()));
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnSairFired(ActionEvent ae) {
        this.stage.close();
    }

    private void initTabelaAtestados() {
//        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        dataCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atestado,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atestado,String> d) {
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                return new SimpleObjectProperty<>(dt.format(d.getValue().getData()));
            }
        });
        atestadoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
       
    private void addAtestadosListener() { 
        sopAtestados.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaAtestados.setItems(sopAtestados);
        }
        });
    }  
    
    public void addListenerAtestado() {
        sopAtestado.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if ((sopAtestado.get()!= null)) { 
                try {
                    status = StatusBtn.SHOWING;
                    limpaAtestado();
                    mostraAtestado();
                    setButtons();
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }            
        });
    }
    
    public void AddListenerSelecModelo() {
        tabelaAtestados.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            atestado = tabelaAtestados.getSelectionModel().getSelectedItem();
            sopAtestado.set(atestado);
        }
        }); 
    }
    
    public void AddListenerTabPage() {
        tpAtestado.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (tpAtestado.getSelectionModel().getSelectedIndex()==0) {
                setAtestados(Atestados.getObsLista(sopPaciente.get()));
            }
        }
        });
    }
    
    private void mostraAtestado() {
        dataAtestado.setValue(Util.ld(atestado.getData()));
        nomeAtestado.setText(atestado.getTipo());
        htmlEditorCabecalho.setHtmlText(atestado.getCabecalho());
        htmlEditorCorpo.setHtmlText(atestado.getCorpo());
        htmlEditorRodape.setHtmlText(atestado.getRodape());
    }
    
    private void limpaAtestado() {
        dataAtestado.setValue(null);
        nomeAtestado.clear();
        htmlEditorCabecalho.setHtmlText("");
        htmlEditorCorpo.setHtmlText("");
        htmlEditorRodape.setHtmlText("");
    }
    
    
    private Boolean preencheAtestado(Atestado atest) {        
        Boolean resultado = Boolean.FALSE;
        atest.setData(Util.udate(dataAtestado.getValue()));
        atest.setTipo(nomeAtestado.getText());
        atest.setPaciente(sopPaciente.get());
        try {            
            if (htmlEditorCabecalho.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o cabeçalho do atestado");
            } else {
                atest.setCabecalho(htmlEditorCabecalho.getHtmlText().trim());
            }

            if (htmlEditorCorpo.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o corpo do atestado");
            } else {
                atest.setCorpo(htmlEditorCorpo.getHtmlText().trim());
            }
            
            if (htmlEditorRodape.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o rodapé do atestado");
            } else {
                atest.setRodape(htmlEditorRodape.getHtmlText().trim());
            }
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ex) {
            ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }
    
/*    
    public void nomepacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@nomePaciente@@");
//        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(texto);
    }
    
    public void nascpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@nascimentoPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void identpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@identidadePaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void cpfpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@cpfPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void datapacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@datadodiaPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }
*/

    public void habilEdicaoFired() {
        nomeAtestado.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorCabecalho.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
//        htmlEditorCorpo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorRodape.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    private void setButtons() {
        btnNovoAtestado.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnEditaAtestado.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnDuplicar.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnExcluiAtestado.setDisable(status!=StatusBtn.SHOWING);
        btnConfirma.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancela.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miNovoAtestado.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        miEditaAtestado.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miDuplicar.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miExcluiAtestado.setDisable(status!=StatusBtn.SHOWING);
        miConfirma.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miCancela.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miAtestadoCarta.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoA4.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoGaveta.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoReduzido.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoMeioA4.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoCartaT.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoA4T.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoGavetaT.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoMeioA4T.setDisable(status!=StatusBtn.SHOWING);
        miAtestadoReduzidoT.setDisable(status!=StatusBtn.SHOWING);
    }
    
    @SuppressWarnings("empty-statement")
    private String trataTagsAtestado(String html) {
        
        if (html.contains("@@nomePaciente@@")) {
            html = html.replace("@@nomePaciente@@", sopPaciente.get().getNome());
        };
        
        if (html.contains("@@nascimentoPaciente@@")) {
            html = html.replace("@@nascimentoPaciente@@", Util.formataData(sopPaciente.get().getNascimento()));
        }; 
        
        if (html.contains("@@identidadePaciente@@")) {
            html = html.replace("@@identidadePaciente@@", sopPaciente.get().getIdentidade());
        }; 
        
        if (html.contains("@@cpfPaciente@@")) {
            html = html.replace("@@cpfPaciente@@", sopPaciente.get().getCpf());
        }; 
        
        if (html.contains("@@datadodiaPaciente@@")) {
            html = html.replace("@@datadodiaPaciente@@", Util.formataData(Util.ldHoje()));
        }; 
        
        return html;
        
        
    }
    
}
