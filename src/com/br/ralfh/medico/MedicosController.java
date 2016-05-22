package com.br.ralfh.medico;

import com.br.ralfh.medico.dlg.AlertDlgController;
import com.br.ralfh.medico.modelos.Medico;
import com.br.ralfh.medico.modelos.Medicos;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class MedicosController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private Medico medico;
    private StatusBtn status;
    private final SimpleObjectProperty<Medico> sopMedico;
    private final ObservableList<Medico> sopMedicos;    
    
    private Boolean atendeSeg;
    private Boolean atendeTer;
    private Boolean atendeQua;
    private Boolean atendeQui;
    private Boolean atendeSex;
    private Boolean atendeSab;
    private Boolean atendeDom;
        
    @FXML Button btnCriarMedico;
    @FXML Button btnAtualMedico;
    @FXML Button btnDelMedico;
    @FXML Button btnConfMedico;
    @FXML Button btnCancMedico;    
    @FXML Button btnSairMedico;
    
    @FXML TextField nomeCompleto;  
    @FXML TextField nomeMedico;  
    @FXML TextField identidade;
    @FXML TextField cpf;

    @FXML DatePicker nascPaciente;

    @FXML TextField endereco;
    @FXML TextField numEndereco;
    @FXML TextField compEndereco;
    @FXML TextField bairro;
    @FXML TextField cep;
    @FXML TextField cidade;
    @FXML ComboBox uf;
    @FXML TextField telefone1;
    @FXML TextField telefone2;
    @FXML TextField email;
    
    @FXML ToggleGroup tgAtivo;
    @FXML RadioButton rbAtivo;
    @FXML RadioButton rbInativo;
    
    @FXML CheckBox cbAtendeSeg;
    @FXML CheckBox cbAtendeTer;
    @FXML CheckBox cbAtendeQua;
    @FXML CheckBox cbAtendeQui;
    @FXML CheckBox cbAtendeSex;
    @FXML CheckBox cbAtendeSab;
    @FXML CheckBox cbAtendeDom;
    
    @FXML Slider slidInicioSeg;
    @FXML TextField inicioSeg;
    @FXML Slider slidInicioTer;
    @FXML TextField inicioTer;
    @FXML Slider slidInicioQua;
    @FXML TextField inicioQua;
    @FXML Slider slidInicioQui;
    @FXML TextField inicioQui;
    @FXML Slider slidInicioSex;
    @FXML TextField inicioSex;
    @FXML Slider slidInicioSab;
    @FXML TextField inicioSab;
    @FXML Slider slidInicioDom;
    @FXML TextField inicioDom;

    @FXML Slider slidFimSeg;  
    @FXML TextField fimSeg;
    @FXML Slider slidFimTer;
    @FXML TextField fimTer;
    @FXML Slider slidFimQua;
    @FXML TextField fimQua;
    @FXML Slider slidFimQui;
    @FXML TextField fimQui;
    @FXML Slider slidFimSex;
    @FXML TextField fimSex;
    @FXML Slider slidFimSab;
    @FXML TextField fimSab;
    @FXML Slider slidFimDom;
    @FXML TextField fimDom;
    
    @FXML Slider slidIntSeg;  
    @FXML TextField interSeg;
    @FXML Slider slidIntTer;
    @FXML TextField interTer;
    @FXML Slider slidIntQua;
    @FXML TextField interQua;
    @FXML Slider slidIntQui;
    @FXML TextField interQui;
    @FXML Slider slidIntSex;
    @FXML TextField interSex;
    @FXML Slider slidIntSab;
    @FXML TextField interSab;
    @FXML Slider slidIntDom;
    @FXML TextField interDom;

    @FXML Slider slidLimConsDia;
    @FXML TextField limiteConsDia;
    @FXML Slider slidLimPrimVezDia;
    @FXML TextField limitePrimVezDia;
    @FXML Slider slidLimEmergDia;
    @FXML TextField limiteEmergDia;

    
    private final SimpleObjectProperty<String> sopSlider;
    
    public MedicosController() {
        this.status = StatusBtn.IDLE;
        this.sopMedicos = FXCollections.observableArrayList();
        this.sopMedico = new SimpleObjectProperty<>(); 
        
        this.sopSlider = new SimpleObjectProperty<>();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtons();
        habilEdicaoFired();
        initCombos();
        initGrupoAtivo();
        addMedicoListener();
        addMedicosListener();
        preparaSliders();
    } 
        
    private void preparaSliders() {        
        addListenerSlider(slidInicioSeg, inicioSeg);
        addListenerSlider(slidFimSeg, fimSeg);
        addListenerSlider(slidIntSeg, interSeg);
        addListenerSlider(slidInicioTer, inicioTer);
        addListenerSlider(slidFimTer, fimTer);
        addListenerSlider(slidIntTer, interTer);
        addListenerSlider(slidInicioQua, inicioQua);
        addListenerSlider(slidFimQua, fimQua);
        addListenerSlider(slidIntQua, interQua);
        addListenerSlider(slidInicioQui, inicioQui);
        addListenerSlider(slidFimQui, fimQui);
        addListenerSlider(slidIntQui, interQui);
        addListenerSlider(slidInicioSex, inicioSex);
        addListenerSlider(slidFimSex, fimSex);
        addListenerSlider(slidIntSex, interSex);
        addListenerSlider(slidInicioSab, inicioSab);
        addListenerSlider(slidFimSab, fimSab);
        addListenerSlider(slidIntSab, interSab);
        addListenerSlider(slidInicioDom, inicioDom);
        addListenerSlider(slidFimDom, fimDom);
        addListenerSlider(slidIntDom, interDom);
        addListenerSlider(slidLimConsDia, limiteConsDia);
        addListenerSlider(slidLimPrimVezDia, limitePrimVezDia);
        addListenerSlider(slidLimEmergDia, limiteEmergDia);
        
    }

    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Fechando formulário");
          }
    });     
    }
    
    private void addMedicoListener() { 
        sopMedico.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopMedico.get() != null) {
                mostraMedico();
                status = StatusBtn.SHOWING;
                setButtons();
                habilEdicaoFired();
            }
        }
    });                
    }
        
    private void addMedicosListener() { 
        sopMedicos.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                apagaMedico();
                if (sopMedicos.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecMedico.fxml";
                        String titleGUI = "Selecionar Medico";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecMedico = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle);
                        selecMedico.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecMedicoController controller = (SelecMedicoController) selecMedico.getController();
                        controller.setMedico(sopMedicos);
                        selecMedico.showAndWait();
                        if (controller.closeModal) {
                            medico = controller.tabelaMedicos.getSelectionModel().getSelectedItem();
                            sopMedico.set(medico);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopMedicos.size() == 1) {
                    medico = sopMedicos.iterator().next();
                    sopMedico.set(medico);
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
        uf.getItems().addAll(options);        
    }
    
    private void initGrupoAtivo() {
        this.rbAtivo.setUserData(Boolean.TRUE);
        this.rbInativo.setUserData(Boolean.FALSE);
        this.tgAtivo = new ToggleGroup();
        this.tgAtivo.getToggles().add(rbAtivo);
        this.tgAtivo.getToggles().add(rbInativo);
    }
        
    public void criaMedicoFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        apagaMedico();
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaMedicoFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnDelMedicoFired(ActionEvent event) {
        String fxmlGUI = "dlg/AlertDlg.fxml";
        String fxmlTitle = "JHTC - Cadastro de Medicos";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        try {             
            GUIFactory dlg = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle);
            ((AlertDlgController) dlg.getController()).configProperties(TipoDialogo.EXCLUSÃO);
            
//           xxxxxxxxx ver retorno de boolean atribuindo tela a variável.
            
            
            dlg.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(MedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public void confMedicoFired(ActionEvent event) {      
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        if (status==StatusBtn.UPDATING) {
            preencheMedico();
            manager.merge(this.medico);
        } else if (status==StatusBtn.INSERTING) {
            this.medico = new Medico();
            preencheMedico();        
            manager.persist(this.medico);        
        }
        manager.getTransaction().commit();
        manager.close();
        
        status = StatusBtn.IDLE;
        setButtons();      
        habilEdicaoFired();
    }
    
    public void cancMedicoFired(ActionEvent event) {
        try {
            if (medico.getId() == -1) {
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
        apagaMedico();        
        sopMedicos.setAll(FXCollections.observableArrayList(Medicos.getObsLista()));

/*        status = StatusBtn.IDLE;
        setButtons();
        habilEdicaoFired();
*/
    }
    

    public void sairMedicoFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    public void addListenerSlider(Slider slid, TextField tf) {
        slid.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (!Objects.isNull(newVal)) {
                String sValor;
                NumberFormat formatter = new DecimalFormat("00"); 
                if (tf.getId().contains("inter")) {  
                    Integer a = (int) Math.round(slid.getValue());
                    sValor = formatter.format(a);
                } else if (tf.getId().contains("limite")) {
                    Integer a = (int) Math.round(slid.getValue());
                    sValor = String.valueOf(a);
                } else {                
                    Integer a = (int) Math.round(slid.getValue());
                    Integer b = Math.floorDiv(a*5,60);
                    Integer c = Math.floorMod(a*5,60);
                    sValor = formatter.format(b)+":"+formatter.format(c);
                }
                tf.setText(sValor);
            } else {
                tf.setText("00:00");
            }
            
        }
    });
    }

    
    private void mostraMedico() {       
        nomeMedico.setText(medico.getNome());
        nomeCompleto.setText(medico.getNomeCompleto());
        identidade.setText(medico.getIdentidade()); 
        cpf.setText(medico.getCpf());
        endereco.setText(medico.getEndereco());
        numEndereco.setText(medico.getNumero());
        compEndereco.setText(medico.getComplemento());
        bairro.setText(medico.getBairro());
        cep.setText(medico.getCep());
        cidade.setText(medico.getCidade());
        try {
            uf.getSelectionModel().select(medico.getUf());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        telefone1.setText(medico.getTelefone1());
        telefone2.setText(medico.getTelefone2());
        email.setText(medico.getEmail());
        
        int totAT = this.tgAtivo.getToggles().size();
        for(int i=0;i<totAT;i++) {
            Boolean temp = (Boolean) this.tgAtivo.getToggles().get(i).getUserData();
            if (temp.equals(medico.getAtivo())) {
                this.tgAtivo.getToggles().get(i).setSelected(true);
                break;
            }  
        }  
        
        getAtende(medico.getAtende());
        cbAtendeSeg.setSelected(atendeSeg);
        cbAtendeTer.setSelected(atendeTer);
        cbAtendeQua.setSelected(atendeQua);
        cbAtendeQui.setSelected(atendeQui);
        cbAtendeSex.setSelected(atendeSex);
        cbAtendeSab.setSelected(atendeSab);
        cbAtendeDom.setSelected(atendeDom);
        
        slidInicioSeg.setValue(medico.getSegIniI());
        slidFimSeg.setValue(medico.getSegFimI());
        slidIntSeg.setValue(medico.getSegInt());
        slidInicioTer.setValue(medico.getTerIniI());
        slidFimTer.setValue(medico.getTerFimI());
        slidIntTer.setValue(medico.getTerInt());
        slidInicioQua.setValue(medico.getQuaIniI());
        slidFimQua.setValue(medico.getQuaFimI());
        slidIntQua.setValue(medico.getQuaInt());
        slidInicioQui.setValue(medico.getQuiIniI());
        slidFimQui.setValue(medico.getQuiFimI());
        slidIntQui.setValue(medico.getQuiInt());
        slidInicioSex.setValue(medico.getSexIniI());
        slidFimSex.setValue(medico.getSexFimI());
        slidIntSex.setValue(medico.getSexInt());
        slidInicioSab.setValue(medico.getSabIniI());
        slidFimSab.setValue(medico.getSabFimI());
        slidIntSab.setValue(medico.getSabInt());
        slidInicioDom.setValue(medico.getDomIniI());
        slidFimDom.setValue(medico.getDomFimI());
        slidIntDom.setValue(medico.getDomInt());
        slidLimConsDia.setValue(medico.getLimDiaConsultas());
        slidLimPrimVezDia.setValue(medico.getLimDiaPrimvez());
        slidLimEmergDia.setValue(medico.getLimDiaEmergencias());        
    }

    private void preencheMedico() {    
        medico.setNome(nomeMedico.getText());
        medico.setNomeCompleto(nomeCompleto.getText()); 
        medico.setIdentidade(identidade.getText());
        medico.setCpf(cpf.getText());
        medico.setEndereco(endereco.getText());
        medico.setNumero(numEndereco.getText());
        medico.setComplemento(compEndereco.getText());
        medico.setBairro(bairro.getText());
        medico.setCep(cep.getText());
        medico.setCidade(cidade.getText());
        medico.setUf(uf.getSelectionModel().getSelectedItem().toString());
        medico.setTelefone1(telefone1.getText());
        medico.setTelefone2(telefone2.getText());
        medico.setEmail(email.getText());
        try {
            Boolean at = (Boolean) tgAtivo.getSelectedToggle().getUserData();
            medico.setAtivo(at);
        } catch (NullPointerException ex) {
            medico.setAtivo(Boolean.FALSE);
        }
        
        atendeSeg=cbAtendeSeg.isSelected();
        medico.setSegIni(inicioSeg.getText());
        medico.setSegFim(fimSeg.getText());
        try {
            medico.setSegInt(Integer.parseInt(interSeg.getText()));
        } catch (NumberFormatException ne) {
            medico.setSegInt(0);
        }
        
        atendeTer=cbAtendeTer.isSelected();
        medico.setTerIni(inicioTer.getText());
        medico.setTerFim(fimTer.getText());
        try {
            medico.setTerInt(Integer.parseInt(interTer.getText()));
        } catch (NumberFormatException ne) {
            medico.setTerInt(0);
        }

        atendeQua=cbAtendeQua.isSelected();
        medico.setQuaIni(inicioQua.getText());
        medico.setQuaFim(fimQua.getText());
        try {
            medico.setQuaInt(Integer.parseInt(interQua.getText()));
        } catch (NumberFormatException ne) {
            medico.setQuaInt(0);
        }
        
        atendeQui=cbAtendeQui.isSelected();
        medico.setQuiIni(inicioQui.getText());
        medico.setQuiFim(fimQui.getText());
        try {
            medico.setQuiInt(Integer.parseInt(interQui.getText()));
        } catch (NumberFormatException ne) {
            medico.setQuiInt(0);
        }

        atendeSex=cbAtendeSex.isSelected();
        medico.setSexIni(inicioSex.getText());
        medico.setSexFim(fimSex.getText());
        try {
            medico.setSexInt(Integer.parseInt(interSex.getText()));
        } catch (NumberFormatException ne) {
            medico.setSexInt(0);
        }

        atendeSab=cbAtendeSab.isSelected();
        medico.setSabIni(inicioSab.getText());
        medico.setSabFim(fimSab.getText());
        try {
            medico.setSabInt(Integer.parseInt(interSab.getText()));
        } catch (NumberFormatException ne) {
            medico.setSabInt(0);
        }

        atendeDom=cbAtendeDom.isSelected();
        medico.setDomIni(inicioDom.getText());
        medico.setDomFim(fimDom.getText());
        try {
            medico.setDomInt(Integer.parseInt(interDom.getText()));
        } catch (NumberFormatException ne) {
            medico.setDomInt(0);
        }
        
        setAtende();
        
        medico.setLimDiaConsultas(Integer.parseInt(limiteConsDia.getText()));
        medico.setLimDiaPrimvez(Integer.parseInt(limitePrimVezDia.getText()));
        medico.setLimDiaEmergencias(Integer.parseInt(limiteEmergDia.getText()));        
    }
        
    
    public void apagaMedico() {
        nomeMedico.clear();
        nomeCompleto.clear();
        identidade.clear();
        cpf.clear();
        endereco.clear();
        numEndereco.clear();
        compEndereco.clear();
        bairro.clear();
        cep.clear();
        cidade.clear();
        uf.getSelectionModel().clearSelection();
        telefone1.clear();
        telefone2.clear();
        email.clear();
        
        cbAtendeSeg.setSelected(Boolean.FALSE);
        cbAtendeTer.setSelected(Boolean.FALSE);
        cbAtendeQua.setSelected(Boolean.FALSE);
        cbAtendeQui.setSelected(Boolean.FALSE);
        cbAtendeSex.setSelected(Boolean.FALSE);
        cbAtendeSab.setSelected(Boolean.FALSE);
        cbAtendeDom.setSelected(Boolean.FALSE);
        
        slidInicioSeg.setValue(0);
        slidFimSeg.setValue(0);
        slidIntSeg.setValue(0);
        slidInicioTer.setValue(0);
        slidFimTer.setValue(0);
        slidIntTer.setValue(0);
        slidInicioQua.setValue(0);
        slidFimQua.setValue(0);
        slidIntQua.setValue(0);
        slidInicioQui.setValue(0);
        slidFimQui.setValue(0);
        slidIntQui.setValue(0);
        slidInicioSex.setValue(0);
        slidFimSex.setValue(0);
        slidIntSex.setValue(0);
        slidInicioSab.setValue(0);
        slidFimSab.setValue(0);
        slidIntSab.setValue(0);
        slidInicioDom.setValue(0);
        slidFimDom.setValue(0);
        slidIntDom.setValue(0);
        slidLimConsDia.setValue(0);
        slidLimPrimVezDia.setValue(0);
        slidLimEmergDia.setValue(0);
    }
    
    public void habilEdicaoFired() {
        nomeCompleto.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        identidade.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        cpf.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        endereco.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        numEndereco.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        compEndereco.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        bairro.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        cep.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        cidade.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        uf.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telefone1.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telefone2.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        email.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        
        rbAtivo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        rbInativo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        
    }
    
    private void setButtons() {
        btnCriarMedico.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualMedico.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnDelMedico.setDisable(status!=StatusBtn.SHOWING);
        btnConfMedico.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancMedico.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnSairMedico.setDisable((status!=StatusBtn.IDLE)&(status!=StatusBtn.SHOWING));
    }
    
    public void getAtende(String atende) {
        atendeSeg=("0".equals(atende.substring(0, 1))?Boolean.FALSE:Boolean.TRUE);
        atendeTer=("0".equals(atende.substring(1, 2))?Boolean.FALSE:Boolean.TRUE);
        atendeQua=("0".equals(atende.substring(2, 3))?Boolean.FALSE:Boolean.TRUE);
        atendeQui=("0".equals(atende.substring(3, 4))?Boolean.FALSE:Boolean.TRUE);
        atendeSex=("0".equals(atende.substring(4, 5))?Boolean.FALSE:Boolean.TRUE);
        atendeSab=("0".equals(atende.substring(5, 6))?Boolean.FALSE:Boolean.TRUE);
        atendeDom=("0".equals(atende.substring(6, 7))?Boolean.FALSE:Boolean.TRUE);
    }
    
     public void setAtende() {
       String seg = (Objects.equals(atendeSeg, Boolean.FALSE)?"0":"1");
       String ter = (Objects.equals(atendeTer, Boolean.FALSE)?"0":"1");
       String qua = (Objects.equals(atendeQua, Boolean.FALSE)?"0":"1");
       String qui = (Objects.equals(atendeQui, Boolean.FALSE)?"0":"1");
       String sex = (Objects.equals(atendeSex, Boolean.FALSE)?"0":"1");
       String sab = (Objects.equals(atendeSab, Boolean.FALSE)?"0":"1");
       String dom = (Objects.equals(atendeDom, Boolean.FALSE)?"0":"1");       
       medico.setAtende(seg.concat(ter).concat(qua).concat(qui).concat(sex).concat(sab).concat(dom));             
    }
   
    
}