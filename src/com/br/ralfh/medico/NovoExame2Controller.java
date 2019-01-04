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
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class NovoExame2Controller extends Controller {

    /**
     * Initializes the controller class.
     */
        
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
        initTGroup();
        initListeners();
        setToolTips();        
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
    
    public void initListeners() {
        addTGMaterialListener();
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

        ShowDialog("S", "Exame solicitado com sucesso", null,this.getStage());

        if (PreencheExame()) {
            exames.add(exame);
            editExame.clear();
            editMaterial.clear();
        }
        
    }
    
    public void actionCancelar(ActionEvent ae) {       
        //exame = null;
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