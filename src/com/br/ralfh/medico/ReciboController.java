package com.br.ralfh.medico;

import static com.br.ralfh.medico.Util.BigDecToStr;
import static com.br.ralfh.medico.Util.StrToBigDec;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Recibo;
import com.br.ralfh.medico.modelos.Recibos;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.stage.StageStyle;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ReciboController extends Controller {
    
    private StatusBtn status;

    private Recibo recibo;
    private final SimpleObjectProperty<Recibo> sopRecibo;
    private final ObservableList<Recibo> sopRecibos;
    private final SimpleObjectProperty<Paciente> sopPaciente;
//    private Boolean getCloseModal;
    
    @FXML public Button btnNovoRecibo; @FXML public MenuItem miNovoRecibo;
    @FXML public Button btnEditaRecibo; @FXML public MenuItem miEditaRecibo;
    @FXML public Button btnExcluiRecibo; @FXML public MenuItem miExcluiRecibo;
    @FXML public Button btnConfirma; @FXML public MenuItem miConfirma;
    @FXML public Button btnCancela; @FXML public MenuItem miCancela;
    @FXML public SplitMenuButton btnRelatRecibo; @FXML public MenuItem miClonar;
    @FXML public MenuItem miReciboCarta;
    @FXML public MenuItem miReciboA4;
    @FXML public MenuItem miReciboGaveta;
    @FXML public MenuItem miReciboMeioA4;
//    @FXML public MenuItem miReciboEnvelope;
    @FXML public MenuItem miReciboReduzido;
    

    @FXML public MenuItem miReciboCartaT;
    @FXML public MenuItem miReciboA4T;
    @FXML public MenuItem miReciboGavetaT;
    @FXML public MenuItem miReciboMeioA4T;
//    @FXML public MenuItem miReciboEnvelopeT;
    @FXML public MenuItem miReciboReduzidoT;
    
    @FXML public Button btnClonar;
    @FXML public Button btnSair;
    @FXML public TextField nomeRecibo;
    @FXML public TextField valorRecibo;
    @FXML public TableView<Recibo> tabelaRecibos;
    @FXML public TableColumn<Recibo,String> dataCol;
    @FXML public TableColumn<Recibo,String> reciboCol;
    @FXML public HTMLEditor htmlEditorCabecalho;
    @FXML public HTMLEditor htmlEditorCorpo;
    @FXML public HTMLEditor htmlEditorRodape;
    
    @FXML public Button nomepac;
    @FXML public Button nascpac;
    @FXML public Button identpac;
    @FXML public Button cpfpac;
    @FXML public Button datapac;
    
    public ReciboController() {   
        this.status = StatusBtn.IDLE;
        sopPaciente = new SimpleObjectProperty<>();
        sopRecibos = FXCollections.observableArrayList(); 
        sopRecibo = new SimpleObjectProperty<>();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelaRecibos();
        addRecibosListener();
        addListenerRecibo();
        AddListenerSelecRecibo();
        setButtons();
        habilEdicaoFired();
    }    
    
    public void setPaciente(Paciente paciente) {
        this.sopPaciente.set(paciente);
        setRecibos(Recibos.getObsLista(paciente));
    }
    
    public void setRecibos(ObservableList<Recibo> recibos) {
        limpaRecibo();
        this.sopRecibos.setAll(recibos);
    }
    
    @FXML
    public void btnNovoReciboFired(ActionEvent event) {        
        limpaRecibo();
        String fxmlGUI = "fxml/SelecModeloRecibo.fxml";
        String titleGUI = "Selecione um modelo de recibo";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory recibos;   
        SelecModeloReciboController controller = null;
        try {
            recibos = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,this.getStage());
            controller = (SelecModeloReciboController) recibos.getController();
            recibos.showAndWait(); 

            if (controller.getCloseModal()) {  
                nomeRecibo.setText(controller.getNomeModelo());
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
    public void btnEditaReciboFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    @FXML
    public void btnExcluiReciboFired(ActionEvent event) {        
        if (ExcluiRegistroDlg("EAT", "", null,this.getStage())) {
            limpaRecibo();
            Recibos.excluiRecibo(recibo);
            setRecibos(Recibos.getObsLista(this.sopPaciente.get()));
        }
    }
    
    @FXML
    public void miReciboCartaFired(ActionEvent ev) {
        String fileName = "relatorios/recibos/RecibosCarta.jasper";
        PrintRecibo(fileName);
    }
    @FXML
    public void miReciboA4Fired(ActionEvent ev) {
        String fileName = "relatorios/recibos/RecibosA4.jasper";
        PrintRecibo(fileName);
    }
    @FXML
    public void miReciboGavetaFired(ActionEvent ev) {
        String fileName = "relatorios/recibos/RecibosG.jasper";
        PrintRecibo(fileName);
    }
    @FXML
    public void miReciboMeioA4Fired(ActionEvent ev) {
        String fileName = "relatorios/recibos/RecibosMeioA4.jasper";
        PrintRecibo(fileName);
    }
    @FXML
    public void miReciboReduzidoFired(ActionEvent ev) {
        String fileName = "relatorios/recibos/RecibosReduz.jasper";
        PrintRecibo(fileName);
    }
    
    public void PrintRecibo(String file) {
        HashMap hm = new HashMap();

        hm.put("dataRecibo", Util.formataDataExtenso(recibo.getData()));    
        hm.put("idRecibo", recibo.getId());   
        
        ImageIcon logoCabecalho = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_03.gif")); //("imagens/formularioJHTC_03.png")); 
        ImageIcon logoRodape = new ImageIcon(getClass().getResource("imagens/formularioJHTC-Rev1_14.gif")); //("imagens/formularioJHTC_11.png")); 
        
        hm.put("logoCabecalho",logoCabecalho.getImage());
        hm.put("logoRodape", logoRodape.getImage());
        
        DataAccessRelatorios relat = new DataAccessRelatorios();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            relat.openReport( "Recibo",inputStream,hm);
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
            this.recibo = new Recibo();
            this.recibo.setData(Util.dHoje());
            if (preencheRecibo(this.recibo)) {        
                Recibos.novoRecibo(recibo);
            } else return;
        } else {
            if (preencheRecibo(this.recibo)) {
                Recibos.atualizaRecibo(this.recibo);
            } else return;
        }
        status = StatusBtn.SHOWING;
        setRecibos(Recibos.getObsLista(this.sopPaciente.get()));
        setButtons();
        habilEdicaoFired();
    }

    public void btnCancelaFired(ActionEvent event) {
        try {
            if (status==StatusBtn.INSERTING) {
                limpaRecibo();
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
    
    public void btnClonarFired(ActionEvent ae) {
        Recibo clone = new Recibo();
        clone.setData(Util.dHoje());
        if (preencheRecibo(clone)) {        
            Recibos.novoRecibo(clone);
        } else return;
        setRecibos(Recibos.getObsLista(this.sopPaciente.get()));
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnSairFired(ActionEvent ae) {
        this.stage.close();
    }

    private void initTabelaRecibos() {
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        reciboCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
       
    private void addRecibosListener() { 
        sopRecibos.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaRecibos.setItems(sopRecibos);
        }
        });
    }  
    
    public void addListenerRecibo() {
        sopRecibo.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if ((sopRecibo.get()!= null)) { 
                try {
                    status = StatusBtn.SHOWING;
                    limpaRecibo();
                    mostraRecibo();
                    setButtons();
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }            
        });
    }
    
    public void AddListenerSelecRecibo() {
        tabelaRecibos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            recibo = tabelaRecibos.getSelectionModel().getSelectedItem();
            sopRecibo.set(recibo);
        }
        }); 
    }
    
    
    private void mostraRecibo() {
        nomeRecibo.setText(recibo.getTipo());
        valorRecibo.setText(BigDecToStr(recibo.getValor()));
        htmlEditorCabecalho.setHtmlText(recibo.getCabecalho());
        htmlEditorCorpo.setHtmlText(recibo.getCorpo());
        htmlEditorRodape.setHtmlText(recibo.getRodape());
    }
    
    private void limpaRecibo() {
        nomeRecibo.clear();
        valorRecibo.clear();
        htmlEditorCabecalho.setHtmlText(null);
        htmlEditorCorpo.setHtmlText(null);
        htmlEditorRodape.setHtmlText(null);
    }
    
    
    private Boolean preencheRecibo(Recibo recib) {        
        Boolean resultado = Boolean.FALSE;
        recib.setTipo(nomeRecibo.getText());
        recib.setPaciente(sopPaciente.get());
        try {    
            if (valorRecibo.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o valor do recibo");
            } else {
                recib.setValor(StrToBigDec(valorRecibo.getText()));
            }
            if (htmlEditorCabecalho.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o cabeçalho do recibo");
            } else {
                recib.setCabecalho(htmlEditorCabecalho.getHtmlText());
            }

            if (htmlEditorCorpo.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o corpo do recibo");
            } else {
                recib.setCorpo(htmlEditorCorpo.getHtmlText());
            }
            
            if (htmlEditorRodape.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o rodapé do recibo");
            } else {
                recib.setRodape(htmlEditorRodape.getHtmlText());
            }
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ex) {
            ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }
    
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
    
    public void habilEdicaoFired() {
        nomeRecibo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        valorRecibo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorCabecalho.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorCorpo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorRodape.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    private void setButtons() {
        btnNovoRecibo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnEditaRecibo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnExcluiRecibo.setDisable(status!=StatusBtn.SHOWING);
        btnConfirma.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancela.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnClonar.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miNovoRecibo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        miEditaRecibo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        miExcluiRecibo.setDisable(status!=StatusBtn.SHOWING);
        miConfirma.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miCancela.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miClonar.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        
        miReciboCarta.setDisable(status!=StatusBtn.SHOWING);
        miReciboA4.setDisable(status!=StatusBtn.SHOWING);
        miReciboGaveta.setDisable(status!=StatusBtn.SHOWING);
        miReciboMeioA4.setDisable(status!=StatusBtn.SHOWING);
        miReciboReduzido.setDisable(status!=StatusBtn.SHOWING);
        miReciboCartaT.setDisable(status!=StatusBtn.SHOWING);
        miReciboA4T.setDisable(status!=StatusBtn.SHOWING);
        miReciboGavetaT.setDisable(status!=StatusBtn.SHOWING);
        miReciboMeioA4T.setDisable(status!=StatusBtn.SHOWING);
        miReciboReduzidoT.setDisable(status!=StatusBtn.SHOWING);
        
        nomepac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        nascpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        identpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        cpfpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        datapac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
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
