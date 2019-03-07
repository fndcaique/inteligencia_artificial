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
    private int x, y;

    public Nodo() {
    }

    public Nodo(int[][] matrix, int x, int y, ArrayList<Pair<Integer, Integer>> caminho, int fc, int fa) {
        this.fc = fc;
        this.fa = fa;
        this.caminho = caminho;
        this.matrix = matrix;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
