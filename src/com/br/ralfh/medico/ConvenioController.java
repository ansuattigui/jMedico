package com.br.ralfh.medico;

import static com.br.ralfh.medico.Controller.ShowDialog;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.exceptions.CampoNuloException;
import com.br.ralfh.medico.modelos.Convenio;
import com.br.ralfh.medico.modelos.Convenios;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ConvenioController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private Convenio convenio;
    private StatusBtn status;
    private final SimpleObjectProperty<Convenio> sopConvenio;
    private final ObservableList<Convenio> sopConvenios;    
    
    @FXML Button btnCriarConvenio;
    @FXML Button btnAtualConvenio;
    @FXML Button btnDelConvenio;
    @FXML Button btnConfConvenio;
    @FXML Button btnCancConvenio;    
    @FXML Button btnSairConvenio;
    @FXML Button btnProcNome;
    
    @FXML TextField codANS;
    @FXML TextField nomeConvenio;
    @FXML TextField cnpjConvenio;

    @FXML DatePicker nascPaciente;

    @FXML TextField enderConvenio;
    @FXML TextField numEndConvenio;
    @FXML TextField compEndConvenio;
    @FXML TextField bairroConvenio;
    @FXML TextField cepConvenio;
    @FXML TextField cidadeConvenio;
    @FXML ChoiceBox<Estado> ufConvenio;
    @FXML TextField telCom1Convenio;
    @FXML TextField telCom2Convenio;
    @FXML TextField telCom3Convenio;
    @FXML TextField faxConvenio;
    @FXML TextField emailConvenio;
    @FXML TextField wwwConvenio;
    
    @FXML TextField valorConsulta;
    @FXML TextField valorECG;
    @FXML TextField valorInternacao;
    
    @FXML TextField diaIniEntrega;
    @FXML TextField diaFimEntrega;
    
    public ConvenioController() {
        this.status = StatusBtn.IDLE;
        this.sopConvenios = FXCollections.observableArrayList();
        this.sopConvenio = new SimpleObjectProperty<>();  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtons();
        habilEdicaoFired();
        initCombos();
        addConvenioListener();
        addConveniosListener();
//        addStageCloseListener();
    } 

    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Fechando formulário");
          }
    });     
    }
    
    private void addConvenioListener() { 
        sopConvenio.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopConvenio.get() != null) {
                mostraConvenio();
                status = StatusBtn.SHOWING;
                setButtons();
                habilEdicaoFired();
            }
        }
    });                
    }
        
    private void addConveniosListener() { 
        sopConvenios.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                apagaConvenio();
                if (sopConvenios.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecConvenio.fxml";
                        String titleGUI = "Selecionar Convenio";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecConvenio = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle);
                        selecConvenio.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecConvenioController controller = (SelecConvenioController) selecConvenio.getController();
                        controller.setConvenio(sopConvenios);
                        selecConvenio.showAndWait();
                        if (controller.closeModal) {
                            convenio = controller.tabelaConvenios.getSelectionModel().getSelectedItem();
                            sopConvenio.set(convenio);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopConvenios.size() == 1) {
                    convenio = sopConvenios.iterator().next();
                    sopConvenio.set(convenio);
                }
            }
        });     
    }  
        
    private void initCombos() {
        initComboUF();
    }
        
    private void initComboUF() {       
        ObservableList<Estado> options = 
            FXCollections.observableArrayList(
                Estado.values()
            );        
        ufConvenio.getItems().addAll(options);        
    }
    
    public void criaConvenioFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaConvenioFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnDelConvenioFired(ActionEvent event) {

        if (ExcluiRegistroDlg("ECON", "", null)) {
            apagaConvenio();
            Convenios.excluiConvenio(convenio);
        }
        
    }
            
    public void confConvenioFired(ActionEvent event) {      
        if (status==StatusBtn.INSERTING) {
            this.convenio = new Convenio();
            if (preencheConvenio()) {        
                Convenios.novoConvenio(convenio);
            } else return;
        } else {
            if (preencheConvenio()) {
                Convenios.atualizaConvenio(convenio);
            } else return;
        }

        if (convenio.getId() == -1) {
            status = StatusBtn.IDLE;
        } else {
            status = StatusBtn.SHOWING;
        }        
        setButtons();      
        habilEdicaoFired();
    }
    
    public void cancConvenioFired(ActionEvent event) {
        try {
            if (convenio.getId() == -1) {
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
    
    
    public void btnProcurarFired(ActionEvent event) {
        apagaConvenio();
        status = StatusBtn.IDLE;
        setButtons();
        habilEdicaoFired();
    }
    

    public void sairConvenioFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    private void mostraConvenio() {        
        codANS.setText(String.valueOf(convenio.getCodigoANS()));
        nomeConvenio.setText(convenio.getNome()); 
        cnpjConvenio.setText(convenio.getCnpj());
        enderConvenio.setText(convenio.getEndereco());
        numEndConvenio.setText(convenio.getNumero());
        compEndConvenio.setText(convenio.getComplemento());
        bairroConvenio.setText(convenio.getBairro());
        cepConvenio.setText(convenio.getCep());
        cidadeConvenio.setText(convenio.getCidade());
        try {
            ufConvenio.getSelectionModel().select(convenio.getEstado());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        telCom1Convenio.setText(convenio.getTelefone1());
        telCom2Convenio.setText(convenio.getTelefone2());
        telCom3Convenio.setText(convenio.getTelefone3());
        faxConvenio.setText(convenio.getFax());
        emailConvenio.setText(convenio.getEmail());
        wwwConvenio.setText(convenio.getWww());
        valorConsulta.setText(convenio.getValorConsulta()==null?"0,00":String.format("%1$,.2f",convenio.getValorConsulta()));
        valorECG.setText(convenio.getValorECG()==null?"0,00":String.format("%1$,.2f",convenio.getValorECG()));
        valorInternacao.setText(convenio.getValorInternacao()==null?"0,00":String.format("%1$,.2f",convenio.getValorInternacao()));
        diaIniEntrega.setText(convenio.getInicioEntrega());
        diaFimEntrega.setText(convenio.getFimEntrega());        
    }

    private Boolean preencheConvenio() {        
        Boolean resultado = Boolean.FALSE;
        try {            
            if (codANS.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o codigo ANS do convenio");
            } else {
                convenio.setCodigoANS(codANS.getText());
            }
            if (nomeConvenio.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o nome do convenio");
            } else {
                convenio.setNome(nomeConvenio.getText()); 
            }                        
            convenio.setCnpj(cnpjConvenio.getText());
            convenio.setEndereco(enderConvenio.getText());
            convenio.setNumero(numEndConvenio.getText());
            convenio.setComplemento(compEndConvenio.getText());
            convenio.setBairro(bairroConvenio.getText());
            convenio.setCep(cepConvenio.getText());
            convenio.setCidade(cidadeConvenio.getText());
            if (ufConvenio.getSelectionModel().getSelectedItem()==null) {
                throw new CampoNuloException("Informe o estado do convenio");
            } else {
                convenio.setEstado(ufConvenio.getSelectionModel().getSelectedItem());
            }                                    
            convenio.setTelefone1(telCom1Convenio.getText());
            convenio.setTelefone2(telCom2Convenio.getText());
            convenio.setTelefone3(telCom3Convenio.getText());
            convenio.setFax(faxConvenio.getText());
            convenio.setEmail(emailConvenio.getText());
            convenio.setWww(wwwConvenio.getText());

            BigDecimal valorCons = new BigDecimal(valorConsulta.getText().replaceAll("\\.","").replace(",","."));
            convenio.setValorConsulta(valorCons);

            BigDecimal valECG = new BigDecimal(valorECG.getText().replaceAll("\\.","").replace(",","."));
            convenio.setValorECG(valECG);

            BigDecimal valInternacao = new BigDecimal(valorInternacao.getText().replaceAll("\\.","").replace(",","."));
            convenio.setValorInternacao(valInternacao);

            convenio.setInicioEntrega(diaIniEntrega.getText());
            convenio.setFimEntrega(diaFimEntrega.getText());
            resultado = Boolean.TRUE;
        } catch (CampoNuloException | CampoEmBrancoException cne) {
            ShowDialog("EX", cne.getMessage(), null);
        }
        return resultado;

    }
        
    
    public void btnProcNomeFired(ActionEvent event) throws Exception {
        Convenios convenios = new Convenios();
        sopConvenios.setAll(FXCollections.observableArrayList(Convenios.getObsListaWithNome(nomeConvenio.getText())));
    }   
    
    public void apagaConvenio() {
        codANS.clear();
        nomeConvenio.clear();
        cnpjConvenio.clear();
        enderConvenio.clear();
        numEndConvenio.clear();
        compEndConvenio.clear();
        bairroConvenio.clear();
        cepConvenio.clear();
        cidadeConvenio.clear();
        ufConvenio.getSelectionModel().clearSelection();
        telCom1Convenio.clear();
        telCom2Convenio.clear();
        telCom3Convenio.clear();
        faxConvenio.clear();
        emailConvenio.clear();
        wwwConvenio.clear();
        valorConsulta.clear();
        valorECG.clear();
        valorInternacao.clear();
        diaIniEntrega.clear();
        diaFimEntrega.clear();
    }
    
    public void habilEdicaoFired() {
        codANS.setEditable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        nomeConvenio.setEditable(((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING))|(status==StatusBtn.IDLE));
        cnpjConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        enderConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        numEndConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        compEndConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        bairroConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        cepConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        cidadeConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
//        ufConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telCom1Convenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telCom2Convenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telCom3Convenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        faxConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        emailConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        wwwConvenio.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        valorConsulta.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        valorECG.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        valorInternacao.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        diaIniEntrega.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        diaFimEntrega.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
    }
    
    private void setButtons() {
        btnCriarConvenio.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualConvenio.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnDelConvenio.setDisable(status!=StatusBtn.SHOWING);
        btnConfConvenio.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancConvenio.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnProcNome.setDisable(status!=StatusBtn.IDLE);
        btnSairConvenio.setDisable((status!=StatusBtn.IDLE)&(status!=StatusBtn.SHOWING));
    }
}