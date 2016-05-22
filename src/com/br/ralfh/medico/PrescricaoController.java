package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Medicamentos;
import com.br.ralfh.medico.modelos.Posologias;
import com.br.ralfh.medico.modelos.Prescricao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PrescricaoController extends Controller {

    /**
     * Initializes the controller class.
     */
        
    @FXML ListView<String> listaPrincipio;    
    @FXML ListView<String> listaNomeComercial;    
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
    
    @FXML Button btnConfirma;
    @FXML Button btnCancela;
//    @FXML Button btnApagaChavePosologia;
//    @FXML Button btnApagaChaveViaAdmin;
//    @FXML Button btnApagaChaveQuantidade;

    @FXML ComboBox<String> cbGrupo;
    
    private Prescricao prescricao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelas();
        initComboGrupo(); 
        initTGroup();
        initListeners();
        setToolTips();
    }  
    
    public void initListeners() {
        addReceitaListener();
        addTGViaAdminListener();
        addListaPrincipioListener();
        addListaNomeComercialListener();
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

    public void addListaPrincipioListener() {
        listaPrincipio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editMedicamento.setText(listaPrincipio.getSelectionModel().getSelectedItem());
            }
        });
    }
    public void addListaNomeComercialListener() {
        listaNomeComercial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editMedicamento.setText(listaNomeComercial.getSelectionModel().getSelectedItem());
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
        initListaPosologas();
        initListaPrincipio();
        initListaNomeComercial();
    }
    
    private void initTGroup() {
        rbUsoInterno.setUserData("Uso Interno");
        rbUsoExterno.setUserData("Uso Externo");
        rbUsoTopico.setUserData("Uso Topico");
        rbUsoViaOral.setUserData("Uso Via Oral");
        rbUsoInjetavel.setUserData("Uso Injetavel");
        rbUsoOutros.setUserData("Uso Outros");
    }
    
    private void setToolTips() {
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

    public void actionConfirmar(ActionEvent ae) {       
        prescricao = new Prescricao();
        if (PreenchePrescricao()) {
            this.getStage().close();
        }
    }
    
    public void actionCancelar(ActionEvent ae) {       
        prescricao = null;
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
            ShowDialog("EX", ex.getMessage(), null);
        }
        return resultado;
    }
    private void addReceitaListener() { 
        
/*        
        sopReceita.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                initReceita();
                configBotoes((sopReceita.get().getId() == null));                        
            }
        });
*/        
    }       
    
    private void addPrescricoesListener() { 
/*        
        olPrescricoes.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!olPrescricoes.isEmpty()) {
                    tablePrescricoes.setItems(null);
                    tablePrescricoes.setItems(olPrescricoes);
                }
            }
        });
*/        
    }
       
    public void addChaveMedicamentoKeyEvent(KeyEvent event) { 
/*        
        if (!chaveMedicamento.getText().isEmpty() && chaveMedicamento.getText() != null) {
            try {
                for (String medicamento : listviewMedicamentos.getItems()) {
                    if (medicamento.startsWith(chaveMedicamento.getText())) {
                        listviewMedicamentos.getSelectionModel().select(medicamento);
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
*/        
    }

    public void listviewMedicamentoClicked(MouseEvent event) { 
/*        
        
        if (listviewMedicamentos.getSelectionModel().getSelectedIndex() >= 0) {
            chaveMedicamento.setText(listviewMedicamentos.getSelectionModel().getSelectedItem());
        }
*/        
    }        
    
    public void addChaveModoDeUsoKeyEvent(KeyEvent event) { 
/*        
        if (!chavePosologia.getText().isEmpty() && chavePosologia.getText() != null) {
            try {
                for (String modo : listviewPosologia.getItems()) {
                    if (modo.startsWith(chavePosologia.getText())) {
                        listviewPosologia.getSelectionModel().select(modo);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
*/        
    }    

    public void initListaPosologas() {        
        Posologias posologias = new Posologias();
        listaPosologias.setItems(posologias.getObsSLista());
    }

    public void initListaPrincipio() {        
        listaPrincipio.setItems(Medicamentos.getObsSListaPrincipio());
    }
    public void initListaPrincipio(Grupo grupo) {        
        listaPrincipio.setItems(Medicamentos.getObsSListaPrincipio(grupo));
    }

    public void initListaNomeComercial() {        
        listaNomeComercial.setItems(Medicamentos.getObsSListaNomeComercial());
    }
    public void initListaNomeComercial(Grupo grupo) {        
        listaNomeComercial.setItems(Medicamentos.getObsSListaNomeComercial(grupo));
    }
    
    public void initComboGrupo(){
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
                    initListaPrincipio(grupo);
                    initListaNomeComercial(grupo);
                } else {
                    initListaPrincipio();
                    initListaNomeComercial();
                }                
            }        
        });
    }    
       
    public void btnApagaChaveMedicamentoClicked(ActionEvent event) {
        if (editMedicamento.getText() != null) {
            editMedicamento.clear();
        }
    }


    public void btnApagaChaveModoUsoClicked(ActionEvent event) {
        if (editPosologia.getText() != null) {
            editPosologia.clear();
        }
    }

    public void btnApagaChaveAplicacaoClicked(ActionEvent event) {
        if (editViaAdmin.getText() != null) {
            editViaAdmin.clear();
        }
    }    
    
    public void btnApagaChaveQuantidadeClicked(ActionEvent event) {
        if (editQuantidade.getText() != null) {
            editQuantidade.clear();
        }
    }    

    /**
     * @return the prescricao
     */
    public Prescricao getPrescricao() {
        return prescricao;
    }
}