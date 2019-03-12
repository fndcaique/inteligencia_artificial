/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import com.sun.webkit.network.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author fndcaique
 */
public class Tabuleiro {

    private boolean continuar;
    private int[][] begin;
    private String estFinal;
    private int iteracoes;
    private HashMap<Integer, Pair<Integer, Integer>> manhattan;
    private ArrayList<Pair<Integer, Integer>> caminho;

    private final int[] dx = {1, -1, 0, 0}, dy = {0, 0, -1, 1};
    private final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3, DIRECTIONS = 4;

    public Tabuleiro(int[][] begin, int[][] end) {
        this.begin = begin;
        estFinal = Utils.arrayToString(end);
        manhattan = new HashMap<>();
        for (int i = 0; i < begin.length; ++i) {
            for (int j = 0; j < begin[0].length; ++j) {
                manhattan.put(end[i][j], new Pair<>(j, i));
            }
        }
    }

    private int distManhattan(int x, int y, int value) {
        Pair<Integer, Integer> coord = manhattan.get(value); // 0 a 8
        return Math.abs(x - coord.getKey()) + Math.abs(y - coord.getValue());
    }


    /**
     * @param y coordenada y do curinga
     * @param x coordenada x do curinga
     * @return list com os passos para a solução
     */
    public ArrayList<Pair<Integer, Integer>> solveDFS(int y, int x) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        Stack<Nodo> pq = new Stack<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.push(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.pop();
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) 
                        {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, 0, 0);
                            aux.setVisits(list);
                            pq.push(aux);
                        }

                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    /**
     * @param y coordenada y do curinga
     * @param x coordenada x do curinga
     * @return list com os passos para a solução
     */
    public ArrayList<Pair<Integer, Integer>> solveBFS(int x, int y) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        ArrayList<Nodo> pq = new ArrayList<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.add(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove(0);
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) 
                        {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, 0, 0);
                            aux.setVisits(list);
                            pq.add(aux);
                        }

                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    public ArrayList<Pair<Integer, Integer>> solveBestFirst_QtdeFora(int x, int y) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.add(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, 0, analiseQtdeFora(mat));
                            aux.setVisits(list);
                            pq.add(aux);
                        }

                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    public ArrayList<Pair<Integer, Integer>> solveBestFirst_DistManhattan(int x, int y) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.add(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, 0, analiseDistManhattan(mat));
                            aux.setVisits(list);
                            pq.add(aux);
                        }
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    private int analiseQtdeFora(int[][] m) {
        int qtde = 0;
        for (int i = 0, k = 1; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j, ++k) {
                if (m[i][j] != k) {
                    qtde++;
                }
            }
        }
        return qtde;
    }

    public ArrayList<Pair<Integer, Integer>> solveA_DistManhattan(int x, int y) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.add(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, no.getFc() + 1, analiseDistManhattan(mat));
                            aux.setVisits(list);
                            pq.add(aux);
                        }

                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    public ArrayList<Pair<Integer, Integer>> solveA_QtdeFora(int x, int y) {
        String est;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(x, y));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(begin, x, y, caminhoatual, 0, 0), aux;
        pq.add(no);
        caminho = null;
        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            est = Utils.arrayToString(no.getMatrix());
            //insertBinaryVisits(Utils.copyMatrix(no.getMatrix()));
            no.insertVisit(est);
            if (est.compareTo(estFinal) == 0) {
                caminho = no.getCaminho();
                break;
            } else {
                x = no.getX();
                y = no.getY();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (Utils.inMatrix(begin.length, x + dx[i], y + dy[i])) {
                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                        // if(binarySearchVisits(mat) >= visits.size()
                        est = Utils.arrayToString(mat);
                        if (!no.isVisit(est)) {
                            ArrayList<String> list = Utils.copyArray(no.getVisits());
                            novocam = Utils.copyArrayList(no.getCaminho());
                            novocam.add(new Pair(x + dx[i], y + dy[i]));
                            aux = new Nodo(Utils.copyMatrix(mat), x + dx[i], y + dy[i],
                                    novocam, no.getFc() + 1, analiseQtdeFora(mat));
                            aux.setVisits(list);
                            pq.add(aux);
                        }

                        Utils.swap(mat, x, y, x + dx[i], y + dy[i]);
                    }
                }
            }
        }
        return caminho;
    }

    private int analiseDistManhattan(int[][] ma) {
        int fa = 0;
        for (int i = 0; i < ma.length; ++i) {
            for (int j = 0; j < ma[0].length; ++j) {
                fa += distManhattan(j, i, ma[i][j]);
            }
        }
        return fa;
    }

    public int getIteracoes() {
        return iteracoes;
    }

    public void stopSolve() {
        continuar = false;
    }

    public int[] getDx() {
        return dx;
    }

    public int[] getDy() {
        return dy;
    }

    public int getRIGHT() {
        return RIGHT;
    }

    public int getLEFT() {
        return LEFT;
    }

    public int getUP() {
        return UP;
    }

    public int getDOWN() {
        return DOWN;
    }


}
