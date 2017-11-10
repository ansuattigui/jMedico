package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class ExamesGrupo implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    @ManyToOne( fetch = FetchType.EAGER )
    private GrupoExames grupo;
    private String exame;
    private String material;
    
    public ExamesGrupo() {
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
     * @return the grupo
     */
    public GrupoExames getGrupo() {
        return grupo;
    }

    /**
     * @param grupo
     */
    public void setGrupoExames(GrupoExames grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof ExamesGrupo)) {
            return false;
        }
        ExamesGrupo other = (ExamesGrupo) object;
        return !((this.exame == null && other.exame != null) || (this.exame != null && !this.exame.equals(other.exame)));
    }        

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.exame);
        return hash;
    }
}
