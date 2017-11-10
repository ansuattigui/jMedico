package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import com.br.ralfh.medico.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.chart.XYChart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class FichaMedica {    
    
    private Paciente paciente;
    private static PrimeiraConsulta primeiraConsulta;
    private static List<ConsultaSubs> consultasSubs;
    private static Prontuario prontuario;
    
    public FichaMedica(Paciente pac) {
        this.paciente = pac;
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
    
    public static PrimeiraConsulta getPrimeiraConsulta(Paciente pac) {    
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "from PrimeiraConsulta pc where pc.id = :paciente";
        TypedQuery<PrimeiraConsulta> query = manager.createQuery(jpql,PrimeiraConsulta.class);
        query.setParameter("paciente", pac);
        try {
            primeiraConsulta = query.getSingleResult();
        } catch (NoResultException ex) {
            manager.getTransaction().begin();
            primeiraConsulta = criaPrimeiraConsulta(manager,pac);
            manager.getTransaction().commit();
        }
        manager.close();                        
        return primeiraConsulta;
    }    

    public static String getDataPrimCons(Paciente pac) {    
        String data = "";
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select data from PrimeiraConsulta pc where pc.id = :paciente";
        Query query = manager.createQuery(jpql);
        query.setParameter("paciente", pac);
        try {
            data = Util.formataData( (Date) query.getSingleResult());
        } catch (NoResultException ex) {
            manager.getTransaction().begin();
            data = "";
            manager.getTransaction().commit();
        }
        manager.close();                        
        return data;
    }    
    
    
    public static List<ConsultaSubs> getConsultasSubs(Paciente pac) {  
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "from ConsultaSubs cs where cs.id.paciente = :paciente order by cs.id.dataCS desc";
        Query query = manager.createQuery(jpql,ConsultaSubs.class);
        query.setParameter("paciente", pac);
        try {
            consultasSubs = query.getResultList();
        } catch (NoResultException ex) {
            consultasSubs = null;
        }
        manager.close(); 
        return consultasSubs;
    }    
    
    
    private static PrimeiraConsulta criaPrimeiraConsulta(EntityManager manager, Paciente pac) {
        PrimeiraConsulta pc = new PrimeiraConsulta();
        pc.setId(pac);
        pc.setData(Util.dHoje());
        manager.persist(pc);                
        return pc;
    }
    
    public static Boolean salvaPrimeiraConsulta(EntityManager manager, PrimeiraConsulta pc) {
        Boolean resultado = Boolean.TRUE;
        try {
            manager.merge(pc);
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    public static Boolean novaConsultaSubs(EntityManager manager, ConsultaSubs consubs) {
        Boolean resultado = Boolean.TRUE;
        try {
            manager.persist(consubs);
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    
    public static Boolean salvaConsultaSubs(EntityManager manager, ConsultaSubs consubs) {
        Boolean resultado = Boolean.TRUE;
        try {
            manager.merge(consubs);
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    
    public static Boolean excluiConsultaSubs(EntityManager manager,ConsultaSubs consubs) {
        Boolean resultado = Boolean.FALSE;
        try {    
            String jpql = "delete from ConsultaSubs cs where cs.id = :id";
            Query query = manager.createQuery(jpql);
            query.setParameter("id", consubs.getId());
            query.executeUpdate();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }    
    
    
    public static XYChart.Series consultaSeriePA(Paciente pac,String campo) {
        
        EntityManager manager = JPAUtil.getEntityManager();        
        String jpql = "from PrimeiraConsulta pc where pc.id = :paciente";
        TypedQuery<PrimeiraConsulta> querypc = manager.createQuery(jpql,PrimeiraConsulta.class);
        querypc.setParameter("paciente", pac);        
        PrimeiraConsulta respc = (PrimeiraConsulta) querypc.getSingleResult();
        
        String jpqlCS = "from ConsultaSubs cs where cs.id.paciente = :paciente order by cs.id.dataCS";
        TypedQuery<ConsultaSubs> querycs = manager.createQuery(jpqlCS,ConsultaSubs.class);
        querycs.setParameter("paciente", pac);
        ArrayList<ConsultaSubs> rescs = (ArrayList) querycs.getResultList();
        manager.close();  
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        XYChart.Series serie = new XYChart.Series();
        if ((respc!=null) & (!rescs.isEmpty())) {
            
/*            if (null != campo.toUpperCase())switch (campo.toUpperCase()) {
                case "DIASTOLICA":
                    serie.getData().add(0,new XYChart.Data(sdf.format(primeiraConsulta.getData()),primeiraConsulta.getPa_diast()));
                    break;
                case "SISTOLICA":
                    serie.getData().add(0,new XYChart.Data(sdf.format(primeiraConsulta.getData()),primeiraConsulta.getPa_sist()));
                    break;
                case "FREQCARD":
                    serie.getData().add(0,new XYChart.Data(sdf.format(primeiraConsulta.getData()),primeiraConsulta.getFreqcard()));
                    break;
                case "PULSO":
                    serie.getData().add(0,new XYChart.Data(sdf.format(primeiraConsulta.getData()),primeiraConsulta.getPulso()));
            }
*/                        
            String data = sdf.format(respc.getData());
            if (null != campo.toUpperCase())switch (campo.toUpperCase()) {
                case "DIASTOLICA":
                    serie.getData().add(new XYChart.Data(data,respc.getPa_diast()));
                    break;
                case "SISTOLICA":
                    serie.getData().add(new XYChart.Data(data,respc.getPa_sist()));
                    break;
                case "FREQCARD":
                    serie.getData().add(new XYChart.Data(data,respc.getFreqcard()));
                    break;
                case "PULSO":
                    serie.getData().add(new XYChart.Data(data,respc.getPulso()));
                    break;
            }    
                    
            for(int i=0;i<rescs.size();i++) {         
                data = sdf.format(rescs.get(i).getId().getDataCS());
                if (null != campo.toUpperCase())switch (campo.toUpperCase()) {
                    case "DIASTOLICA":
                        serie.getData().add(new XYChart.Data(data,rescs.get(i).getPa_diast()));
                        break;
                    case "SISTOLICA":
                        serie.getData().add(new XYChart.Data(data,rescs.get(i).getPa_sist()));
                        break;
                    case "FREQCARD":
                        serie.getData().add(new XYChart.Data(data,rescs.get(i).getFreqcard()));
                        break;
                    case "PULSO":
                        serie.getData().add(new XYChart.Data(data,rescs.get(i).getPulso()));
                        break;
                }    
            }
        }
        return serie;
    }
    
    
    public static ArrayList<String> getAparResp() {
        ArrayList<String> aparresp = null;
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select distinct(aparcircul) from PrimeiraConsulta pc order by aparcircul";
        Query query = manager.createQuery(jpql);
        aparresp = (ArrayList<String>) query.getResultList();
        manager.close();                        
        return aparresp;
    }
    

    /**
     * @param pac
     * @return the prontuario
     */
    public static Prontuario getProntuario(Paciente pac) {
        return FichaMedica.prontuario = new Prontuario(pac);
    }

    public static class Prontuario {
        
        private String qp = "";
        private String ef = "";
        private String co = "";
        
        public Prontuario(Paciente pac) {
            carregaProntuario(pac);
        }        

        public final void carregaProntuario(Paciente pac) {
            List<ConsultaSubs> consubs = getConsultasSubs(pac);            
            for (ConsultaSubs consulta :consubs) {
                if (!"".equals(consulta.getQp().trim())) {
                    qp = qp + consulta.getQp() + "\n";
                }
                if (!"".equals(consulta.getExamefisico().trim())) {
                    ef = ef + consulta.getExamefisico() + "\n";
                }
                if (!"".equals(consulta.getCondterap().trim())) {
                    co = co + consulta.getCondterap() + "\n";
                }
            }
        }
                
        
        /**
         * @return the qp
         */
        public String getQp() {
            return qp;
        }

        /**
         * @return the ef
         */
        public String getEf() {
            return ef;
        }

        /**
         * @return the co
         */
        public String getCo() {
            return co;
        }
        
        
    }
}
