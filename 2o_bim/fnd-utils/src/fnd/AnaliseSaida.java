/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnd;

/**
 *
 * @author fnd
 */
public class AnaliseSaida {

    /*
    acuracia = (qtde de classificados corretamente) / (total de exemplos)
    erro = 1 - acuracia
    
     */
    private double mtconf[][];
    private double total, acuracia, corretos;

    public AnaliseSaida(double matrizconfusao[][]) {
        this.mtconf = matrizconfusao;
        total = corretos = 0;
        for (int i = 0; i < mtconf.length; ++i) {
            corretos += mtconf[i][i];
            for (int j = 0; j < mtconf.length; ++j) {
                total += mtconf[i][j];
            }
        }
        acuracia = -1;
    }

    public double acuracia() {
        return acuracia;
    }

    /*********      DUVIDA: CALCULO P/ MATRIZ CONFUSAO DE 2 CLASSES      *********/
    public double taxaErroClasse(int idx) {
        double verdadeiroPositivo = mtconf[idx][idx], falsoNegativo = -verdadeiroPositivo;
        for (int i = 0; i < mtconf.length; ++i) {
            falsoNegativo += mtconf[idx][i];
        }
        return falsoNegativo / (verdadeiroPositivo + falsoNegativo);
    }
    
    public double confiabilidadePositiva(){
        return 0;
    }
    
    public double confiabilidadeNegativa(){
        return 0;
    }
    
    public double suporte(){
        return 0;
    }
    /*****************      FIM DUVIDAS      ****************/

}
