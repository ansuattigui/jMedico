package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "tab_medico", schema = "docplus")
public class Agenda implements Serializable {    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_medico") private Integer id;
    @Column(name = "nome_medico") private String medico;
    @Column(name = "end_medico") private String endereco;
    @Column(name = "bairro_medico") private String bairro;
    @Column(name = "cidade_medico") private String cidade;
    @Column(name = "uf_medico") private String uf;
    @Column(name = "cep_medico") private String cep;
    @Column(name = "telres_medico") private String telefone;
    @Column(name = "telcel_medico") private String celular;
    @Column(name = "prim_hor_seg") private String prim_hor_seg;
    @Column(name = "ult_hor_seg") private String ult_hor_seg;
    @Column(name = "inter_seg") private Integer inter_seg;
    @Column(name = "prim_hor_ter") private String prim_hor_ter;
    @Column(name = "ult_hor_ter") private String ult_hor_ter;
    @Column(name = "inter_ter") private Integer inter_ter;
    @Column(name = "prim_hor_qua") private String prim_hor_qua;
    @Column(name = "ult_hor_qua") private String ult_hor_qua;
    @Column(name = "inter_qua") private Integer inter_qua;
    @Column(name = "prim_hor_qui") private String prim_hor_qui;
    @Column(name = "ult_hor_qui") private String ult_hor_qui;
    @Column(name = "inter_qui") private Integer inter_qui;
    @Column(name = "prim_hor_sex") private String prim_hor_sex;
    @Column(name = "ult_hor_sex") private String ult_hor_sex;
    @Column(name = "inter_sex") private Integer inter_sex;
    @Column(name = "prim_hor_sab") private String prim_hor_sab;
    @Column(name = "ult_hor_sab") private String ult_hor_sab;
    @Column(name = "inter_sab") private Integer inter_sab;
    @Column(name = "prim_hor_dom") private String prim_hor_dom;
    @Column(name = "ult_hor_dom") private String ult_hor_dom;
    @Column(name = "inter_dom") private Integer inter_dom;
    @Column(name = "atendidas") private String atende;    
    
    public Agenda() {
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
     * @return the medico
     */
    public String getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(String medico) {
        this.medico = medico;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the prim_hor_seg
     */
    public String getPrim_hor_seg() {
        return prim_hor_seg;
    }

    /**
     * @param prim_hor_seg the prim_hor_seg to set
     */
    public void setPrim_hor_seg(String prim_hor_seg) {
        this.prim_hor_seg = prim_hor_seg;
    }

    /**
     * @return the ult_hor_seg
     */
    public String getUlt_hor_seg() {
        return ult_hor_seg;
    }

    /**
     * @param ult_hor_seg the ult_hor_seg to set
     */
    public void setUlt_hor_seg(String ult_hor_seg) {
        this.ult_hor_seg = ult_hor_seg;
    }

    /**
     * @return the inter_seg
     */
    public Integer getInter_seg() {
        return inter_seg;
    }

    /**
     * @param inter_seg the inter_seg to set
     */
    public void setInter_seg(Integer inter_seg) {
        this.inter_seg = inter_seg;
    }

    /**
     * @return the prim_hor_ter
     */
    public String getPrim_hor_ter() {
        return prim_hor_ter;
    }

    /**
     * @param prim_hor_ter the prim_hor_ter to set
     */
    public void setPrim_hor_ter(String prim_hor_ter) {
        this.prim_hor_ter = prim_hor_ter;
    }

    /**
     * @return the ult_hor_ter
     */
    public String getUlt_hor_ter() {
        return ult_hor_ter;
    }

    /**
     * @param ult_hor_ter the ult_hor_ter to set
     */
    public void setUlt_hor_ter(String ult_hor_ter) {
        this.ult_hor_ter = ult_hor_ter;
    }

    /**
     * @return the inter_ter
     */
    public Integer getInter_ter() {
        return inter_ter;
    }

    /**
     * @param inter_ter the inter_ter to set
     */
    public void setInter_ter(Integer inter_ter) {
        this.inter_ter = inter_ter;
    }

    /**
     * @return the prim_hor_qua
     */
    public String getPrim_hor_qua() {
        return prim_hor_qua;
    }

    /**
     * @param prim_hor_qua the prim_hor_qua to set
     */
    public void setPrim_hor_qua(String prim_hor_qua) {
        this.prim_hor_qua = prim_hor_qua;
    }

    /**
     * @return the ult_hor_qua
     */
    public String getUlt_hor_qua() {
        return ult_hor_qua;
    }

    /**
     * @param ult_hor_qua the ult_hor_qua to set
     */
    public void setUlt_hor_qua(String ult_hor_qua) {
        this.ult_hor_qua = ult_hor_qua;
    }

    /**
     * @return the inter_qua
     */
    public Integer getInter_qua() {
        return inter_qua;
    }

    /**
     * @param inter_qua the inter_qua to set
     */
    public void setInter_qua(Integer inter_qua) {
        this.inter_qua = inter_qua;
    }

    /**
     * @return the prim_hor_qui
     */
    public String getPrim_hor_qui() {
        return prim_hor_qui;
    }

    /**
     * @param prim_hor_qui the prim_hor_qui to set
     */
    public void setPrim_hor_qui(String prim_hor_qui) {
        this.prim_hor_qui = prim_hor_qui;
    }

    /**
     * @return the ult_hor_qui
     */
    public String getUlt_hor_qui() {
        return ult_hor_qui;
    }

    /**
     * @param ult_hor_qui the ult_hor_qui to set
     */
    public void setUlt_hor_qui(String ult_hor_qui) {
        this.ult_hor_qui = ult_hor_qui;
    }

    /**
     * @return the inter_qui
     */
    public Integer getInter_qui() {
        return inter_qui;
    }

    /**
     * @param inter_qui the inter_qui to set
     */
    public void setInter_qui(Integer inter_qui) {
        this.inter_qui = inter_qui;
    }

    /**
     * @return the prim_hor_sex
     */
    public String getPrim_hor_sex() {
        return prim_hor_sex;
    }

    /**
     * @param prim_hor_sex the prim_hor_sex to set
     */
    public void setPrim_hor_sex(String prim_hor_sex) {
        this.prim_hor_sex = prim_hor_sex;
    }

    /**
     * @return the ult_hor_sex
     */
    public String getUlt_hor_sex() {
        return ult_hor_sex;
    }

    /**
     * @param ult_hor_sex the ult_hor_sex to set
     */
    public void setUlt_hor_sex(String ult_hor_sex) {
        this.ult_hor_sex = ult_hor_sex;
    }

    /**
     * @return the inter_sex
     */
    public Integer getInter_sex() {
        return inter_sex;
    }

    /**
     * @param inter_sex the inter_sex to set
     */
    public void setInter_sex(Integer inter_sex) {
        this.inter_sex = inter_sex;
    }

    /**
     * @return the prim_hor_sab
     */
    public String getPrim_hor_sab() {
        return prim_hor_sab;
    }

    /**
     * @param prim_hor_sab the prim_hor_sab to set
     */
    public void setPrim_hor_sab(String prim_hor_sab) {
        this.prim_hor_sab = prim_hor_sab;
    }

    /**
     * @return the ult_hor_sab
     */
    public String getUlt_hor_sab() {
        return ult_hor_sab;
    }

    /**
     * @param ult_hor_sab the ult_hor_sab to set
     */
    public void setUlt_hor_sab(String ult_hor_sab) {
        this.ult_hor_sab = ult_hor_sab;
    }

    /**
     * @return the inter_sab
     */
    public Integer getInter_sab() {
        return inter_sab;
    }

    /**
     * @param inter_sab the inter_sab to set
     */
    public void setInter_sab(Integer inter_sab) {
        this.inter_sab = inter_sab;
    }

    /**
     * @return the prim_hor_dom
     */
    public String getPrim_hor_dom() {
        return prim_hor_dom;
    }

    /**
     * @param prim_hor_dom the prim_hor_dom to set
     */
    public void setPrim_hor_dom(String prim_hor_dom) {
        this.prim_hor_dom = prim_hor_dom;
    }

    /**
     * @return the ult_hor_dom
     */
    public String getUlt_hor_dom() {
        return ult_hor_dom;
    }

    /**
     * @param ult_hor_dom the ult_hor_dom to set
     */
    public void setUlt_hor_dom(String ult_hor_dom) {
        this.ult_hor_dom = ult_hor_dom;
    }

    /**
     * @return the inter_dom
     */
    public Integer getInter_dom() {
        return inter_dom;
    }

    /**
     * @param inter_dom the inter_dom to set
     */
    public void setInter_dom(Integer inter_dom) {
        this.inter_dom = inter_dom;
    }

    /**
     * @return the atende
     */
    public String getAtende() {
        return atende;
    }

    /**
     * @param atende the atende to set
     */
    public void setAtende(String atende) {
        this.atende = atende;
    }
    
    
}
