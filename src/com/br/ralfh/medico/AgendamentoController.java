/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import static com.br.ralfh.medico.Controller.ShowDialog;
import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.modelos.Convenios;
import com.br.ralfh.medico.modelos.HorarioAgenda;
import com.br.ralfh.medico.modelos.HorariosAgenda;
import com.br.ralfh.medico.modelos.Paciente;
import com.br.ralfh.medico.modelos.Pacientes;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;
import jfxtras.labs.scene.control.BigDecimalField;
import jfxtras.labs.scene.control.BigDecimalFieldBuilder;
import jfxtras.labs.scene.control.LocalTimeTextField;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class AgendamentoController extends Controller {
    
    private TipoTarefa tipoTar;
    private HorarioAgenda horario;
    private Paciente paciente;
    private SimpleObjectProperty<Paciente> sopPaciente;
    private ObservableList<Paciente> sopPacientes;    
    private final ArrayList<String> listaEventos;
    private EntityManager manager;
    
    @FXML Button btnAgendar;
//    @FXML Button btnExcluir;
//    @FXML Button btnTransferir;
    @FXML Button btnProcNome;
    @FXML Button btnApagaNome;
    
    @FXML public ToggleGroup tgPresenca;
    @FXML public RadioButton rbCompareceu;
    @FXML public RadioButton rbFaltou;
    @FXML public ToggleGroup tgAtendido;
    @FXML public RadioButton rbAtendido;
    @FXML public RadioButton rbNaoAtendido;
    
    @FXML Pane paneHoraMarc;
    @FXML LocalTimeTextField horaMarcada;
    @FXML Pane paneIntervalos;
    @FXML BigDecimalField numIntervalos;
    
    @FXML TextField dataConsulta;
    @FXML TextField horaChegada;
    @FXML TextField codPaciente;
    @FXML TextField codAntPaciente;
    @FXML CheckBox ecg;
    @FXML TextField nomePaciente;
    @FXML TextField telefone1;
    @FXML TextField telefone2;
    @FXML ComboBox comboConvenio;
    @FXML ComboBox comboEvento;
    @FXML TextArea observacoes;

    public AgendamentoController() {
        this.listaEventos = new ArrayList<>();
        this.sopPacientes = FXCollections.observableArrayList();
        this.sopPaciente = new SimpleObjectProperty<>();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initControls();
        initCombos();
        AddTGPresencaListener();
        AddTGAtendidoListener();
        addPacientesListener();
        addPacienteListener();
        AddSelectEventoListener();
    }    
    
    public void initControls() {
        horaMarcada = new LocalTimeTextField();
        horaMarcada  .setShowLabels(Boolean.FALSE);
        horaMarcada.setDateFormat(DateFormat.getTimeInstance(DateFormat.SHORT));
        horaMarcada.setMinuteStep(1);
        horaMarcada.setMaxWidth(80);
        paneHoraMarc.getChildren().add(0,horaMarcada);
        numIntervalos = BigDecimalFieldBuilder.create()                        
                        .number(new BigDecimal("1"))
                        .minValue(BigDecimal.ONE)
                        .maxValue(BigDecimal.TEN)
                        .stepwidth(new BigDecimal("1"))
                        .format(new DecimalFormat("0"))
                        .build();
        numIntervalos.setPrefSize(60, 25);
        paneIntervalos.getChildren().add(numIntervalos);        
    }
    
    private void addPacienteListener() { 
        sopPaciente.addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (newVal==null) {
                apagaPaciente();
                return;
            }
            horario.setCodAntigoPaciente(sopPaciente.get().getCodAntigo());
            horario.setCodPaciente(sopPaciente.get().getId());
            horario.setPaciente(sopPaciente.get().getNome());
            horario.setConvenio(sopPaciente.get().getConvenio().getId());
            horario.setTelefone1(sopPaciente.get().getTelResidencial());
            horario.setTelefone2(sopPaciente.get().getTelComercial());
            mostraPaciente();
        }
    });                
    }

    
    private void addPacientesListener() { 
        sopPacientes.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c ) {
                if (sopPacientes.size() > 1) {
                    try {
                        String fxmlGUI = "fxml/SelecPaciente.fxml";
                        String titleGUI = "Selecionar Paciente";
                        StageStyle fxmlStyle = StageStyle.UTILITY;
                        GUIFactory selecPaciente = new GUIFactory(fxmlGUI,titleGUI,fxmlStyle,getStage());
                        selecPaciente.getController().getStage().initStyle(StageStyle.UNDECORATED);
                        SelecPacienteController controller = (SelecPacienteController) selecPaciente.getController();
                        controller.setPaciente(sopPacientes);
                        selecPaciente.showAndWait();
                        if (controller.closeModal) {
                            paciente = controller.tabelaPacientes.getSelectionModel().getSelectedItem();
                            sopPaciente.set(paciente);
                        } 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sopPacientes.size() == 1) {
                    paciente = sopPacientes.iterator().next();
                    sopPaciente.set(paciente);
                }
            }
        });        
    }  
    
    private void AddSelectEventoListener() {        
        comboEvento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (comboEvento.getSelectionModel().getSelectedItem()=="Primeira Vez") {
                if (Objects.equals(AgendaConsultasController.horarios.estatistica.pvez, 
                        AgendaConsultasController.medico.getLimDiaPrimvez())) {
                    ShowDialog("S", "Atingido limite de agendamentos para Primeira Vez!", null,getStage());
                    btnAgendar.setDisable(true);
                }
            } else if (comboEvento.getSelectionModel().getSelectedItem()=="Primeira Vez - Dois Horários") {
                numIntervalos.setNumber(BigDecimal.valueOf(2));           
            } else if (comboEvento.getSelectionModel().getSelectedItem()!="Primeira Vez - Dois Horários") {
                numIntervalos.setNumber(BigDecimal.valueOf(1));          
            } else if (comboEvento.getSelectionModel().getSelectedItem()=="Emergência") {
/*                if (Objects.equals(AgendaConsultasController.horarios.estatistica, 
                        AgendaConsultasController.medico.getLimDiaPrimvez())) {
                    ShowDialog("S", "Atingido limite de agendamentos para Primeira Vez!", null);
                    btnAgendar.setDisable(true);
                } */
            } else btnAgendar.setDisable(false);
        }
        }); 
    }
    
    
    
    private void AddTGPresencaListener() {
        tgPresenca.selectedToggleProperty().addListener(new ChangeListener() { 
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (tgPresenca.selectedToggleProperty().isNotNull().get()) {
                horario.setPresente(rbCompareceu.isSelected());
            }
        }            
        });
    }
    
    private void AddTGAtendidoListener() {
        tgAtendido.selectedToggleProperty().addListener(new ChangeListener() { 
        @Override
        public void changed(ObservableValue o,Object oldVal,Object newVal) {
            if (tgAtendido.selectedToggleProperty().isNotNull().get()) {
                horario.setAtendido(rbAtendido.isSelected());
            }
        }            
        });
    }
    
    public void setTarefa(TipoTarefa tt) {
        tipoTar = tt;  
        initBtns();
    }
    
    public void setHorario(HorarioAgenda ha) {
        horario = ha;
        mostraHorario();
    }
    
    private void mostraHorario() {        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat shf = new SimpleDateFormat("HH:mm");
        try {
            dataConsulta.setText(sdf.format(horario.getDataHora()));
            horaMarcada.setLocalTime(Util.lt(horario.getDataHora())); 
            rbCompareceu.setSelected(horario.getPresente());
            rbAtendido.setSelected(horario.getAtendido());                
            numIntervalos.setNumber(BigDecimal.valueOf(horario.getIntervalos()));
        
/*        Convenio convenio = Convenios.getConvenioWithCod(horario.getConvenio());
        if (convenio!=null) {
            comboConvenio.getSelectionModel().select(convenio.getNome());
        }
*/        
            comboEvento.getSelectionModel().select(horario.getEvento());
            observacoes.setText(horario.getObservacoes()==null?"":horario.getObservacoes());
            ecg.setSelected(horario.getEcg());            
            horaChegada.setText(horario.getHoraChegada()==null?"":shf.format(horario.getHoraChegada()));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        
        mostraPaciente();

    }
    
    private void mostraPaciente() {
        nomePaciente.setText(horario.getPaciente());
        codPaciente.setText(horario.getCodPaciente()==null?"":String.valueOf(horario.getCodPaciente()));
        codAntPaciente.setText(horario.getCodAntigoPaciente()==null?"":String.valueOf(horario.getCodAntigoPaciente()));
        telefone1.setText(horario.getTelefone1());
        telefone2.setText(horario.getTelefone2());
        try {
            String conv = Convenios.getConvenioWithCod(horario.getConvenio()).getNome().trim();
            comboConvenio.getSelectionModel().select(conv);
        } catch(NullPointerException e) {
            comboConvenio.getSelectionModel().select(-1);
        }            
    }
        
    private Boolean descarregaHorario() {                  
        Boolean resultado = Boolean.FALSE;
        try {
            // testar para horarios ocupados
            horario.setDataHora(Util.udate(LocalDateTime.of(Util.ld(horario.getDataHora()),horaMarcada.getLocalTime())));
            horario.setIntervalos(numIntervalos.getNumber().intValue());
            horario.setEcg(ecg.isSelected());            
//            horario.setEncaixe(tipoTar.equals(TipoTarefa.ENCAIXAR)?Boolean.TRUE:Boolean.FALSE);            
            if (nomePaciente.getText().isEmpty()) {
                throw new CampoEmBrancoException("Informe o nome do paciente");
            } else horario.setPaciente(nomePaciente.getText());
            
            if (telefone1.getText()==null&(telefone2.getText()==null)) {
                throw new CampoEmBrancoException("Informe pelo menos um telefone");
            } else {
                horario.setTelefone1(telefone1.getText());
                horario.setTelefone2(telefone2.getText());
            }            
            if ((String) comboConvenio.getValue()==null) {
                throw new CampoEmBrancoException("Informe o convenio do paciente");
            } else {
                horario.setConvenio(Convenios.getConvenioWithNome(comboConvenio.getSelectionModel().getSelectedItem().toString()).getId());
            }
            
            if ((String) comboEvento.getValue()==null) {
                throw new CampoEmBrancoException("Informe o Evento para este agendamento");
            } else {
                horario.setEvento((String) comboEvento.getValue());
            }            
            horario.setObservacoes(observacoes.getText());               
            
            if (rbCompareceu.isSelected()) {
                horario.setHoraChegada(Util.udate(LocalDateTime.of(Util.ld(horario.getDataHora()),LocalTime.now())));
                horario.setPresente(true);
            } else {
                horario.setPresente(false);
            }     
            
            if (horario.getIntervalos() == 2) {         
                //substituir por variável que contenha o tamanho do intervalo no dia da semana
                LocalDateTime data = Util.ldt(horario.getDataHora()).plusMinutes(15);              
                if (HorariosAgenda.getHorarioSeguinteOcupado(Util.udate(data))) {
                    throw new CampoEmBrancoException("Horário seguinte indisponível. Selecione outro horário");
                } /* else {
//                    EntityManager manager = JPAUtil.getEntityManager();
//                    manager.getTransaction().begin();        
                    HorarioAgenda horario_2;
                    horario_2 = new HorarioAgenda();
                    horario_2.setData(horario.getData());
                    horario_2.setDataHora(horario.getDataHora());
                    horario_2.setDataHora(Util.udate(data));
                    horario_2.setDataAgendamento(Util.dHoje());
                    horario_2.setDataAtualizacao(Util.dHoje());
                    horario_2.setCodAntigoPaciente(horario.getCodAntigoPaciente());
                    horario_2.setCodPaciente(horario.getCodPaciente());
                    horario_2.setIntervalos(1);
                    horario_2.setPaciente(horario.getPaciente());
                    manager.persist(horario_2);
//                    manager.getTransaction().commit();
//                    manager.close();
                } */
            }            
            resultado = Boolean.TRUE;
        } catch (CampoEmBrancoException ceb) {
            ShowDialog("EX", ceb.getMessage(), null,this.getStage());
        }
        return resultado;
    }

    private void apagaPaciente() {
        nomePaciente.clear();
        codPaciente.clear();
        codAntPaciente.clear();
        telefone1.clear();
        telefone2.clear();
        comboConvenio.getSelectionModel().select(0);
        comboEvento.getSelectionModel().select(0);
        observacoes.clear();
    }
    
    public void executaTarefa(ActionEvent e) {
        manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();        
        if (descarregaHorario()) {
            if (tipoTar.equals(TipoTarefa.AGENDAR) || tipoTar.equals(TipoTarefa.ENCAIXAR)) {
                horario.setDataAgendamento(Util.dHoje());
                horario.setDataAtualizacao(Util.dHoje());
                manager.persist(horario);
            } else if (tipoTar.equals(TipoTarefa.CONFIRMAR)) {
                horario.setDataAtualizacao(Util.dHoje());
                manager.merge(horario);
            } else if (tipoTar.equals(TipoTarefa.EXCLUIR)) {       
                manager.remove(manager.getReference(HorarioAgenda.class, horario.getId()));  
            }        
            manager.getTransaction().commit();
            manager.close();
            this.stage.close();
        }
    }
    
    private void initCombos() {
        initComboEventos();
        initComboConvenio();        
    }
    
    private void initComboConvenio() {
        comboConvenio.getItems().add("");
        ObservableList<String> options = 
            FXCollections.observableArrayList(Convenios.getListaNomes());    
        comboConvenio.getItems().addAll(options);        
    }
    
    private void initComboEventos() {
        listaEventos.add("");
        listaEventos.add("Consulta");
        listaEventos.add("Emergência");
        listaEventos.add("Primeira Vez");
        listaEventos.add("Primeira Vez - Dois Horários");
        listaEventos.add("Receita");
        listaEventos.add("Receita Controlada");
        listaEventos.add("Representante");
        listaEventos.add("Reunião");
        listaEventos.add("Revisão");
        listaEventos.add("Risco Cirúrgico");
        comboEvento.setItems(FXCollections.observableList(listaEventos));
    }
    
    private void initBtns() {
        
        if (tipoTar.equals(TipoTarefa.AGENDAR)) {
            btnAgendar.setText("Agendar");
        } else if (tipoTar.equals(TipoTarefa.ENCAIXAR)) {
            btnAgendar.setText("Encaixar");
        } else {
            btnAgendar.setText("Atualizar");
        }
        
//        btnExcluir.setDisable(!tipoTar.equals(TipoTarefa.EXCLUIR));        
//        btnTransferir.setDisable(true);        
        rbCompareceu.setDisable(!(tipoTar.equals(TipoTarefa.AGENDAR))&&!(tipoTar.equals(TipoTarefa.ENCAIXAR))&&!(tipoTar.equals(TipoTarefa.CONFIRMAR)));
        rbFaltou.setDisable(!(tipoTar.equals(TipoTarefa.AGENDAR))&&!(tipoTar.equals(TipoTarefa.ENCAIXAR))&&!(tipoTar.equals(TipoTarefa.CONFIRMAR)));
        rbAtendido.setDisable(!(tipoTar.equals(TipoTarefa.AGENDAR))&&!(tipoTar.equals(TipoTarefa.ENCAIXAR))&&!(tipoTar.equals(TipoTarefa.CONFIRMAR)));
        rbNaoAtendido.setDisable(!(tipoTar.equals(TipoTarefa.AGENDAR))&&!(tipoTar.equals(TipoTarefa.ENCAIXAR))&&!(tipoTar.equals(TipoTarefa.CONFIRMAR)));        
        //btnProcNome.setDisable(true);
    }
    
    private void habilEdicaoFired(Boolean status) {
        codAntPaciente.setEditable(status);
        codPaciente.setEditable(status);
        nomePaciente.setEditable(status);        
    }
    
    public void btnProcNomeFired(ActionEvent ae) {        
        String nome = nomePaciente.getText();
        if (nome!=null) {
            nome = "%" + nome.replace(" ", "%");            
            sopPacientes.setAll(FXCollections.observableArrayList(Pacientes.getObsListaWithNome(nome)));
        }
    }
    
    public void btnApagaNomeFired(ActionEvent ae) {
        sopPaciente.set(null);
    }
    
}
