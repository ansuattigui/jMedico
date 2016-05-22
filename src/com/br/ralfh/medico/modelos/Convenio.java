package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.Estado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "tab_convenio", schema = "docplus")
public class Convenio implements Serializable {    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_convenio")
    private Integer id;
    @Column(name = "codigo_convenio")
    private String codigoANS;
    @Column(name = "nome_convenio")
    private String nome;
    @Column(name = "cgc_convenio")
    private String cnpj;
    @Column(name = "end_convenio")
    private String endereco;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro_convenio")
    private String bairro;
    @Column(name = "cep_convenio")
    private String cep;
    @Column(name = "cidade_convenio")
    private String cidade;
    @Column(name = "uf_convenio")
    private String estado;
    @Column(name = "telefone1")
    private String telefone1;
    @Column(name = "telefone2")
    private String telefone2;
    @Column(name = "telefone3")
    private String telefone3;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;    
    @Column(name = "www")
    private String www;    
    @Column (name = "valor_honorario")
    private BigDecimal valorConsulta;
    @Column (name = "honorario_ecg")
    private BigDecimal valorECG;
    @Column (name = "honorario_internacao")
    private BigDecimal valorInternacao;
    @Column (name = "dia_inici_entrega")
    private String inicioEntrega;
    @Column (name = "dia_fim_entrega")
    private String fimEntrega;
    @Column (name = "data_cadastro") 
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column (name = "data_atualizacao") 
    @Temporal(TemporalType.DATE)
    private Date dataAtualizacao;
    @OneToMany(mappedBy = "convenio", fetch = FetchType.LAZY)
    private Collection<Paciente> pacientes;
//    @OneToMany(mappedBy = "convenio", fetch = FetchType.LAZY)
//    private Collection<Procedimento_Convenio> procedimentos;   
    
    public Convenio() {
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
     * @return the codigoANS
     */
    public String getCodigoANS() {
        return codigoANS;
    }

    /**
     * @param codigoANS the codigoANS to set
     */
    public void setCodigoANS(String codigoANS) {
        this.codigoANS = codigoANS;
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
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
     * @return the estado
     */
    public Estado getEstado() {        
        String est;
        Estado esTado = null;
        try {
            est = estado.toUpperCase();
            for(Estado es : Estado.values()) {
                if (es.toString().equals(est)) {
                    esTado = es;
                }
            }        
        } catch (Exception e) {
            esTado = null;
        }        
        return esTado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado.toString();
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
     * @return the telefone3
     */
    public String getTelefone3() {
        return telefone3;
    }

    /**
     * @param telefone3 the telefone3 to set
     */
    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
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
     * @return the www
     */
    public String getWww() {
        return www;
    }

    /**
     * @param www the www to set
     */
    public void setWww(String www) {
        this.www = www;
    }

    /**
     * @return the valorConsulta
     */
    public BigDecimal getValorConsulta() {
        return valorConsulta;
    }

    /**
     * @param valorConsulta the valorConsulta to set
     */
    public void setValorConsulta(BigDecimal valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    /**
     * @return the valorECG
     */
    public BigDecimal getValorECG() {
        return valorECG;
    }

    /**
     * @param valorECG the valorECG to set
     */
    public void setValorECG(BigDecimal valorECG) {
        this.valorECG = valorECG;
    }

    /**
     * @return the valorInternacao
     */
    public BigDecimal getValorInternacao() {
        return valorInternacao;
    }

    /**
     * @param valorInternacao the valorInternacao to set
     */
    public void setValorInternacao(BigDecimal valorInternacao) {
        this.valorInternacao = valorInternacao;
    }

    /**
     * @return the inicioEntrega
     */
    public String getInicioEntrega() {
        return inicioEntrega;
    }

    /**
     * @param inicioEntrega the inicioEntrega to set
     */
    public void setInicioEntrega(String inicioEntrega) {
        this.inicioEntrega = inicioEntrega;
    }

    /**
     * @return the fimEntrega
     */
    public String getFimEntrega() {
        return fimEntrega;
    }

    /**
     * @param fimEntrega the fimEntrega to set
     */
    public void setFimEntrega(String fimEntrega) {
        this.fimEntrega = fimEntrega;
    }

    /**
     * @return the dataCadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * @param dataCadastro the dataCadastro to set
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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
     * @return the pacientes
     */
    public Collection<Paciente> getPacientes() {
        return pacientes;
    }

    /**
     * @param pacientes the pacientes to set
     */
    public void setPacientes(Collection<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    /**
     * @return the procedimentos
     
    public Collection<Procedimento_Convenio> getProcedimentos() {
        return procedimentos;
    }
*/
    /**
     * @param procedimentos the procedimentos to set
     
    public void setProcedimentos(Collection<Procedimento_Convenio> procedimentos) {
        this.procedimentos = procedimentos;
    }
*/
            
}
