/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import com.br.ralfh.medico.modelos.Tuss;
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
public class EditProcedimentoController extends Controller {
    
//    private Stage stage;
//    private MedicamentosController mainController;    
    private TipoOper tipoOperacao;

//    private ObservableList<Grupo> gruposMed;
//    private ObservableList<String> nomesGrupos = FXCollections.observableArrayList();
    
    private Tuss tuss;
    
//    @FXML private Text lblAcao;
    @FXML private ComboBox<String> cbGrupos;
    @FXML private ComboBox<String> cbSubgrupos;
    @FXML private TextField codigo;
    @FXML private TextField procedimento;
    @FXML private Button btnConfirma;
    @FXML private Button btnCancela;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        initComboGrupos();  
        initCombosSubgrupos();
    }  

    public void setProcedimento(Tuss proced) {
        this.tuss = proced;
        mostraProcedimento();
    }
    
    public void setGrupo(Grupo grupo) {
        cbGrupos.getSelectionModel().select(grupo.getGrupo());
    }
    
    public void setAcao(TipoOper to) {
        this.tipoOperacao = to;
    }
        
    public void initComboGrupos(){
//        cbGrupos.setItems(Grupos.getObsSLista());                
        cbGrupos.getItems().clear();
        cbGrupos.getItems().add("CONSULTAS");
        cbGrupos.getItems().add("ELETROFISIOLOGICOS/MECÂNICOS E FUNCIONAIS");
    }
    
    public void initCombosSubgrupos(){
        //subgrupo dependendo de grupo
        cbSubgrupos.getItems().clear();
        cbSubgrupos.getItems().add("CONSULTAS");
        cbSubgrupos.getItems().add("ECG-TE");
    }

    public void mostraProcedimento(){
        cbGrupos.getSelectionModel().select(this.tuss.getGrupo());
        cbSubgrupos.getSelectionModel().select(this.tuss.getSubgrupo());
        codigo.setText(this.tuss.getCodigo());                
        procedimento.setText(this.tuss.getProcedimento());                
    }

    public void limpaMedicamento(){
        cbGrupos.getSelectionModel().select(-1);
        cbSubgrupos.getSelectionModel().select(-1);
        codigo.clear();
        procedimento.clear();
    }

    
    public void carregaProcedimento() {
        this.tuss.setGrupo(cbGrupos.getValue());     //grupos.getGrupoPeloNome(cbGrupos.getSelectionModel().getSelectedItem()));
        this.tuss.setSubgrupo(cbSubgrupos.getValue());     
        this.tuss.setCodigo(codigo.getText());
        this.tuss.setProcedimento(procedimento.getText());
    }

    
    public void btnConfirmaFired(ActionEvent event) {
        switch (this.tipoOperacao) {
            case INCLUSÃO: 
                incluiProcedimento();
                break;
            case ALTERAÇÃO: 
                alteraProcedimento();
                break;
            case EXCLUSÃO: 
                excluiMedicamento();                        
                break;
        }
        this.getStage().close();
    }
    
    public void btnCancelaFired(ActionEvent event) {
        ShowDialog("INFO", "Operação cancelada pelo usuário", null);       
        this.getStage().close();
    }    
    
    private void incluiProcedimento() {        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();                
        this.tuss = new Tuss();
        carregaProcedimento();        
        manager.persist(this.tuss);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void alteraProcedimento(){        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.tuss = manager.find(Tuss.class, this.tuss.getId());
        carregaProcedimento();        
        manager.merge(this.tuss);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void excluiMedicamento() {        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.tuss = manager.find(Tuss.class, this.tuss.getId());        
        manager.remove(this.tuss);
        manager.getTransaction().commit();
        manager.close();        
    }
    
}
