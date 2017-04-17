package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Exame;
import com.br.ralfh.medico.modelos.Exames;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class NovoExameController extends Controller {

    /**
     * Initializes the controller class.
     */
        
    @FXML TableView<Exame> tabelaExames;
    @FXML TableColumn<Exame,String> colExame;
    @FXML TableColumn<Exame,String> colMaterial;
    
    @FXML TextArea editExame;    
    @FXML TextField editMaterial;    
    
    @FXML RadioButton rbFezes;
    @FXML RadioButton rbSangue;
    @FXML RadioButton rbUrina;
    @FXML RadioButton rbImagem;
    @FXML RadioButton rbOutros;
    @FXML ToggleGroup tgMaterial;
    
    @FXML Button btnConfirma;
    @FXML Button btnCancela;
    @FXML Button btnApagaExame;
    @FXML Button btnApagaMaterial;

    @FXML ComboBox<String> cbMaterial;
    
    private Exame exame;
    private List<Exame> exames;
    private ObservableList<Exame> masterExames = FXCollections.observableArrayList();
    private String oper;

    /**
     * @return the exame
     */
    public Exame getExame() {
        return exame;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oper = "";
        exames = new ArrayList<>();
        initTabelas();
        initComboMaterial(); 
        initTGroup();
        initListeners();
        setToolTips();        
        initExames();        		        
    }  
    
    private void initExames() {
        ArrayList<Exame> listaExames = Exames.getLista();
        masterExames.setAll(listaExames);
    }

    public void initExame(Exame exame) {
        oper = "EDIT";
        this.exame = exame;
        editExame.setText(exame.getExame());                
        for(Toggle t :tgMaterial.getToggles()) {
            if (t.getUserData().equals(exame.getMaterial())) {
                t.setSelected(true);
                if (t.getUserData().equals("Outros")) {
                    editMaterial.setText(exame.getMaterial());
                }
            }
        }        
    }    
    
    private void initExames(String material) {
        ArrayList<Exame> listaExames = Exames.getListaPorMaterial(material);
        masterExames.setAll(listaExames);
    }
    
    public void initListeners() {
        addTGMaterialListener();
        addTabelaExamesListener();
    }

    
    public void addTGMaterialListener() {
        tgMaterial.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) {
                    if(!"Outros".equals(((RadioButton) tgMaterial.getSelectedToggle()).getText())){
                        editMaterial.setText(((RadioButton) tgMaterial.getSelectedToggle()).getText());
                    }
            }
        });
    }
    
    private void initTabelas() {
        initTabelaExames();
    }
    
    private void initTGroup() {
        rbFezes.setUserData("Fezes");
        rbSangue.setUserData("Sangue");
        rbUrina.setUserData("Urina");
        rbImagem.setUserData("Imagem");
        rbOutros.setUserData("Outros");
    }
    
    private void setToolTips() {
        btnApagaExame.setTooltip(new Tooltip("Apaga a caixa exame"));
        btnApagaMaterial.setTooltip(new Tooltip("Apaga a caixa material"));
    }

    public void actionConfirmar(ActionEvent ae) {    
        if (!"EDIT".equals(oper)) {
            exame = new Exame();
        } 
        
        if (PreencheExame()) {
            exames.add(exame);
            editExame.clear();
            editMaterial.clear();
        }
    }
    
    public void actionCancelar(ActionEvent ae) {       
        exame = null;
        this.getStage().close();
    }
    
    public boolean PreencheExame() {
        Boolean resultado = Boolean.FALSE;
        try {            
            exame.setExame(editExame.getText());
            exame.setMaterial(editMaterial.getText());
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ex) {
            ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }
    
    private void initTabelaExames() {        
        colExame.setCellValueFactory(new PropertyValueFactory<>("exame"));
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        tabelaExames.setItems(masterExames);        
    }

    
    public void addTabelaExamesListener() {
        tabelaExames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                editExame.setText(tabelaExames.getSelectionModel().getSelectedItem().getExame());                
                for(Toggle t :tgMaterial.getToggles()) {
                    if (t.getUserData().equals(tabelaExames.getSelectionModel().getSelectedItem().getMaterial())) {
                        t.setSelected(true);
                        return;
                    }
                }
            }
        });
    }    
    
    private void initComboMaterial(){
        ArrayList<String> materiais = new ArrayList<>();
        if (!cbMaterial.getItems().isEmpty()) {
            cbMaterial.getItems().clear();
        }
        cbMaterial.getItems().add(0, "");
        cbMaterial.getItems().add(1, "Fezes");
        cbMaterial.getItems().add(2, "Sangue");
        cbMaterial.getItems().add(3, "Urina");
        cbMaterial.getItems().add(4, "Imagem");
        cbMaterial.getItems().add(5, "Outros");

        AddListenerToComboGrupo();
    }
    
    public void AddListenerToComboGrupo() {
        cbMaterial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {                
                Integer index = cbMaterial.getSelectionModel().getSelectedIndex();
                if (index > 0) {
                    String material = cbMaterial.getSelectionModel().selectedItemProperty().get();
                    initExames(material);
                } else {
                    initExames();
                }                
            }        
        });
    }    
       
    public void btnApagaExameFired(ActionEvent event) {
        editExame.clear();
    }

    public void btnApagaMaterialFired(ActionEvent event) {
        editMaterial.clear();
    }

    /**
     * @return the exames
     */
    public List<Exame> getExames() {
        return exames;
    }

    /**
     * @param exames the exames to set
     */
    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    /**
     * @return the oper
     */
    public String getOper() {
        return oper;
    }

    /**
     * @param oper the oper to set
     */
    public void setOper(String oper) {
        this.oper = oper;
    }

}