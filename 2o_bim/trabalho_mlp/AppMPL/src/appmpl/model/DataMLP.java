/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author fnd
 */
public class DataMLP {

    private FndFile file;
    private ArrayList<Double> maximos,minimos;
    private ArrayList<double[]> dados;
    private ArrayList<String> classes;
    private String sep;
    private boolean header;
    private String[] arrheader;
    private double[][] tabledata;
    private int[] index;

    public DataMLP(String path, String sep, boolean header) throws FileNotFoundException {
        this.file = new FndFile(path);
        this.header = header;
        this.sep = sep;
        readData();
    }

    private void readData() {
        file.open("r");
        dados = new ArrayList<>();
        classes = new ArrayList<>();
        String line, arr[];
        if (header && (line = file.readLine()) != null) {
            arrheader = line.split(sep);
        }
        int idx;
        while ((line = file.readLine()) != null) {
            arr = line.split(sep);
            double[] dl = new double[arr.length];
            idx = classes.indexOf(arr[arr.length-1]);
            if(idx == -1){
                classes.add(arr[arr.length-1]);
                idx = classes.size()-1;
            }
            for (int i = 0; i < arr.length-1; i++) {
                String str = arr[i];
                dl[i] = Double.parseDouble(str);
            }
            dl[arr.length-1] = idx;
            dados.add(dl);
        }
        index = new int[dados.size()];
        file.close();
        normalizar();
    }
    
    private void normalizar(){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double[] arr;
        int i,j;
        for (i = 0; i < dados.size(); i++) {
            arr = dados.get(i);
            for (j = 0; j < arr.length; j++) {
                if(min > arr[j])
                    min = arr[j];
                if(max < arr[j])
                    max = arr[j];
            }
            max = max - min;
            for (j = 0; j < arr.length; j++) {
                arr[j] = (arr[j] - min)/max;
            }
        }
    }

    public int getColsdata() {
        return dados.get(0).length;
    }

    public int getRows() {
        return dados.size();
    }
    
    public int getQtdeClass(){
        return classes.size();
    }
    
    public ArrayList<String> getClasses(){
        
        return classes;
    }

    public String getClassOfData(int idxlinedata) {
        return classes.get((int)dados.get(idxlinedata)[dados.get(idxlinedata).length-1]);
    }

    public double[] getLine(int idx) {
        return dados.get(idx);
    }

    public double[][] getTableData() {
        if (tabledata == null) {
            tabledata = new double[dados.size()][dados.get(0).length];
            tabledata = dados.toArray(tabledata);
        }
        return tabledata;
    }

    public String[] getHeader(){
        return arrheader;
    }

    public int[] getIndex() {
        return index;
    }
    
}
