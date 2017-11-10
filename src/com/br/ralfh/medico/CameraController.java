/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.br.ralfh.medico;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author ralfh
 */
public class CameraController extends Controller {

    /**
     * Initializes the controller class.
     */
    
    @FXML Button btnFotografar;
    @FXML SwingNode swingNode;
    private final Dimension size;
    private final Webcam webcam;  
    private final WebcamPanel panel; 
    
    public byte[] bFile;
    public Boolean closeModal;
    public File file;
    
    public FileInputStream fileInputStream;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.swingNode.setContent(panel);        
        if (!webcam.isOpen()) {
            webcam.open();
        }        
    }    
    
    public CameraController() {
        this.closeModal = Boolean.FALSE;
        this.size = new Dimension(WebcamResolution.VGA.getSize());  
        this.webcam = Webcam.getDefault();
        this.webcam.setViewSize(this.size);
        this.panel = new WebcamPanel(webcam, size, false);
//        this.panel.setFPSDisplayed(true);
        this.panel.setFillArea(true);  
    }
    
    public void btnFotografarFired(ActionEvent event) throws Exception {
        try {
            file = new File(String.format("paciente.jpg"));        
            ImageIO.write(webcam.getImage(), "JPG", file);
            bFile = new byte[(int) file.length()];
            try {
                fileInputStream = new FileInputStream(file);
                //convert file into array of bytes
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                 e.printStackTrace();
            }                        
            this.webcam.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.closeModal = true;
        this.stage.close();
    }   
    
    
    
    
}
