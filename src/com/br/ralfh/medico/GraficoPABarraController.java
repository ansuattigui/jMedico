/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.FichaMedica;
import com.br.ralfh.medico.modelos.Paciente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class GraficoPABarraController extends Controller {
    
    @FXML BarChart<Number,Number> lcGrafico;
    
    String tipoGrafico;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setTipoGrafico(String tg) {
        tipoGrafico = tg;
    }
    
    public void initGrafico(Paciente paciente) { //(FichaMedica fichamedica) {
        
        if (null != tipoGrafico) switch (tipoGrafico) {
            case "btnGrafPA":
                lcGrafico.setTitle("Variação da Pressão Arterial");
                XYChart.Series seriePS = FichaMedica.consultaSeriePA(paciente,"SISTOLICA"); //    fichamedica.consultaSeriePA("SISTOLICA");
                seriePS.setName("Sistolica");
                XYChart.Series seriePD = FichaMedica.consultaSeriePA(paciente,"DIASTOLICA"); //fichamedica.consultaSeriePA("DIASTOLICA");
                seriePD.setName("Diastólica");
                lcGrafico.getData().add(seriePS);
                lcGrafico.getData().add(seriePD);
                break;
            case "btnGrafFC":{
                lcGrafico.setTitle("Variação da Frequência Cardíaca");
                    XYChart.Series serie = FichaMedica.consultaSeriePA(paciente,"FREQCARD"); //fichamedica.consultaSeriePA("FREQCARD");
                    serie.setName("Frequência Cardíaca");
                    lcGrafico.getData().add(serie);
                    break;
                }
            case "btnGrafPu":{
                lcGrafico.setTitle("Variação do Pulso");
                    XYChart.Series serie = FichaMedica.consultaSeriePA(paciente,"PULSO"); //fichamedica.consultaSeriePA("PULSO");
                    serie.setName("Pulso");
                    lcGrafico.getData().add(serie);
                    break;
                }
        }
        
    }
    
}
