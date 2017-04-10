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
public class Exame implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "pedido_id", nullable = false )
    private PedidoExames pedido;
    private String exame;
    private String material;
    
    public Exame() {
    }
    
    public Exame(String nome, String material) {
        this.exame = nome;
        this.material = material;
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
     * @return the pedido
     */
    public PedidoExames getPedidoExames() {
        return pedido;
    }

    /**
     * @param pedido
     */
    public void setPedidoExames(PedidoExames pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the exame
     */
    public String getExame() {
        return exame;
    }

    /**
     * @param exame the exame to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setExame(String exame) throws CampoEmBrancoException {
        if (exame.trim().isEmpty()) {
            throw new CampoEmBrancoException("Selecione um exame");
        } else {
            this.exame = exame;
        }
    }

    /**
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     * @throws com.br.ralfh.medico.exceptions.CampoEmBrancoException
     */
    public void setMaterial(String material) throws CampoEmBrancoException {
        if (material.trim().isEmpty()) {
            throw new CampoEmBrancoException("Informe o material do exame");
        } else {
            this.material = material;
        }
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Exame)) {
            return false;
        }
        Exame other = (Exame) object;
        if ((this.exame == null && other.exame != null) || (this.exame != null && !this.exame.equals(other.exame))) {
            return false;
        }
        return true;
    }
    
    
}
