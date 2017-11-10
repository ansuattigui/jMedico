/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.MedicoController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ralfh
 */
public class CEPs {
        
    public CEPs() {
    }
    
    
/*    public static ArrayList<Medicamento> getLista() {

        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select m from Medicamento m order by m.principio";
        TypedQuery<Medicamento> query = manager.createQuery(jpql,Medicamento.class);
        ArrayList<Medicamento> medicamentos = (ArrayList) query.getResultList();
        manager.close();        
        
        return medicamentos;
    }
    public static ObservableList<Medicamento> getObsLista() {        
        return FXCollections.observableArrayList(CEPs.getLista());
    }    
    
*/        
    
    public static CEP getCEPPeloNome(String scep, String suf){                
        CEP cep = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String query = "select * from ".concat(suf).concat(" where cep=?");
        Connection conn = MedicoController.getConn().getConnection();
        try {
            stmt = conn.prepareStatement(query);   
            stmt.setString(1, scep);            
            rs = stmt.executeQuery();
            rs.first();
            cep = new CEP();
            cep.setId(rs.getInt("id"));
            cep.setBairro(rs.getString("bairro"));
            cep.setCep(rs.getString("cep"));
            cep.setCidade(rs.getString("cidade"));
            cep.setLogradouro(rs.getString("logradouro"));
            cep.setTp_logradouro(rs.getString("tp_logradouro"));
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro na consulta!");
        }
        return cep;
    }
    
    
}
