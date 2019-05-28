/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessamento;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fnd
 */
public class PreProcessamento {

    public void exec(String pathent, String pathsai) throws Exception {
        FndFile in = new FndFile(pathent), out = new FndFile(pathsai);

        ArrayList<HashMap<String, String>> col = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            col.add(new HashMap());
        }

        col.get(0).put("vhigh", "4");
        col.get(0).put("high", "3");
        col.get(0).put("med", "2");
        col.get(0).put("low", "1");

        col.get(1).put("vhigh", "4");
        col.get(1).put("high", "3");
        col.get(1).put("med", "2");
        col.get(1).put("low", "1");

        col.get(2).put("2", "2");
        col.get(2).put("3", "3");
        col.get(2).put("4", "4");
        col.get(2).put("5more", "5");

        col.get(3).put("2", "2");
        col.get(3).put("4", "4");
        col.get(3).put("more", "5");

        col.get(4).put("small", "1");
        col.get(4).put("med", "2");
        col.get(4).put("big", "3");

        col.get(5).put("low", "1");
        col.get(5).put("med", "2");
        col.get(5).put("high", "3");

        col.get(6).put("unacc", "unacc");
        col.get(6).put("acc", "acc");
        col.get(6).put("good", "good");
        col.get(6).put("vgood", "vgood");

        in.open("r");
        out.open("rw");
        out.setLength(0);
        String linha, arr[];
        int i, lin = 1;
        while ((linha = in.readLine()) != null) {
            arr = linha.split(",");
            System.out.print("["+(lin++)+"]");
            for (i = 0; i < arr.length - 1; i++) {
                out.writeString(col.get(i).get(arr[i])+',');
                System.out.print(col.get(i).get(arr[i])+',');
            }
            out.writeString(col.get(i).get(arr[i])+"\n");
            System.out.println(col.get(i).get(arr[i]));
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        PreProcessamento prep = new PreProcessamento();
        try {
            prep.exec("car.data", "car.csv");
        } catch (Exception ex) {
            Logger.getLogger(PreProcessamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
