/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author fnd
 */
public class DataMLP {

    private HashMap<String, Boolean> mapclasses;
    private HashMap<Integer, String> classedados;
    private FndFile file;
    private ArrayList<double[]> dados;
    private String sep;
    private boolean header;
    private String[] arrheader, arrclass;
    private double[][] tabledata;
    private int colsdata, rows;

    public DataMLP(String path, String sep, boolean header) throws FileNotFoundException {
        this.file = new FndFile(path);
        this.header = header;
        this.sep = sep;
        mapclasses = new HashMap<>();
        classedados = new HashMap<>();
        readData();
    }

    private void readData() {
        file.open("r");
        dados = new ArrayList<>();
        String line, arr[];
        if (header && (line = file.readLine()) != null) {
            arrheader = line.split(sep);
        }
        int idx = 0, cols = 0;
        while ((line = file.readLine()) != null) {
            arr = line.split(sep);
            double[] dl = new double[cols = arr.length - 1];
            mapclasses.put(arr[cols], true);
            for (int i = 0; i < cols; i++) {
                String str = arr[i];
                dl[i] = Double.parseDouble(str);
            }
            dados.add(dl);
            classedados.put(idx, arr[cols]);
            ++idx;
        }
        colsdata = cols;
        file.close();
    }

    public int getColsdata() {
        return colsdata;
    }

    public int getRows() {
        return rows;
    }
    
    public int getQtdeClass(){
        return mapclasses.size();
    }
    
    public String[] getClasses(){
        if(arrclass == null){
            mapclasses.keySet().toArray(arrclass = new String[mapclasses.size()]);
        }
        return arrclass;
    }

    public String getClassOfData(int idxlinedata) {
        return classedados.get(idxlinedata);
    }

    public double[] getLine(int idx) {
        return dados.get(idx);
    }

    public double[][] getTableData() {
        if (tabledata == null) {
            tabledata = new double[dados.size()][colsdata];
            tabledata = dados.toArray(tabledata);
        }
        return tabledata;
    }

    public String[] getHeader(){
        return arrheader;
    }
}
