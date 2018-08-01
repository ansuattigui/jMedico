package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.Etnia;
import com.br.ralfh.medico.Sexo;
import com.br.ralfh.medico.Util;
import com.br.ralfh.medico.exceptions.CampoNuloException;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name = "tab_cliente", schema = "docplus")
public class Paciente implements Serializable {    
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_paciente") private Integer id;    
    @Column(name = "cod_antigo_paciente") private Integer codAntigo;
    @Column(name = "medico_paciente") private Integer medico;
    @Column(name = "nome_paciente") private String nome;
    @Column(name = "sexo_paciente") private String sexo;    
    @Column(name = "nasc_paciente") @Temporal(TemporalType.DATE) private Date nascimento;    
    @Column(name = "natural_paciente") private String naturalidade;
    @Column(name = "nacional_paciente") private String nacionalidade;
    @Column(name = "ident_paciente") private String identidade;
    @Column(name = "cpf_paciente") private String cpf;
    @Column(name = "est_civil_paciente") private String estadoCivil;
    @Column(name = "cor_paciente") private String etnia;
    @Column(name = "profissao_paciente") private String profissao;
    @Column(name = "end_paciente") private String endereco;
    @Column(name = "numero_rua") private String numero;
    @Column(name = "complemento_endereco") private String complemento;
    @Column(name = "bairro_paciente") private String bairro;
    @Column(name = "cep_paciente") private String cep;
    @Column(name = "cidade_paciente") private String cidade;
    @Column(name = "uf_paciente") private String estado;
    @Column(name = "tel_res_paciente") private String telResidencial;
    @Column(name = "tel_com_paciente") private String telComercial;
    @Column(name = "tel_cel_paciente") private String celular;
    @Column(name = "tel_fax_paciente") private String fax;
    @Column(name = "email") private String email;    
    @Column(name = "indicado_por") private String indicacao;
    @Column(name = "status_paciente") private Integer status;
    @Lob private byte[] fotografia;
    @ManyToOne @JoinColumn(name = "cod_convenio_paciente", nullable = true)
    private Convenio convenio;    
    @Column(name = "numero_conveniado") private String numConveniado;
    
    public Paciente() {
        id = -1;
        //primconsulta = new PrimeiraConsulta();
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
     * @return the codAntigo
     */
    public Integer getCodAntigo() {
        if (codAntigo==null) {
            return 0;
        } else {
            return codAntigo;
        }
    }

    /**
     * @param codAntigo the codAntigo to set
     */
    public void setCodAntigo(Integer codAntigo) {
        this.codAntigo = codAntigo;
    }

    /**
     * @return the medico
     */
    public Integer getMedico() {
        if (medico==null) {
            return 0;
        } else {
            return medico;
        }
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Integer medico) {
        this.medico = medico;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the sexo
     */
    public Sexo getSexo() {
        String sexopac;
        Sexo sSexo = null;
        try {
            sexopac = sexo.trim().substring(0,1).toUpperCase();
            for(Sexo s : Sexo.values()) {
                if (s.toString().startsWith(sexopac)) {
                    sSexo = s;
                }
            }        
        } catch (StringIndexOutOfBoundsException e) {
            sSexo = null;
        }        
        return sSexo;
    }

    public void setSexo(Sexo sexo) throws CampoNuloException {
        if (sexo==null) {
            throw new CampoNuloException("Informe o Sexo do paciente");
        } else this.sexo = sexo.toString();
    }

    public Date getNascimento() {            
//        Instant instant = Instant.ofEpochMilli(nascimento.getTime());
//        LocalDate dataNasc = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();        
        return nascimento;
    }

    public void setNascimento(Date nascimento) throws CampoNuloException {
        if (nascimento==null) {
            throw new CampoNuloException("Informe a data de nascimento do paciente");
        } else {
            Instant instant = Util.ld(nascimento).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date dataNasc = Date.from(instant);        
            this.nascimento = dataNasc;
        }
    }

    public String getNaturalidade() {
        if (naturalidade==null) {
            return "";
        } else {
            return naturalidade;
        }
    }
/*
    public void setNaturalidade(String naturalidade) throws CampoEmBrancoException {
        if (naturalidade.isEmpty()) {
            throw new CampoEmBrancoException("Informe a naturalidade do paciente");
        } else {
            this.naturalidade = naturalidade;
        }
    }
*/    
    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        if (nacionalidade==null) {
            return "";
        } else {
            return nacionalidade;
        }
    }
/*
    public void setNacionalidade(String nacionalidade) throws CampoEmBrancoException {
        if (nacionalidade.isEmpty()) {
         throw new CampoEmBrancoException("Informe a nacionalidade do paciente");   
        } else {
            this.nacionalidade = nacionalidade;
        }
    }
*/    
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getIdentidade() {
        if (identidade==null) {
            return "";
        } else {
            return identidade;
        }
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getCpf() {
        if (cpf==null) {
            return "";
        } else {
            return cpf;
        }
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstadoCivil() {
        if (estadoCivil==null) {
            return "";
        } else {
            return estadoCivil;
        }
    }
/*    
    public void setEstadoCivil(String estadoCivil) throws CampoNuloException {
        if (estadoCivil==null) {
            throw new CampoNuloException("Informe o estado civil do paciente");
        } else {
            this.estadoCivil = estadoCivil;
        }
    }
*/    
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the profissao
     */
    public String getProfissao() {
        if (profissao==null) {
            return "";
        } else {
            return profissao;
        }
    }

    /**
     * @param profissao the profissao to set
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        if (endereco==null) {
            return "";
        } else {
            return endereco;
        }
    }

    /**
     * @param endereco the endereco to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
/*    
    public void setEndereco(String endereco) throws CampoEmBrancoException {
        if (endereco.isEmpty()) {
            throw new CampoEmBrancoException("Informe o endereço do paciente");
        } else {
            this.endereco = endereco;
        }
    }
*/    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        if (numero==null) {
            return "";
        } else {                   
            return numero;
        }
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
        if (complemento==null) {
            return "";
        } else {
            return complemento;
        }
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
        if (bairro==null) {
            return "";
        } else {
            return bairro;
        }
    }

    public String getEnderecoCompleto() {
        if (endereco==null) {
            return "";
        } else {
            return endereco+" , "+"nº "+numero+" "+complemento+" , "+bairro+" , "+cidade+" , "+estado;
        }
    }
    
    
    /**
     * @param bairro the bairro to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
/*    
    public void setBairro(String bairro) throws CampoEmBrancoException {
        if (bairro.isEmpty()) {
            throw new CampoEmBrancoException("Informe o bairro do paciente");
        } else {
            this.bairro = bairro;
        }
    }
*/    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        if (cep==null) {
            return "";
        } else {
            return cep;
        }
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
        if (cidade==null) {
            return "";
        } else {
            return cidade;
        }
    }

    /**
     * @param cidade the cidade to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
/*    
    public void setCidade(String cidade) throws CampoEmBrancoException {
        if (cidade.isEmpty()) {
            throw new CampoEmBrancoException("Informe a cidade do paciente");
        } else {
            this.cidade = cidade;
        }
    }
*/    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        if (estado==null) {
            return "";
        } else {
            return estado;
        }
    }

    /**
     * @param estado the estado to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
/*    
    public void setEstado(String estado) throws CampoNuloException {
        if (estado==null) {
            throw new CampoNuloException("Informe o estado do paciente");
        } else {
            this.estado = estado;
        }
    }
*/    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the telResidencial
     */
    public String getTelResidencial() {
        if (telResidencial==null) {
            return "";
        } else {
            return telResidencial;
        }        
    }

    /**
     * @param telResidencial the telResidencial to set
     */
    public void setTelResidencial(String telResidencial) {
        this.telResidencial = telResidencial;
    }

    /**
     * @return the telComercial
     */
    public String getTelComercial() {
        if (telComercial==null) {
            return "";
        } else {
            return telComercial;
        }        
    }

    /**
     * @param telComercial the telComercial to set
     */
    public void setTelComercial(String telComercial) {
        this.telComercial = telComercial;
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
     * @return the receitas
     
    public Collection<Receita> getReceitas() {
        return receitas;
    }

    
     * @param receitas the receitas to set
     
    public void setReceitas(Collection<Receita> receitas) {
        this.receitas = receitas;
    }
    * 
    */

    /**
     * @return the fotografia
     */
    public byte[] getFotografia() {
        return fotografia;
    }

    /**
     * @param fotografia the fotografia to set
     */
    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * @return the etnia
     */
    public Etnia getEtnia() {
        String etn;
        Etnia eTnia = null;
        try {
            etn = etnia.toUpperCase();
            for(Etnia et : Etnia.values()) {
                if (et.toString().equals(etn)) {
                    eTnia = et;
                }
            }        
        } catch (Exception e) {
            eTnia = null;
        }        
        return eTnia;
    }

    /**
     * @param etnia the etnia to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
    public void setEtnia(Etnia etnia) throws CampoNuloException {
        if (etnia==null) {
            throw new CampoNuloException("Informe a etnia do paciente");
        } else {
            this.etnia = etnia.toString();
        }
    }


    /**
     * @return the numConveniado
     */
    public String getNumConveniado() {
        return numConveniado;
    }

    /**
     * @param numConveniado the numConveniado to set
     */
    public void setNumConveniado(String numConveniado) {
        this.numConveniado = numConveniado;
    }

    /**
     * @return the convenio
     */
    public Convenio getConvenio() {
        return convenio;
    }

    /**
     * @param convenio the convenio to set
     * @throws com.br.ralfh.medico.exceptions.CampoNuloException
     */
    public void setConvenio(String convenio) throws CampoNuloException {
        if (convenio==null) {
            throw new CampoNuloException("Informe o convênio do paciente");
        } else {            
            this.convenio = Convenios.getConvenioWithNome(convenio);                        
        }
    }
    
    public void setConvenio(Integer convenio) {
        this.convenio = Convenios.getConvenioWithCod(convenio);                        
    }

    /**
     * @return the indicacao
     */
    public String getIndicacao() {
        return indicacao;
    }

    /**
     * @param indicacao the indicacao to set
     */
    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    /**
     * @return the status
     */
    public String getStatus() {        
        String retorno = null;        
        switch (status) {
            case 0: retorno = "Normal";
                    break;
            case 1: retorno = "Vip";
                    break;
            case 2: retorno = "Super Vip";
                    break;
        }
        return retorno;
    }

    /**
     * @param opcao
     */
    public void setStatus(String opcao) {
        switch (opcao) {
            case "Normal": status = 0;
                    break;
            case "Vip": status = 1;
                    break;
            case "Super Vip": status = 2;
                    break;
        }
    }

    /**
     * @return the primconsulta
     */
/*    
    public PrimeiraConsulta getPrimconsulta() {
        return primconsulta;
    }
*/    
    /**
     * @param primconsulta the primconsulta to set
     */
/*    
    public void setPrimconsulta(PrimeiraConsulta primconsulta) {
        this.primconsulta = primconsulta;
    }
*/    
    /**
     * @return the consultasSubs
     */
    /*
    public Collection<ConsultaSubs> getConsultasSubs() {
        return consultasSubs;
    }

    /**
     * @param consultasSubs the consultasSubs to set
     */
    /*
    public void setConsultasSubs(Collection<ConsultaSubs> consultasSubs) {
        this.consultasSubs = consultasSubs;
    }
    */
    
    
}
