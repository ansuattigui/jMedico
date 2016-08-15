/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import com.br.ralfh.medico.exceptions.CampoEmBrancoException;
import com.br.ralfh.medico.jdbc.DataAccessRelatorios;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class FaturConvenioController extends Controller {
    
    @FXML public DatePicker dataInicio;
    @FXML public DatePicker dataFim;
    @FXML public RadioButton repHoje;
    @FXML public RadioButton repOntem;
    @FXML public RadioButton repSemana;
    @FXML public RadioButton repMes;
    @FXML public RadioButton repPeriodo;
    @FXML public Button btnConfirma;
    @FXML public Button btnCancela;
    @FXML public ToggleGroup tgPerRelat;
    
    private TipoRelatorio tipoRelat;
    private HashMap hm ;
    
    public FaturConvenioController () {
        
        tipoRelat = TipoRelatorio.convenioSintetico;
        
    }    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataInicio.setValue(LocalDate.now());
        dataFim.setValue(LocalDate.now());
        // TODO
    }  
    
    private boolean preparaRelatorio() {
        String strDataIni = null;
        String strDataFim = null;
        Boolean resultado = Boolean.FALSE;
        try {
            if (dataInicio.getValue()==null) {
                throw new CampoEmBrancoException("Informe a data inicial para o relatorio");
            } else {
                strDataIni = dataInicio.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            if (dataFim.getValue()==null) {
                throw new CampoEmBrancoException("Informe a data final para o relatorio");
            } else {
                strDataFim = dataFim.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            String logo = new File("logoJHTC.jpg").getAbsolutePath();
            Date dDataIni = Util.udate(dataInicio.getValue().atStartOfDay());
            Date dDataFim = Util.udate(dataFim.getValue().atTime(23,59));
            String periodo = strDataIni + " à " + strDataFim;

            hm = new HashMap();        
            hm.put("periodo",periodo);
            hm.put("inicio", dDataIni);
            hm.put("fim", dDataFim);         
            hm.put("logo",logo);  
            
            resultado = Boolean.TRUE;
            
        } catch (CampoEmBrancoException ex) {
           ShowDialog("EX", ex.getMessage(), null,this.getStage());
        }
        return resultado;
    }
    
    
    public void btnConfirmaFired(ActionEvent event) {        
        switch (tipoRelat) {            
            case convenioSintetico: {
                if (preparaRelatorio()) {                
                    String fileName = "relatorios/convenios/FaturConvSintetico_2.jasper";
                    DataAccessRelatorios relat = new DataAccessRelatorios();
                    try {
                        InputStream inputStream = getClass().getResourceAsStream(fileName);
                        relat.openReport( "Relatório",inputStream,hm);
                    } catch (JRException e) {
                        e.printStackTrace();   //System.exit(1);
                    } catch (Exception e) {
                        e.printStackTrace();//System.exit(1);
                    }                
                }
                break;
            }
            case convenioAnalitico: {
                String fileName = "relatorios/repVisitaACS.jasper";
                String strDataIni = dataInicio.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String strDataFim = dataFim.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Date dDataIni = Util.udate(dataInicio.getValue());
                Date dDataFim = Util.udate(dataFim.getValue());
                HashMap hm = new HashMap();        
                hm.put("parmPeriodoVis",strDataIni+" a "+strDataFim);
                hm.put("parmDataIni", dDataIni);
                hm.put("parmDataFim", dDataFim);
                DataAccessRelatorios relat = new DataAccessRelatorios();
                try {
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    relat.openReport( "Relatório",inputStream,hm);
                } catch (JRException e) {
                    e.printStackTrace();
                    System.exit(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }                
                break;
            }
        }
                
        
    }
    
    public void btnCancelaFired(ActionEvent event) {
        this.stage.close();
    }


    /**
     * @return the tipoRelat
     */
    public TipoRelatorio getTipoRelat() {
        return tipoRelat;
    }

    /**
     * @param tipoRelat the tipoRelat to set
     */
    public void setTipoRelat(TipoRelatorio tipoRelat) {
        this.tipoRelat = tipoRelat;
    }
    
    
    public void repHojePushed(ActionEvent ev) {
        dataInicio.setValue(LocalDate.now());
        dataFim.setValue(LocalDate.now());
    }
    
    public void repOntemPushed(ActionEvent ev) {
        dataInicio.setValue(LocalDate.now().minusDays(1));
        dataFim.setValue(LocalDate.now().minusDays(1));
    }
    
    public void repSemanaPushed(ActionEvent ev) {
        dataInicio.setValue(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
        dataFim.setValue(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)));
    }
    
    public void repMesPushed(ActionEvent ev) {
        dataInicio.setValue(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        dataFim.setValue(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
    }   
    
    public void repPeriodoPushed(ActionEvent ev) {
        dataInicio.setValue(null);
        dataFim.setValue(null);
    }   
    
}
