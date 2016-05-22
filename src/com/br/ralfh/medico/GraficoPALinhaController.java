/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import com.br.ralfh.medico.modelos.FichaMedica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class GraficoPALinhaController extends Controller {
    
    @FXML LineChart<Number,Number> lcGrafico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
    public void initGrafico(FichaMedica fichamedica) {
        
        lcGrafico.setTitle("Variação da Pressão Arterial");
        
        
//        XYChart.Series seriePD = fichamedica.consultaSeriePA("DIASTOLICA");  
//        seriePD.setName("Diastólica");
        
        
//        XYChart.Series seriePS = fichamedica.consultaSeriePA("SISTOLICA");  
//        seriePS.setName("Sistolica");
        
        
//        lcGrafico.getData().add(seriePD);
//        lcGrafico.getData().add(seriePS);
        
    }
    
}
