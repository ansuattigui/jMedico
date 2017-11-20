package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.ExamesGrupo;
import com.br.ralfh.medico.modelos.GrupoExames;
import com.br.ralfh.medico.modelos.GruposExames;
import com.br.ralfh.medico.modelos.Receita;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class SelecGrupoExamesController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    private GrupoExames grupoExames;
    private SimpleObjectProperty<GrupoExames> sopGrupo;
    private ObservableList<GrupoExames> sopGrupos ;    
    private ObservableList<ExamesGrupo> sopExames = FXCollections.observableArrayList() ;    
    
    @FXML public Button btnConfirmaNome;
    @FXML public Button btnCancelaNome;
    
    @FXML public TableView<GrupoExames> tabelaGrupos;
    @FXML public TableColumn<GrupoExames,String> ordemCol;
    @FXML public TableColumn<GrupoExames,String> indicacaoClinica;    
    
    @FXML public TableView<ExamesGrupo> tabelaExames;
    @FXML public TableColumn<ExamesGrupo,String> exameCol;
    @FXML public TableColumn<ExamesGrupo,String> materialCol;    
        
    @FXML Button btnSalvaGrupo; @FXML public MenuItem miSalvaGrupo;
    @FXML Button btnCancelaGrupo; @FXML public MenuItem miCancelaGrupo;

    public SelecGrupoExamesController() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sopGrupo = new SimpleObjectProperty<>();
        sopGrupos = FXCollections.observableArrayList();
        sopExames = FXCollections.observableArrayList();

        initTabelas();
        initListeners();
        setToolTips();

        sopGrupos.setAll(GruposExames.getLista());
        
        setButtons();  
    }    
    
    public void initListeners() {
        AddSelecGrupoListener();
        addGrupoListener();
        addGruposListener();
        addExamesListener();   
    }

    private void initTabelas() {
        initTabelaGrupos();
        initTabelaExames();
    }
    
    private void setToolTips() {
        btnSalvaGrupo.setTooltip(new Tooltip("Confirmar a seleção de grupo"));
        btnCancelaGrupo.setTooltip(new Tooltip("Cancelar a seleção de grupo"));
    }

    private void addGruposListener() { 
        sopGrupos.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {                
                tabelaGrupos.getItems().setAll(sopGrupos);
            }
        });        
    }        
    
    private void addGrupoListener() { 
        sopGrupo.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                if (sopGrupo.get()!=null) {
                    sopExames.setAll(sopGrupo.get().getExames());
                    setButtons();
                }
            }
        });
    }       
    
    public void AddSelecGrupoListener() {
        tabelaGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o,Object oldVal,Object newVal) {
                grupoExames = tabelaGrupos.getSelectionModel().getSelectedItem();
                sopGrupo.set(grupoExames); 
                setButtons();
            }
        }); 
    }
    
    private void addExamesListener() { 
        sopExames.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (!sopExames.isEmpty()) {
                    tabelaExames.setItems(sopExames); 
                }
            }
        });
    }
    
    public void initTabelaGrupos() {
        ordemCol.setCellValueFactory(new Callback<CellDataFeatures<GrupoExames, String>, ObservableValue<String>>() {
          @Override public ObservableValue<String> call(CellDataFeatures<GrupoExames, String> p) {
            return new ReadOnlyObjectWrapper(tabelaGrupos.getItems().indexOf(p.getValue())+1 + "");
          }
        });           

        indicacaoClinica.setCellValueFactory(new Callback<CellDataFeatures<GrupoExames,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GrupoExames,String> rec) {
                return new SimpleObjectProperty<>(rec.getValue().getIndicacaoClinica());
            }
        });  
    }           
    
    public void initTabelaExames() {
        exameCol.setCellValueFactory(new PropertyValueFactory<>("exame"));
        materialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
    }      
    
    @FXML
    public void btnSalvaGrupoFired(ActionEvent event) {
        this.stage.close();
    }   
    
    @FXML
    public void btnCancelaGrupoFired(ActionEvent ae) {
        grupoExames = null;
        this.stage.close();
    }
    
             
    private void setButtons() {
        btnSalvaGrupo.setDisable(grupoExames==null);
//        btnCancelaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
        miSalvaGrupo.setDisable(grupoExames==null);
//        miCancelaGrupo.setDisable((status!=StatusBtn.INSERTING)&(status!=StatusBtn.UPDATING));
    }
    
    public class TextCell extends TableCell<Receita, String> {
        
        final Label txt = new Label();
        
        public TextCell(String campo) {
            txt.setFont(Font.font("System", FontWeight.BOLD, 12));
            txt.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            txt.setPadding(new Insets(2));
        }
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
                txt.setText(item.trim().isEmpty()?"":item);
                txt.setTextAlignment(TextAlignment.CENTER);
                setGraphic(txt);
            } else {
                setGraphic(null);
            }
        }
    }

    /**
     * @return the grupoExames
     */
    public GrupoExames getGrupoExames() {
        return grupoExames;
    }
}
