/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.modelos;

import com.br.ralfh.medico.JPAUtil;
import com.br.ralfh.medico.Util;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ralfh
 */
public class HorariosAgenda {
    
    private Medico agenda;
    public static EstatConsultas estatistica;
    private static Date data;
    
    public HorariosAgenda(Date d) {        
        data = d;   
        initAgenda();
    }
    
    private void initAgenda() {
        EntityManager manager = JPAUtil.getEntityManager();
        manager.getTransaction().begin();
        agenda = manager.find(Medico.class,1);
        manager.getTransaction().commit();
        manager.close();        
    }

    public static Boolean excluiAgendamento(HorarioAgenda horario) {
        Boolean resultado = Boolean.FALSE;
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();  
            manager.remove(manager.getReference(HorarioAgenda.class, horario.getId()));  
            manager.getTransaction().commit();
            manager.close();
            resultado = Boolean.TRUE;
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = Boolean.FALSE;
        }
        return resultado;
    }
    

    public static void atualizaPacienteHorario(Paciente paciente, Integer id) {        
        if (paciente.getId()!=-1) {
            EntityManager manager = JPAUtil.getEntityManager();
            manager.getTransaction().begin();
            HorarioAgenda horario = manager.find(HorarioAgenda.class,id);        
            horario.setCodPaciente(paciente.getId());
            horario.setPaciente(paciente.getNome());
            horario.setCodAntigoPaciente(paciente.getCodAntigo());
            horario.setConvenio(paciente.getConvenio().getId());        
            manager.getTransaction().commit();
            manager.close();  
        }
    }
    
    public static Boolean getHorarioSeguinteOcupado(Date horario) {
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select ha from HorarioAgenda ha where ha.dataHora = :horario";
        TypedQuery<HorarioAgenda> query = manager.createQuery(jpql,HorarioAgenda.class);
        query.setParameter("horario",horario);
        ArrayList<HorarioAgenda> horarios = (ArrayList) query.getResultList();                
        manager.close();    
        return (!horarios.isEmpty());
    }

    public static ArrayList<HorarioAgenda> getListaPaciente(Paciente pac) {        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select ha from HorarioAgenda ha where ha.codPaciente = :ppaciente order by ha.dataHora DESC";
        TypedQuery<HorarioAgenda> query = manager.createQuery(jpql,HorarioAgenda.class);
        query.setParameter("ppaciente",pac.getId());
        ArrayList<HorarioAgenda> horarios = (ArrayList) query.getResultList();        
        manager.close();            
        return horarios;
    }        
    
    
    
    public static ArrayList<HorarioAgenda> getLista() {        
        EntityManager manager = JPAUtil.getEntityManager();
        String jpql = "select ha from HorarioAgenda ha where ha.data = :pdata order by ha.dataHora";
        TypedQuery<HorarioAgenda> query = manager.createQuery(jpql,HorarioAgenda.class);
        query.setParameter("pdata",data);
        ArrayList<HorarioAgenda> horarios = (ArrayList) query.getResultList();        
        manager.close();            
        estatistica = new EstatConsultas(horarios);        
        return horarios;
    }        
    
    
    public ObservableList<HorarioAgenda> getObsLista() {        
        ObservableList<HorarioAgenda> lista = FXCollections.observableArrayList(this.getLista());        
        return lista;
    }    
    
    public ArrayList<HorarioAgenda> getListaT() {
        
        Integer dia = Util.ldt(data).getDayOfMonth();
        Integer mes = Util.ldt(data).getMonthValue();
        Integer ano = Util.ldt(data).getYear();

        Integer horaInicio = 0;
        Integer minutoInicio = 0;
        Integer horaFim = 0;
        Integer minutoFim = 0;
        Integer intervalo = 0;
        
        switch(Util.ldt(data).getDayOfWeek()) {
            
            case SUNDAY: 
                horaInicio = Integer.parseInt(agenda.getDomIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getDomIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getDomIni().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getDomFim().substring(3,5));
                intervalo = agenda.getDomInt();
                break;            
            case MONDAY: 
                horaInicio = Integer.parseInt(agenda.getSegIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getSegIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getSegFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getSegFim().substring(3,5));
                intervalo = agenda.getSegInt();
                break;
            case TUESDAY: 
                horaInicio = Integer.parseInt(agenda.getTerIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getTerIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getTerFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getTerFim().substring(3,5));
                intervalo = agenda.getTerInt();
                break;
            case WEDNESDAY: 
                horaInicio = Integer.parseInt(agenda.getQuaIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getQuaIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getQuaFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getQuaFim().substring(3,5));
                intervalo = agenda.getQuaInt();
                break;
            case THURSDAY: 
                horaInicio = Integer.parseInt(agenda.getQuiIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getQuiIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getQuiFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getQuiFim().substring(3,5));
                intervalo = agenda.getQuiInt();
                break;
            case FRIDAY: 
                horaInicio = Integer.parseInt(agenda.getSexIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getSexIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getSexFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getSexFim().substring(3,5));
                intervalo = agenda.getSexInt();
                break;
            case SATURDAY: 
                horaInicio = Integer.parseInt(agenda.getSabIni().substring(0,2));
                minutoInicio = Integer.parseInt(agenda.getSabIni().substring(3,5));
                horaFim = Integer.parseInt(agenda.getSabFim().substring(0,2));
                minutoFim = Integer.parseInt(agenda.getSabFim().substring(3,5));
                intervalo = agenda.getSabInt();
                break;
        }
        
        LocalDateTime inicio = LocalDateTime.of(ano, mes, dia, horaInicio, minutoInicio);
        LocalDateTime fim = LocalDateTime.of(ano, mes, dia, horaFim, minutoFim);
        
        ArrayList<HorarioAgenda> listaT = new ArrayList<>();
        
        do {
            HorarioAgenda tempHor = new HorarioAgenda();
            tempHor.setData(data);
            tempHor.setDataHora(Timestamp.valueOf(inicio));
            tempHor.setPaciente("");
            tempHor.setPresente(Boolean.FALSE);
            tempHor.setAtendido(Boolean.FALSE);
            listaT.add(tempHor);
            inicio = inicio.plusMinutes(intervalo);
        } while (!inicio.isAfter(fim));
        
        
        return listaT;
    }    
    
    public ObservableList<HorarioAgenda> getObsListaT() {
        return FXCollections.observableArrayList(getListaT());
    }
    
    public ObservableList<HorarioAgenda> getObsListaAgenda() {
        
        ArrayList<HorarioAgenda> listaA = getLista();
        ArrayList<HorarioAgenda> listaB = getListaT();
               
        Boolean achou;
        Boolean vazio;
        Boolean dois_horarios = Boolean.FALSE;
        for (Integer i=0;i<listaA.size();i++) {
            achou = Boolean.FALSE;
            vazio = Boolean.TRUE;            
            Integer indice = -1;
            dois_horarios = (listaA.get(i).getIntervalos() == 2);
            for (Integer j=0;j<listaB.size();j++) {
                if (listaA.get(i).getDataHora().equals(listaB.get(j).getDataHora())) {
                    achou = Boolean.TRUE;
                    if (listaB.get(j).getPaciente().trim().isEmpty()) {
                        indice = j;
                        vazio = Boolean.TRUE;
                    } else {
                        vazio = Boolean.FALSE;
                        indice = j;
                    }
                    break;
                } else if ((listaA.get(i)).getDataHora().before(listaB.get(j).getDataHora())) {
                    indice = j;
                    break;
                }                                        
            }                                
            if (achou) {
                if (vazio) {
                    listaB.get(indice).setId(listaA.get(i).getId());
                    listaB.get(indice).setPaciente(listaA.get(i).getPaciente());
                    listaB.get(indice).setPresente(listaA.get(i).getPresente());
                    listaB.get(indice).setAtendido(listaA.get(i).getAtendido());
                    listaB.get(indice).setEcg(listaA.get(i).getEcg());          
                    listaB.get(indice).setCodAntigoPaciente(listaA.get(i).getCodAntigoPaciente());
                    listaB.get(indice).setCodPaciente(listaA.get(i).getCodPaciente());
                    listaB.get(indice).setConvenio(listaA.get(i).getConvenio());
                    listaB.get(indice).setEncaixe(listaA.get(i).getEncaixe());
                    listaB.get(indice).setEvento(listaA.get(i).getEvento());
                    listaB.get(indice).setHoraChegada(listaA.get(i).getHoraChegada());
                    listaB.get(indice).setIntervalos(listaA.get(i).getIntervalos());
                    listaB.get(indice).setObservacoes(listaA.get(i).getObservacoes());
                    listaB.get(indice).setTelefone1(listaA.get(i).getTelefone1());
                    listaB.get(indice).setTelefone2(listaA.get(i).getTelefone2());   
                    
                    if (dois_horarios) {
                        listaB.remove(indice+1);
                        dois_horarios = Boolean.FALSE;
                        continue;
                }

                } else {
                    listaB.add(indice,listaA.get(i));
                }
            } else {
                indice = i;
                listaB.add(indice,listaA.get(i));
            } 
        } 
        ObservableList<HorarioAgenda> oLista = FXCollections.observableArrayList(listaB);        
        return oLista;
    }
    

    public ObservableList<HorarioAgenda> getObsListaAgenda(Boolean livres) { 
        
        ArrayList<HorarioAgenda> lista = new ArrayList<>();
        ArrayList<HorarioAgenda> listaA = getLista();
        ArrayList<HorarioAgenda> listaB = getListaT();
        
        if (!livres) {
            lista = listaA;
        } else {        
            if (listaA.isEmpty()) {
                lista.addAll(listaB);
            } else {
                Boolean achou;
                Boolean vazio;
                Boolean dois_horarios = Boolean.FALSE;
                for (Integer i=0;i<listaB.size();i++) {
                    achou = Boolean.FALSE;
                    //vazio = Boolean.TRUE;
                    //Integer indice = -1;
                    if (dois_horarios) {
                        continue;
                    }
                    for (Integer j=0;j<listaA.size();j++) {
                        if (listaB.get(i).getDataHora().equals(listaA.get(j).getDataHora())) {
                            achou = Boolean.TRUE;                        
                            if (listaA.get(j).getIntervalos()==2) {
                                dois_horarios = Boolean.TRUE;
                            } else {
                                dois_horarios = Boolean.FALSE;
                            }
                            break;
                        }                                
                    }
                    
                    if (!achou) {
                        if (livres) {
                            lista.add(listaB.get(i));
                        }
                    }
                }
            }
        }
        ObservableList<HorarioAgenda> oLista = FXCollections.observableArrayList(lista);        
        return oLista;
    }
    
    public static class EstatConsultas {
        
        private ArrayList<HorarioAgenda> horarios;
        public Integer marcadas = 0;
        public Integer atendidas = 0;
        public Integer ecgs = 0;
        public Integer encaixes = 0;
        public Integer pvez = 0;
        
        public EstatConsultas(ArrayList<HorarioAgenda> h) {
            horarios = h;
            init();
        }
        
        public void init() {
            for (HorarioAgenda horarioAgenda : horarios) {
                marcadas++;
                if (horarioAgenda.getAtendido()) atendidas++;
                if (horarioAgenda.getEcg()) ecgs++;
                if (horarioAgenda.getEncaixe()) encaixes++;
                if (horarioAgenda.getEvento().equals("Primeira Vez")) pvez++;
            }
        }
                        
    }
}