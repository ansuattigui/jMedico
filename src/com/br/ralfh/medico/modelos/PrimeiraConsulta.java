package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.FormatoNumericoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "tab_primconsulta", schema = "docplus")
public class PrimeiraConsulta implements Serializable {    
    @Id 
    @OneToOne (cascade = CascadeType.MERGE) @JoinColumn(name = "cod_paciente")
//    @Column(name = "cod_paciente")
    private Paciente id;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "data_atualizacao")
    @Temporal(TemporalType.DATE)
    private Date dataatual;
    @Column(name = "queixa_principal")
    private String qp;
    @Column(name = "hda")
    private String hda;
    @Column(name = "has")
    private Boolean has;
    @Column(name = "has_hf")
    private Boolean hashf;
    @Column(name = "dm")
    private Boolean dm;
    @Column(name = "dm_hf")
    private Boolean dmhf;
    @Column(name = "dislipidemia")
    private Boolean dislipidemia;
    @Column(name = "dislipidemia_hf")
    private Boolean dislipidemiahf;
    @Column(name = "ave")
    private Boolean ave;
    @Column(name = "ave_hf")
    private Boolean avehf;
    @Column(name = "iam")
    private Boolean iam;
    @Column(name = "iam_hf")
    private Boolean iamhf;
    @Column(name = "doencaspulmon")
    private Boolean doencpulmon;
    @Column(name = "doencaspulmon_hf")
    private Boolean doencpulmonhf;
    @Column(name = "relacao_doencaspulmon")
    private String reldoencaspulmon;
    @Column(name = "vpi")
    private Boolean vpi;
    @Column(name = "hepatite")
    private Boolean hepatite;
    @Column(name = "hepatite_tipo")
    private Boolean hepatitetipo;
    @Column(name = "malaria")
    private Boolean malaria;
    @Column(name = "bk")
    private Boolean bk;
    @Column(name = "dst")
    private Boolean dst;
    @Column(name = "infecurina")
    private Boolean infecurina;
    @Column(name = "constipacao")
    private Boolean constipacao;
    @Column(name = "constipacao_obs")
    private String constipacaoobs;    
    @Column(name = "etilismo")
    private Boolean etilismo;    
    @Column(name = "etilismo_tempo")
    private Integer etilismotempo;    
    @Column(name = "etilismo_tipo")
    private String etilismotipo;    
    @Column(name = "tabagismo")
    private Boolean tabagismo;    
    @Column(name = "tabagismo_tempo")
    private Integer tabagismotempo;    
    @Column(name = "tabagismo_tipo")
    private String tabagismotipo;    
    @Column(name = "Insonia")
    private Boolean insonia;    
    @Column(name = "Sedentarismo")
    private Boolean sedentarismo;    
    @Column(name = "Stress")
    private Boolean stress;    
    @Column(name = "medicamentos")
    private Boolean medicamentos;    
    @Column(name = "relacao_medicamentos")
    private String relmedicamentos;    
    @Column(name = "alergia")
    private Boolean alergia;    
    @Column(name = "relacao_alergias")
    private String relalergias;    
    @Column(name = "cirurgias")
    private Boolean cirurgia;    
    @Column(name = "relacao_cirurgias")
    private String relcirurgias;    
    @Column(name = "menarca")
    private Boolean menarca;    
    @Column(name = "menarca_idade")
    private Integer menarcaidade;    
    @Column(name = "gestacoes")
    private Boolean gestacoes;    
    @Column(name = "gestacoes_numero")
    private Integer gestanumeros;    
    @Column(name = "menopausa")
    private Boolean menopausa;    
    @Column(name = "menopausa_idade")
    private Integer menopidade;    
    @Column(name = "altura")
    private Integer altura;    
    @Column(name = "peso")
    private Integer peso;    
    @Column(name = "ectoscopia")
    private String ectoscopia;    
    @Column(name = "apar_circul")
    private String aparcircul;    
    @Column(name = "pa_sist")
    private Integer pa_sist;    
    @Column(name = "pa_diast")
    private Integer pa_diast;    
    @Column(name = "freq_card")
    private Integer freqcard;    
    @Column(name = "pulso")
    private Integer pulso;    
    @Column(name = "apar_resp")
    private String apar_resp;    
    @Column(name = "freq_resp")
    private Integer freq_resp;    
    @Column(name = "pescoco")
    private String pescoco;    
    @Column(name = "jugulares")
    private String jugulares;    
    @Column(name = "tireoide")
    private String tireoide;    
    @Column(name = "ganglios")
    private String ganglios;    
    @Column(name = "abdome")
    private String abdome;    
    @Column(name = "memb_sup")
    private String membsup;    
    @Column(name = "memb_inf")
    private String membinf;    
    @Column(name = "sist_nerv")
    private String sistnerv;    
    @Column(name = "outros")
    private String outros;    
    @Column(name = "conduta_terapeutica")
    private String condterap;    
    
    private BigDecimal colestTotPrimCons;
    private BigDecimal colestHDLPrimCons;
    private BigDecimal colestLDLPrimCons;
    private BigDecimal triglicPrimCons;
    private BigDecimal acUriPrimCons;
    private BigDecimal hemoGlicPrimCons;
    private BigDecimal tirT3PrimCons;
    private BigDecimal tirT4PrimCons;
    private BigDecimal tirT4LivPrimCons;
    private BigDecimal tirTSHPrimCons;
    private BigDecimal psaLivPrimCons;
    private BigDecimal psaTotPrimCons;
    private BigDecimal psaRelPrimCons;
    private BigDecimal hepTGOPrimCons;
    private BigDecimal hepTGPPrimCons;
    private BigDecimal hepFAPrimCons;
    private BigDecimal hepGGTPrimCons;
    private String urinaPrimCons;
    private String fezesPrimCons;    
    private Boolean optNegFezesPrimCons;
    private Boolean optOutFezesPrimCons;
    private Boolean optNormECGPrimCons;
    private Boolean optOutECGPrimCons;
    private String ecgPrimCons;
    private Boolean optNormPAPPrimCons;
    private Boolean optOutPAPPrimCons;
    private String papPrimCons;
    private String rxAbdomPrimCons;    
    private String rxOutrPrimCons;
    private String outUSPrimCons;
    private String tcPrimCons;
    
    public PrimeiraConsulta() {
        this.altura = 0;
        this.etilismotempo = 0;
        this.freq_resp = 0;
        this.freqcard = 0;
        this.gestanumeros = 0;
        this.menarcaidade = 0;
        this.menopidade = 0;
        this.pa_diast = 0;
        this.pa_sist = 0;
        this.peso = 0;
        this.pulso = 0;
        this.tabagismotempo = 0;
        
        this.abdome = "Flácido, sem visceromegalia";
        this.apar_resp = "mv audível, sem r.a.";
        this.aparcircul = "";
        this.condterap = "";
        this.constipacaoobs = "";
        this.ectoscopia = "Mucosas coradas, anictéricas";
        this.etilismotipo = "";
        this.ganglios = "";
        this.hda = "";
        this.jugulares = "";
        this.membinf = "";
        this.membsup = "";
        this.outros = "";
        this.pescoco = "";
        this.qp = "";
        this.relalergias = "";
        this.relcirurgias = "";
        this.reldoencaspulmon = "";
        this.relmedicamentos = "";
        this.sistnerv = "";
        this.tabagismotipo = "";
        this.tireoide = "";        
        
        this.alergia = Boolean.FALSE;
        this.ave = Boolean.FALSE;
        this.avehf = Boolean.FALSE;
        this.bk = Boolean.FALSE;
        this.cirurgia = Boolean.FALSE;
        this.constipacao = Boolean.FALSE;
        this.dislipidemia = Boolean.FALSE;
        this.dislipidemiahf = Boolean.FALSE;
        this.dm = Boolean.FALSE;
        this.dmhf = Boolean.FALSE;
        this.doencpulmon = Boolean.FALSE;
        this.doencpulmonhf = Boolean.FALSE;
        this.dst = Boolean.FALSE;
        this.etilismo = Boolean.FALSE;
        this.gestacoes = Boolean.FALSE;
        this.has = Boolean.FALSE;
        this.hashf = Boolean.FALSE;
        this.hepatite = Boolean.FALSE;
        this.hepatitetipo = Boolean.FALSE;
        this.iam = Boolean.FALSE;
        this.iamhf = Boolean.FALSE;
        this.infecurina = Boolean.FALSE;
        this.insonia = Boolean.FALSE;
        this.malaria = Boolean.FALSE;
        this.medicamentos = Boolean.FALSE;
        this.menarca = Boolean.FALSE;
        this.menopausa = Boolean.FALSE;
        this.sedentarismo = Boolean.FALSE;
        this.stress = Boolean.FALSE;
        this.tabagismo = Boolean.FALSE;
        this.vpi = Boolean.FALSE;  
        
        
    }

    /**
     * @return the id
     */
    public Paciente getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Paciente id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param dataprimcons  the data to set
     */
    public void setData(Date dataprimcons) {
        this.data = dataprimcons;
    }

    /**
     * @return the dataatual
     */
    public Date getDataatual() {
        return dataatual;
    }

    /**
     * @param dataatual the dataatual to set
     */
    public void setDataatual(Date dataatual) {
        this.dataatual = dataatual;
    }

    /**
     * @return the qp
     */
    public String getQp() {
        return qp;
    }

    /**
     * @param qp the qp to set
     */
    public void setQp(String qp) {
        this.qp = qp;
    }

    /**
     * @return the hda
     */
    public String getHda() {
        return hda;
    }

    /**
     * @param hda the hda to set
     */
    public void setHda(String hda) {
        this.hda = hda;
    }

    /**
     * @return the has
     */
    public Boolean getHas() {
        return has;
    }

    /**
     * @param has the has to set
     */
    public void setHas(Boolean has) {
        this.has = has;
    }

    /**
     * @return the hashf
     */
    public Boolean getHashf() {
        return hashf;
    }

    /**
     * @param hashf the hashf to set
     */
    public void setHashf(Boolean hashf) {
        this.hashf = hashf;
    }

    /**
     * @return the dm
     */
    public Boolean getDm() {
        return dm;
    }

    /**
     * @param dm the dm to set
     */
    public void setDm(Boolean dm) {
        this.dm = dm;
    }

    /**
     * @return the dmhf
     */
    public Boolean getDmhf() {
        return dmhf;
    }

    /**
     * @param dmhf the dmhf to set
     */
    public void setDmhf(Boolean dmhf) {
        this.dmhf = dmhf;
    }

    /**
     * @return the dislipidemia
     */
    public Boolean getDislipidemia() {
        return dislipidemia;
    }

    /**
     * @param dislipidemia the dislipidemia to set
     */
    public void setDislipidemia(Boolean dislipidemia) {
        this.dislipidemia = dislipidemia;
    }

    /**
     * @return the dislipidemiahf
     */
    public Boolean getDislipidemiahf() {
        return dislipidemiahf;
    }

    /**
     * @param dislipidemiahf the dislipidemiahf to set
     */
    public void setDislipidemiahf(Boolean dislipidemiahf) {
        this.dislipidemiahf = dislipidemiahf;
    }
    
    /**
     * @return the ave
     */
    public Boolean getAve() {
        return ave;
    }

    /**
     * @param ave the ave to set
     */
    public void setAve(Boolean ave) {
        this.ave = ave;
    }

    /**
     * @return the avehf
     */
    public Boolean getAvehf() {
        return avehf;
    }

    /**
     * @param avehf the avehf to set
     */
    public void setAvehf(Boolean avehf) {
        this.avehf = avehf;
    }

    /**
     * @return the iam
     */
    public Boolean getIam() {
        return iam;
    }

    /**
     * @param iam the iam to set
     */
    public void setIam(Boolean iam) {
        this.iam = iam;
    }

    /**
     * @return the iamhf
     */
    public Boolean getIamhf() {
        return iamhf;
    }

    /**
     * @param iamhf the iamhf to set
     */
    public void setIamhf(Boolean iamhf) {
        this.iamhf = iamhf;
    }

    /**
     * @return the doencpulmon
     */
    public Boolean getDoencpulmon() {
        return doencpulmon;
    }

    /**
     * @param doencpulmon the doencpulmon to set
     */
    public void setDoencpulmon(Boolean doencpulmon) {
        this.doencpulmon = doencpulmon;
    }

    /**
     * @return the doencpulmonhf
     */
    public Boolean getDoencpulmonhf() {
        return doencpulmonhf;
    }

    /**
     * @param doencpulmonhf the doencpulmonhf to set
     */
    public void setDoencpulmonhf(Boolean doencpulmonhf) {
        this.doencpulmonhf = doencpulmonhf;
    }

    /**
     * @return the reldoencaspulmon
     */
    public String getReldoencaspulmon() {
        return reldoencaspulmon;
    }

    /**
     * @param reldoencaspulmon the reldoencaspulmon to set
     */
    public void setReldoencaspulmon(String reldoencaspulmon) {
        this.reldoencaspulmon = reldoencaspulmon;
    }

    /**
     * @return the vpi
     */
    public Boolean getVpi() {
        return vpi;
    }

    /**
     * @param vpi the vpi to set
     */
    public void setVpi(Boolean vpi) {
        this.vpi = vpi;
    }

    /**
     * @return the hepatite
     */
    public Boolean getHepatite() {
        return hepatite;
    }

    /**
     * @param hepatite the hepatite to set
     */
    public void setHepatite(Boolean hepatite) {
        this.hepatite = hepatite;
    }

    /**
     * @return the hepatitetipo
     */
    public Boolean getHepatitetipo() {
        return hepatitetipo;
    }

    /**
     * @param hepatitetipo the hepatitetipo to set
     */
    public void setHepatitetipo(Boolean hepatitetipo) {
        this.hepatitetipo = hepatitetipo;
    }

    /**
     * @return the malaria
     */
    public Boolean getMalaria() {
        return malaria;
    }

    /**
     * @param malaria the malaria to set
     */
    public void setMalaria(Boolean malaria) {
        this.malaria = malaria;
    }

    /**
     * @return the bk
     */
    public Boolean getBk() {
        return bk;
    }

    /**
     * @param bk the bk to set
     */
    public void setBk(Boolean bk) {
        this.bk = bk;
    }

    /**
     * @return the dst
     */
    public Boolean getDst() {
        return dst;
    }

    /**
     * @param dst the dst to set
     */
    public void setDst(Boolean dst) {
        this.dst = dst;
    }

    /**
     * @return the infecurina
     */
    public Boolean getInfecurina() {
        return infecurina;
    }

    /**
     * @param infecurina the infecurina to set
     */
    public void setInfecurina(Boolean infecurina) {
        this.infecurina = infecurina;
    }

    /**
     * @return the constipacao
     */
    public Boolean getConstipacao() {
        return constipacao;
    }

    /**
     * @param constipacao the constipacao to set
     */
    public void setConstipacao(Boolean constipacao) {
        this.constipacao = constipacao;
    }

    /**
     * @return the constipacaoobs
     */
    public String getConstipacaoobs() {
        return constipacaoobs;
    }

    /**
     * @param constipacaoobs the constipacaoobs to set
     */
    public void setConstipacaoobs(String constipacaoobs) {
        this.constipacaoobs = constipacaoobs;
    }

    /**
     * @return the etilismo
     */
    public Boolean getEtilismo() {
        return etilismo;
    }

    /**
     * @param etilismo the etilismo to set
     */
    public void setEtilismo(Boolean etilismo) {
        this.etilismo = etilismo;
    }

    /**
     * @return the etilismotempo
     */
    public Integer getEtilismotempo() {
        return etilismotempo;
    }

    /**
     * @param etilismotempo the etilismotempo to set
     */
    public void setEtilismotempo(Integer etilismotempo) {
        this.etilismotempo = etilismotempo;
    }

    /**
     * @return the etilismotipo
     */
    public String getEtilismotipo() {
        return etilismotipo;
    }

    /**
     * @param etilismotipo the etilismotipo to set
     */
    public void setEtilismotipo(String etilismotipo) {
        this.etilismotipo = etilismotipo;
    }

    /**
     * @return the tabagismo
     */
    public Boolean getTabagismo() {
        return tabagismo;
    }

    /**
     * @param tabagismo the tabagismo to set
     */
    public void setTabagismo(Boolean tabagismo) {
        this.tabagismo = tabagismo;
    }

    /**
     * @return the tabagismotempo
     */
    public Integer getTabagismotempo() {
        return tabagismotempo;
    }

    /**
     * @param tabagismotempo the tabagismotempo to set
     */
    public void setTabagismotempo(Integer tabagismotempo) {
        this.tabagismotempo = tabagismotempo;
    }

    /**
     * @return the tabagismotipo
     */
    public String getTabagismotipo() {
        return tabagismotipo;
    }

    /**
     * @param tabagismotipo the tabagismotipo to set
     */
    public void setTabagismotipo(String tabagismotipo) {
        this.tabagismotipo = tabagismotipo;
    }

    /**
     * @return the insonia
     */
    public Boolean getInsonia() {
        return insonia;
    }

    /**
     * @param insonia the insonia to set
     */
    public void setInsonia(Boolean insonia) {
        this.insonia = insonia;
    }

    /**
     * @return the sedentarismo
     */
    public Boolean getSedentarismo() {
        return sedentarismo;
    }

    /**
     * @param sedentarismo the sedentarismo to set
     */
    public void setSedentarismo(Boolean sedentarismo) {
        this.sedentarismo = sedentarismo;
    }

    /**
     * @return the stress
     */
    public Boolean getStress() {
        return stress;
    }

    /**
     * @param stress the stress to set
     */
    public void setStress(Boolean stress) {
        this.stress = stress;
    }

    /**
     * @return the medicamentos
     */
    public Boolean getMedicamentos() {
        return medicamentos;
    }

    /**
     * @param medicamentos the medicamentos to set
     */
    public void setMedicamentos(Boolean medicamentos) {
        this.medicamentos = medicamentos;
    }

    /**
     * @return the relmedicamentos
     */
    public String getRelmedicamentos() {
        return relmedicamentos;
    }

    /**
     * @param relmedicamentos the relmedicamentos to set
     */
    public void setRelmedicamentos(String relmedicamentos) {
        this.relmedicamentos = relmedicamentos;
    }

    /**
     * @return the alergia
     */
    public Boolean getAlergia() {
        return alergia;
    }

    /**
     * @param alergia the alergia to set
     */
    public void setAlergia(Boolean alergia) {
        this.alergia = alergia;
    }

    /**
     * @return the relalergias
     */
    public String getRelalergias() {
        return relalergias;
    }

    /**
     * @param relalergias the relalergias to set
     */
    public void setRelalergias(String relalergias) {
        this.relalergias = relalergias;
    }

    /**
     * @return the cirugia
     */
    public Boolean getCirurgia() {
        return cirurgia;
    }

    /**
     * @param cirurgia the cirugia to set
     */
    public void setCirurgia(Boolean cirurgia) {
        this.cirurgia = cirurgia;
    }

    /**
     * @return the relcirurgias
     */
    public String getRelcirurgias() {
        return relcirurgias;
    }

    /**
     * @param relcirurgias the relcirurgias to set
     */
    public void setRelcirurgias(String relcirurgias) {
        this.relcirurgias = relcirurgias;
    }

    /**
     * @return the menarca
     */
    public Boolean getMenarca() {
        return menarca;
    }

    /**
     * @param menarca the menarca to set
     */
    public void setMenarca(Boolean menarca) {
        this.menarca = menarca;
    }

    /**
     * @return the menarcaidade
     */
    public Integer getMenarcaidade() {
        return menarcaidade;
    }

    /**
     * @param menarcaidade the menarcaidade to set
     */
    public void setMenarcaidade(Integer menarcaidade) {
        this.menarcaidade = menarcaidade;
    }

    /**
     * @return the gestacoes
     */
    public Boolean getGestacoes() {
        return gestacoes;
    }

    /**
     * @param gestacoes the gestacoes to set
     */
    public void setGestacoes(Boolean gestacoes) {
        this.gestacoes = gestacoes;
    }

    /**
     * @return the gestanumeros
     */
    public Integer getGestanumeros() {
        return gestanumeros;
    }

    /**
     * @param gestanumeros the gestanumeros to set
     */
    public void setGestanumeros(Integer gestanumeros) {
        this.gestanumeros = gestanumeros;
    }

    /**
     * @return the menopausa
     */
    public Boolean getMenopausa() {
        return menopausa;
    }

    /**
     * @param menopausa the menopausa to set
     */
    public void setMenopausa(Boolean menopausa) {
        this.menopausa = menopausa;
    }

    /**
     * @return the menopidade
     */
    public Integer getMenopidade() {
        return menopidade;
    }

    /**
     * @param menopidade the menopidade to set
     */
    public void setMenopidade(Integer menopidade) {
        this.menopidade = menopidade;
    }

    /**
     * @return the altura
     */
    public Integer getAltura() {
        return altura;
    }

    /**
     * @param altura the altura to set
     */
    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    /**
     * @return the peso
     */
    public Integer getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    /**
     * @return the ectoscopia
     */
    public String getEctoscopia() {
        return ectoscopia;
    }

    /**
     * @param ectoscopia the ectoscopia to set
     */
    public void setEctoscopia(String ectoscopia) {
        this.ectoscopia = ectoscopia;
    }

    /**
     * @return the aparcircul
     */
    public String getAparcircul() {
        return aparcircul;
    }

    /**
     * @param aparcircul the aparcircul to set
     */
    public void setAparcircul(String aparcircul) {
        this.aparcircul = aparcircul;
    }

    /**
     * @return the pa_sist
     */
    public Integer getPa_sist() {
        return pa_sist;
    }

    /**
     * @param pa_sist the pa_sist to set
     */
    public void setPa_sist(Integer pa_sist) {
        this.pa_sist = pa_sist;
    }

    /**
     * @return the pa_diast
     */
    public Integer getPa_diast() {
        return pa_diast;
    }

    /**
     * @param pa_diast the pa_diast to set
     */
    public void setPa_diast(Integer pa_diast) {
        this.pa_diast = pa_diast;
    }

    /**
     * @return the freqcard
     */
    public Integer getFreqcard() {
        return freqcard;
    }

    /**
     * @param freqcard the freqcard to set
     */
    public void setFreqcard(Integer freqcard) {
        this.freqcard = freqcard;
    }

    /**
     * @return the pulso
     */
    public Integer getPulso() {
        return pulso;
    }

    /**
     * @param pulso the pulso to set
     */
    public void setPulso(Integer pulso) {
        this.pulso = pulso;
    }

    /**
     * @return the apar_resp
     */
    public String getApar_resp() {
        return apar_resp;
    }

    /**
     * @param apar_resp the apar_resp to set
     */
    public void setApar_resp(String apar_resp) {
        this.apar_resp = apar_resp;
    }

    /**
     * @return the freq_resp
     */
    public Integer getFreq_resp() {
        return freq_resp;
    }

    /**
     * @param freq_resp the freq_resp to set
     */
    public void setFreq_resp(Integer freq_resp) {
        this.freq_resp = freq_resp;
    }

    /**
     * @return the pescoco
     */
    public String getPescoco() {
        return pescoco;
    }

    /**
     * @param pescoco the pescoco to set
     */
    public void setPescoco(String pescoco) {
        this.pescoco = pescoco;
    }

    /**
     * @return the jugulares
     */
    public String getJugulares() {
        return jugulares;
    }

    /**
     * @param jugulares the jugulares to set
     */
    public void setJugulares(String jugulares) {
        this.jugulares = jugulares;
    }

    /**
     * @return the tireoide
     */
    public String getTireoide() {
        return tireoide;
    }

    /**
     * @param tireoide the tireoide to set
     */
    public void setTireoide(String tireoide) {
        this.tireoide = tireoide;
    }

    /**
     * @return the ganglios
     */
    public String getGanglios() {
        return ganglios;
    }

    /**
     * @param ganglios the ganglios to set
     */
    public void setGanglios(String ganglios) {
        this.ganglios = ganglios;
    }

    /**
     * @return the abdome
     */
    public String getAbdome() {
        return abdome;
    }

    /**
     * @param abdome the abdome to set
     */
    public void setAbdome(String abdome) {
        this.abdome = abdome;
    }

    /**
     * @return the membsup
     */
    public String getMembsup() {
        return membsup;
    }

    /**
     * @param membsup the membsup to set
     */
    public void setMembsup(String membsup) {
        this.membsup = membsup;
    }

    /**
     * @return the membinf
     */
    public String getMembinf() {
        return membinf;
    }

    /**
     * @param membinf the membinf to set
     */
    public void setMembinf(String membinf) {
        this.membinf = membinf;
    }

    /**
     * @return the sistnerv
     */
    public String getSistnerv() {
        return sistnerv;
    }

    /**
     * @param sistnerv the sistnerv to set
     */
    public void setSistnerv(String sistnerv) {
        this.sistnerv = sistnerv;
    }

    /**
     * @return the outros
     */
    public String getOutros() {
        return outros;
    }

    /**
     * @param outros the outros to set
     */
    public void setOutros(String outros) {
        this.outros = outros;
    }

    /**
     * @return the condterap
     */
    public String getCondterap() {
        return condterap;
    }

    /**
     * @param condterap the condterap to set
     */
    public void setCondterap(String condterap) {
        this.condterap = condterap;
    }

    /**
     * @return the colestTotPrimCons
     */
    public String getColestTotPrimCons() {                        
        return (colestTotPrimCons==null?"":colestTotPrimCons.toPlainString());
    }

    /**
     * @param colestTotPrimCons the colestTotPrimCons to set 
     * @throws com.br.ralfh.medico.exceptions.FormatoNumericoInvalidoException 
     */
    public void setColestTotPrimCons(String colestTotPrimCons) {
        if (!colestTotPrimCons.isEmpty()) {
            try {
                this.colestTotPrimCons = new BigDecimal(colestTotPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol Total inválido");
            }
        }
    }

    /**
     * @return the colestHDLPrimCons
     */
    public String getColestHDLPrimCons() {
        return (colestHDLPrimCons==null?"":colestHDLPrimCons.toPlainString());
    }

    /**
     * @param colestHDLPrimCons the colestHDLPrimCons to set
     */
    public void setColestHDLPrimCons(String colestHDLPrimCons) {
        if (!colestHDLPrimCons.isEmpty()) {
            try {
                this.colestHDLPrimCons = new BigDecimal(colestHDLPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol HDL inválido");
            }
        }
    }

    /**
     * @return the colestLDLPrimCons
     */
    public String getColestLDLPrimCons() {
        return (colestLDLPrimCons==null?"":colestLDLPrimCons.toPlainString());
    }

    /**
     * @param colestLDLPrimCons the colestLDLPrimCons to set
     */
    public void setColestLDLPrimCons(String colestLDLPrimCons) {
        if (!colestLDLPrimCons.isEmpty()) {
            try {
                this.colestLDLPrimCons = new BigDecimal(colestLDLPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol LDL inválido");
            }
        }
    }

    /**
     * @return the triglicPrimCons
     */
    public String getTriglicPrimCons() {
        return (triglicPrimCons==null?"":triglicPrimCons.toPlainString());
    }

    /**
     * @param triglicPrimCons the triglicPrimCons to set
     */
    public void setTriglicPrimCons(String triglicPrimCons) { 
        if (!triglicPrimCons.isEmpty()) {
            try {
                this.triglicPrimCons = new BigDecimal(triglicPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor dos Triglicerídeos inválido");
            }
        }
    }

    /**
     * @return the acUriPrimCons
     */
    public String getAcUriPrimCons() {
        return (acUriPrimCons==null?"":acUriPrimCons.toPlainString());
    }

    /**
     * @param acUriPrimCons the acUriPrimCons to set
     */
    public void setAcUriPrimCons(String acUriPrimCons) {
        if (!acUriPrimCons.isEmpty()) {
            try {
                this.acUriPrimCons = new BigDecimal(acUriPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Ácido Úrico inválido");
            }
        }
    }

    /**
     * @return the hemoGlicPrimCons
     */
    public String getHemoGlicPrimCons() {
        return (hemoGlicPrimCons==null?"":hemoGlicPrimCons.toPlainString());
    }

    /**
     * @param hemoGlicPrimCons the hemoGlicPrimCons to set
     */
    public void setHemoGlicPrimCons(String hemoGlicPrimCons) {
        if (!hemoGlicPrimCons.isEmpty()) {
            try {
                this.hemoGlicPrimCons = new BigDecimal(hemoGlicPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor da Hemoglobina Glicada inválido");
            }
        }
    }

    /**
     * @return the tirT3PrimCons
     */
    public String getTirT3PrimCons() {
        return (tirT3PrimCons==null?"":tirT3PrimCons.toPlainString());
    }

    /**
     * @param tirT3PrimCons the tirT3PrimCons to set
     */
    public void setTirT3PrimCons(String tirT3PrimCons) {
        if (!tirT3PrimCons.isEmpty()) {
            try {
                this.tirT3PrimCons = new BigDecimal(tirT3PrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T3 inválido");
            }
        }
    }

    /**
     * @return the tirT4PrimCons
     */
    public String getTirT4PrimCons() {
        return (tirT4PrimCons==null?"":tirT4PrimCons.toPlainString());
    }

    /**
     * @param tirT4PrimCons the tirT4PrimCons to set
     */
    public void setTirT4PrimCons(String tirT4PrimCons) {
        if (!tirT4PrimCons.isEmpty()) {
            try {
                this.tirT4PrimCons = new BigDecimal(tirT4PrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T4 inválido");
            }
        }
    }

    /**
     * @return the tirT4LivPrimCons
     */
    public String getTirT4LivPrimCons() {
        return (tirT4LivPrimCons==null?"":tirT4LivPrimCons.toPlainString());
    }

    /**
     * @param tirT4LivPrimCons the tirT4LivPrimCons to set
     */
    public void setTirT4LivPrimCons(String tirT4LivPrimCons) {
        if (!tirT4LivPrimCons.isEmpty()) {
            try {
                this.tirT4LivPrimCons = new BigDecimal(tirT4LivPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T4 Livre inválido");
            }
        }
    }

    /**
     * @return the tirTSHPrimCons
     */
    public String getTirTSHPrimCons() {
        return (tirTSHPrimCons==null?"":tirTSHPrimCons.toPlainString());
    }

    /**
     * @param tirTSHPrimCons the tirTSHPrimCons to set
     */
    public void setTirTSHPrimCons(String tirTSHPrimCons) {
        if (!tirTSHPrimCons.isEmpty()) {
            try {
                this.tirTSHPrimCons = new BigDecimal(tirTSHPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TSH inválido");
            }
        }
    }

    /**
     * @return the psaLivPrimCons
     */
    public String getPsaLivPrimCons() {
        return (psaLivPrimCons==null?"":psaLivPrimCons.toPlainString());
    }

    /**
     * @param psaLivPrimCons the psaLivPrimCons to set
     */
    public void setPsaLivPrimCons(String psaLivPrimCons) {
        if (!psaLivPrimCons.isEmpty()) {
            try {
                this.psaLivPrimCons = new BigDecimal(psaLivPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do PSA Livre inválido");
            }
        }
    }

    /**
     * @return the psaTotPrimCons
     */
    public String getPsaTotPrimCons() {
        return (psaTotPrimCons==null?"":psaTotPrimCons.toPlainString());
    }

    /**
     * @param psaTotPrimCons the psaTotPrimCons to set
     */
    public void setPsaTotPrimCons(String psaTotPrimCons) {
        if (!psaTotPrimCons.isEmpty()) {
            try {
                this.psaTotPrimCons = new BigDecimal(psaTotPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do PSA Total inválido");
            }
        }
    }

    /**
     * @return the psaRelPrimCons
     */
    public String getPsaRelPrimCons() {
        return (psaRelPrimCons==null?"":psaRelPrimCons.toPlainString());
    }

    /**
     * @param psaRelPrimCons the psaRelPrimCons to set
     */
    public void setPsaRelPrimCons(String psaRelPrimCons) {
        if (!psaRelPrimCons.isEmpty()) {
            try {
                this.psaRelPrimCons = new BigDecimal(psaRelPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor da Relação PSA inválido");
            }
        }
    }

    /**
     * @return the hepTGOPrimCons
     */
    public String getHepTGOPrimCons() {
        return (hepTGOPrimCons==null?"":hepTGOPrimCons.toPlainString());
    }

    /**
     * @param hepTGOPrimCons the hepTGOPrimCons to set
     */
    public void setHepTGOPrimCons(String hepTGOPrimCons) {
        if (!hepTGOPrimCons.isEmpty()) {
            try {
                this.hepTGOPrimCons = new BigDecimal(hepTGOPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TGO inválido");
            }
        }
    }

    /**
     * @return the hepTGPPrimCons
     */
    public String getHepTGPPrimCons() {
        return (hepTGPPrimCons==null?"":hepTGPPrimCons.toPlainString());
    }

    /**
     * @param hepTGPPrimCons the hepTGPPrimCons to set
     */
    public void setHepTGPPrimCons(String hepTGPPrimCons) {
        if (!hepTGPPrimCons.isEmpty()) {
            try {
                this.hepTGPPrimCons = new BigDecimal(hepTGPPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TGP inválido");
            }
        }
    }

    /**
     * @return the hepFAPrimCons
     */
    public String getHepFAPrimCons() {
        return (hepFAPrimCons==null?"":hepFAPrimCons.toPlainString());
    }

    /**
     * @param hepFAPrimCons the hepFAPrimCons to set
     */
    public void setHepFAPrimCons(String hepFAPrimCons) {
        if (!hepFAPrimCons.isEmpty()) {
            try {
                this.hepFAPrimCons = new BigDecimal(hepFAPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do F.A. inválido");
            }
        }
    }

    /**
     * @return the hepGGTPrimCons
     */
    public String getHepGGTPrimCons() {
        return (hepGGTPrimCons==null?"":hepGGTPrimCons.toPlainString());
    }

    /**
     * @param hepGGTPrimCons the hepGGTPrimCons to set
     */
    public void setHepGGTPrimCons(String hepGGTPrimCons) {
        if (!hepGGTPrimCons.isEmpty()) {
            try {
                this.hepGGTPrimCons = new BigDecimal(hepGGTPrimCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do GGT inválido");
            }
        }
    }

    /**
     * @return the urinaPrimCons
     */
    public String getUrinaPrimCons() {
        return (urinaPrimCons==null?"":urinaPrimCons);
    }

    /**
     * @param urinaPrimCons the urinaPrimCons to set
     */
    public void setUrinaPrimCons(String urinaPrimCons) {
        this.urinaPrimCons = urinaPrimCons;
    }

    /**
     * @return the fezesPrimCons
     */
    public String getFezesPrimCons() {
        return (fezesPrimCons==null?"":fezesPrimCons);
    }

    /**
     * @param fezesPrimCons the fezesPrimCons to set
     */
    public void setFezesPrimCons(String fezesPrimCons) {
        this.fezesPrimCons = fezesPrimCons;
    }

    /**
     * @return the optNegFezesPrimCons
     */
    public Boolean getOptNegFezesPrimCons() {
        return (optNegFezesPrimCons==null?false:optNegFezesPrimCons);
    }

    /**
     * @param optNegFezesPrimCons the optNegFezesPrimCons to set
     */
    public void setOptNegFezesPrimCons(Boolean optNegFezesPrimCons) {
        this.optNegFezesPrimCons = optNegFezesPrimCons;
    }

    /**
     * @return the optOutFezesPrimCons
     */
    public Boolean getOptOutFezesPrimCons() {
        return (optOutFezesPrimCons==null?false:optOutFezesPrimCons);
    }

    /**
     * @param optOutFezesPrimCons the optOutFezesPrimCons to set
     */
    public void setOptOutFezesPrimCons(Boolean optOutFezesPrimCons) {
        this.optOutFezesPrimCons = optOutFezesPrimCons;
    }

    /**
     * @return the optNormECGPrimCons
     */
    public Boolean getOptNormECGPrimCons() {
        return (optNormECGPrimCons==null?false:optNormECGPrimCons);
    }

    /**
     * @param optNormECGPrimCons the optNormECGPrimCons to set
     */
    public void setOptNormECGPrimCons(Boolean optNormECGPrimCons) {
        this.optNormECGPrimCons = optNormECGPrimCons;
    }

    /**
     * @return the optOutECGPrimCons
     */
    public Boolean getOptOutECGPrimCons() {
        return (optOutECGPrimCons==null?false:optOutECGPrimCons);
    }

    /**
     * @param optOutECGPrimCons the optOutECGPrimCons to set
     */
    public void setOptOutECGPrimCons(Boolean optOutECGPrimCons) {
        this.optOutECGPrimCons = optOutECGPrimCons;
    }

    /**
     * @return the ecgPrimCons
     */
    public String getEcgPrimCons() {
        return (ecgPrimCons==null?"":ecgPrimCons);
    }

    /**
     * @param ecgPrimCons the ecgPrimCons to set
     */
    public void setEcgPrimCons(String ecgPrimCons) {
        this.ecgPrimCons = ecgPrimCons;
    }

    /**
     * @return the papPrimCons
     */
    public String getPapPrimCons() {
        return (papPrimCons==null?"":papPrimCons);
    }

    /**
     * @param papPrimCons the papPrimCons to set
     */
    public void setPapPrimCons(String papPrimCons) {
        this.papPrimCons = papPrimCons;
    }

    /**
     * @return the rxAbdomPrimCons
     */
    public String getRxAbdomPrimCons() {
        return (rxAbdomPrimCons==null?"":rxAbdomPrimCons);
    }

    /**
     * @param rxAbdomPrimCons the rxAbdomPrimCons to set
     */
    public void setRxAbdomPrimCons(String rxAbdomPrimCons) {
        this.rxAbdomPrimCons = rxAbdomPrimCons;
    }

    /**
     * @return the rxOutrPrimCons
     */
    public String getRxOutrPrimCons() {
        return (rxOutrPrimCons==null?"":rxOutrPrimCons);
    }

    /**
     * @param rxOutrPrimCons the rxOutrPrimCons to set
     */
    public void setRxOutrPrimCons(String rxOutrPrimCons) {
        this.rxOutrPrimCons = rxOutrPrimCons;
    }

    /**
     * @return the outUSPrimCons
     */
    public String getOutUSPrimCons() {
        return (outUSPrimCons==null?"":outUSPrimCons);
    }

    /**
     * @param outUSPrimCons the outUSPrimCons to set
     */
    public void setOutUSPrimCons(String outUSPrimCons) {
        this.outUSPrimCons = outUSPrimCons;
    }

    /**
     * @return the tcPrimCons
     */
    public String getTcPrimCons() {
        return (tcPrimCons==null?"":tcPrimCons);
    }

    /**
     * @param tcPrimCons the tcPrimCons to set
     */
    public void setTcPrimCons(String tcPrimCons) {
        this.tcPrimCons = tcPrimCons;
    }

    /**
     * @return the optNormPAPPrimCons
     */
    public Boolean getOptNormPAPPrimCons() {
        return (optNormPAPPrimCons==null?false:optNormPAPPrimCons);
    }

    /**
     * @param optNormPAPPrimCons the optNormPAPPrimCons to set
     */
    public void setOptNormPAPPrimCons(Boolean optNormPAPPrimCons) {
        this.optNormPAPPrimCons = optNormPAPPrimCons;
    }

    /**
     * @return the optOutPAPPrimCons
     */
    public Boolean getOptOutPAPPrimCons() {
        return (optOutPAPPrimCons==null?false:optOutPAPPrimCons);
    }

    /**
     * @param optOutPAPPrimCons the optOutPAPPrimCons to set
     */
    public void setOptOutPAPPrimCons(Boolean optOutPAPPrimCons) {
        this.optOutPAPPrimCons = optOutPAPPrimCons;
    }
    
    
}
