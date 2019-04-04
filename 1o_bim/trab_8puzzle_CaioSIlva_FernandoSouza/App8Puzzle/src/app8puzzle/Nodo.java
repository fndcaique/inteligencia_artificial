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
 */
public class Nodo implements Comparable<Nodo> {

    private int fc, fa, profundidade;
    private ArrayList<Pair<Integer, Integer>> caminho;
    private ArrayList<String> visits;
    private int[][] matrix;
    private int x, y;

    public Nodo() {

    }

    public Nodo(int[][] matrix, int x, int y, ArrayList<Pair<Integer, Integer>> caminho, int fc, int fa, int profundidade) {
        this.profundidade = profundidade;
        this.fc = fc;
        this.fa = fa;
        this.caminho = caminho;
        this.matrix = matrix;
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize() {
        visits = new ArrayList<>();
    }

    public void setVisits(ArrayList<String> visits) {
        this.visits = visits;
    }

    public void insertVisit(String est) {
        int pos = binarySearch(est);
        if (pos >= visits.size()) {
            pos -= visits.size();
            visits.add(pos, est);
        }

    }

    public boolean isVisit(String est) {
        return binarySearch(est) < visits.size();
    }

    public int binarySearch(String est) {
        if (visits.isEmpty()) {
            return 0;
        }
        int ini = 0, fim = visits.size() - 1;
        int meio = (ini + fim) >> 1;
        int comp = est.compareTo(visits.get(meio));
        while (ini < fim && comp != 0) {
            if (comp > 0) {
                ini = meio + 1;
            } else {
                fim = meio - 1;
            }
            if (ini < fim) {
                meio = (ini + fim) >> 1;
                comp = est.compareTo(visits.get(meio));
            }
        }

        if (comp == 0) {
            return meio;
        }
        if (comp > 0) {
            return visits.size() + meio + 1;
        }
        return visits.size() + meio;

    }

    public ArrayList<String> getVisits() {
        return visits;
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

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
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
        return (fc + fa) - (o.getFc() + o.getFa());
    }

}
