/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fnd
 */
public class AppMPL extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/TelaPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    static private void k_foud(double[] dados){
        int[] treino,teste;
        int range=dados.length/4;
        treino = new int[dados.length-range];
        teste = new int[range];
        int l=0;
        for (int i = 0; i < 4; i++) {
            l=0;
            for (int j = 0; j < range; j++,l+=3) {
                teste[j] = i*range+j;
                treino[l] = (((i+1)*range)+j)%dados.length;
                treino[l+1] = (((i+2)*range)+j)%dados.length;
                treino[l+2] = (((i+3)*range)+j)%dados.length;
            }


            System.out.println("\nTESTE");
            for (int k = 0; k < teste.length; k++) {
                System.out.print(teste[k]+", ");
            }
            System.out.println("\nTreino");
            for (int k = 0; k < treino.length; k++) {
                System.out.print(treino[k]+", ");
            }
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //
        //double[] dados = {2,3,4,5,5,5,5,55,5,5,5,5,5,5,5,5,5,5,5,5};
        //k_foud(dados);
        //Map<Integer,Integer> m = new HashMap<Integer,Integer>();
        
        //System.out.println(m.get(0));
        launch(args);
        //System.out.println(""+(1.0/3));
        System.exit(0);
    }
    
}
