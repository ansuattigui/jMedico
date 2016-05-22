/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ralfh
 */
@Entity @Table(name = "conexao", schema = "docplus")
public class Conexao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Usuario usuario;
    private String machine;
    private String ip;
    @Temporal(TemporalType.DATE)
    private Date horaConexao;  
//    @Transient
//    private String usuarioNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conexao)) {
            return false;
        }
        Conexao other = (Conexao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.br.ralfh.medico.modelos.Conexao[ id=" + id + " ]";
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param user the usuario to set
     */
    public void setUsuario(Usuario user) {
        this.usuario = user;
    }

    /**
     * @return the machine
     */
    public String getMachine() {
        return machine;
    }

    /**
     * @param machine the machine to set
     */
    public void setMachine(String machine) {
        this.machine = machine;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the horaConexao
     */
    public Date getHoraConexao() {
        return horaConexao;
    }

    /**
     * @param horaConexao the horaConexao to set
     */
    public void setHoraConexao(Date horaConexao) {
        this.horaConexao = horaConexao;
    }

    /**
     * @return the usuarioNome
     */
    /*public String getUsuarioNome() {
        return usuario.getUsuario();
    }

    /**
     * @param usuarioNome the usuarioNome to set
     */
    /*
    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
    */
}
