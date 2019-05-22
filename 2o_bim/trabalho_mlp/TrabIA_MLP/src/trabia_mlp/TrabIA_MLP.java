/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabia_mlp;

import java.util.ArrayList;

/**
 *
 * @author Alexandre LM
 */
public class TrabIA_MLP
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        double [][] m = {{0,0,0},{0,1,1},{1,0,1},{1,1,0}};
        double n = 0.01,limiar = 0.2;
        Arquitetura arq = new Arquitetura(2, 4, 2, 0);
        MLP mlp = new MLP();
        //Resultado res = 
        ArrayList al = mlp.backpropagation(arq,m,n,limiar,200);
        
        /*for(int i=0;i<res.i_oculta_saida[0].length; i++)
            System.out.println(""+res.i_oculta_saida[0][i]);*/
    }
    
}
