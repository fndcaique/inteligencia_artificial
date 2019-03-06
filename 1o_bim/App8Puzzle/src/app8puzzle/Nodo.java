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
 * @author fnd
 * @param <T>
 */
public class Nodo implements Comparable<Nodo>
{
    private int fc, fa;
    private ArrayList<Pair<Integer, Integer>> caminho;
    private int [][] matrix;
    private int cx, cy;

    public Nodo() {
    }

    public Nodo(int fc, int fa, ArrayList<Pair<Integer, Integer>> caminho, int[][] matrix, int cx, int cy) {
        this.fc = fc;
        this.fa = fa;
        this.caminho = caminho;
        this.matrix = matrix;
        this.cx = cx;
        this.cy = cy;
    }
    
    

    public ArrayList<Pair<Integer, Integer>> getCaminho() {
        return caminho;
    }

    public void setCaminho(ArrayList<Pair<Integer, Integer>> caminho) {
        this.caminho = caminho;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }
    
    

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getFa() {
        return fa;
    }

    public void setFa(int fa) {
        this.fa = fa;
    }

    @Override
    public int compareTo(Nodo o) {
        return (fc+fa) - (o.getFc() + o.getFa());
    }
    
}
