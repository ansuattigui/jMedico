package com.br.ralfh.medico;

import com.br.ralfh.medico.dlg.AlertDlgController;
import com.br.ralfh.medico.modelos.Usuario;
import com.br.ralfh.medico.modelos.Usuarios;
import java.net.URL;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
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
public class UsuarioController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private Usuario usuario;
    private StatusBtn status;
    private final SimpleObjectProperty<Usuario> sopUsuario;
    private final ObservableList<Usuario> sopUsuarios;    
    
    @FXML Button btnCriarUsuario;
    @FXML Button btnAtualUsuario;
    @FXML Button btnDelUsuario;
    @FXML Button btnConfUsuario;
    @FXML Button btnCancUsuario;    
    @FXML Button btnSairUsuario;
    
    @FXML TextField nomeCompleto;  
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

    @FXML TextField username;
    @FXML PasswordField password;
    
    @FXML CheckBox expiraValidade;
    @FXML DatePicker dataExpira;
    
    @FXML ToggleGroup tgTipoUsu;
    @FXML RadioButton rbAdministrador;
    @FXML RadioButton rbMedico;
    @FXML RadioButton rbRecepcionista;
    @FXML RadioButton rbVisitante;

    
    public UsuarioController() {
        this.status = StatusBtn.IDLE;
        this.sopUsuarios = FXCollections.observableArrayList();
        this.sopUsuario = new SimpleObjectProperty<>();  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtons();
        habilEdicaoFired();
        initCombos();
        initGrupoTipoUsuario();
        initGrupoAtivo();
        addUsuarioListener();
        addUsuariosListener();
        //addStageCloseListener();
    } 

    public void addStageCloseListener() {
        getController().getStage().setOnHiding(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Fechando formulário");
          }
    });     
    }
    
    private void addUsuarioListener() { 
        sopUsuario.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (sopUsuario.get() != null) {
                mostraUsuario();
                status = StatusBtn.SHOWING;
                setButtons();
                habilEdicaoFired();
            }
        }
    });                
    }
        
    private void addUsuariosListener() { 
        sopUsuarios.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c ) {
                apagaConvenio();
                if (sopUsuarios.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecUsuario.fxml";
                        String titleGUI = "Selecionar Usuario";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecUsuario = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,getStage());
                        selecUsuario.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecUsuarioController controller = (SelecUsuarioController) selecUsuario.getController();
                        controller.setUsuario(sopUsuarios);
                        selecUsuario.showAndWait();
                        if (controller.closeModal) {
                            usuario = controller.tabelaUsuarios.getSelectionModel().getSelectedItem();
                            sopUsuario.set(usuario);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopUsuarios.size() == 1) {
                    usuario = sopUsuarios.iterator().next();
                    sopUsuario.set(usuario);
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
    
    private void initGrupoTipoUsuario() {
        this.rbAdministrador.setUserData("Administrador");
        this.rbMedico.setUserData("Medico");
        this.rbRecepcionista.setUserData("Recepcionista");
        this.rbVisitante.setUserData("Visitante");
        this.tgTipoUsu = new ToggleGroup();
        this.tgTipoUsu.getToggles().add(rbAdministrador);
        this.tgTipoUsu.getToggles().add(rbMedico);
        this.tgTipoUsu.getToggles().add(rbRecepcionista);
        this.tgTipoUsu.getToggles().add(rbVisitante);
    }

    private void initGrupoAtivo() {
        this.rbAtivo.setUserData(Boolean.TRUE);
        this.rbInativo.setUserData(Boolean.FALSE);
        this.tgAtivo = new ToggleGroup();
        this.tgAtivo.getToggles().add(rbAtivo);
        this.tgAtivo.getToggles().add(rbInativo);
    }
        
    public void criaUsuarioFired(ActionEvent event) {
        status = StatusBtn.INSERTING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void atualizaUsuarioFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    public void btnDelUsuarioFired(ActionEvent event) {
        String fxmlGUI = "dlg/AlertDlg.fxml";
        String fxmlTitle = "JHTC - Cadastro de Usuarios";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        try {             
            GUIFactory dlg = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
            ((AlertDlgController) dlg.getController()).configProperties(TipoDialogo.EXCLUSÃO);
            
//           xxxxxxxxx ver retorno de boolean atribuindo tela a variável.
            
            
            dlg.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public void confUsuarioFired(ActionEvent event) {      
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        if (usuario.getId() == -1) {
            this.usuario = new Usuario();
            preencheUsuario();        
            manager.persist(this.usuario);        
        } else {
            preencheUsuario();
            manager.merge(this.usuario);
        }
        manager.getTransaction().commit();
        manager.close();
        
        if (usuario.getId() == -1) {
            status = StatusBtn.IDLE;
        } else {
            status = StatusBtn.SHOWING;
        }        
        setButtons();      
        habilEdicaoFired();
    }
    
    public void cancUsuarioFired(ActionEvent event) {
        try {
            if (usuario.getId() == -1) {
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
        apagaConvenio();
        
        sopUsuarios.setAll(FXCollections.observableArrayList(Usuarios.getObsLista()));

/*        status = StatusBtn.IDLE;
        setButtons();
        habilEdicaoFired();
*/
    }
    

    public void sairUsuarioFired(ActionEvent event) {
        this.stage.close();
    }    
    
    
    private void mostraUsuario() {        
        nomeCompleto.setText(String.valueOf(usuario.getNomeCompleto()));
        identidade.setText(usuario.getIdentidade()); 
        cpf.setText(usuario.getCpf());
        endereco.setText(usuario.getEndereco());
        numEndereco.setText(usuario.getNumero());
        compEndereco.setText(usuario.getComplemento());
        bairro.setText(usuario.getBairro());
        cep.setText(usuario.getCep());
        cidade.setText(usuario.getCidade());
        try {
            uf.getSelectionModel().select(usuario.getUf());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        telefone1.setText(usuario.getTelefone1());
        telefone2.setText(usuario.getTelefone2());
        email.setText(usuario.getEmail());
        username.setText(usuario.getUsuario());
        
        int totAT = this.tgAtivo.getToggles().size();
        for(int i=0;i<totAT;i++) {
            Boolean temp = (Boolean) this.tgAtivo.getToggles().get(i).getUserData();
            if (temp.equals(usuario.getAtivo())) {
                this.tgAtivo.getToggles().get(i).setSelected(true);
                break;
            }  
        }        
        expiraValidade.setSelected(usuario.getSenhaExpira());
        dataExpira.setValue(usuario.getDataExpira()!=null?Util.ld(usuario.getDataExpira()):null);
        int totTU = this.tgTipoUsu.getToggles().size();
        for(int i=0;i<totTU;i++) {
            String temp = this.tgTipoUsu.getToggles().get(i).getUserData().toString();
            if (temp.equals(usuario.getTipoUsuario())) {
                this.tgTipoUsu.getToggles().get(i).setSelected(true);
                break;
            }  
        }        
    }

    private void preencheUsuario() {        
        usuario.setNomeCompleto(nomeCompleto.getText()); 
        usuario.setIdentidade(identidade.getText());
        usuario.setCpf(cpf.getText());
        usuario.setEndereco(endereco.getText());
        usuario.setNumero(numEndereco.getText());
        usuario.setComplemento(compEndereco.getText());
        usuario.setBairro(bairro.getText());
        usuario.setCep(cep.getText());
        usuario.setCidade(cidade.getText());
        usuario.setUf(uf.getSelectionModel().getSelectedItem().toString());
        usuario.setTelefone1(telefone1.getText());
        usuario.setTelefone2(telefone2.getText());
        usuario.setEmail(email.getText());
        usuario.setUsuario(username.getText());
        
        if (password.getText().trim().isEmpty()) {
            usuario.setSenha(usuario.getSenha());
        } else {
            usuario.setSenha(password.getText());
        }
        
        usuario.setSenhaExpira(expiraValidade.isSelected());
        usuario.setDataExpira(dataExpira.getValue()!=null?Util.udate(dataExpira.getValue()):null);
        usuario.setAtivo((Boolean) this.tgAtivo.getSelectedToggle().getUserData());
        usuario.setTipoUsuario(this.tgTipoUsu.getSelectedToggle().getUserData().toString());
    }
        
    
    public void apagaConvenio() {
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
        username.clear();
        password.clear();
        expiraValidade.setSelected(Boolean.FALSE);
        dataExpira.setValue(null);
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
        uf.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        telefone1.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        telefone2.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        email.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        username.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        password.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        expiraValidade.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        dataExpira.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        
        rbAdministrador.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        rbMedico.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        rbRecepcionista.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        rbVisitante.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        
        rbAtivo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        rbInativo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        
        
    }
    
    private void setButtons() {
        btnCriarUsuario.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnAtualUsuario.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnDelUsuario.setDisable(status!=StatusBtn.SHOWING);
        btnConfUsuario.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancUsuario.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnSairUsuario.setDisable((status!=StatusBtn.IDLE)&(status!=StatusBtn.SHOWING));
    }
}