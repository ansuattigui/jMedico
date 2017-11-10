package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Medicamento;
import com.br.ralfh.medico.modelos.MedicamentoAux;
import com.br.ralfh.medico.modelos.Medicamentos;
import com.br.ralfh.medico.modelos.Posologias;
import com.br.ralfh.medico.modelos.Prescricao;
import com.br.ralfh.medico.modelos.PrescricaoExterno;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PrescricaoExternoController extends Controller {

    /**
     * Initializes the controller class.
     */
        
    @FXML TableView<MedicamentoAux> tabelaMedicamentos;
    @FXML TableColumn<MedicamentoAux,String> colunaMedicamento;
    @FXML TableView<MedicamentoAux> tabelaPrincipios;
    @FXML TableColumn<MedicamentoAux,String> colunaPrincipio;
    
    @FXML TextField editMedicamento;    
    @FXML ListView<String> listaPosologias;    
    @FXML TextField editPosologia;
    @FXML TextField editViaAdmin;
    @FXML TextField editQuantidade;    
    
    @FXML RadioButton rbUsoInterno;
    @FXML RadioButton rbUsoExterno;
    @FXML RadioButton rbUsoTopico;
    @FXML RadioButton rbUsoViaOral;
    @FXML RadioButton rbUsoInjetavel;
    @FXML RadioButton rbUsoOutros;
    @FXML ToggleGroup tgViaAdmin;
    
    @FXML TabPane tPaneMedicamento;
    @FXML Tab tabPrincipio;
    @FXML Tab tabMedicamento;
    
    @FXML Button btnConfirma;
    @FXML Button btnCancela;
    @FXML Button btnApagaMedicamento;
//    @FXML Button btnApagaChaveQuantidade;

    @FXML ComboBox<String> cbGrupo;
    
    private PrescricaoExterno prescricao;
    private List<PrescricaoExterno> prescricoes;
    private ObservableList<MedicamentoAux> masterMedicamentos = FXCollections.observableArrayList();
    private ObservableList<MedicamentoAux> masterPrincipios = FXCollections.observableArrayList();
    private String oper;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oper = "";
        setPrescricoes(new ArrayList<>());
        initTabelas();
        initComboGrupo(); 
        initTGroup();
        initListeners();
        setToolTips();        
        initMedicamentos();        		        
    }  
    
    private void initMedicamentos() {
        masterMedicamentos.clear();
        masterPrincipios.clear();
        ArrayList<Medicamento> medicamentos = Medicamentos.getLista();
        for (Medicamento item:medicamentos) {
            masterMedicamentos.add(new MedicamentoAux(item.getNomecomercial(), item.getPrincipio()));
            masterPrincipios.add(new MedicamentoAux(item.getNomecomercial(), item.getPrincipio()));
        }
    }

    private void initMedicamentos(Grupo grupo) {
        masterMedicamentos.clear();
        masterPrincipios.clear();
        ArrayList<Medicamento> medicamentos = Medicamentos.getLista(grupo);
        for (Medicamento item:medicamentos) {
            masterMedicamentos.add(new MedicamentoAux(item.getNomecomercial(), item.getPrincipio()));
            masterPrincipios.add(new MedicamentoAux(item.getNomecomercial(), item.getPrincipio()));
        }
    }
    
    public void initExame(PrescricaoExterno prescricao) {
        oper = "EDIT";
        this.prescricao = prescricao;
        editMedicamento.setText(prescricao.getMedicamento());                
        editPosologia.setText(prescricao.getPosologia());                
        editQuantidade.setText(prescricao.getQuantidade());                
        for(Toggle t :tgViaAdmin.getToggles()) {
            if (t.getUserData().equals(prescricao.getViaAdmin())) {
                t.setSelected(true);
                if (t.getUserData().equals("Outros")) {
                    editViaAdmin.setText(prescricao.getViaAdmin());
                }
            }
        }        
    }
    
    
    public void initListeners() {
        addTGViaAdminListener();
        addTabelaPrincipioListener();
        addTabelaMedicamentosListener();
        addListaPosologiasListener();
    }
    
    public void addTGViaAdminListener() {
        tgViaAdmin.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) {
                    if(!"Outros".equals(((RadioButton) tgViaAdmin.getSelectedToggle()).getText())){
                        editViaAdmin.setText(((RadioButton) tgViaAdmin.getSelectedToggle()).getText());
                    }
            }
        });
    }
    
    public void addListaPosologiasListener() {
        listaPosologias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editPosologia.setText(listaPosologias.getSelectionModel().getSelectedItem());
            }
        });
    }
    
    
    private void initTabelas() {
        initTabelaMedicamentos();
        initTabelaPrincipios();
        initListaPosologias();
    }
    
    private void initTGroup() {
        rbUsoInterno.setUserData("Uso interno");
        rbUsoExterno.setUserData("Uso externo");
        rbUsoTopico.setUserData("Uso tópico");
        rbUsoViaOral.setUserData("Uso via oral");
        rbUsoInjetavel.setUserData("Uso injetável");
        rbUsoOutros.setUserData("Outros");
    }
    
    private void setToolTips() {
        btnApagaMedicamento.setTooltip(new Tooltip("Apaga a caixa medicamento"));

        /*        btnSalvaReceita.setTooltip(new Tooltip("Salvar receita do paciente"));
        btnExcluiReceita.setTooltip(new Tooltip("Excluir receita selecionada"));
        btnPrintReceita.setTooltip(new Tooltip("Imprime a recceita selecionada"));       
        btnInsMedicamento.setTooltip(new Tooltip("Incluir este medicamento na receita"));        
        btnApagaChaveMedicamento.setTooltip(new Tooltip("Apaga a caixa medicamento"));
        btnApagaChavePosologia.setTooltip(new Tooltip("Apaga a caixa modo de uso"));
        btnApagaChaveViaAdmin.setTooltip(new Tooltip("Apaga a caixa aplicação"));
        btnApagaChaveQuantidade.setTooltip(new Tooltip("Apaga a caixa quantidade "));
        
        cbGrupo.setTooltip(new Tooltip("Filtrar medicamentos e modo de uso por grupo"));
*/        
    }
    
    @FXML
    public void actionConfirmar(ActionEvent ae) {       
        if (!"EDIT".equals(oper)) {
            prescricao = new PrescricaoExterno();
        } 
        
        if (PreenchePrescricao()) {
            prescricoes.add(prescricao);
            editMedicamento.clear();
            editPosologia.clear();
            editQuantidade.clear();
            ShowDialog("S", "Medicamento prescrito com sucesso", null,this.getStage());        
        } 
    }
    
    public void actionCancelar(ActionEvent ae) {       
        //prescricao = null;
        this.getStage().close();
    }
    
    public boolean PreenchePrescricao() {
        Boolean resultado = Boolean.FALSE;
        try {            
            getPrescricao().setMedicamento(editMedicamento.getText());
            getPrescricao().setPosologia(editPosologia.getText());
            getPrescricao().setQuantidade(editQuantidade.getText());
            getPrescricao().setViaAdmin(editViaAdmin.getText());
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ex) {
            ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }

    private void initTabelaMedicamentos() {
        colunaMedicamento.setCellValueFactory(new PropertyValueFactory<>("nomeComercial"));        
        tabelaMedicamentos.setItems(masterMedicamentos);        
    }
    public void addTabelaMedicamentosListener() {
        tabelaMedicamentos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editMedicamento.setText(tabelaMedicamentos.getSelectionModel().getSelectedItem().getNomeComercial());
            }
        });
    }    

    private void initTabelaPrincipios() {
        colunaPrincipio.setCellValueFactory(new PropertyValueFactory<>("principioAtivo"));
        tabelaPrincipios.setItems(masterPrincipios);        
    }
    public void addTabelaPrincipioListener() {
        tabelaPrincipios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editMedicamento.setText(tabelaPrincipios.getSelectionModel().getSelectedItem().getPrincipioAtivo());
            }
        });
    }
    
    
    private void initListaPosologias() {        
        Posologias posologias = new Posologias();
        listaPosologias.setItems(posologias.getObsSLista());
    }

    private void initComboGrupo(){
        Grupos grupos = new Grupos();
        if (!cbGrupo.getItems().isEmpty()) {
            cbGrupo.getItems().clear();
        }
        cbGrupo.setItems(grupos.getObsSLista());  
        AddListenerToComboGrupo();
    }
    
    public void AddListenerToComboGrupo() {
        cbGrupo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {                
                Integer index = cbGrupo.getSelectionModel().getSelectedIndex();
                if (index > 0) {
                    Grupo grupo = Grupos.getGrupoPeloNome(cbGrupo.getSelectionModel().selectedItemProperty().get());
                    initMedicamentos(grupo);
                } else {
                    initMedicamentos();
                }                
            }        
        });
    }    

    public void btnApagaMedicamentoFired(ActionEvent event) {
        editMedicamento.clear();
    }

    public void btnApagaChaveModoUsoClicked(ActionEvent event) {
        editPosologia.clear();
    }

    public void btnApagaChaveAplicacaoClicked(ActionEvent event) {
        editViaAdmin.clear();
    }    
    
    public void btnApagaChaveQuantidadeClicked(ActionEvent event) {
        editQuantidade.clear();
    }    

    /**
     * @return the prescricao
     */
    public PrescricaoExterno getPrescricao() {
        return prescricao;
    }

    /**
     * @return the prescricoes
     */
    public List<PrescricaoExterno> getPrescricoes() {
        return prescricoes;
    }

    /**
     * @param prescricoes the prescricoes to set
     */
    public void setPrescricoes(List<PrescricaoExterno> prescricoes) {
        this.prescricoes = prescricoes;
    }
    
    @Override
    public void addStageCloseListener() {        
        getController().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                btnConfirma.fire();
            }
        });
    }
    
}