/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl.view;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author fnd
 */
public class TelaPrincipalController implements Initializable {
    
    private Label label;
    @FXML
    private JFXTextField txcamentrada;
    @FXML
    private JFXTextField txcamsaida;
    @FXML
    private JFXTextField txcamoculta;
    @FXML
    private JFXTextField txerro;
    @FXML
    private JFXTextField txmaxepocas;
    @FXML
    private JFXTextField txtaxaaprendizado;
    @FXML
    private Label lbfilename;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void selectFuncaoTransferencia(ActionEvent event) {
    }

    @FXML
    private void clkAbrirArquivo(ActionEvent event) {
    }
    
}
