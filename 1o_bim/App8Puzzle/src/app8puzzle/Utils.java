/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author fndcaique
 */
public class Utils {

    public static ArrayList<Pair<Integer, Integer>> copyArrayList(ArrayList<Pair<Integer, Integer>> arr) {
        ArrayList<Pair<Integer, Integer>> b = new ArrayList<>();
        arr.forEach((p) -> {
            b.add(new Pair<>(p.getKey(), p.getValue()));
        });
        return b;
    }
    
    public static ArrayList<String> copyArray(ArrayList<String> l){
        ArrayList<String> lis = new ArrayList<>();
        for (String li : lis) {
            l.add(li);
        }
        return l;
    }

    public static void swap(int[][] m, int x1, int y1, int x2, int y2) {
        // m[y1][x1] = a, m[y2][x2] = b,   a = 0100, b = 1011
        m[y1][x1] ^= m[y2][x2]; // a ^= b, a = 1111, b = 1011
        m[y2][x2] ^= m[y1][x1]; // b ^= a, a = 1111, b = 0100
        m[y1][x1] ^= m[y2][x2]; // a ^= b, a = 1011, b = 0100 
    }

    public static int[][] copyMatrix(int[][] m) {
        int[][] a = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                a[i][j] = m[i][j];
            }
        }
        return a;
    }
    
    public static boolean inMatrix(int N, int x, int y) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }


    public static String arrayToString(int[][] m) {
        String s = "";
        for (int[] is : m) {
            for (int i : is) {
                s += i;
            }
        }
        return s;
    }


}
