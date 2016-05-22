package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Grupo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Ralfh
 */
public class EditGruposController extends Controller {
    
    private TipoOper tipoOperacao;    
    private Grupo grupo;
    
    @FXML
    private Text lblAcao; 
    @FXML 
    private TextField nomeGrupo;
    @FXML
    private Button btnConfirma;
    @FXML
    private Button btnCancela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setGrupo(Grupo grupomed) {
        this.grupo = grupomed;
        mostraGrupo();
    }
        
    public void mostraGrupo(){
        nomeGrupo.setText(this.grupo.getGrupo());                
    }
    
    private void carregaGrupo() {
        this.grupo.setGrupo(nomeGrupo.getText());
    }
    
    public void setAcao(TipoOper to) {
        this.tipoOperacao = to;
    }
    
    
    public void btnConfirmaFired(ActionEvent event) {
        switch (this.tipoOperacao) {
            case INCLUSÃO: incluiGrupo();
                    break;
            case ALTERAÇÃO: alteraGrupo();
                    break;
            case EXCLUSÃO: excluiGrupo();                        
                    break;
        }
        this.getStage().close();
    }


    public void btnCancelaFired(ActionEvent event) {
        System.out.println("Operação cancelada pelo usuário");
        this.getStage().close();
    }
    
    private void incluiGrupo() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
                
        this.grupo = new Grupo();
        this.grupo.setGrupo(nomeGrupo.getText());
        
        manager.persist(this.grupo);
        
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void alteraGrupo(){
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        
        this.grupo.setGrupo(nomeGrupo.getText());
        manager.merge(this.grupo);
        
        manager.getTransaction().commit();
        manager.close();                
    }
    
    private void excluiGrupo() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        
        this.grupo = manager.find(Grupo.class, this.grupo.getId());
        manager.remove(this.grupo);
        
        manager.getTransaction().commit();
        manager.close();        
    }
    
}
