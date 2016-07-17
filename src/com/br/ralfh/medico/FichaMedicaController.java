package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.FormatoNumericoInvalidoException;
import com.br.ralfh.medico.modelos.ConsultaSubs;
import com.br.ralfh.medico.modelos.FichaMedica;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.PrimeiraConsulta;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import jidefx.scene.control.field.DateField;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class FichaMedicaController extends Controller {

    /**
     * Initializes the controller class.
     */

    private StatusBtn status;
    private Paciente paciente;
    private FichaMedica fichamedica;
    private PrimeiraConsulta primeiraconsulta;
    private final SimpleObjectProperty<PrimeiraConsulta> sopPrimeiraConsulta;
    private ConsultaSubs consubs;
    private List<ConsultaSubs> listaConsubs;
    private final ObservableList<ConsultaSubs> olConsultasSubs;
    private FichaMedica.Prontuario prontuario;
    private SimpleObjectProperty<FichaMedica.Prontuario> olProntuario;
    
    @FXML TabPane tbPaneConsultas;  @FXML Tab tabPrimCons;
    @FXML Tab tabConsSubs;          @FXML ToolBar tbConsultasSubs;
    @FXML TabPane tpConsultaDoDia;
    @FXML Tab tabPrimeiraCons;    @FXML Tab tabCons;
    
    
    @FXML Button btnNovaConsSubs;           @FXML Button btnAtualConsSubs;
    @FXML Button btnDelConsSubs;            //@FXML MenuItem miNovaConsulta;
    //@FXML MenuItem miAtualizaConsulta;      //@FXML MenuItem miExcluiConsulta;
    //@FXML MenuItem miPrimeiraConsulta;      
    @FXML Button btnGrafPA;
    @FXML Button btnGrafFC;                 @FXML Button btnAtualPrimCons;
    @FXML Button btnConfFichaMedica;        @FXML Button btnCancFichaMedica;    
    @FXML Button btnSairFichaMedica;        @FXML Button btnFichaMedica;
    @FXML Button btnProcCod;                @FXML Button btnProcCodAnt;
    @FXML Button btnProcNome;
    
//////////////////////////////////////////////////////////////////////////////////////    
    //@FXML DatePicker dataPrimeiraCons;       
    @FXML DateField dataPrimConsulta;
    @FXML TextArea qpPrimeiraCons;           @FXML TextArea hdaPrimeiraCons;
    @FXML TextArea ctPrimeiraCons;           @FXML CheckBox hasPrimeiraCons;
    @FXML CheckBox hashfPrimeiraCons;        @FXML CheckBox dmPrimeiraCons;
    @FXML CheckBox dmhfPrimeiraCons;         @FXML CheckBox dislipPrimeiraCons;
    @FXML CheckBox disliphfPrimeiraCons;     @FXML CheckBox avePrimeiraCons;
    @FXML CheckBox avehfPrimeiraCons;        @FXML CheckBox iamPrimeiraCons;
    @FXML CheckBox iamhfPrimeiraCons;        @FXML CheckBox dpulmPrimeiraCons;
    @FXML CheckBox dpulmhfPrimeiraCons;      @FXML TextField dpulmtxtPrimeiraCons;    
    @FXML CheckBox vpiPrimeiraCons;          @FXML CheckBox hepatPrimeiraCons;
    @FXML CheckBox malarPrimeiraCons;        @FXML CheckBox bkPrimeiraCons;
    @FXML CheckBox dstPrimeiraCons;          @FXML CheckBox infurPrimeiraCons;
    @FXML CheckBox constipPrimeiraCons;      @FXML TextField constiptxtPrimeiraCons;        
    @FXML CheckBox alergPrimeiraCons;        @FXML TextField alergtxtPrimeiraCons;        
    @FXML CheckBox cirurgPrimeiraCons;       @FXML TextField cirurgtxtPrimeiraCons;        
    @FXML CheckBox stressPrimeiraCons;       @FXML CheckBox sedentPrimeiraCons;
    @FXML CheckBox insonPrimeiraCons;        @FXML CheckBox tabagPrimeiraCons;
    @FXML CheckBox etilisPrimeiraCons;       @FXML TextField etilistxtPrimeiraCons;        
    @FXML CheckBox medicamPrimeiraCons;      @FXML TextField medicamtxtPrimeiraCons;        
    @FXML CheckBox menarcPrimeiraCons;       @FXML TextField menarcAnosPrimeiraCons;        
    @FXML CheckBox gestacPrimeiraCons;       @FXML TextField gestacNumPrimeiraCons;        
    @FXML CheckBox menopPrimeiraCons;        @FXML TextField menopAnosPrimeiraCons;        
    @FXML TextArea outinfoPrimeiraCons;      @FXML TextField alturaPrimeiraCons;        
    @FXML TextField pesoPrimeiraCons;        @FXML TextField ectosPrimeiraCons;        
    @FXML TextField pescoPrimeiraCons;       @FXML TextField jugulPrimeiraCons;        
    @FXML TextField tireoPrimeiraCons;       @FXML TextField ganglPrimeiraCons;     
    @FXML ComboBox<String> comboapcircPrimeiraCons;    
//    @FXML TextField apcircPrimeiraCons;    
    @FXML TextField psistoPrimeiraCons;      @FXML TextField pdiastPrimeiraCons;    
    @FXML TextField frcardPrimeiraCons;      @FXML TextField pulsoPrimeiraCons;
    @FXML TextField aprespPrimeiraCons;      @FXML TextField frrespPrimeiraCons;
    @FXML TextField abdoPrimeiraCons;        @FXML TextField msuperPrimeiraCons;
    @FXML TextField minferPrimeiraCons;      @FXML TextField sisnerPrimeiraCons;
    
    @FXML TextField colestTotPrimeiraCons; @FXML TextField colestHDLPrimeiraCons;
    @FXML TextField colestLDLPrimeiraCons; @FXML TextField triglicPrimeiraCons;
    @FXML TextField acUriPrimeiraCons;     @FXML TextField hemoGlicPrimeiraCons;
    @FXML TextField tirT3PrimeiraCons;     @FXML TextField tirT4PrimeiraCons;
    @FXML TextField tirT4LivPrimeiraCons;  @FXML TextField tirTSHPrimeiraCons;
    @FXML TextField psaLivPrimeiraCons;    @FXML TextField psaTotPrimeiraCons;
    @FXML TextField psaRelPrimeiraCons;    @FXML TextField hepTGOPrimeiraCons;
    @FXML TextField hepTGPPrimeiraCons;    @FXML TextField hepFAPrimeiraCons;
    @FXML TextField hepGGTPrimeiraCons;    @FXML TextArea urinaPrimeiraCons;
    @FXML TextArea fezesPrimeiraCons;      @FXML RadioButton optNegFezesPrimeiraCons;
    @FXML RadioButton optOutFezesPrimeiraCons;  @FXML RadioButton optNormECGPrimeiraCons;
    @FXML RadioButton optOutECGPrimeiraCons;    @FXML TextArea ecgPrimeiraCons;
    @FXML RadioButton optNormPAPPrimeiraCons;   @FXML RadioButton optOutPAPPrimeiraCons;
    @FXML TextArea papPrimeiraCons;             @FXML TextArea rxAbdomPrimeiraCons;
    @FXML TextArea rxOutrPrimeiraCons;          @FXML TextArea outUSPrimeiraCons;
    @FXML TextArea tcPrimeiraCons;
    
    @FXML VBox datasConsultas;
    @FXML DateField dataConsSubs; 
    @FXML TextArea qpConsSubs;      @FXML TextArea exafisConsSubs;
    @FXML TextArea conterConsSubs;  @FXML TextField apcircConsSubs;
    @FXML TextField psistoConsSubs; @FXML TextField pdiastConsSubs;    
    @FXML TextField frcardConsSubs; @FXML TextField pulsoConsSubs;
    @FXML TextField aprespConsSubs; @FXML TextField abdoConsSubs;
    @FXML TextField outrosConsSubs;
    
    @FXML TextField colestTotConsSubs;        @FXML TextField colestHDLConsSubs;
    @FXML TextField colestLDLConsSubs;        @FXML TextField triglicConsSubs;
    @FXML TextField acUriConsSubs;            @FXML TextField hemoGlicConsSubs;
    @FXML TextField tirT3ConsSubs;            @FXML TextField tirT4ConsSubs;
    @FXML TextField tirT4LivConsSubs;         @FXML TextField tirTSHConsSubs;
    @FXML TextField psaLivConsSubs;           @FXML TextField psaTotConsSubs;
    @FXML TextField psaRelConsSubs;           @FXML TextField hepTGOConsSubs;
    @FXML TextField hepTGPConsSubs;           @FXML TextField hepFAConsSubs;
    @FXML TextField hepGGTConsSubs;           @FXML TextArea urinaConsSubs;
    @FXML TextArea fezesConsSubs;             @FXML RadioButton optNegFezesConsSubs;
    @FXML RadioButton optOutFezesConsSubs;    @FXML RadioButton optNormECGConsSubs;
    @FXML RadioButton optOutECGConsSubs;      @FXML TextArea ecgConsSubs;
    @FXML RadioButton optNormPAPConsSubs;     @FXML RadioButton optOutPAPConsSubs;
    @FXML TextArea papConsSubs;               @FXML TextArea rxAbdomConsSubs;
    @FXML TextArea rxOutrConsSubs;            @FXML TextArea outUSConsSubs;
    @FXML TextArea tcConsSubs;
    
//////////////////////////////////////////////////////////////////////////
    
    @FXML TextArea qpProntuario;
    @FXML TextArea exafisProntuario;
    @FXML TextArea conterProntuario;

////////////////////////////////////////////////////////////////////////////  
    
    public FichaMedicaController() {
        this.status = StatusBtn.IDLE;
        this.sopPrimeiraConsulta = new SimpleObjectProperty<>();
        this.olConsultasSubs = FXCollections.observableArrayList();
        this.listaConsubs = new LinkedList<>();
        this.olProntuario = new SimpleObjectProperty<>();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        initComboAparCircPrCons();
        addFreqCardPrimConsListener();
        addFreqCardConsSubsListener();
        addTabConsultasChangeListener();
        addPrimeiraConsultaListener();
        addConsultasSubsListener();
        addProntuarioListener();
        initForm();
    }
    
    @Override
    public void addStageCloseListener() {        
        getController().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              btnConfFichaMedica.fire();
          }
        });
    }

    @FXML
    public void addCheckButtonsListener(ActionEvent ae) {        
        CheckBox cb = (CheckBox) ae.getSource();        
        switch (cb.getId()) {            
            case "dpulmhfPrimeiraCons": dpulmtxtPrimeiraCons.requestFocus();break;
            case "constipPrimeiraCons": constiptxtPrimeiraCons.requestFocus();break;
            case "alergPrimeiraCons": alergtxtPrimeiraCons.requestFocus();break;
            case "cirurgPrimeiraCons": cirurgtxtPrimeiraCons.requestFocus();break;
            case "etilisPrimeiraCons": etilistxtPrimeiraCons.requestFocus();break;
            case "medicamPrimeiraCons": medicamtxtPrimeiraCons.requestFocus();break;
            case "menarcPrimeiraCons": menarcAnosPrimeiraCons.requestFocus();break;
            case "gestacPrimeiraCons": gestacNumPrimeiraCons.requestFocus();break;
            case "menopPrimeiraCons": menopAnosPrimeiraCons.requestFocus();break;
        }
    }
    
    private void initDatasCS() {
        datasConsultas.getChildren().clear();
        for(ConsultaSubs cs : listaConsubs) {
            Button bt = new Button();
            bt.setMinWidth(140);
            bt.setMinHeight(32);
            bt.setStyle("botaotool");
            bt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    consubs = new ConsultaSubs();
                    consubs = (ConsultaSubs)((Button) event.getSource()).getUserData();
                    mostraConsultaSubs(consubs);     
                }
            });                        
            bt.setText(Util.formataData(cs.getId().getDataCS()));
            bt.setUserData(cs);            
            datasConsultas.getChildren().add(bt);
        }                
    }
    
    
    private void initComboAparCircPrCons() {
        comboapcircPrimeiraCons.getItems().clear();
        comboapcircPrimeiraCons.getItems().add("RCI");
        comboapcircPrimeiraCons.getItems().add("RCR, 2T");
    }
        
    private void addFreqCardPrimConsListener() {
        frcardPrimeiraCons.textProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (newVal!=null) {
                pulsoPrimeiraCons.setText(newVal.toString());
            } else {
                pulsoPrimeiraCons.setText(null);
            }
            
        }
    });
    }

    private void addFreqCardConsSubsListener() {
        frcardConsSubs.textProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (newVal!=null) {
                pulsoConsSubs.setText(newVal.toString());
            } else {
                pulsoConsSubs.setText(null);
            }
            
        }
    });
    }    
    
    private void addTabConsultasChangeListener() {        
        tbPaneConsultas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
            {
                @Override
                public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab mostRecentlySelectedTab)
                {
                    setButtons();
                }
            });        
    }
    
    public void setPaciente(Paciente pac) {
        this.paciente = pac;
        this.primeiraconsulta = FichaMedica.getPrimeiraConsulta(pac);
        this.sopPrimeiraConsulta.set(this.primeiraconsulta);
        this.listaConsubs.addAll(FichaMedica.getConsultasSubs(pac));
        this.olConsultasSubs.setAll(listaConsubs);
        this.prontuario = FichaMedica.getProntuario(paciente);
        this.olProntuario.set(prontuario);
    }
    
    private void addPrimeiraConsultaListener() { 
        sopPrimeiraConsulta.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopPrimeiraConsulta.get() != null) {
                mostraPrimeiraConsulta();
                status = StatusBtn.IDLE;
                setButtons();
                habilEdicaoPrimConsFired();                        
            }
        }
    });                        
    }

    private void addProntuarioListener() { 
        olProntuario.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (olProntuario.get() != null) {
                qpProntuario.setText(prontuario.getQp());
                exafisProntuario.setText(prontuario.getEf());
                conterProntuario.setText(prontuario.getCo()); 
            }
        }
    });                        
    }    
    
    private void addConsultasSubsListener() { 
        olConsultasSubs.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                if (olConsultasSubs.size() > 0) {
                    prontuario = FichaMedica.getProntuario(paciente);
                    olProntuario.set(prontuario);
                    initDatasCS();
                }
            }
        });           
    }  
    
    public void btnGrafFired(ActionEvent ae) throws IOException {        
        String fxmlGUI = "fxml/GraficoPABarra.fxml";
        String titleGUI = paciente.getNome();
        StageStyle fxmlStyle = StageStyle.UTILITY;
        GUIFactory graficopa = new GUIFactory(fxmlGUI, titleGUI,fxmlStyle);
        GraficoPABarraController controller = (GraficoPABarraController) graficopa.getController();        
        
        Button btn = (Button) ae.getSource();
        controller.setTipoGrafico(btn.getId());
        
        controller.initGrafico(paciente);        
        graficopa.showAndWait();                
    }
    
    public void atualizaPrimConsFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoPrimConsFired();
    }

    public void novaConsSubsFired(ActionEvent event) {
        status = StatusBtn.INSERTCONSSUB;
        setButtons();
        apagaConsultaSubs();
        habilEdicaoConsSubs();
    }
    
    
    public void atualConsSubsFired(ActionEvent event) {
        try {
            if (consubs.getId().getPaciente()!=null) {
                status = StatusBtn.UPDATECONSSUB;
                setButtons();
                habilEdicaoConsSubs();
            }
        } catch (NullPointerException ex) {
            ShowDialog("EX", "Selecione uma consulta", null);
        }
    }
    
    public void delConsSubsFired(ActionEvent event) {
        if (ExcluiRegistroDlg("ECS", "", null)) {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            if (FichaMedica.excluiConsultaSubs(manager,consubs)) {
                manager.getTransaction().commit();
                Controller.ShowDialog("ATBS", "Registro excluído com sucesso!", null);
            } else {
                manager.getTransaction().rollback();
                Controller.ShowDialog("ATBS", "Não foi possível excluir o registro selecionado!", null);
            }
            manager.close();
            apagaConsultaSubs();
            listaConsubs = FichaMedica.getConsultasSubs(paciente);
            olConsultasSubs.setAll(listaConsubs);
        }
    }
    
    public void confFichaMedicaFired(ActionEvent event) {    
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        if (null != status) switch (status) {
            case UPDATING:
                try {
                    setPrimeiraConsulta();
                } catch (Exception ex) {
                    Controller.ShowDialog("EX", ex.getMessage(), null);
                    return;
                }   
                if (FichaMedica.salvaPrimeiraConsulta(manager,primeiraconsulta)) {                
                    manager.getTransaction().commit();
                    Controller.ShowDialog("ATBS", "Registro atualizado com sucesso!", null);
                    status = StatusBtn.IDLE;
                } else {
                    manager.getTransaction().rollback();
                    Controller.ShowDialog("ATBS", "Não foi possivel atualizar o registro!", null);
                }   break;
            case UPDATECONSSUB:
                try {
                    setConsultaSubs();
                } catch (FormatoNumericoInvalidoException ex) {
                    Controller.ShowDialog("EX", ex.getMessage(), null);
                    return;
                }   if (FichaMedica.salvaConsultaSubs(manager, consubs)) {  
                    Controller.ShowDialog("ATBS", "Registro atualizado com sucesso!", null);
                    status = StatusBtn.IDLE;
                    manager.getTransaction().commit();
                } else {
                    manager.getTransaction().rollback();
                    Controller.ShowDialog("ATBS", "Não foi possivel atualizar o registro!", null);
                }   olConsultasSubs.setAll(FichaMedica.getConsultasSubs(paciente));
                break;
            case INSERTCONSSUB:
                consubs = new ConsultaSubs();
                setConsultaSubs();
                if (FichaMedica.novaConsultaSubs(manager, consubs)) {
                    try {
                        manager.getTransaction().commit();
                    } catch (PersistenceException ex) {
                        Controller.ShowDialog("EX", "Já existe consulta com esta data", null);
                        break;
                    }
                    Controller.ShowDialog("ATBS", "Registro incluido com sucesso!", null);
                    status = StatusBtn.IDLE;
                } else {
                    manager.getTransaction().rollback();
                    Controller.ShowDialog("ATBS", "Não foi possivel atualizar o registro!", null);
                }   
                listaConsubs = FichaMedica.getConsultasSubs(paciente);
                olConsultasSubs.setAll(listaConsubs);
                break;
            default:
                break;
        }
        manager.close();
        setButtons();      
        habilEdicaoPrimConsFired();
        habilEdicaoConsSubs();
    }
    
    public void cancFichaMedFired(ActionEvent event) {
        if (status==StatusBtn.UPDATING) {
            mostraPrimeiraConsulta();
        } else if (status == StatusBtn.UPDATECONSSUB) {
            mostraConsultaSubs(consubs);
        }
        status = StatusBtn.IDLE;
        setButtons();      
        habilEdicaoPrimConsFired();
    }    
    
    public void sairFichaMedicaFired(ActionEvent event) {
        getController().getStage().close();
    }    

        
    private void mostraPrimeiraConsulta() {   
        dataPrimConsulta.setValue(primeiraconsulta.getData());
        qpPrimeiraCons.setText(primeiraconsulta.getQp());
        hdaPrimeiraCons.setText(primeiraconsulta.getHda());
        ctPrimeiraCons.setText(primeiraconsulta.getCondterap());
        hasPrimeiraCons.setSelected(primeiraconsulta.getHas());
        hashfPrimeiraCons.setSelected(primeiraconsulta.getHashf());
        dmPrimeiraCons.setSelected(primeiraconsulta.getDm());
        dmhfPrimeiraCons.setSelected(primeiraconsulta.getDmhf());
        dislipPrimeiraCons.setSelected(primeiraconsulta.getDislipidemia());
        disliphfPrimeiraCons.setSelected(primeiraconsulta.getDislipidemiahf());
        avePrimeiraCons.setSelected(primeiraconsulta.getAve());
        avehfPrimeiraCons.setSelected(primeiraconsulta.getAvehf());
        iamPrimeiraCons.setSelected(primeiraconsulta.getIam());
        iamhfPrimeiraCons.setSelected(primeiraconsulta.getIamhf());
        dpulmPrimeiraCons.setSelected(primeiraconsulta.getDoencpulmon());
        dpulmhfPrimeiraCons.setSelected(primeiraconsulta.getDoencpulmonhf());
        dpulmtxtPrimeiraCons.setText(primeiraconsulta.getReldoencaspulmon());
        vpiPrimeiraCons.setSelected(primeiraconsulta.getVpi());
        hepatPrimeiraCons.setSelected(primeiraconsulta.getHepatite());
        malarPrimeiraCons.setSelected(primeiraconsulta.getMalaria());
        bkPrimeiraCons.setSelected(primeiraconsulta.getBk());
        dstPrimeiraCons.setSelected(primeiraconsulta.getDst());
        infurPrimeiraCons.setSelected(primeiraconsulta.getInfecurina());
        constipPrimeiraCons.setSelected(primeiraconsulta.getConstipacao());
        constiptxtPrimeiraCons.setText(primeiraconsulta.getConstipacaoobs());
        alergPrimeiraCons.setSelected(primeiraconsulta.getAlergia());
        alergtxtPrimeiraCons.setText(primeiraconsulta.getRelalergias());
        cirurgPrimeiraCons.setSelected(primeiraconsulta.getCirurgia());
        cirurgtxtPrimeiraCons.setText(primeiraconsulta.getRelcirurgias());
        stressPrimeiraCons.setSelected(primeiraconsulta.getStress());
        sedentPrimeiraCons.setSelected(primeiraconsulta.getSedentarismo());
        insonPrimeiraCons.setSelected(primeiraconsulta.getInsonia());
        tabagPrimeiraCons.setSelected(primeiraconsulta.getTabagismo());
        etilisPrimeiraCons.setSelected(primeiraconsulta.getEtilismo());
        etilistxtPrimeiraCons.setText(primeiraconsulta.getEtilismotipo());
        medicamPrimeiraCons.setSelected(primeiraconsulta.getMedicamentos());
        medicamtxtPrimeiraCons.setText(primeiraconsulta.getRelmedicamentos());
        menarcPrimeiraCons.setSelected(primeiraconsulta.getMenarca());
        menarcAnosPrimeiraCons.setText(String.valueOf(primeiraconsulta.getMenarcaidade()));
        gestacPrimeiraCons.setSelected(primeiraconsulta.getGestacoes());
        gestacNumPrimeiraCons.setText(String.valueOf(primeiraconsulta.getGestanumeros()));
        menopPrimeiraCons.setSelected(primeiraconsulta.getMenopausa());
        menopAnosPrimeiraCons.setText(String.valueOf(primeiraconsulta.getMenopidade()));
        outinfoPrimeiraCons.setText(primeiraconsulta.getOutros());        
        alturaPrimeiraCons.setText(String.valueOf(primeiraconsulta.getAltura()));
        pesoPrimeiraCons.setText(String.valueOf(primeiraconsulta.getPeso()));
        ectosPrimeiraCons.setText(primeiraconsulta.getEctoscopia());
        pescoPrimeiraCons.setText(primeiraconsulta.getPescoco());
        jugulPrimeiraCons.setText(primeiraconsulta.getJugulares());
        tireoPrimeiraCons.setText(primeiraconsulta.getTireoide());
        ganglPrimeiraCons.setText(primeiraconsulta.getGanglios());        
        comboapcircPrimeiraCons.getEditor().setText(primeiraconsulta.getAparcircul());        
        //apcircPrimeiraCons.setText(primeiraconsulta.getAparcircul());
        psistoPrimeiraCons.setText(String.valueOf(primeiraconsulta.getPa_sist()));
        pdiastPrimeiraCons.setText(String.valueOf(primeiraconsulta.getPa_diast()));
        frcardPrimeiraCons.setText(String.valueOf(primeiraconsulta.getFreqcard()));
        pulsoPrimeiraCons.setText(String.valueOf(primeiraconsulta.getPulso()));
        aprespPrimeiraCons.setText(primeiraconsulta.getApar_resp());
        frrespPrimeiraCons.setText(String.valueOf(primeiraconsulta.getFreq_resp()));
        abdoPrimeiraCons.setText(primeiraconsulta.getAbdome());
        msuperPrimeiraCons.setText(primeiraconsulta.getMembsup());
        minferPrimeiraCons.setText(primeiraconsulta.getMembinf());
        sisnerPrimeiraCons.setText(primeiraconsulta.getSistnerv());      
        
        colestTotPrimeiraCons.setText(primeiraconsulta.getColestTotPrimCons());
        colestHDLPrimeiraCons.setText(primeiraconsulta.getColestHDLPrimCons());
        colestLDLPrimeiraCons.setText(primeiraconsulta.getColestLDLPrimCons());
        triglicPrimeiraCons.setText(primeiraconsulta.getTriglicPrimCons());
        acUriPrimeiraCons.setText(primeiraconsulta.getAcUriPrimCons());
        hemoGlicPrimeiraCons.setText(primeiraconsulta.getHemoGlicPrimCons());
        tirT3PrimeiraCons.setText(primeiraconsulta.getTirT3PrimCons());
        tirT4PrimeiraCons.setText(primeiraconsulta.getTirT4PrimCons());
        tirT4LivPrimeiraCons.setText(primeiraconsulta.getTirT4LivPrimCons());
        tirTSHPrimeiraCons.setText(primeiraconsulta.getTirTSHPrimCons());
        psaLivPrimeiraCons.setText(primeiraconsulta.getPsaLivPrimCons());
        psaTotPrimeiraCons.setText(primeiraconsulta.getPsaTotPrimCons());
        psaRelPrimeiraCons.setText(primeiraconsulta.getPsaRelPrimCons());
        hepTGOPrimeiraCons.setText(primeiraconsulta.getHepTGOPrimCons());
        hepTGPPrimeiraCons.setText(primeiraconsulta.getHepTGPPrimCons());
        hepFAPrimeiraCons.setText(primeiraconsulta.getHepFAPrimCons());
        hepGGTPrimeiraCons.setText(primeiraconsulta.getHepGGTPrimCons());
        urinaPrimeiraCons.setText(primeiraconsulta.getUrinaPrimCons());
        fezesPrimeiraCons.setText(primeiraconsulta.getFezesPrimCons());    
        optNegFezesPrimeiraCons.setSelected(primeiraconsulta.getOptNegFezesPrimCons());
        optOutFezesPrimeiraCons.setSelected(primeiraconsulta.getOptOutFezesPrimCons());
        optNormECGPrimeiraCons.setSelected(primeiraconsulta.getOptNormECGPrimCons());
        optOutECGPrimeiraCons.setSelected(primeiraconsulta.getOptOutECGPrimCons());
        ecgPrimeiraCons.setText(primeiraconsulta.getEcgPrimCons());
        optNormPAPPrimeiraCons.setSelected(primeiraconsulta.getOptNormPAPPrimCons());
        optOutPAPPrimeiraCons.setSelected(primeiraconsulta.getOptOutPAPPrimCons());
        papPrimeiraCons.setText(primeiraconsulta.getPapPrimCons());
        rxAbdomPrimeiraCons.setText(primeiraconsulta.getRxAbdomPrimCons());  
        rxOutrPrimeiraCons.setText(primeiraconsulta.getRxOutrPrimCons());
        outUSPrimeiraCons.setText(primeiraconsulta.getOutUSPrimCons());
        tcPrimeiraCons.setText(primeiraconsulta.getTcPrimCons());
    }
    
    private void setPrimeiraConsulta() throws Exception {
        primeiraconsulta.setData(dataPrimConsulta.getValue());
        primeiraconsulta.setQp(qpPrimeiraCons.getText());
        primeiraconsulta.setHda(hdaPrimeiraCons.getText());
        primeiraconsulta.setCondterap(ctPrimeiraCons.getText());
        primeiraconsulta.setHas(hasPrimeiraCons.isSelected());
        primeiraconsulta.setHashf(hashfPrimeiraCons.isSelected());        
        primeiraconsulta.setDm(dmPrimeiraCons.isSelected());
        primeiraconsulta.setDmhf(dmhfPrimeiraCons.isSelected());
        primeiraconsulta.setDislipidemia(dislipPrimeiraCons.isSelected());
        primeiraconsulta.setDislipidemiahf(disliphfPrimeiraCons.isSelected());        
        primeiraconsulta.setAve(avePrimeiraCons.isSelected());
        primeiraconsulta.setAvehf(avehfPrimeiraCons.isSelected());
        primeiraconsulta.setIam(iamPrimeiraCons.isSelected());
        primeiraconsulta.setIamhf(iamhfPrimeiraCons.isSelected());        
        primeiraconsulta.setDoencpulmon(dpulmPrimeiraCons.isSelected());
        primeiraconsulta.setDoencpulmonhf(dpulmhfPrimeiraCons.isSelected());        
        primeiraconsulta.setReldoencaspulmon(dpulmtxtPrimeiraCons.getText());
        primeiraconsulta.setVpi(vpiPrimeiraCons.isSelected());
        primeiraconsulta.setHepatite(hepatPrimeiraCons.isSelected());
        primeiraconsulta.setMalaria(malarPrimeiraCons.isSelected());
        primeiraconsulta.setBk(bkPrimeiraCons.isSelected());
        primeiraconsulta.setDst(dstPrimeiraCons.isSelected());        
        primeiraconsulta.setInfecurina(infurPrimeiraCons.isSelected());
        primeiraconsulta.setConstipacao(constipPrimeiraCons.isSelected());        
        primeiraconsulta.setConstipacaoobs(constiptxtPrimeiraCons.getText());        
        primeiraconsulta.setAlergia(alergPrimeiraCons.isSelected());
        primeiraconsulta.setRelalergias(alergtxtPrimeiraCons.getText());        
        primeiraconsulta.setCirurgia(cirurgPrimeiraCons.isSelected());
        primeiraconsulta.setRelcirurgias(cirurgtxtPrimeiraCons.getText());        
        primeiraconsulta.setStress(stressPrimeiraCons.isSelected());
        primeiraconsulta.setSedentarismo(sedentPrimeiraCons.isSelected());
        primeiraconsulta.setInsonia(insonPrimeiraCons.isSelected());
        primeiraconsulta.setTabagismo(tabagPrimeiraCons.isSelected());
        primeiraconsulta.setEtilismo(etilisPrimeiraCons.isSelected());        
        primeiraconsulta.setEtilismotipo(etilistxtPrimeiraCons.getText());        
        primeiraconsulta.setMedicamentos(medicamPrimeiraCons.isSelected());        
        primeiraconsulta.setRelmedicamentos(medicamtxtPrimeiraCons.getText());        
        primeiraconsulta.setMenarca(menarcPrimeiraCons.isSelected());   
        try {
            primeiraconsulta.setMenarcaidade(Integer.parseInt(menarcAnosPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setMenarcaidade(0);
        }      
        primeiraconsulta.setGestacoes(gestacPrimeiraCons.isSelected());        
        try {
            primeiraconsulta.setGestanumeros(Integer.parseInt(gestacNumPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setGestanumeros(0);
        }        
        primeiraconsulta.setMenopausa(menopPrimeiraCons.isSelected());
        try {
            primeiraconsulta.setMenopidade(Integer.parseInt(menopAnosPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setMenopidade(0);
        }
        primeiraconsulta.setOutros(outinfoPrimeiraCons.getText());     
        try {
        primeiraconsulta.setAltura(Integer.parseInt(alturaPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setAltura(0);
        }
        try {
        primeiraconsulta.setPeso(Integer.parseInt(pesoPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setPeso(0);
        }
        primeiraconsulta.setEctoscopia(ectosPrimeiraCons.getText());
        primeiraconsulta.setPescoco(pescoPrimeiraCons.getText());
        primeiraconsulta.setJugulares(jugulPrimeiraCons.getText());
        primeiraconsulta.setTireoide(tireoPrimeiraCons.getText());
        primeiraconsulta.setGanglios(ganglPrimeiraCons.getText());        
        primeiraconsulta.setAparcircul(comboapcircPrimeiraCons.getEditor().getText());
//        primeiraconsulta.setAparcircul(apcircPrimeiraCons.getText());
        try {
            primeiraconsulta.setPa_sist(Integer.parseInt(psistoPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setPa_sist(0);
        }
        try {
            primeiraconsulta.setPa_diast(Integer.parseInt(pdiastPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setPa_diast(0);
        }
        try {
            primeiraconsulta.setFreqcard(Integer.parseInt(frcardPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setFreqcard(0);
        } catch (NumberFormatException ex1) {
            throw new Exception("Verifique o campo Frequência Cardíaca");
        }
        try {
            primeiraconsulta.setPulso(Integer.parseInt(pulsoPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setPulso(0);
        }
        primeiraconsulta.setApar_resp(aprespPrimeiraCons.getText());
        try {
            primeiraconsulta.setFreq_resp(Integer.parseInt(frrespPrimeiraCons.getText()));
        } catch (NullPointerException ex) {
            primeiraconsulta.setFreq_resp(0);
        }
        primeiraconsulta.setAbdome(abdoPrimeiraCons.getText());
        primeiraconsulta.setMembsup(msuperPrimeiraCons.getText());
        primeiraconsulta.setMembinf(minferPrimeiraCons.getText());
        primeiraconsulta.setSistnerv(sisnerPrimeiraCons.getText());
        
        primeiraconsulta.setColestTotPrimCons(colestTotPrimeiraCons.getText());                    
        primeiraconsulta.setColestHDLPrimCons(colestHDLPrimeiraCons.getText());
        primeiraconsulta.setColestLDLPrimCons(colestLDLPrimeiraCons.getText());        
        primeiraconsulta.setTriglicPrimCons(triglicPrimeiraCons.getText());
        primeiraconsulta.setAcUriPrimCons(acUriPrimeiraCons.getText());
        primeiraconsulta.setHemoGlicPrimCons(hemoGlicPrimeiraCons.getText());
        primeiraconsulta.setTirT3PrimCons(tirT3PrimeiraCons.getText());
        primeiraconsulta.setTirT4PrimCons(tirT4PrimeiraCons.getText());
        primeiraconsulta.setTirT4LivPrimCons(tirT4LivPrimeiraCons.getText());
        primeiraconsulta.setTirTSHPrimCons(tirTSHPrimeiraCons.getText());
        primeiraconsulta.setPsaLivPrimCons(psaLivPrimeiraCons.getText());
        primeiraconsulta.setPsaTotPrimCons(psaTotPrimeiraCons.getText());
        primeiraconsulta.setPsaRelPrimCons(psaRelPrimeiraCons.getText());
        primeiraconsulta.setHepTGOPrimCons(hepTGOPrimeiraCons.getText());
        primeiraconsulta.setHepTGPPrimCons(hepTGPPrimeiraCons.getText());
        primeiraconsulta.setHepFAPrimCons(hepFAPrimeiraCons.getText());
        primeiraconsulta.setHepGGTPrimCons(hepGGTPrimeiraCons.getText());
        primeiraconsulta.setUrinaPrimCons(urinaPrimeiraCons.getText());
        primeiraconsulta.setFezesPrimCons(fezesPrimeiraCons.getText());    
        primeiraconsulta.setOptNegFezesPrimCons(optNegFezesPrimeiraCons.isSelected());
        primeiraconsulta.setOptOutFezesPrimCons(optOutFezesPrimeiraCons.isSelected());
        primeiraconsulta.setOptNormECGPrimCons(optNormECGPrimeiraCons.isSelected());
        primeiraconsulta.setOptOutECGPrimCons(optOutECGPrimeiraCons.isSelected());
        primeiraconsulta.setEcgPrimCons(ecgPrimeiraCons.getText());
        primeiraconsulta.setOptNormPAPPrimCons(optNormPAPPrimeiraCons.isSelected());
        primeiraconsulta.setOptOutPAPPrimCons(optOutPAPPrimeiraCons.isSelected());
        primeiraconsulta.setPapPrimCons(papPrimeiraCons.getText());
        primeiraconsulta.setRxAbdomPrimCons(rxAbdomPrimeiraCons.getText());
        primeiraconsulta.setTcPrimCons(tcPrimeiraCons.getText());
        primeiraconsulta.setRxOutrPrimCons(rxOutrPrimeiraCons.getText());
        primeiraconsulta.setOutUSPrimCons(outUSPrimeiraCons.getText());
    }
    
    private void mostraConsultaSubs(ConsultaSubs consulta) {    
        dataConsSubs.setValue(consulta.getId().getDataCS());
        qpConsSubs.setText(consulta.getQp());
        exafisConsSubs.setText(consulta.getExamefisico());
        conterConsSubs.setText(consulta.getCondterap());
        apcircConsSubs.setText(consulta.getAparcircul());
        psistoConsSubs.setText(String.valueOf(consulta.getPa_sist()));
        pdiastConsSubs.setText(String.valueOf(consulta.getPa_diast()));
        frcardConsSubs.setText(String.valueOf(consulta.getFreqcard()));
        pulsoConsSubs.setText(String.valueOf(consulta.getPulso()));
        aprespConsSubs.setText(consulta.getApar_resp());
        abdoConsSubs.setText(consulta.getAbdome());
        outrosConsSubs.setText(consulta.getOutros());
        
        colestTotConsSubs.setText(consulta.getColestTotCons());
        colestHDLConsSubs.setText(consulta.getColestHDLCons());
        colestLDLConsSubs.setText(consulta.getColestLDLCons());
        triglicConsSubs.setText(consulta.getTriglicCons());
        acUriConsSubs.setText(consulta.getAcUriCons());
        hemoGlicConsSubs.setText(consulta.getHemoGlicCons());
        tirT3ConsSubs.setText(consulta.getTirT3Cons());
        tirT4ConsSubs.setText(consulta.getTirT4Cons());
        tirT4LivConsSubs.setText(consulta.getTirT4LivCons());
        tirTSHConsSubs.setText(consulta.getTirTSHCons());
        psaLivConsSubs.setText(consulta.getPsaLivCons());
        psaTotConsSubs.setText(consulta.getPsaTotCons());
        psaRelConsSubs.setText(consulta.getPsaRelCons());
        hepTGOConsSubs.setText(consulta.getHepTGOCons());
        hepTGPConsSubs.setText(consulta.getHepTGPCons());
        hepFAConsSubs.setText(consulta.getHepFACons());
        hepGGTConsSubs.setText(consulta.getHepGGTCons());
        urinaConsSubs.setText(consulta.getUrinaCons());
        fezesConsSubs.setText(consulta.getFezesCons());    
        optNegFezesConsSubs.setSelected(consulta.getOptNegFezesCons());
        optOutFezesConsSubs.setSelected(consulta.getOptOutFezesCons());
        optNormECGConsSubs.setSelected(consulta.getOptNormECGCons());
        optOutECGConsSubs.setSelected(consulta.getOptOutECGCons());
        ecgConsSubs.setText(consulta.getEcgCons());
        optNormPAPConsSubs.setSelected(consulta.getOptNormPAPCons());
        optOutPAPConsSubs.setSelected(consulta.getOptOutPAPCons());
        papConsSubs.setText(consulta.getPapCons());
        rxAbdomConsSubs.setText(consulta.getRxAbdomCons());  
        rxOutrConsSubs.setText(consulta.getRxOutrCons());
        outUSConsSubs.setText(consulta.getOutUSCons());
        tcConsSubs.setText(consulta.getTcCons());
        
    }

    private void setConsultaSubs() throws FormatoNumericoInvalidoException { //  Boolean novaConsulta) {
        consubs.getId().setPaciente(this.paciente);
        consubs.getId().setDataCS(dataConsSubs.getValue());
        consubs.setQp(qpConsSubs.getText());
        consubs.setExamefisico(exafisConsSubs.getText());
        consubs.setCondterap(conterConsSubs.getText());
        consubs.setAparcircul(apcircConsSubs.getText());
        try {
            consubs.setPa_sist(Integer.parseInt(psistoConsSubs.getText()));
        } catch (NullPointerException | NumberFormatException ex) {
            consubs.setPa_sist(0);
        }
        try {
            consubs.setPa_diast(Integer.parseInt(pdiastConsSubs.getText()));
        } catch (NullPointerException | NumberFormatException ex) {
            consubs.setPa_diast(0);
        }
        try {
            consubs.setFreqcard(Integer.parseInt(frcardConsSubs.getText()));
        } catch (NullPointerException | NumberFormatException ex) {
            consubs.setFreqcard(0);
        }
        try {
            consubs.setPulso(Integer.parseInt(pulsoConsSubs.getText()));
        } catch (NullPointerException | NumberFormatException ex) {
            consubs.setPulso(0);
        }
        consubs.setApar_resp(aprespConsSubs.getText());
        consubs.setAbdome(abdoConsSubs.getText());
        consubs.setOutros(outrosConsSubs.getText());
        
        consubs.setColestTotCons(colestTotConsSubs.getText());                    
        consubs.setColestHDLCons(colestHDLConsSubs.getText());
        consubs.setColestLDLCons(colestLDLConsSubs.getText());        
        consubs.setTriglicCons(triglicConsSubs.getText());
        consubs.setAcUriCons(acUriConsSubs.getText());
        consubs.setHemoGlicCons(hemoGlicConsSubs.getText());
        consubs.setTirT3Cons(tirT3ConsSubs.getText());
        consubs.setTirT4Cons(tirT4ConsSubs.getText());
        consubs.setTirT4LivCons(tirT4LivConsSubs.getText());
        consubs.setTirTSHCons(tirTSHConsSubs.getText());
        consubs.setPsaLivCons(psaLivConsSubs.getText());
        consubs.setPsaTotCons(psaTotConsSubs.getText());
        consubs.setPsaRelCons(psaRelConsSubs.getText());
        consubs.setHepTGOCons(hepTGOConsSubs.getText());
        consubs.setHepTGPCons(hepTGPConsSubs.getText());
        consubs.setHepFACons(hepFAConsSubs.getText());
        consubs.setHepGGTCons(hepGGTConsSubs.getText());
        consubs.setUrinaCons(urinaConsSubs.getText());
        consubs.setFezesCons(fezesConsSubs.getText());    
        consubs.setOptNegFezesCons(optNegFezesConsSubs.isSelected());
        consubs.setOptOutFezesCons(optOutFezesConsSubs.isSelected());
        consubs.setOptNormECGCons(optNormECGConsSubs.isSelected());
        consubs.setOptOutECGCons(optOutECGConsSubs.isSelected());
        consubs.setEcgCons(ecgConsSubs.getText());
        consubs.setOptNormPAPCons(optNormPAPConsSubs.isSelected());
        consubs.setOptOutPAPCons(optOutPAPConsSubs.isSelected());
        consubs.setPapCons(papConsSubs.getText());
        consubs.setRxAbdomCons(rxAbdomConsSubs.getText());
        consubs.setTcCons(tcConsSubs.getText());
        consubs.setRxOutrCons(rxOutrConsSubs.getText());
        consubs.setOutUSCons(outUSConsSubs.getText());
        
    }
    
    public void apagaConsultaSubs() {
        dataConsSubs.setValue(Util.dHoje());
        qpConsSubs.clear();
        exafisConsSubs.clear();
        conterConsSubs.clear();
        apcircConsSubs.clear();
        psistoConsSubs.clear();
        pdiastConsSubs.clear();
        frcardConsSubs.clear();
        pulsoConsSubs.clear();
        aprespConsSubs.clear();
        abdoConsSubs.clear();
        outrosConsSubs.clear();
    }
    
    public void apagaProntuario() {
        qpProntuario.clear();
        exafisProntuario.clear();
        conterProntuario.clear();
    }
    
    
    private void habilEdicaoPrimConsFired() {        
        this.abdoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.alergPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.alergtxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.alturaPrimeiraCons.setEditable(status!=StatusBtn.IDLE);                
        this.comboapcircPrimeiraCons.getEditor().setEditable(status!=StatusBtn.IDLE);        
//        this.apcircPrimeiraCons.setEditable(status!=StatusBtn.IDLE);                
        this.aprespPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.avePrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.avehfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.bkPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.cirurgPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.cirurgtxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.constipPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.constiptxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.ctPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.dataPrimConsulta.setDisable(status==StatusBtn.IDLE); //  .setEditable(status!=StatusBtn.IDLE);
        this.dislipPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.disliphfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.dmPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.dmhfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.dpulmPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.dpulmhfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.dpulmtxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.dstPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.ectosPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.etilisPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.etilistxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.frcardPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.frrespPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.ganglPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.gestacNumPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.gestacPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.hasPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.hashfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.hdaPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.hepatPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.iamPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.iamhfPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.infurPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.insonPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.jugulPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.malarPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.medicamPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.medicamtxtPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.menarcAnosPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.menarcPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.menopAnosPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.menopPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.minferPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.msuperPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.outinfoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.pdiastPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.pescoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.pesoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.psistoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.pulsoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.qpPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.sedentPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.sisnerPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.stressPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.tabagPrimeiraCons.setDisable(status==StatusBtn.IDLE);
        this.tireoPrimeiraCons.setEditable(status!=StatusBtn.IDLE);
        this.vpiPrimeiraCons.setDisable(status==StatusBtn.IDLE);
    }
    
    private void habilEdicaoConsSubs() {
        dataConsSubs.setDisable(status==StatusBtn.IDLE);
        qpConsSubs.setEditable(status!=StatusBtn.IDLE);
        exafisConsSubs.setEditable(status!=StatusBtn.IDLE);
        conterConsSubs.setEditable(status!=StatusBtn.IDLE);
        apcircConsSubs.setEditable(status!=StatusBtn.IDLE);
        psistoConsSubs.setEditable(status!=StatusBtn.IDLE);
        pdiastConsSubs.setEditable(status!=StatusBtn.IDLE);
        frcardConsSubs.setEditable(status!=StatusBtn.IDLE);
        pulsoConsSubs.setEditable(status!=StatusBtn.IDLE);
        aprespConsSubs.setEditable(status!=StatusBtn.IDLE);
        abdoConsSubs.setEditable(status!=StatusBtn.IDLE);
        outrosConsSubs.setEditable(status!=StatusBtn.IDLE);
    }
    
    private void habilEdicaoProntuario() {
        qpProntuario.setEditable(Boolean.FALSE);
        exafisProntuario.setEditable(Boolean.FALSE);
        conterProntuario.setEditable(Boolean.FALSE);
    }
    
    private void initForm() {
        setButtons();
        habilEdicaoPrimConsFired();
        habilEdicaoConsSubs();
        habilEdicaoProntuario();        
    }
        
    private void setButtons() {        
//        miPrimeiraConsulta.setDisable((status==StatusBtn.UPDATING) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!= 0));        
        btnNovaConsSubs.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
//        miNovaConsulta.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
        btnAtualConsSubs.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
//        miAtualizaConsulta.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
        btnDelConsSubs.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
//        miExcluiConsulta.setDisable((status!=StatusBtn.IDLE) | (tbPaneConsultas.getSelectionModel().getSelectedIndex()!=1));
        btnConfFichaMedica.setDisable(status==StatusBtn.IDLE);
        btnCancFichaMedica.setDisable(status==StatusBtn.IDLE);             
        btnSairFichaMedica.setDisable(status!=StatusBtn.IDLE); 
    }
}
