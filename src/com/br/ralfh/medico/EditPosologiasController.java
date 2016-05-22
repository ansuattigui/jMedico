/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Posologia;
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
public class EditPosologiasController extends Controller {
    
    private TipoOper tipoOperacao;        
    private Posologia posologia;
    
    @FXML
    private Text lblAcao;
    @FXML
    private TextField nomePosologia;
    @FXML
    private Button btnConfirma;
    @FXML
    private Button btnCancela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void setPosologia(Posologia posologia) {
        this.posologia = posologia;
        mostraPosologia();
    }
    
    //Mostra o medicamento no caso de alteração ou exclusão
    public void mostraPosologia(){
        nomePosologia.setText(this.posologia.getPosologia());                
    }
    
    private void carregaPosologia() {
        this.posologia.setPosologia(nomePosologia.getText());
    }

    public void setAcao(TipoOper to) {
        this.tipoOperacao = to;
    }    
    
    public void btnConfirmaFired(ActionEvent event) {
        switch (this.tipoOperacao) {
            case INCLUSÃO: incluiPosologia();
                    break;
            case ALTERAÇÃO: alteraPosologia();
                    break;
            case EXCLUSÃO: excluiPosologia();                        
                    break;
        }
        this.getStage().close();
    }


    public void btnCancelaFired(ActionEvent event) {
        System.out.println("Operação cancelada pelo usuário");
        this.getStage().close();
    }
    
    private void incluiPosologia() {
        
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        
        this.posologia = new Posologia();
        carregaPosologia();
        
        manager.persist(this.posologia);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void alteraPosologia(){

        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.posologia = manager.find(Posologia.class, this.posologia.getId());

        carregaPosologia();
        
        manager.merge(this.posologia);
        manager.getTransaction().commit();
        manager.close();
    }
    
    private void excluiPosologia() {

        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        this.posologia = manager.find(Posologia.class, this.posologia.getId());
        
        manager.remove(this.posologia);
        manager.getTransaction().commit();
        manager.close();        
    }
    
}
