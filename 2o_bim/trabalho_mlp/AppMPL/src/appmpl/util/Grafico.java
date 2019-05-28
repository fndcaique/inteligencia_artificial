/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl.util;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;


/**
 *
 * @author Alexandre LM
 */
public class Grafico extends ScrollPane implements Observador{

    private LineChart<Double, Double> chart;
    private NumberAxis xAxis,yAxis;
    private double xoffset,yoffset,orix,oriy,escala;
    private XYChart.Series<Double,Double> serieatual;
    
    public Grafico(){
        xAxis = new NumberAxis();
        xAxis.setAnimated(false);
        xAxis.setLabel("Época");
        yAxis = new NumberAxis();
        yAxis.setLabel("Erro Médio");
        chart = new LineChart(xAxis,yAxis);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setPrefHeight(400);
        this.setContent(chart);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        
//        chart.setOnMousePressed((event) -> {
//            orix = event.getX();
//            oriy = event.getY();
//        });
//        
//        chart.setOnMouseDragged((event) -> {
//            yoffset = (event.getY() - oriy) * 0.025;
//            xoffset = (event.getX() - orix) * 0.025;
//            //System.out.println(xoffset);
//            //System.out.println(yoffset);
//            yAxis.setUpperBound(yAxis.getUpperBound() + yoffset);
//            yAxis.setLowerBound(yAxis.getLowerBound() + yoffset);
//            xAxis.setUpperBound(xAxis.getUpperBound() - xoffset);
//            xAxis.setLowerBound(xAxis.getLowerBound() - xoffset);
//            orix = event.getX();
//            oriy = event.getY();
//        });
//        
//        chart.setOnScroll((event) -> {
//            Platform.runLater(()->{
//            if (event.getDeltaY() < 0) {
//                //escala += 0.01;
//                yAxis.setUpperBound(yAxis.getUpperBound() * 1.2);
//                yAxis.setLowerBound(yAxis.getLowerBound() * 1.2);
//                xAxis.setUpperBound(xAxis.getUpperBound() * 1.2);
//                xAxis.setLowerBound(xAxis.getLowerBound() * 1.2);
//                
//            }
//            else {
//                //escala-=0.01;
//                yAxis.setUpperBound(yAxis.getUpperBound() * 0.8);
//                yAxis.setLowerBound(yAxis.getLowerBound() * 0.8);
//                xAxis.setUpperBound(xAxis.getUpperBound() * 0.8);
//                xAxis.setLowerBound(xAxis.getLowerBound() * 0.8);
//            }
//            yAxis.setTickUnit((yAxis.getUpperBound() - yAxis.getLowerBound()) / 10);
//            xAxis.setTickUnit((xAxis.getUpperBound() - xAxis.getLowerBound()) / 10);
//            });
//        });
        
    }
    
    public void initNewSerie(){
        
        serieatual = new XYChart.Series<>();
        Platform.runLater(()->chart.getData().add(serieatual));
    }
    
    public void clear(){
        Platform.runLater(()->chart.getData().clear());
    }
    
    @Override
    public void update(double x, double y) {
        Platform.runLater(()->serieatual.getData().add(new XYChart.Data<>(x, y)));
        //series.getData().add(new XYChart.Data<>(x, y));
    }
}
