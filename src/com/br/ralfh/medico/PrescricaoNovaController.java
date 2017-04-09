package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Medicamento;
import com.br.ralfh.medico.modelos.Medicamentos;
import com.br.ralfh.medico.modelos.MedicamentoAux;
import com.br.ralfh.medico.modelos.Posologias;
import com.br.ralfh.medico.modelos.Prescricao;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class PrescricaoNovaController extends Controller {

    /**
     * Initializes the controller class.
     */
        
    @FXML TableView<MedicamentoAux> tabelaMedicamentos;
    @FXML TableColumn<MedicamentoAux,String> colunaMedicamento;
    @FXML TableView<MedicamentoAux> tabelaPrincipios;
    @FXML TableColumn<MedicamentoAux,String> colunaPrincipio;
    
    @FXML TextField editMedicamento;    
    @FXML TextField editPrincipio;    
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
    @FXML Button btnApagaPrincipio;
    @FXML Button btnApagaMedicamento;
//    @FXML Button btnApagaChaveQuantidade;

    @FXML ComboBox<String> cbGrupo;
    
    private Prescricao prescricao;
    private ObservableList<MedicamentoAux> masterMedicamentos = FXCollections.observableArrayList();
    private ObservableList<MedicamentoAux> masterPrincipios = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        rbUsoInterno.setUserData("Uso Interno");
        rbUsoExterno.setUserData("Uso Externo");
        rbUsoTopico.setUserData("Uso Topico");
        rbUsoViaOral.setUserData("Uso Via Oral");
        rbUsoInjetavel.setUserData("Uso Injetavel");
        rbUsoOutros.setUserData("Uso Outros");
    }
    
    private void setToolTips() {
        btnApagaPrincipio.setTooltip(new Tooltip("Apaga a caixa princípio ativo"));
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
            if (tPaneMedicamento.getSelectionModel().getSelectedIndex()==0) {
                getPrescricao().setMedicamento(editPrincipio.getText());
            } else {
                getPrescricao().setMedicamento(editMedicamento.getText());
            }
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
        colunaMedicamento.setCellValueFactory(cellData -> cellData.getValue().nomeComercialProperty());        
        FilteredList<MedicamentoAux> filteredMedicamento = new FilteredList<>(masterMedicamentos, p -> true);        
        editMedicamento.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMedicamento.setPredicate(medicamentoAux -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                        return true;
                }				
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (medicamentoAux.getNomeComercial().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<MedicamentoAux> sortedMedicamento = new SortedList<>(filteredMedicamento);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedMedicamento.comparatorProperty().bind(tabelaMedicamentos.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tabelaMedicamentos.setItems(sortedMedicamento);        
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
        colunaPrincipio.setCellValueFactory(cellData -> cellData.getValue().principioAtivoProperty());
        FilteredList<MedicamentoAux> filteredPrincipio = new FilteredList<>(masterPrincipios, p -> true);        
        editPrincipio.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPrincipio.setPredicate(medicamentoAux -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                        return true;
                }				
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (medicamentoAux.getPrincipioAtivo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<MedicamentoAux> sortedPrincipio = new SortedList<>(filteredPrincipio);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedPrincipio.comparatorProperty().bind(tabelaMedicamentos.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tabelaPrincipios.setItems(sortedPrincipio);        
    }
    public void addTabelaPrincipioListener() {
        tabelaPrincipios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editPrincipio.setText(tabelaPrincipios.getSelectionModel().getSelectedItem().getPrincipioAtivo());
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
       
    public void btnApagaPrincipioFired(ActionEvent event) {
        editPrincipio.clear();
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
    public Prescricao getPrescricao() {
        return prescricao;
    }
}