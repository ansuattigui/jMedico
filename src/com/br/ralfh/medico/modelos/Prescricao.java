package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class Prescricao implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "receita_id", nullable = false )
    private Receita receita;
    private String medicamento;
    private String quantidade;
    private String posologia;
    private String viaAdmin;
    
    public Prescricao() {
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
     * @return the receita
     */
    public Receita getReceita() {
        return receita;
    }

    /**
     * @param receita the receita to set
     */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    /**
     * @return the medicamento
     */
    public String getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setMedicamento(String medicamento) throws CampoEmBrancoException {
        if (medicamento.trim().isEmpty()) {
            throw new CampoEmBrancoException("Selecione um medicamento");
        } else {
            this.medicamento = medicamento;
        }
    }

    /**
     * @return the quantidade
     */
    public String getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setQuantidade(String quantidade) throws CampoEmBrancoException {
        if (quantidade.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe a quantidade do medicamento");
        } else {
            this.quantidade = quantidade;
        }
    }

    /**
     * @return the posologia
     */
    public String getPosologia() {
        return posologia;
    }

    /**
     * @param posologia the posologia to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setPosologia(String posologia) throws CampoEmBrancoException {
        if (posologia.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe a posologia do medicamento");
        } else {
            this.posologia = posologia;
        }
    }

    /**
     * @return the viaAdmin
     */
    public String getViaAdmin() {
        return viaAdmin;
    }

    /**
     * @param viaAdmin the viaAdmin to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setViaAdmin(String viaAdmin) throws CampoEmBrancoException {
        if (viaAdmin.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe a via de administração do medicamento");
        } else {
            this.viaAdmin = viaAdmin;
        }
    }
    
    
}
