/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 *
 * @author fnd
 */
public class DataMLP {

    private FndFile file;
    private ArrayList<double[]> dados;
    private ArrayList<String> classes;
    private String sep;
    private boolean header;
    private String[] arrheader;
    private double[][] tabledata;

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
        file.close();
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
}
