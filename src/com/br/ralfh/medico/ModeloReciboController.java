package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.ModeloRecibo;
import com.br.ralfh.medico.modelos.ModelosRecibo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class ModeloReciboController extends Controller {
    
    private StatusBtn status;

    private ModeloRecibo modelo;
    private final SimpleObjectProperty<ModeloRecibo> sopModelo;
    private final ObservableList<ModeloRecibo> sopModelos;
    private Boolean closeModal;
    
    @FXML public TextField nomeModelo;
    @FXML public Button btnNovoModelo;
    @FXML public Button btnEditaModelo;
    @FXML public Button btnExcluiModelo;
    @FXML public Button btnConfirma;
    @FXML public Button btnCancela;
    @FXML public Button btnSair;
    @FXML public TableView<ModeloRecibo> tabelaModelos;
    @FXML public TableColumn<ModeloRecibo,String> modeloCol;
    @FXML public HTMLEditor htmlEditorCabecalho;
    @FXML public HTMLEditor htmlEditorCorpo;
    @FXML public HTMLEditor htmlEditorRodape;
    
    @FXML public Button nomepac;
    @FXML public Button nascpac;
    @FXML public Button identpac;
    @FXML public Button cpfpac;
    @FXML public Button datapac;
    
    public ModeloReciboController() {   
        this.status = StatusBtn.IDLE;
        sopModelos = FXCollections.observableArrayList(); 
        sopModelo = new SimpleObjectProperty<>();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabelaModelos();
        addModelosListener();
        addListenerModelo();
        AddListenerSelecModelo();
        setModelos();
        setButtons();
        habilEdicaoFired();  
        customizeHTMLEditor();
    }    
    
    @FXML
    public void btnNovoModeloFired(ActionEvent event) {
        limpaModelo();
        status = StatusBtn.INSERTING;
        setButtons();
        habilEdicaoFired();        
    }
    
    @FXML
    public void btnEditaModeloFired(ActionEvent event) {
        status = StatusBtn.UPDATING;
        setButtons();
        habilEdicaoFired();
    }
    
    @FXML
    public void btnExcluiModeloFired(ActionEvent event) {
        if (ExcluiRegistroDlg("EMRC", "", null,this.getStage())) {
            if (ModelosRecibo.excluiModeloRecibo(modelo)) {
                limpaModelo();
                setModelos();
            }
        }
    }
    
    public void btnConfirmaFired(ActionEvent event) {        
        if (status==StatusBtn.INSERTING) {
            this.modelo = new ModeloRecibo();
            if (preencheModelo()) {        
                if (!ModelosRecibo.novoModeloRecibo(modelo)) {
                     ShowDialog("EX", "Não foi possível criar o modelo desejado.", null,this.getStage());
                     return;
                } else {
                    ShowDialog("INFO", "O modelo foi criado com sucesso.", null,this.getStage());
                    status = StatusBtn.SHOWING;
                }
            }
        } else {
            if (preencheModelo()) {
                if (!ModelosRecibo.atualizaModeloRecibo(modelo)) {
                    ShowDialog("EX", "Não foi possível atualizar o modelo desejado.", null,this.getStage());
                    return;
                } else { 
                    ShowDialog("INFO", "O modelo foi atualizado com sucesso.", null,this.getStage());
                    status = StatusBtn.SHOWING;
                }
            }
        }
        setModelos();
        setButtons();
        habilEdicaoFired();
    }

    public void btnCancelaFired(ActionEvent event) {
        try {
            if (status==StatusBtn.INSERTING) {
                status = StatusBtn.IDLE;
            } else {
                status = StatusBtn.SHOWING;
            }        
        } catch (NullPointerException e) {
            status = StatusBtn.IDLE;
        }
        mostraModelo();
        setButtons();      
        habilEdicaoFired();
    }
    
    public void btnSairFired(ActionEvent ae) {
        this.stage.close();
    }
    

    private void initTabelaModelos() {
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
    }
    
    public void setModelos() {
        this.sopModelos.setAll(ModelosRecibo.getLista());
    }
    
    private void addModelosListener() { 
        sopModelos.addListener(new ListChangeListener() {
        @Override
        public void onChanged(ListChangeListener.Change change) {
            tabelaModelos.setItems(sopModelos);
            tabelaModelos.getSelectionModel().selectFirst();
        }
        });
    }  
    
    public void addListenerModelo() {
        sopModelo.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if ((sopModelo.get()!= null)) { 
                try {
                    status = StatusBtn.SHOWING;
                    mostraModelo();
                    setButtons();
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }            
        });
    }
    
    public void AddListenerSelecModelo() {
        tabelaModelos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            modelo = tabelaModelos.getSelectionModel().getSelectedItem();
            sopModelo.set(modelo);
        }
        }); 
    }
    
    
    private void mostraModelo() {
        nomeModelo.setText(modelo.getNomeModelo());
        htmlEditorCabecalho.setHtmlText(modelo.getCabecalho());
        htmlEditorCorpo.setHtmlText(modelo.getCorpo());
        htmlEditorRodape.setHtmlText(modelo.getRodape());
    }
    
    private void limpaModelo() {
        nomeModelo.clear();
        htmlEditorCabecalho.setHtmlText("");
        htmlEditorCorpo.setHtmlText("");
        htmlEditorRodape.setHtmlText("");
    }
    
    
    private Boolean preencheModelo() {        
        Boolean resultado = Boolean.FALSE;
        try {
            if (nomeModelo.getText().trim().isEmpty()) {
                throw new CampoEmBrancoException("Informe o nome do modelo");
            } else {
                modelo.setNomeModelo(nomeModelo.getText());
            }
            
            if (htmlEditorCabecalho.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o cabeçalho do modelo");
            } else {
                modelo.setCabecalho(htmlEditorCabecalho.getHtmlText());
            }

            if (htmlEditorCorpo.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o corpo do modelo");
            } else {
                modelo.setCorpo(htmlEditorCorpo.getHtmlText());
            }
            
            if (htmlEditorRodape.getHtmlText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o rodapé do modelo");
            } else {
                modelo.setRodape(htmlEditorRodape.getHtmlText());
            }
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ex) {
            ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }

    
    public void nomepacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@nomePaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }
    
    public void nascpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@nascimentoPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void identpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@identidadePaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void cpfpacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@cpfPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }

    public void datapacFired(ActionEvent ae) {
        String html = htmlEditorCorpo.getHtmlText();
        String texto = new HtmlToPlainText().getPlainText(Jsoup.parse(html));
        texto = texto.concat("@@datadodiaPaciente@@");
        String htmlNew = Jsoup.parse(texto).html();
        htmlEditorCorpo.setHtmlText(htmlNew);
    }
    
    public void habilEdicaoFired() {
        nomeModelo.setEditable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        htmlEditorCabecalho.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorCorpo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        htmlEditorRodape.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    private void setButtons() {
        btnNovoModelo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING));
        btnEditaModelo.setDisable((status==StatusBtn.INSERTING)|(status==StatusBtn.UPDATING)|(status!=StatusBtn.SHOWING));
        btnExcluiModelo.setDisable(status!=StatusBtn.SHOWING);
        btnConfirma.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        btnCancela.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        
        nomepac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        nascpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        identpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        cpfpac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        datapac.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    
    private void customizeHTMLEditor() {

        List<String> limitedFonts = FXCollections.observableArrayList("Arial", "Times New Roman", "Courier New", "Comic Sans MS");        
        // modify font selections.
        int i = 0;
        for (Node candidate: (htmlEditorCabecalho.lookupAll("MenuButton"))) {
          // fonts are selected by the second menu in the htmlEditor.
          if (candidate instanceof MenuButton && i == 1) {
            // limit the font selections to our predefined list.
            MenuButton menuButton = (MenuButton) candidate;
            List<MenuItem> removalList = FXCollections.observableArrayList();
            final List<MenuItem> fontSelections = menuButton.getItems();
            for (MenuItem item: fontSelections) {
              if (!limitedFonts.contains(item.getText())) {
                removalList.add(item);
              }
            }
            fontSelections.removeAll(removalList);
            // Select a font from out limited font selection.
            // Selection done in Platform.runLater because if you try to do
            // the selection immediately, it won't take place.
            Platform.runLater(new Runnable() {
              @Override public void run() {
                boolean fontSelected = false;
                for (final MenuItem item: fontSelections) {
                  if ("Arial".equals(item.getText())) {
                    if (item instanceof RadioMenuItem) {
                      ((RadioMenuItem) item).setSelected(true);
                      fontSelected = true;
                    }
                  }
                }
                if (!fontSelected && fontSelections.size() > 0 && fontSelections.get(0) instanceof RadioMenuItem) {
                  ((RadioMenuItem) fontSelections.get(0)).setSelected(true);
                }
              }
            });  
          }
          i++;
        }
        // add a custom button to the top toolbar.
/*        Node node = htmlEditorCabecalho.lookup(".top-toolbar");
        if (node instanceof ToolBar) {
          ToolBar bar = (ToolBar) node;
          ImageView graphic = new ImageView(new Image("http://bluebuddies.com/gallery/title/jpg/Smurf_Fun_100x100.jpg", 32, 32, true, true));
          graphic.setEffect(new DropShadow());
          Button smurfButton = new Button("", graphic);
          bar.getItems().add(smurfButton);
          smurfButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
              htmlEditorCabecalho.setHtmlText("<font face='Comic Sans MS' color='blue'>Smurfs are having fun :-)</font>");
            }
          });
        } */
    }
  }