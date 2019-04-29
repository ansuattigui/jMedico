/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.Convenio;
import com.br.ralfh.medico.modelos.Convenios;
import com.br.ralfh.medico.modelos.HorarioAgenda;
import com.br.ralfh.medico.modelos.HorariosAgenda;
import com.br.ralfh.medico.modelos.Medico;
import com.br.ralfh.medico.modelos.Medicos;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Pacientes;
import com.br.ralfh.medico.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jfxtras.labs.scene.control.CalendarPicker;



/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class AgendaConsultasController extends Controller {
    
    private SimpleObjectProperty<HorarioAgenda> horario;
    private GUIFactory agendamentoGUI;
    public static Medico medico;
    public static HorariosAgenda horarios;
    private static Usuario perfilUsuario;
    @FXML Accordion accAgenda;    
    @FXML Label lblDataAgenda;
    @FXML Button btnAgendar;
    @FXML Button btnEncaixar;
    @FXML Button btnConfirmar;
    @FXML Button btnTransferir;
    @FXML Button btnExcluir;    
    @FXML Button btnAutorizar;
    @FXML Button btnCadastro;
    @FXML Button btnLocalizar;
    
    @FXML TableView<HorarioAgenda> tvAgendaConsultas;
    @FXML TableColumn<HorarioAgenda,Boolean> tcStatus;
    @FXML TableColumn<HorarioAgenda,String> tcHorario;
    @FXML TableColumn<HorarioAgenda,Boolean> tcConfirmado;
    @FXML TableColumn<HorarioAgenda,String> tcPaciente;
    @FXML TableColumn<HorarioAgenda,Boolean> tcAtendido;
    @FXML TableColumn<HorarioAgenda,Boolean> tcECG;
    @FXML TableColumn<HorarioAgenda,String> tcEvento;
    @FXML VBox vbCalendar;
    @FXML CalendarPicker calendAgenda;
    
    @FXML TextField consMarcadas;
    @FXML TextField consAtendidas;
    @FXML TextField numECG;
    @FXML TextField numEncaixes;
    @FXML TextField numPVez;
    
    @FXML ToggleGroup tgHorarios;
    @FXML RadioButton rbTodos;
    @FXML RadioButton rbOcupados;
    @FXML RadioButton rbLivres;
    
    @FXML TextField codigo;
    @FXML TextField codigoant;
    @FXML TextField evento;
    @FXML TextField nomeConvenio;
    @FXML TextField telefoneI;
    @FXML TextField telefoneII;
    @FXML TextArea observacoes;

    public Timer timer;
    public AgendaConsultasController(){
        timer = new Timer();
        medico = Medicos.getMedicoWithId(1);
        perfilUsuario = MedicoController.getPerfilUsuario();
    }  
    
    public void AddListenerTimer() {       
        tvAgendaConsultas.focusedProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (tvAgendaConsultas.isFocused()) {
                initTimer();
            } else {        
                closeTimer();
            }}
        }); 
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {                        
        if (perfilUsuario.getTipoUsuario().equals("Medico")) {
            btnAutorizar.setVisible(Boolean.TRUE);
        } else {
            btnAutorizar.setVisible(Boolean.FALSE);
        }
        horario = new SimpleObjectProperty<>();
        
        initTGrHorarios();
        initAgendaConsultas();
        initCalendar();
        AddListenerSelecHorario();
        AddListenerHorario();
        AddListenerTimer();
        calendAgenda.setCalendar(Calendar.getInstance());
        accAgenda.setExpandedPane(accAgenda.getPanes().get(0));
        AddDblClickAgenda();
    }  
    
    
    
    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
            public void run() {
                initCalendar();
                //initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));
            }
        },10000,120000); //executar após 10 segundos, intervalo         
    }
    
    private void closeTimer() {
        timer.cancel();
    }
    
    public void initCalendar() {
        calendAgenda.calendarProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (calendAgenda.calendarProperty().getValue() != null) {
                initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));
                tvAgendaConsultas.getSelectionModel().selectFirst();
                tvAgendaConsultas.requestFocus();
            } 
        }
        });        
    }
        
    public void initAgendaConsultas() {
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("encaixe"));        
        tcStatus.setCellFactory(new Callback<TableColumn<HorarioAgenda, Boolean>, TableCell<HorarioAgenda, Boolean>>() {
            @Override 
            public TableCell<HorarioAgenda, Boolean> call(TableColumn<HorarioAgenda, Boolean> encaixeTC) {
              return new ColoredCell();
            }
        });            
        tcHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HorarioAgenda,String>, ObservableValue<String>>() {
            @Override 
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HorarioAgenda,String> h) {
                SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
                return new SimpleObjectProperty<>(dt.format(h.getValue().getDataHora()));
            }
        });
        tcHorario.setCellFactory(new Callback<TableColumn<HorarioAgenda, String>,TableCell<HorarioAgenda, String>> (){
            @Override
            public TableCell<HorarioAgenda, String> call(TableColumn<HorarioAgenda, String> param) {
                    return new TextCell("hora");
            }
               
        });    
        tcConfirmado.setCellValueFactory(new PropertyValueFactory<>("presente"));        
        tcConfirmado.setCellFactory(new Callback<TableColumn<HorarioAgenda, Boolean>, TableCell<HorarioAgenda, Boolean>>() {
            @Override 
            public TableCell<HorarioAgenda, Boolean> call(TableColumn<HorarioAgenda, Boolean> atendidoTC) {
              return new CheckBoxCell();
            }
        });            
        tcECG.setCellValueFactory(new PropertyValueFactory<>("ecg"));        
        tcECG.setCellFactory(new Callback<TableColumn<HorarioAgenda, Boolean>, TableCell<HorarioAgenda, Boolean>>() {
            @Override 
            public TableCell<HorarioAgenda, Boolean> call(TableColumn<HorarioAgenda, Boolean> atendidoTC) {
              return new CheckBoxCell();
            }
        });            
        tcAtendido.setCellValueFactory(new PropertyValueFactory<>("atendido"));        
        tcAtendido.setCellFactory(new Callback<TableColumn<HorarioAgenda, Boolean>, TableCell<HorarioAgenda, Boolean>>() {
            @Override 
            public TableCell<HorarioAgenda, Boolean> call(TableColumn<HorarioAgenda, Boolean> atendidoTC) {
              return new CheckBoxCell();
            }
        });            
        tcPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        tcPaciente.setCellFactory(new Callback<TableColumn<HorarioAgenda, String>,TableCell<HorarioAgenda, String>> (){
            @Override
            public TableCell<HorarioAgenda, String> call(TableColumn<HorarioAgenda, String> param) {
                    return new TextCell("paciente");
                }
            });    
        tcEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        tcEvento.setCellFactory(new Callback<TableColumn<HorarioAgenda, String>,TableCell<HorarioAgenda, String>> (){
            @Override 
            public TableCell<HorarioAgenda, String> call(TableColumn<HorarioAgenda, String> eventoTC) {
              return new ColoredCellExt();
            }
        });            
    }
    
    public void initTGrHorarios() {
        rbTodos.setUserData("TODOS");
        rbLivres.setUserData("LIVRES");
        rbOcupados.setUserData("OCUPADOS");
        tgHorarios = new ToggleGroup();
        tgHorarios.getToggles().add(rbTodos);
        tgHorarios.getToggles().add(rbOcupados);
        tgHorarios.getToggles().add(rbLivres);
        tgHorarios.selectToggle(rbTodos);
        tgHorarios.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
                initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));
            }
        });
    }
    
    public void initAgendaDados(Date data) {        
        horarios = new HorariosAgenda(data);
        RadioButton tg = (RadioButton) tgHorarios.getSelectedToggle();        
        switch (tg.getUserData().toString()) {
            case "TODOS":
                tvAgendaConsultas.getItems().setAll(horarios.getObsListaAgenda());
                break;
            case "LIVRES":
                tvAgendaConsultas.getItems().setAll(horarios.getObsListaAgenda(Boolean.TRUE));   
                break;
            case "OCUPADOS":
                tvAgendaConsultas.getItems().setAll(horarios.getObsListaAgenda(Boolean.FALSE));
                break;
        }
            
        consMarcadas.setText(String.valueOf(horarios.estatistica.marcadas)); 
        consAtendidas.setText(String.valueOf(horarios.estatistica.atendidas)); 
        numECG.setText(String.valueOf(horarios.estatistica.ecgs)); 
        numEncaixes.setText(String.valueOf(horarios.estatistica.encaixes)); 
        numPVez.setText(String.valueOf(horarios.estatistica.pvez)); 
        
        SimpleDateFormat dt = new SimpleDateFormat("EEEEE, dd 'de' MMMMM 'de' yyyy");        
        lblDataAgenda.setText(dt.format(data));
        
        tvAgendaConsultas.getSelectionModel().clearSelection();
    }
    
    public void goPrevDate(ActionEvent e) {        
        LocalDateTime data = Util.ldt(calendAgenda.calendarProperty().getValue().getTime()).minusDays(1);              
        calendAgenda.setCalendar(Util.calDate(data));
    }
    
    public void goNextDate(ActionEvent e) {
        LocalDateTime data = Util.ldt(calendAgenda.calendarProperty().getValue().getTime()).plusDays(1);              
        calendAgenda.setCalendar(Util.calDate(data));
    }
    
    public void AddDblClickAgenda() {        
        tvAgendaConsultas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if (tvAgendaConsultas.getSelectionModel().getSelectedItem().getPaciente().isEmpty()) {
                        btnAgendar.fire();
                    } else {
                        btnConfirmar.fire();
                    }                    
                }
            }
        });
    }
    
    
    public void AddListenerSelecHorario() {
        tvAgendaConsultas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
                horario.set(tvAgendaConsultas.getSelectionModel().getSelectedItem());
//            } else apagaPaciente();
        }
        }); 
    }
    
    
    public void AddListenerHorario() {
        horario.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if ((horario.get()!= null)) { //&&(!horario.get().getPaciente().trim().isEmpty())) {
                try {
                    mostraPaciente();
                    configBtns(horario.get().getPaciente().trim().isEmpty() ? Boolean.TRUE : Boolean.FALSE);
                } catch (Exception ex) {
                    Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else apagaPaciente();
        }            
        });
    }

    private void mostraPaciente() {      
        codigo.setText(String.valueOf(horario.get().getCodPaciente()));
        codigoant.setText(String.valueOf(horario.get().getCodAntigoPaciente()));
        evento.setText(horario.get().getEvento());
        Convenio convenio = Convenios.getConvenioWithCod(horario.get().getConvenio());
        nomeConvenio.setText(convenio==null?"":convenio.getNome());
        telefoneI.setText(horario.get().getTelefone1());
        telefoneII.setText(horario.get().getTelefone2());        
        observacoes.setText(horario.get().getObservacoes());
    }
    
    private void apagaPaciente() {
        codigo.clear();
        codigoant.clear();
        evento.clear();
        nomeConvenio.clear();
        telefoneI.clear();
        telefoneII.clear();
        observacoes.clear();
    }
    
    @FXML
    public void btnExcluirFired(ActionEvent ae) {
        if (ExcluiRegistroDlg("EAG", "", null,this.getStage())) {
            if (HorariosAgenda.excluiAgendamento(horario.get())) {
                apagaPaciente();
                initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));                
            }
        }
    }
    
    public void btnAgendarFired(ActionEvent e) throws Exception {
            
        String fxmlGUI = "fxml/Agendamento.fxml";
        StageStyle fxmlStyle = StageStyle.UTILITY;
        String fxmlTitle = "";
        TipoTarefa tt = null;
        
        Button btn = (Button) e.getSource();
        String btnNome = btn.getId();
        
        if (btnNome.equals("btnAgendar")) {
            fxmlTitle = "Agendar Consulta";
            tt = TipoTarefa.AGENDAR;
        } else if (btnNome.equals("btnEncaixar")) {
            fxmlTitle = "Agendar Encaixe";
            tt = TipoTarefa.ENCAIXAR;
        } else if (btnNome.equals("btnConfirmar")) {
            fxmlTitle = "Confirmar agendamento";
            tt = TipoTarefa.CONFIRMAR;
        } else if (btnNome.equals("btnTransferir")) {
            fxmlTitle = "Transferir agendamento";
            tt = TipoTarefa.TRANSFERIR;
        } 

        if (tvAgendaConsultas.getSelectionModel().getSelectedItem()==null) {
             ShowDialog("EX", "Selecione um horário", null,this.getStage());
             return;
        }
        
        switch (tt)  {            
            case ENCAIXAR:
                HorarioAgenda ha = new HorarioAgenda();
                ha.setData(tvAgendaConsultas.getSelectionModel().getSelectedItem().getData());
                ha.setDataHora(tvAgendaConsultas.getSelectionModel().getSelectedItem().getDataHora());
                ha.setEncaixe(Boolean.TRUE);
                horario.set(ha);
                break;
        }
        
        agendamentoGUI = new GUIFactory(fxmlGUI,fxmlTitle,fxmlStyle,this.getStage());
        AgendamentoController controller = (AgendamentoController) agendamentoGUI.getController();
        controller.setTarefa(tt);
        controller.setHorario(horario.get());        
//        controller.setHorario(tvAgendaConsultas.getSelectionModel().getSelectedItem());        
        agendamentoGUI.showAndWait();
        initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));                
    }
    
    public void btnAutorizarFired(ActionEvent e) throws Exception {
        if (tvAgendaConsultas.getSelectionModel().getSelectedItem()==null) {
             ShowDialog("EX", "Selecione um horário", null,this.getStage());
        } else {
            ShowDialog("AU", "Autorizar a entrada de ", horario.get(),this.getStage());
        }
    }
    
    
    public void btnCadastroFired(ActionEvent ae) throws IOException {        
        if (!horario.get().getPaciente().isEmpty()) {
            String fxmlGUI = "fxml/Pacientes.fxml";;
            String titleGUI = "Cadastro do Paciente";
            StageStyle fxmlStyle;
            GUIFactory pacientes;
            Controller controller;
            Paciente pac = null;

            fxmlStyle = StageStyle.DECORATED;
            pacientes = new GUIFactory(fxmlGUI, titleGUI,fxmlStyle,this.getStage());
            controller = pacientes.getController();

            if (horario.get().getCodPaciente()==0) {
                pac = new Paciente();
                pac.setCodAntigo(Pacientes.getProcCodAnt());
                pac.setNome(horario.get().getPaciente());        
                pac.setConvenio(horario.get().getConvenio());
                pac.setTelResidencial(horario.get().getTelefone1());
                pac.setTelComercial(horario.get().getTelefone2());            
            }

            controller = (PacienteController) controller;

            if (horario.get().getCodPaciente()!= 0) {
                controller.setPaciente(horario.get().getCodAntigoPaciente(),horario.get().getId());
            } else {
                controller.setPaciente(pac,horario.get().getId());
            }
            controller.addStageCloseListener();
            pacientes.showAndWait();   
            initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));
        }
        
    }
    
    public void localizarFired(ActionEvent ae) throws IOException {
                    
        String fxmlGUI;
        String titleGUI;
        StageStyle fxmlStyle;
        GUIFactory pacientes;
        Controller controller;
        Paciente pac = null;

//            if (perfilUsuario.getTipoUsuario().equals("Medico")) {
//                fxmlGUI = "fxml/PacienteFichaMed.fxml";
//                titleGUI = "Ficha Médica do Paciente";
//            } else {
        fxmlGUI = "fxml/PesquisaPac.fxml";
        titleGUI = "Localizar um Paciente";
//            }

        fxmlStyle = StageStyle.DECORATED;
        pacientes = new GUIFactory(fxmlGUI, titleGUI,fxmlStyle,this.getStage());
        controller = pacientes.getController();

//            controller.addStageCloseListener();
        pacientes.showAndWait();   
//        initAgendaDados(Util.udate(calendAgenda.calendarProperty().getValue()));
    }

    
    private void configBtns(Boolean status) {
        btnAgendar.setDisable(!status);
        btnEncaixar.setDisable(status);
        btnConfirmar.setDisable(status);
//        btnTransferir.setDisable(status);
        btnExcluir.setDisable(status);        
        btnAutorizar.setDisable(status);
        btnCadastro.setDisable(status);
    }
    
    
    /////////////////////////////////////////////////////////////////////
    // Classes Factory para montagem da agenda
    /////////////////////////////////////////////////////////////////////
    
    public class CheckBoxCell extends TableCell<HorarioAgenda, Boolean> {   
        
        final CheckBox checkBox = new CheckBox();    
        final StackPane paneCell = new StackPane();

        public CheckBoxCell() { 
            paneCell.setDisable(false);
            checkBox.setDisable(true);
            paneCell.setPadding(new Insets(3));
            paneCell.getChildren().add(checkBox);
        }
        
        @Override 
        protected void updateItem(Boolean item, boolean empty) {
          super.updateItem(item, empty);
          checkBox.setSelected(item==null ? Boolean.FALSE : item.booleanValue());
          if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paneCell);
        }
      }        
    }   
    
    public class TextCell extends TableCell<HorarioAgenda, String> {
        
        final Label txt = new Label();
        
        public TextCell(String campo) {
            txt.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
//            txt.setTextFill(Paint.valueOf("#6C3100"));
            txt.setFont(Font.font("System", FontWeight.BOLD, 12));
            txt.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            txt.setPadding(new Insets(2));
            if ("hora".equals(campo)) {
                txt.setAlignment(Pos.BASELINE_CENTER);
                setContentDisplay(ContentDisplay.CENTER);
            } else if ("paciente".equals(campo)) {
                txt.setAlignment(Pos.CENTER_LEFT);
                setContentDisplay(ContentDisplay.CENTER);
            }
        }
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
                txt.setText(item.trim().isEmpty()?"":item);
                setGraphic(txt);
            } else {
                setGraphic(null);
            }
        }
    }
                    

    
    public class ColoredCell extends TableCell<HorarioAgenda, Boolean> {   
        
        final Region reg = new Region();
        public ColoredCell() {
            reg.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            reg.setPadding(new Insets(2));
        }
        
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);

            if(Objects.equals(item, Boolean.TRUE)) {
                reg.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));                  
            } else {
                reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
            }
            if(!empty){
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(reg);
            }
        }
    }

    public class ColoredCellExt extends TableCell<HorarioAgenda, String> {   
        
        final Region reg = new Region();
        public ColoredCellExt() {
            reg.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            reg.setPadding(new Insets(2));
        }
        
        @Override
        protected void updateItem(String item, boolean empty) {
            
            if (!empty) {
                if(Objects.equals(item, "Consulta")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                  
                } else if(Objects.equals(item, "Emergência")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Revisão")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Primeira Vez")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Receita")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Receita Controlada")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Representante")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else if(Objects.equals(item, "Reunião")) {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));                 
                } else {
                    reg.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                }            
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(reg);
            } else {
                setGraphic(null);
            }
        }
    }
}
