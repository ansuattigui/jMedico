package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "tab_medico", schema = "docplus")
public class Medico implements Serializable {    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_medico")
    private Integer id;
    @Column(name="nomeCompleto")
    private String nomeCompleto;
    @Column(name = "nome_medico")
    private String nome;
    @Column(name="ativo")
    private Boolean ativo;
    @Column(name="cpf")
    private String cpf;
    @Column(name="rg")
    private String identidade;
    @Column(name="end_medico")
    private String endereco;
    @Column(name="numero")
    private String numero;
    @Column(name="complemento")
    private String complemento;
    @Column(name="bairro_medico")
    private String bairro;
    @Column(name="cep_medico")
    private String cep;
    @Column(name="cidade_medico")
    private String cidade;
    @Column(name="uf_medico")
    private String uf;
    @Column(name="telres_medico")
    private String telefone1;
    @Column(name="telcel_medico")
    private String telefone2;
    @Column(name="email")
    private String email;        
    @Column(name="atenddias")
    private String atende;        
    @Column(name="prim_hor_seg")
    private String segIni;
    @Column(name="ult_hor_seg")
    private String segFim;
    @Column(name="inter_seg")
    private Integer segInt;
    @Column(name="prim_hor_ter")
    private String terIni;
    @Column(name="ult_hor_ter")
    private String terFim;
    @Column(name="inter_ter")
    private Integer terInt;    
    @Column(name="prim_hor_qua")
    private String quaIni;
    @Column(name="ult_hor_qua")
    private String quaFim;
    @Column(name="inter_qua")
    private Integer quaInt;
    @Column(name="prim_hor_qui")
    private String quiIni;
    @Column(name="ult_hor_qui")
    private String quiFim;
    @Column(name="inter_qui")
    private Integer quiInt;
    @Column(name="prim_hor_sex")
    private String sexIni;
    @Column(name="ult_hor_sex")
    private String sexFim;
    @Column(name="inter_sex")
    private Integer sexInt;
    @Column(name="prim_hor_sab")
    private String sabIni;
    @Column(name="ult_hor_sab")
    private String sabFim;
    @Column(name="inter_sab")
    private Integer sabInt;
    @Column(name="prim_hor_dom")
    private String domIni;
    @Column(name="ult_hor_dom")
    private String domFim;
    @Column(name="inter_dom")
    private Integer domInt;
    
    @Column(name = "lim_dia_consultas")
    private Integer limDiaConsultas;
    @Column(name = "lim_dia_primvez")
    private Integer limDiaPrimvez;
    @Column(name = "lim_dia_emergencias")
    private Integer limDiaEmergencias;
    
    
    public Medico() {
        id = -1;
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the identidade
     */
    public String getIdentidade() {
        return identidade;
    }

    /**
     * @param identidade the identidade to set
     */
    public void setIdentidade(String identidade) {
        this.identidade = identidade;
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
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nomeCompleto
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * @param nomeCompleto the nomeCompleto to set
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * @return the segIni
     */
/*    public String getSegIniI() {
        return segIni;
    }
*/
    
    public int getSegIniI() {        
        return calcHora(this.segIni);
    }
    public String getSegIni() {        
        return this.segIni;
    }
    
    
    /**
     * @param segIni the segIni to set
     */
    public void setSegIni(String segIni) {
        this.segIni = segIni;
    }

    /**
     * @return the segFim
     */
    public int getSegFimI() {
        return calcHora(this.segFim); 
    }
    public String getSegFim() {
        return this.segFim; 
    }

    /**
     * @param segFim the segFim to set
     */
    public void setSegFim(String segFim) {
        this.segFim = segFim;
    }

    /**
     * @return the segInt
     */
    public Integer getSegInt() {
        return segInt;
    }

    /**
     * @param segInt the segInt to set
     */
    public void setSegInt(Integer segInt) {
        this.segInt = segInt;
    }

    /**
     * @return the terIni
     */
    public int getTerIniI() {        
        return calcHora(this.terIni);
    }
    public String getTerIni() {        
        return this.terIni;
    }

    /**
     * @param terIni the terIni to set
     */
    public void setTerIni(String terIni) {
        this.terIni = terIni;
    }

    /**
     * @return the terFim
     */
    public int getTerFimI() {
        return calcHora(terFim);
    }
    public String getTerFim() {
        return this.terFim;
    }

    /**
     * @param terFim the terFim to set
     */
    public void setTerFim(String terFim) {
        this.terFim = terFim;
    }

    /**
     * @return the terInt
     */
    public Integer getTerInt() {
        return terInt;
    }

    /**
     * @param terInt the terInt to set
     */
    public void setTerInt(Integer terInt) {
        this.terInt = terInt;
    }

    /**
     * @return the quaIni
     */
    public int getQuaIniI() {
        return calcHora(quaIni);
    }
    public String getQuaIni() {
        return this.quaIni;
    }

    /**
     * @param quaIni the quaIni to set
     */
    public void setQuaIni(String quaIni) {
        this.quaIni = quaIni;
    }

    /**
     * @return the quaFim
     */
    public int getQuaFimI() {
        return calcHora(quaFim);
    }
    public String getQuaFim() {
        return this.quaFim;
    }

    /**
     * @param quaFim the quaFim to set
     */
    public void setQuaFim(String quaFim) {
        this.quaFim = quaFim;
    }

    /**
     * @return the quaInt
     */
    public Integer getQuaInt() {
        return quaInt;
    }

    /**
     * @param quaInt the quaInt to set
     */
    public void setQuaInt(Integer quaInt) {
        this.quaInt = quaInt;
    }

    /**
     * @return the quiIni
     */
    public int getQuiIniI() {
        return calcHora(quiIni);
    }
    public String getQuiIni() {
        return quiIni;
    }

    /**
     * @param quiIni the quiIni to set
     */
    public void setQuiIni(String quiIni) {
        this.quiIni = quiIni;
    }

    /**
     * @return the quiFim
     */
    public int getQuiFimI() {
        return calcHora(quiFim);
    }
    public String getQuiFim() {
        return this.quiFim;
    }

    /**
     * @param quiFim the quiFim to set
     */
    public void setQuiFim(String quiFim) {
        this.quiFim = quiFim;
    }

    /**
     * @return the quiInt
     */
    public Integer getQuiInt() {
        return quiInt;
    }

    /**
     * @param quiInt the quiInt to set
     */
    public void setQuiInt(Integer quiInt) {
        this.quiInt = quiInt;
    }

    /**
     * @return the sexIni
     */
    public int getSexIniI() {
        return calcHora(sexIni);
    }
    public String getSexIni() {
        return this.sexIni;
    }

    /**
     * @param sexIni the sexIni to set
     */
    public void setSexIni(String sexIni) {
        this.sexIni = sexIni;
    }

    /**
     * @return the sexFim
     */
    public int getSexFimI() {
        return calcHora(sexFim);
    }
    public String getSexFim() {
        return this.sexFim;
    }

    /**
     * @param sexFim the sexFim to set
     */
    public void setSexFim(String sexFim) {
        this.sexFim = sexFim;
    }

    /**
     * @return the sexInt
     */
    public Integer getSexInt() {
        return sexInt;
    }

    /**
     * @param sexInt the sexInt to set
     */
    public void setSexInt(Integer sexInt) {
        this.sexInt = sexInt;
    }

    /**
     * @return the sabIni
     */
    public int getSabIniI() {
        return calcHora(sabIni);
    }
    public String getSabIni() {
        return this.sabIni;
    }

    /**
     * @param sabIni the sabIni to set
     */
    public void setSabIni(String sabIni) {
        this.sabIni = sabIni;
    }

    /**
     * @return the sabFim
     */
    public int getSabFimI() {
        return calcHora(sabFim);
    }
    public String getSabFim() {
        return this.sabFim;
    }

    /**
     * @param sabFim the sabFim to set
     */
    public void setSabFim(String sabFim) {
        this.sabFim = sabFim;
    }

    /**
     * @return the sabInt
     */
    public Integer getSabInt() {
        return sabInt;
    }

    /**
     * @param sabInt the sabInt to set
     */
    public void setSabInt(Integer sabInt) {
        this.sabInt = sabInt;
    }

    /**
     * @return the domIni
     */
    public int getDomIniI() {
        return calcHora(domIni);
    }
    public String getDomIni() {
        return this.domIni;
    }

    /**
     * @param domIni the domIni to set
     */
    public void setDomIni(String domIni) {
        this.domIni = domIni;
    }

    /**
     * @return the domFim
     */
    public int getDomFimI() {
        return calcHora(domFim);
    }
    public String getDomFim() {
        return this.domFim;
    }

    /**
     * @param domFim the domFim to set
     */
    public void setDomFim(String domFim) {
        this.domFim = domFim;
    }

    /**
     * @return the domInt
     */
    public Integer getDomInt() {
        return domInt;
    }

    /**
     * @param domInt the domInt to set
     */
    public void setDomInt(Integer domInt) {
        this.domInt = domInt;
    }

    
    private Integer calcHora(String shora) {
        char a[] = shora.toCharArray();  
        int b;
        int c;
        try {
            b = Integer.parseInt(String.copyValueOf(a,0,2));
            c = Integer.parseInt(String.copyValueOf(a,3,2));
        } catch (StringIndexOutOfBoundsException se) {
            b = 0;
            c = 0;
        }
        return (b*60/5)+c/5;        
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

    /**
     * @return the limDiaConsultas
     */
    public Integer getLimDiaConsultas() {
        if (limDiaConsultas==null) return 0;
        else return limDiaConsultas;
    }

    /**
     * @param limDiaConsultas the limDiaConsultas to set
     */
    public void setLimDiaConsultas(Integer limDiaConsultas) {
        this.limDiaConsultas = limDiaConsultas;
    }

    /**
     * @return the limDiaPrimvez
     */
    public Integer getLimDiaPrimvez() {
        if (limDiaPrimvez==null) return 0;
        else return limDiaPrimvez;
    }

    /**
     * @param limDiaPrimvez the limDiaPrimvez to set
     */
    public void setLimDiaPrimvez(Integer limDiaPrimvez) {
        this.limDiaPrimvez = limDiaPrimvez;
    }

    /**
     * @return the limDiaEmergencias
     */
    public Integer getLimDiaEmergencias() {
        if (limDiaEmergencias==null) return 0;
        else return limDiaEmergencias;
    }

    /**
     * @param limDiaEmergencias the limDiaEmergencias to set
     */
    public void setLimDiaEmergencias(Integer limDiaEmergencias) {
        this.limDiaEmergencias = limDiaEmergencias;
    }

        
}
