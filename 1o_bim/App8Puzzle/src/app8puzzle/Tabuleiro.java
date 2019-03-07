/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author fndcaique
 */
public class Tabuleiro {

    private boolean continuar;
    private int[][] matrix;
    private int N, N2, iteracoes;
    private ArrayList<Pair<Integer, Integer>> manhattan;
    private ArrayList<Pair<Integer, Integer>> caminho;
    private ArrayList<int[][]> visits;

    private final int[] dx = {1, -1, 0, 0}, dy = {0, 0, -1, 1};
    private final int TF = 3, RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3, DIRECTIONS = 4;

    public Tabuleiro(int n) {
        this.N = n;
        this.N2 = N * N;
        matrix = new int[n][n];
        visits = new ArrayList<>();
        manhattan = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                manhattan.add(new Pair<>(i, j));
            }
        }
    }

    private int distManhattan(int y, int x, int value) {
        Pair<Integer, Integer> coord = manhattan.get(value - 1); // 0 a 8
        return Math.abs(y - coord.getKey()) + Math.abs(x - coord.getValue());
    }

    /**
     * utilize-a para preencher a matrix problema, informando as coordenadas de
     * onde estão os valores atuais
     */
    public void setValue(int y, int x, int v) {
        matrix[y][x] = v;
    }

    /**
     * informe a coordenada do curinga recomenda-se que o curinga seja um valor
     * não necessário para a ordem da matrix resultado -1 ou 9 por exemplo
     */
    private boolean inMatrix(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private boolean isOrdered(int m[][]) {
        boolean f = true;
        int k = 1;
        for (int i = 0; f && i < N; i++) {
            for (int j = 0; f && j < N; j++) {
                if (m[i][j] == k) {
                    ++k;
                } else {
                    f = false;
                }
            }
        }
        return k >= N2;
    }

    private int qtdeInOrder() {
        int k = 1, qtde = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j, ++k) {
                if (matrix[i][j] == k) {
                    ++qtde;
                }
            }

        }
        return qtde;
    }

    private void swap(int[][] m, int y1, int x1, int y2, int x2) {
        // m[y1][x1] = a, m[y2][x2] = b,   a = 0100, b = 1011
        m[y1][x1] ^= m[y2][x2]; // a ^= b, a = 1111, b = 1011
        m[y2][x2] ^= m[y1][x1]; // b ^= a, a = 1111, b = 0100
        m[y1][x1] ^= m[y2][x2]; // a ^= b, a = 1011, b = 0100 
    }

    private int[][] copyMatrix(int[][] m) {
        int[][] a = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                a[i][j] = m[i][j];
            }
        }
        return a;
    }

    public ArrayList<int[][]> getVisits() {
        return visits;
    }

    public void exibeMatrix(int[][] ma) {
        System.out.println("------------------------------------");
        for (int[] is : ma) {
            for (int i : is) {
                System.out.print(i + ", ");
            }
            System.out.println("");
        }
    }

    public void exibeVisits() {

        for (int[][] visit : visits) {
            System.out.println("Visitada:");
            exibeMatrix(visit);
        }
    }

    private int compareMatrix(int m1[][], int m2[][]) {
        int r;
        for (int i = 0; i < m1.length; ++i) {
            for (int j = 0; j < m1[0].length; ++j) {
                r = m1[i][j] - m2[i][j];
                if (r != 0) {
                    return r;
                }
            }
        }
        return 0;
    }

    /**
     * se achou retorna a posição na list de visits; se não achou retorna
     * visits.size() + a pos que deveria estar
     */
    private int binarySearchVisits(int ma[][]) {
        if (visits.isEmpty()) {
            return 0;
        } else {
            int ini = 0, fim = visits.size() - 1;
            int med = (ini + fim) >> 1;
            int dif = compareMatrix(ma, visits.get(med));
            while (ini < fim && dif != 0) {
                if (dif > 0) {
                    ini = med + 1;
                } else {
                    fim = med - 1;
                }
                if (ini < fim) {
                    med = (ini + fim) >> 1;
                    dif = compareMatrix(ma, visits.get(med));
                }
            }
            if (dif == 0) {
                return med;
            } else {
                dif = compareMatrix(ma, visits.get(med));
                if (dif > 0) {
                    return visits.size() + (med + 1);
                }
                return visits.size() + med;
            }
        }
    }

    public int insertBinaryVisits(int ma[][]) {
        int pos = binarySearchVisits(ma);
        if (pos >= visits.size()) { // ainda não inserido
            pos -= visits.size();
            visits.add(null); // adicionando posição livre
            remanejaVisits(pos);
            visits.set(pos, ma);
        }
        return pos;
    }

    private void remanejaVisits(int pos) {
        for (int i = visits.size() - 2; i >= pos; --i) {
            visits.set(i + 1, visits.get(i));
        }
    }

    /**
     * @param y coordenada y do curinga
     * @param x coordenada x do curinga
     * @return list com os passos para a solução
     */
    public ArrayList<Pair<Integer, Integer>> solveDFS(int y, int x) {
        //System.out.println("Iniciando DFS");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), nc;
        Stack<ArrayList<Pair<Integer, Integer>>> pcaminhos = new Stack<>();
        Stack<Integer> pcoords = new Stack<>(); // pilha de coods curinga
        Stack<int[][]> pmatrix = new Stack<>(); // pilha de matrix

        caminhoatual.add(new Pair<>(y, x));
        pcoords.push(x);
        pcoords.push(y);
        pmatrix.push(copyMatrix(matrix));
        pcaminhos.push(caminhoatual);
        iteracoes = 0;
        continuar = true;
        while (continuar && !pmatrix.isEmpty()) {
            y = pcoords.pop();
            x = pcoords.pop();
            mat = pmatrix.pop();
            caminhoatual = pcaminhos.pop();
            //exibeMatrix(mat);
            ++iteracoes;
            insertBinaryVisits(copyMatrix(mat));
            if (isOrdered(mat)) {
                caminho = caminhoatual;
                solved = true;
                break;
            } else {
                for (int i = DIRECTIONS - 1; i >= 0; --i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);

                        if (binarySearchVisits(mat) >= visits.size()) {
                            nc = copyArrayList(caminhoatual);
                            nc.add(new Pair<>(y + dy[i], x + dx[i]));

                            pcoords.push(x + dx[i]);
                            pcoords.push(y + dy[i]);
                            pmatrix.push(copyMatrix(mat));
                            pcaminhos.push(nc);
                        }
                        swap(mat, y, x, y + dy[i], x + dx[i]);

                    }
                }
            }
        }
        if (solved) {
            return caminho;
        }
        System.out.println("DFS Iterações = " + iteracoes);
        return null;
    }

    /**
     * @param y coordenada y do curinga
     * @param x coordenada x do curinga
     * @return list com os passos para a solução
     */
    public ArrayList<Pair<Integer, Integer>> solveBFS(int y, int x) {
        //System.out.println("Iniciando BFS");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), nc;
        ArrayList<ArrayList<Pair<Integer, Integer>>> filacaminhos = new ArrayList<>();
        ArrayList<Integer> filacoords = new ArrayList<>(); // pilha de coods curinga
        ArrayList<int[][]> filamatrix = new ArrayList<>(); // pilha de matrix

        caminhoatual.add(new Pair<>(y, x));
        filacoords.add(x);
        filacoords.add(y);
        filamatrix.add(copyMatrix(matrix));
        filacaminhos.add(caminhoatual);
        iteracoes = 0;
        continuar = true;
        while (continuar && !filamatrix.isEmpty()) {
            x = filacoords.remove(0);
            y = filacoords.remove(0);
            mat = filamatrix.remove(0);
            caminhoatual = filacaminhos.remove(0);
            //exibeMatrix(mat);
            iteracoes++;
            insertBinaryVisits(copyMatrix(mat));
            if (isOrdered(mat)) {
                caminho = caminhoatual;
                solved = true;
                break;
            } else {
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                        if (binarySearchVisits(mat) >= visits.size()) {
                            nc = copyArrayList(caminhoatual);
                            nc.add(new Pair<>(y + dy[i], x + dx[i]));

                            filacoords.add(x + dx[i]);
                            filacoords.add(y + dy[i]);
                            filamatrix.add(copyMatrix(mat));
                            filacaminhos.add(nc);
                        }

                        swap(mat, y, x, y + dy[i], x + dx[i]);

                    }
                }
            }
        }
        if (solved) {
            return caminho;
        }
        System.out.println("BFS Iterações = " + iteracoes);
        return null;
    }

    public ArrayList<Pair<Integer, Integer>> solveBestFirst_QtdeFora(int y, int x) {
        //System.out.println("Iniciando Best First");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(y, x));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(0, 0, caminhoatual, matrix, x, y), aux;
        pq.add(no);

        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            insertBinaryVisits(copyMatrix(no.getMatrix()));
            if (isOrdered(no.getMatrix())) {
                caminho = no.getCaminho();
                solved = true;
                break;
            } else {
                x = no.getCx();
                y = no.getCy();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                        
                        if (binarySearchVisits(mat) >= visits.size()) {
                            novocam = copyArrayList(no.getCaminho());
                            novocam.add(new Pair(y + dy[i], x + dx[i]));
                            aux = new Nodo(0, analiseQtdeFora(mat),
                                    novocam, copyMatrix(mat), x + dx[i], y + dy[i]);
                            pq.add(aux);
                        }else{
                            System.out.println("Já visitado");
                        }
                        
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                    }
                }
            }
        }

        if (solved) {
            return caminho;
        }
        return null;
    }
    
    public ArrayList<Pair<Integer, Integer>> solveBestFirst_DistManhattan(int y, int x) {
        //System.out.println("Iniciando Best First");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(y, x));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(0, 0, caminhoatual, matrix, x, y), aux;
        pq.add(no);

        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            insertBinaryVisits(copyMatrix(no.getMatrix()));
            if (isOrdered(no.getMatrix())) {
                caminho = no.getCaminho();
                solved = true;
                break;
            } else {
                x = no.getCx();
                y = no.getCy();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                        
                        if (binarySearchVisits(mat) >= visits.size()) {
                            novocam = copyArrayList(no.getCaminho());
                            novocam.add(new Pair(y + dy[i], x + dx[i]));
                            aux = new Nodo(0, analiseDistManhattan(mat),
                                    novocam, copyMatrix(mat), x + dx[i], y + dy[i]);
                            pq.add(aux);
                        }else{
                            System.out.println("Já visitado");
                        }
                        
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                    }
                }
            }
        }

        if (solved) {
            return caminho;
        }
        return null;
    }
    
    private int analiseQtdeFora(int [][]m){
        int qtde = 0;
        for(int i = 0, k = 1; i < m.length; ++i){
            for(int j = 0; j < m[0].length; ++j, ++k){
                if(m[i][j] != k){
                    qtde++;
                }
            }
        }
        return qtde;
    }
    
    
    public ArrayList<Pair<Integer, Integer>> solveA_DistManhattan(int y, int x) {
        //System.out.println("Iniciando A*");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(y, x));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(0, 0, caminhoatual, matrix, x, y), aux;
        pq.add(no);

        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            insertBinaryVisits(copyMatrix(no.getMatrix()));
            if (isOrdered(no.getMatrix())) {
                caminho = no.getCaminho();
                solved = true;
                break;
            } else {
                x = no.getCx();
                y = no.getCy();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                        
                        if (binarySearchVisits(mat) >= visits.size()) {
                            novocam = copyArrayList(no.getCaminho());
                            novocam.add(new Pair(y + dy[i], x + dx[i]));
                            aux = new Nodo(no.getFc() + 1, analiseDistManhattan(mat),
                                    novocam, copyMatrix(mat), x + dx[i], y + dy[i]);
                            pq.add(aux);
                        }
                        
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                    }
                }
            }
        }

        if (solved) {
            return caminho;
        }
        return null;
    }
    
    public ArrayList<Pair<Integer, Integer>> solveA_QtdeFora(int y, int x) {
        //System.out.println("Iniciando A*");
        //exibeMatrix(matrix);
        boolean solved = false;
        int[][] mat;
        ArrayList<Pair<Integer, Integer>> caminhoatual = new ArrayList<>(), novocam;
        caminhoatual.add(new Pair(y, x));
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        iteracoes = 0;
        continuar = true;
        Nodo no = new Nodo(0, 0, caminhoatual, matrix, x, y), aux;
        pq.add(no);

        while (continuar && !pq.isEmpty()) {
            ++iteracoes;
            no = pq.remove();
            insertBinaryVisits(copyMatrix(no.getMatrix()));
            if (isOrdered(no.getMatrix())) {
                caminho = no.getCaminho();
                solved = true;
                break;
            } else {
                x = no.getCx();
                y = no.getCy();
                mat = no.getMatrix();
                for (int i = 0; i < DIRECTIONS; ++i) {
                    if (inMatrix(y + dy[i], x + dx[i])) {
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                        
                        if (binarySearchVisits(mat) >= visits.size()) {
                            novocam = copyArrayList(no.getCaminho());
                            novocam.add(new Pair(y + dy[i], x + dx[i]));
                            aux = new Nodo(no.getFc() + 1, analiseQtdeFora(mat),
                                    novocam, copyMatrix(mat), x + dx[i], y + dy[i]);
                            pq.add(aux);
                        }
                        
                        swap(mat, y, x, y + dy[i], x + dx[i]);
                    }
                }
            }
        }

        if (solved) {
            return caminho;
        }
        return null;
    }

    private int analiseDistManhattan(int[][] ma) {
        int fa = 0;
        for (int i = 0; i < ma.length; ++i) {
            for (int j = 0; j < ma[0].length; ++j) {
                fa += distManhattan(i, j, ma[i][j]);
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

    private ArrayList<Pair<Integer, Integer>> copyArrayList(ArrayList<Pair<Integer, Integer>> arr) {
        ArrayList<Pair<Integer, Integer>> b = new ArrayList<>();
        arr.forEach((p) -> {
            b.add(new Pair<>(p.getKey(), p.getValue()));
        });
        return b;
    }

}
