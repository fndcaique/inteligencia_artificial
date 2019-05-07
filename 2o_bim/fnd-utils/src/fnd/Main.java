/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnd;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author fnd
 */
public class Main {
    public static void main(String[] args){
        DataMLP data;
        try {
            data = new DataMLP("triangulo.csv", ",", true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            data = null;
        }
        if(data != null){
            int i = 0;
            System.out.print("Classes = {");
            for (String classe : data.getClasses()) {
                System.out.print(classe+",");
            }
            System.out.println("}");
            for (double[] ds : data.getTableData()) {
                for (double d : ds) {
                    System.out.print(d+", ");
                }
                System.out.println("- "+data.getClassOfData(i++));
            }
        }
        
    }
}
