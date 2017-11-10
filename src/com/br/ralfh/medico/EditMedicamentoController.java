/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Grupos;
import com.br.ralfh.medico.modelos.Medicamento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class EditMedicamentoController extends Controller {
    
//    private Stage stage;
//    private MedicamentosController mainController;    
    private TipoOper tipoOperacao;

//    private ObservableList<Grupo> gruposMed;
//    private ObservableList<String> nomesGrupos = FXCollections.observableArrayList();
    
    private Medicamento medicamento;
    
//    @FXML private Text lblAcao;
    @FXML
    private ComboBox<String> cbGrupos;
    @FXML
    private TextField principioAtivo;
    @FXML
    private TextField nomeComercial;
    @FXML
    private Button btnConfirma;
    @FXML
    private Button btnCancela;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        initComboGrupos();        
    }  

    public void setMedicamento(Medicamento medicam) {
        this.medicamento = medicam;
        mostraMedicamento();
    }
    
    public void setGrupo(Grupo grupo) {
        cbGrupos.getSelectionModel().select(grupo.getGrupo());
    }
    
    public void setAcao(TipoOper to) {
        this.tipoOperacao = to;
    }
        
    public void initComboGrupos(){
        cbGrupos.setItems(Grupos.getObsSLista());                
    }
    
    public void mostraMedicamento(){
        cbGrupos.getSelectionModel().select(this.medicamento.getGrupo().getGrupo());
        principioAtivo.setText(this.medicamento.getPrincipio());                
        nomeComercial.setText(this.medicamento.getNomecomercial());                
    }

    public void limpaMedicamento(){
        cbGrupos.getSelectionModel().select(-1);
        principioAtivo.clear();
        nomeComercial.clear();
    }

    
    public void carregaMedicamento() {
        Grupos grupos = new Grupos();        
        this.medicamento.setGrupo(grupos.getGrupoPeloNome(cbGrupos.getSelectionModel().getSelectedItem()));
        this.medicamento.setPrincipio(principioAtivo.getText());
        this.medicamento.setNomecomercial(nomeComercial.getText());
    }

    
    public void btnConfirmaFired(ActionEvent event) {
        switch (this.tipoOperacao) {
            case INCLUSÃO: incluiMedicamento();
                    break;
            case ALTERAÇÃO: alteraMedicamento();
                    break;
            case EXCLUSÃO: excluiMedicamento();                        
                    break;
        }
        this.getStage().close();
    }
    
    public void btnCancelaFired(ActionEvent event) {
        System.err.println("Operação cancelada pelo usuário");
        this.getStage().close();
    }    
    
    private void incluiMedicamento() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        
        this.medicamento = new Medicamento();
        carregaMedicamento();
        
        manager.persist(this.medicamento);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void alteraMedicamento(){
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.medicamento = manager.find(Medicamento.class, this.medicamento.getId());

        carregaMedicamento();
        
        manager.merge(this.medicamento);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void excluiMedicamento() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.medicamento = manager.find(Medicamento.class, this.medicamento.getId());
        
        manager.remove(this.medicamento);
        manager.getTransaction().commit();
        manager.close();        
    }
    
}
