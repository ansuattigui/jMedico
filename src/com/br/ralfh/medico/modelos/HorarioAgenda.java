package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "tab_consultas", schema = "docplus")
public class HorarioAgenda implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "chaveprimaria")
    private Integer id;   
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "dataChegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaChegada;
    @Column(name="intervalos")
    private Integer intervalos;
    @Column(name = "idPaciente")
    private Integer codPaciente;
    @Column(name = "cod_antigo_paciente")
    private Integer codAntigoPaciente;
    @Column(name = "nomecliente")
    private String paciente;
    @Column(name = "telefone1")
    private String telefone1;
    @Column(name = "telefone2")
    private String telefone2;
    @Column(name = "presente")
    private Boolean presente;    
    @Column(name = "atendido")
    private Boolean atendido;    
    @Column(name = "ecg")
    private Boolean ecg;    
    @Column(name = "encaixe")
    private Boolean encaixe;  
    @Column(name="evento")
    private  String evento;
    @Column(name="cod_convenio")
    private Integer convenio;
    @Column(name="observacoes")
    private String observacoes;
    @Column(name="dataAgendamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;
    @Column(name="dataAtulizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    
    public HorarioAgenda() {
        codPaciente = 0;
        codAntigoPaciente = 0;
        intervalos = 1;
        presente = Boolean.FALSE;
        atendido = Boolean.FALSE;
        ecg = Boolean.FALSE;
        encaixe = Boolean.FALSE;        
    }
    
    public HorarioAgenda(Integer ha) {
        id = ha;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the dataHora
     */
    public Date getDataHora() {
        return dataHora;
    }

    /**
     * @param dataHora the dataHora to set
     */
    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * @return the paciente
     */
    public String getPaciente() {        
        return (paciente==null?"":paciente);
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the presente
     */
    public Boolean getPresente() {
//        return (presente);
        return (presente==null ? false : presente);
    }

    /**
     * @param presente
     */
    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    /**
     * @return the atendido
     */
    public Boolean getAtendido() {
        return atendido;
    }

    /**
     * @param atendido the atendido to set
     */
    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    /**
     * @return the ecg
     */
    public Boolean getEcg() {
        return ecg;
    }

    /**
     * @param ecg the ecg to set
     */
    public void setEcg(Boolean ecg) {
        this.ecg = ecg;
    }

    /**
     * @return the encaixe
     */
    public Boolean getEncaixe() {
        return encaixe;
    }

    /**
     * @param encaixe the encaixe to set
     */
    public void setEncaixe(Boolean encaixe) {
        this.encaixe = encaixe;
    }

    /**
     * @return the codAntigoPaciente
     */
    public Integer getCodAntigoPaciente() {
        return codAntigoPaciente;
    }

    /**
     * @param codAntigoPaciente the codAntigoPaciente to set
     */
    public void setCodAntigoPaciente(Integer codAntigoPaciente) {
        this.codAntigoPaciente = codAntigoPaciente;
    }

    /**
     * @return the telefone1
     */
    public String getTelefone1() {
        return telefone1;
    }

    /**
     * @param telefone1 the telefone1 to set
     */
    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    /**
     * @return the telefone2
     */
    public String getTelefone2() {
        return telefone2;
    }

    /**
     * @param telefone2 the telefone2 to set
     */
    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }
    
    public boolean isEmpty() {
        return (getCodAntigoPaciente() == null);
    }

    /**
     * @return the codPaciente
     */
    public Integer getCodPaciente() {
        return codPaciente;
    }

    /**
     * @param codPaciente the codPaciente to set
     */
    public void setCodPaciente(Integer codPaciente) {
        this.codPaciente = codPaciente;
    }

    /**
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }
    

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * @return the intervalos
     */
    public Integer getIntervalos() {
        return intervalos;
    }

    /**
     * @param intervalos the intervalos to set
     */
    public void setIntervalos(Integer intervalos) {
        this.intervalos = intervalos;
    }

    /**
     * @return the horaChegada
     */
    public Date getHoraChegada() {
        return horaChegada;
    }

    /**
     * @param horaChegada the horaChegada to set
     */
    public void setHoraChegada(Date horaChegada) {
        this.horaChegada = horaChegada;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the dataAgendamento
     */
    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    /**
     * @param dataAgendamento the dataAgendamento to set
     */
    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    /**
     * @return the dataAtualizacao
     */
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * @param dataAtualizacao the dataAtualizacao to set
     */
    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /**
     * @return the convenio
     */
    public Integer getConvenio() {
        return convenio;
    }

    /**
     * @param convenio the convenio to set
     */
    public void setConvenio(Integer convenio) {
        this.convenio = convenio;
    }
    
}