/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author fnd
 */
public class Csv {
    private FndFile file;
    private boolean header;
    private String separator;
    private ArrayList<String> listheader;

    public Csv(String path, boolean header, String separator) throws FileNotFoundException {
        this.file = new FndFile(path);
        this.header = header;
        this.separator = separator;
    }
    
    public ArrayList<ArrayList<String>> getData(){
        file.open("r");
        String line;
        String[] arr;
        if(header && (line = file.readLine()) != null){
            arr = line.split(separator);
            listheader = new ArrayList<>(arr.length);
            listheader.addAll(Arrays.asList(arr));
        }
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        while((line = file.readLine()) != null){
            arr = line.split(separator);
            ArrayList<String> dtl = new ArrayList<>(arr.length);
            dtl.addAll(Arrays.asList(arr));
            data.add(dtl);
        }
        file.close();
        return data;
    }
    
    
}
