/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.Util;
import com.br.ralfh.medico.exceptions.FormatoNumericoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ralfh
 */
@Entity @Table(name = "tab_consultas_subs",uniqueConstraints = {@UniqueConstraint(columnNames={"cod_paciente", "data"})} ,schema = "docplus")
public class ConsultaSubs implements Serializable {
    
    //private static long serialVersionUID = 1L;

    /**
     */
    /*
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the serialVersionUID
     */
    /*
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
8/    
    
/*    @Id 
    @Column(name = "cod_paciente")
    private Integer id;
*/    
    
    public ConsultaSubs(){    
        this.id = new ConsultaPk();
        this.abdome = "Flácido, sem visceromegalia";
        this.apar_resp = "mv audível, sem r.a.";
    }
    
    
    @Id
    private ConsultaPk id;
    
/*    
    @Id 
    @ManyToOne @JoinColumn(name = "cod_paciente")
    private Paciente id;
    @Id @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date dataCS;
*/    
    
    
    @Column(name = "queixa_principal")
    private String qp;
    @Column(name = "exame_fisico")
    private String examefisico;
    @Column(name = "conduta_terapeutica")
    private String condterap;
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
    @Column(name = "abdome")
    private String abdome;    
    @Column(name = "outros")
    private String outros;    
    
    private BigDecimal colestTotCons;
    private BigDecimal colestHDLCons;
    private BigDecimal colestLDLCons;
    private BigDecimal triglicCons;
    private BigDecimal acUriCons;
    private BigDecimal hemoGlicCons;
    private BigDecimal tirT3Cons;
    private BigDecimal tirT4Cons;
    private BigDecimal tirT4LivCons;
    private BigDecimal tirTSHCons;
    private BigDecimal psaLivCons;
    private BigDecimal psaTotCons;
    private BigDecimal psaRelCons;
    private BigDecimal hepTGOCons;
    private BigDecimal hepTGPCons;
    private BigDecimal hepFACons;
    private BigDecimal hepGGTCons;
    private String urinaCons;
    private String fezesCons;    
    private Boolean optNegFezesCons;
    private Boolean optOutFezesCons;
    private Boolean optNormECGCons;
    private Boolean optOutECGCons;
    private String ecgCons;
    private Boolean optNormPAPCons;
    private Boolean optOutPAPCons;
    private String papCons;
    private String rxAbdomCons;    
    private String rxOutrCons;
    private String outUSCons;
    private String tcCons;
    

/*    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

/*    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultaSubs)) {
            return false;
        }
        ConsultaSubs other = (ConsultaSubs) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.id.equals(other.id));
    }*/
     
/*    
    @Override
    public String toString() {
        return "com.br.ralfh.medico.modelos.ConsultaSub[ cod_paciente=" + getId() + " ]";
    }
*/


    /**
     * @return the id
     */
    /*
    public Paciente getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    /*
    public void setId(Paciente id) {
        this.id = id;
    }

    /**
     * @return the dataCS
     */
    /*
    public Date getDataCS() {
        return dataCS;
    }

    /**
     * @param dataCS the dataCS to set
     */
    /*
    public void setDataCS(Date dataCS) {
        this.dataCS = dataCS;
    }
    */
    
    public String getDataExt(){                
        return Util.formataDataExtenso(this.id.dataCS);
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
     * @return the examefisico
     */
    public String getExamefisico() {
        return examefisico;
    }

    /**
     * @param examefisico the examefisico to set
     */
    public void setExamefisico(String examefisico) {
        this.examefisico = examefisico;
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
     * @return the id
     */
    public ConsultaPk getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(ConsultaPk id) {
        this.id = id;
    }
    
    /**
     * @return the colestTotCons
     */
    public String getColestTotCons() {                        
        return (colestTotCons==null?"":colestTotCons.toPlainString());
    }

    /**
     * @param colestTotCons the colestTotCons to set 
     * @throws com.br.ralfh.medico.exceptions.FormatoNumericoInvalidoException 
     */
    public void setColestTotCons(String colestTotCons) {
        if (!colestTotCons.isEmpty()) {
            try {
                this.colestTotCons = new BigDecimal(colestTotCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol Total inválido");
            }
        }
    }

    /**
     * @return the colestHDLCons
     */
    public String getColestHDLCons() {
        return (colestHDLCons==null?"":colestHDLCons.toPlainString());
    }

    /**
     * @param colestHDLCons the colestHDLCons to set
     */
    public void setColestHDLCons(String colestHDLCons) {
        if (!colestHDLCons.isEmpty()) {
            try {
                this.colestHDLCons = new BigDecimal(colestHDLCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol HDL inválido");
            }
        }
    }

    /**
     * @return the colestLDLCons
     */
    public String getColestLDLCons() {
        return (colestLDLCons==null?"":colestLDLCons.toPlainString());
    }

    /**
     * @param colestLDLCons the colestLDLCons to set
     */
    public void setColestLDLCons(String colestLDLCons) {
        if (!colestLDLCons.isEmpty()) {
            try {
                this.colestLDLCons = new BigDecimal(colestLDLCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Colesterol LDL inválido");
            }
        }
    }

    /**
     * @return the triglicCons
     */
    public String getTriglicCons() {
        return (triglicCons==null?"":triglicCons.toPlainString());
    }

    /**
     * @param triglicCons the triglicCons to set
     */
    public void setTriglicCons(String triglicCons) { 
        if (!triglicCons.isEmpty()) {
            try {
                this.triglicCons = new BigDecimal(triglicCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor dos Triglicerídeos inválido");
            }
        }
    }

    /**
     * @return the acUriCons
     */
    public String getAcUriCons() {
        return (acUriCons==null?"":acUriCons.toPlainString());
    }

    /**
     * @param acUriCons the acUriCons to set
     */
    public void setAcUriCons(String acUriCons) {
        if (!acUriCons.isEmpty()) {
            try {
                this.acUriCons = new BigDecimal(acUriCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do Ácido Úrico inválido");
            }
        }
    }

    /**
     * @return the hemoGlicCons
     */
    public String getHemoGlicCons() {
        return (hemoGlicCons==null?"":hemoGlicCons.toPlainString());
    }

    /**
     * @param hemoGlicCons the hemoGlicCons to set
     */
    public void setHemoGlicCons(String hemoGlicCons) {
        if (!hemoGlicCons.isEmpty()) {
            try {
                this.hemoGlicCons = new BigDecimal(hemoGlicCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor da Hemoglobina Glicada inválido");
            }
        }
    }

    /**
     * @return the tirT3Cons
     */
    public String getTirT3Cons() {
        return (tirT3Cons==null?"":tirT3Cons.toPlainString());
    }

    /**
     * @param tirT3Cons the tirT3Cons to set
     */
    public void setTirT3Cons(String tirT3Cons) {
        if (!tirT3Cons.isEmpty()) {
            try {
                this.tirT3Cons = new BigDecimal(tirT3Cons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T3 inválido");
            }
        }
    }

    /**
     * @return the tirT4Cons
     */
    public String getTirT4Cons() {
        return (tirT4Cons==null?"":tirT4Cons.toPlainString());
    }

    /**
     * @param tirT4Cons the tirT4Cons to set
     */
    public void setTirT4Cons(String tirT4Cons) {
        if (!tirT4Cons.isEmpty()) {
            try {
                this.tirT4Cons = new BigDecimal(tirT4Cons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T4 inválido");
            }
        }
    }

    /**
     * @return the tirT4LivCons
     */
    public String getTirT4LivCons() {
        return (tirT4LivCons==null?"":tirT4LivCons.toPlainString());
    }

    /**
     * @param tirT4LivCons the tirT4LivCons to set
     */
    public void setTirT4LivCons(String tirT4LivCons) {
        if (!tirT4LivCons.isEmpty()) {
            try {
                this.tirT4LivCons = new BigDecimal(tirT4LivCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do T4 Livre inválido");
            }
        }
    }

    /**
     * @return the tirTSHCons
     */
    public String getTirTSHCons() {
        return (tirTSHCons==null?"":tirTSHCons.toPlainString());
    }

    /**
     * @param tirTSHCons the tirTSHCons to set
     */
    public void setTirTSHCons(String tirTSHCons) {
        if (!tirTSHCons.isEmpty()) {
            try {
                this.tirTSHCons = new BigDecimal(tirTSHCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TSH inválido");
            }
        }
    }

    /**
     * @return the psaLivCons
     */
    public String getPsaLivCons() {
        return (psaLivCons==null?"":psaLivCons.toPlainString());
    }

    /**
     * @param psaLivCons the psaLivCons to set
     */
    public void setPsaLivCons(String psaLivCons) {
        if (!psaLivCons.isEmpty()) {
            try {
                this.psaLivCons = new BigDecimal(psaLivCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do PSA Livre inválido");
            }
        }
    }

    /**
     * @return the psaTotCons
     */
    public String getPsaTotCons() {
        return (psaTotCons==null?"":psaTotCons.toPlainString());
    }

    /**
     * @param psaTotCons the psaTotCons to set
     */
    public void setPsaTotCons(String psaTotCons) {
        if (!psaTotCons.isEmpty()) {
            try {
                this.psaTotCons = new BigDecimal(psaTotCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do PSA Total inválido");
            }
        }
    }

    /**
     * @return the psaRelCons
     */
    public String getPsaRelCons() {
        return (psaRelCons==null?"":psaRelCons.toPlainString());
    }

    /**
     * @param psaRelCons the psaRelCons to set
     */
    public void setPsaRelCons(String psaRelCons) {
        if (!psaRelCons.isEmpty()) {
            try {
                this.psaRelCons = new BigDecimal(psaRelCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor da Relação PSA inválido");
            }
        }
    }

    /**
     * @return the hepTGOCons
     */
    public String getHepTGOCons() {
        return (hepTGOCons==null?"":hepTGOCons.toPlainString());
    }

    /**
     * @param hepTGOCons the hepTGOCons to set
     */
    public void setHepTGOCons(String hepTGOCons) {
        if (!hepTGOCons.isEmpty()) {
            try {
                this.hepTGOCons = new BigDecimal(hepTGOCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TGO inválido");
            }
        }
    }

    /**
     * @return the hepTGPCons
     */
    public String getHepTGPCons() {
        return (hepTGPCons==null?"":hepTGPCons.toPlainString());
    }

    /**
     * @param hepTGPCons the hepTGPCons to set
     */
    public void setHepTGPCons(String hepTGPCons) {
        if (!hepTGPCons.isEmpty()) {
            try {
                this.hepTGPCons = new BigDecimal(hepTGPCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do TGP inválido");
            }
        }
    }

    /**
     * @return the hepFACons
     */
    public String getHepFACons() {
        return (hepFACons==null?"":hepFACons.toPlainString());
    }

    /**
     * @param hepFACons the hepFACons to set
     */
    public void setHepFACons(String hepFACons) {
        if (!hepFACons.isEmpty()) {
            try {
                this.hepFACons = new BigDecimal(hepFACons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do F.A. inválido");
            }
        }
    }

    /**
     * @return the hepGGTCons
     */
    public String getHepGGTCons() {
        return (hepGGTCons==null?"":hepGGTCons.toPlainString());
    }

    /**
     * @param hepGGTCons the hepGGTCons to set
     */
    public void setHepGGTCons(String hepGGTCons) {
        if (!hepGGTCons.isEmpty()) {
            try {
                this.hepGGTCons = new BigDecimal(hepGGTCons);
            } catch (NumberFormatException ex) {
                throw new FormatoNumericoInvalidoException("Valor do GGT inválido");
            }
        }
    }

    /**
     * @return the urinaCons
     */
    public String getUrinaCons() {
        return (urinaCons==null?"":urinaCons);
    }

    /**
     * @param urinaCons the urinaCons to set
     */
    public void setUrinaCons(String urinaCons) {
        this.urinaCons = urinaCons;
    }

    /**
     * @return the fezesCons
     */
    public String getFezesCons() {
        return (fezesCons==null?"":fezesCons);
    }

    /**
     * @param fezesCons the fezesCons to set
     */
    public void setFezesCons(String fezesCons) {
        this.fezesCons = fezesCons;
    }

    /**
     * @return the optNegFezesCons
     */
    public Boolean getOptNegFezesCons() {
        return (optNegFezesCons==null?false:optNegFezesCons);
    }

    /**
     * @param optNegFezesCons the optNegFezesCons to set
     */
    public void setOptNegFezesCons(Boolean optNegFezesCons) {
        this.optNegFezesCons = optNegFezesCons;
    }

    /**
     * @return the optOutFezesCons
     */
    public Boolean getOptOutFezesCons() {
        return (optOutFezesCons==null?false:optOutFezesCons);
    }

    /**
     * @param optOutFezesCons the optOutFezesCons to set
     */
    public void setOptOutFezesCons(Boolean optOutFezesCons) {
        this.optOutFezesCons = optOutFezesCons;
    }

    /**
     * @return the optNormECGCons
     */
    public Boolean getOptNormECGCons() {
        return (optNormECGCons==null?false:optNormECGCons);
    }

    /**
     * @param optNormECGCons the optNormECGCons to set
     */
    public void setOptNormECGCons(Boolean optNormECGCons) {
        this.optNormECGCons = optNormECGCons;
    }

    /**
     * @return the optOutECGCons
     */
    public Boolean getOptOutECGCons() {
        return (optOutECGCons==null?false:optOutECGCons);
    }

    /**
     * @param optOutECGCons the optOutECGCons to set
     */
    public void setOptOutECGCons(Boolean optOutECGCons) {
        this.optOutECGCons = optOutECGCons;
    }

    /**
     * @return the ecgCons
     */
    public String getEcgCons() {
        return (ecgCons==null?"":ecgCons);
    }

    /**
     * @param ecgCons the ecgCons to set
     */
    public void setEcgCons(String ecgCons) {
        this.ecgCons = ecgCons;
    }

    /**
     * @return the papCons
     */
    public String getPapCons() {
        return (papCons==null?"":papCons);
    }

    /**
     * @param papCons the papCons to set
     */
    public void setPapCons(String papCons) {
        this.papCons = papCons;
    }

    /**
     * @return the rxAbdomCons
     */
    public String getRxAbdomCons() {
        return (rxAbdomCons==null?"":rxAbdomCons);
    }

    /**
     * @param rxAbdomCons the rxAbdomCons to set
     */
    public void setRxAbdomCons(String rxAbdomCons) {
        this.rxAbdomCons = rxAbdomCons;
    }

    /**
     * @return the rxOutrCons
     */
    public String getRxOutrCons() {
        return (rxOutrCons==null?"":rxOutrCons);
    }

    /**
     * @param rxOutrCons the rxOutrCons to set
     */
    public void setRxOutrCons(String rxOutrCons) {
        this.rxOutrCons = rxOutrCons;
    }

    /**
     * @return the outUSCons
     */
    public String getOutUSCons() {
        return (outUSCons==null?"":outUSCons);
    }

    /**
     * @param outUSCons the outUSCons to set
     */
    public void setOutUSCons(String outUSCons) {
        this.outUSCons = outUSCons;
    }

    /**
     * @return the tcCons
     */
    public String getTcCons() {
        return (tcCons==null?"":tcCons);
    }

    /**
     * @param tcCons the tcCons to set
     */
    public void setTcCons(String tcCons) {
        this.tcCons = tcCons;
    }

    /**
     * @return the optNormPAPCons
     */
    public Boolean getOptNormPAPCons() {
        return (optNormPAPCons==null?false:optNormPAPCons);
    }

    /**
     * @param optNormPAPCons the optNormPAPCons to set
     */
    public void setOptNormPAPCons(Boolean optNormPAPCons) {
        this.optNormPAPCons = optNormPAPCons;
    }

    /**
     * @return the optOutPAPCons
     */
    public Boolean getOptOutPAPCons() {
        return (optOutPAPCons==null?false:optOutPAPCons);
    }

    /**
     * @param optOutPAPCons the optOutPAPCons to set
     */
    public void setOptOutPAPCons(Boolean optOutPAPCons) {
        this.optOutPAPCons = optOutPAPCons;
    }
    
    
    
    
    @Embeddable 
    public static class ConsultaPk implements Serializable { 
        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "cod_paciente")
        private Paciente paciente;         
        @Temporal(TemporalType.DATE)
        @Column(name = "data")
        private Date dataCS; 

        public ConsultaPk() { 
        } 

        /**
         * @return the paciente
         */
        public Paciente getPaciente() {
            return paciente;
        }

        /**
         * @param paciente the paciente to set
         */
        public void setPaciente(Paciente paciente) {
            this.paciente = paciente;
        }

        /**
         * @return the dataCS
         */
        public Date getDataCS() {
            return dataCS;
        }

        /**
         * @param dataCS the dataCS to set
         */
        public void setDataCS(Date dataCS) {
            this.dataCS = dataCS;
        }
        
        
        @Override
        public int hashCode() { 
            int hashCode = 0; 
            if( paciente != null ) hashCode ^= paciente.hashCode(); 
            if( dataCS != null ) hashCode ^= dataCS.hashCode(); 
            return hashCode; 
        } 
        
        
        @Override
        public boolean equals(Object obj) { 
            if( !(obj instanceof ConsultaPk) ) return false; 
            ConsultaPk target = (ConsultaPk)obj; 
            return ((this.paciente == null) ? 
                    (target.paciente == null) : this.paciente.equals(target.paciente)) 
                    && ((this.dataCS == null) ? (target.dataCS == null) : 
                    this.dataCS.equals(target.dataCS)); 
        } 
    }
    
    
}
