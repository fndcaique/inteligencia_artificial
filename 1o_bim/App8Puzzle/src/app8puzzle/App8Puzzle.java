/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fndcaique
 */
public class App8Puzzle extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*int V = 9;
        Stack<Integer> adj = new Stack<>();
        adj.add(0);
        for (int i = 1; i < V; i++) {
            System.out.println("inseriu " + i + " na pos de " + adj.add(i));
        }

        for (int i = 0; i < adj.size(); ++i) {
            System.out.println(adj.get(i));
        }

        System.out.println(adj.size() + " != " + V);
        */
        launch(args);
        /*
        Tabuleiro t = new Tabuleiro(3);
        t.setValue(0, 0, -1);
        t.setValue(0, 1, 1);
        t.setValue(0, 2, 2);
        t.setValue(1, 0, 5);
        t.setValue(1, 1, 6);
        t.setValue(1, 2, 3);
        t.setValue(2, 0, 4);
        t.setValue(2, 1, 7);
        t.setValue(2, 2, 8);
        t.setCoordCuringa(0,  0);
        
        long ini = System.currentTimeMillis();
        //Stack<Pair<Integer, Integer>> s = t.getSolucaoBfs();
        ArrayList<Pair<Integer, Integer>> s = t.getSolucaoBfs();
        long fim = System.currentTimeMillis();
        System.out.println("Tempo para a solução = "+(fim-ini)+" milisegundos");
        /*while(!s.isEmpty()){
            System.out.println(s.peek().getKey()+", "+s.peek().getValue());
            s.pop();
        }*/
        /*System.out.println("Visits: ");
        for (int[][] estado : t.getVisits()) {
            t.exibeMatrix(estado);
        }
        for (Pair<Integer, Integer> p : s) {
            System.out.println("["+p.getKey()+", "+p.getValue()+"] -->>>");
        }
        */
        
        System.exit(0);
    }
    

}
