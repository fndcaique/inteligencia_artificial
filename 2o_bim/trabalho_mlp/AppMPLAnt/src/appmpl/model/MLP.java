/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl.model;

import appmpl.util.Observador;
import appmpl.util.Sujeito;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexandre LM
 */
public class MLP implements Sujeito{

    private Arquitetura arq;
    private Observador grafico;
    
    public MLP(){
        grafico = null;
    }
    
    private double y;

    private double funcao_linear(double v) {
        return v / 10;
    }

    private double der_funcao_linear() {
        return 0.1;
    }

    private double funcao_logistica(double v) {
        return 1 / (1 + Math.exp(-v));
    }

    private double der_funcao_logistica(double v) {
        return v *(1 - v);
    }

    private double funcao_hiperbolica(double v) {
        double aux = Math.exp(-2 * v);
        return (1 - aux) / (1 + (aux));
    }

    private double der_funcao_hiperbolica(double v) {
        return 1 - v * v;
    }

    private double[][] funcao_ativacao(double[][] net) {
        int i, j;
        double[][] retorno = new double[net.length][1];
        switch (arq.getTipo_funcao_ativacao()) {
            case 0:
                for (i = 0; i < net.length; i++)
                    for (j = 0; j < net[0].length; j++)
                        retorno[i][j] = funcao_linear(net[i][j]);
                break;
            case 1:
                for (i = 0; i < net.length; i++)
                    for (j = 0; j < net[0].length; j++)
                        retorno[i][j] = funcao_logistica(net[i][j]);
                break;
            case 2:
                for (i = 0; i < net.length; i++)
                    for (j = 0; j < net[0].length; j++)
                        retorno[i][j] = funcao_hiperbolica(net[i][j]);
                break;
        }
        return retorno;
    }

    private double der_funcao_ativacao(double y) {
        double retorno = 1;
        switch (arq.getTipo_funcao_ativacao()) {
            case 0:
                retorno = der_funcao_linear();
                break;
            case 1:
                retorno = der_funcao_logistica(y);
                break;
            case 2:
                retorno = der_funcao_hiperbolica(y);
                break;
        }
        return retorno;
    }

    private double[][] appendColumn(double[][] m, double value) {
        double[][] res = new double[m.length + 1][m[0].length];
        int i, j = 0;
        for (i = 0; i < m.length; i++)
            for (j = 0; j < m[0].length; j++)
                res[i][j] = m[i][j];
        for (j = 0; j < m[0].length; j++)
            res[i][j] = value;
        return res;
    }

    private double[][] multi_matrix(double[][] m1, double[][] m2) {
        double[][] res = new double[m1.length][m2[0].length];
        int i, j, k;
        double soma;
        for (i = 0; i < m1.length; i++)
            for (j = 0; j < m2[0].length; j++) {
                for (k = 0, soma = 0.0; k < m2.length; k++)
                    soma += m1[i][k] * m2[k][j];
                res[i][j] = soma;
            }
        return res;
    }

    private double[][] transposta(double[][] m) {
        double[][] res = new double[m[0].length][m.length];
        int i, j;
        for (i = 0; i < m.length; i++)
            for (j = 0; j < m[0].length; j++)
                res[j][i] = m[i][j];
        return res;
    }

    public Resultado propagation(Arquitetura arq, double[][] exemplo) {
        this.arq = arq;
        double[][] net_entrada_oculta = multi_matrix(arq.pesos_oculta, appendColumn(exemplo, 1));
        double[][] i_entrada_oculta = funcao_ativacao(net_entrada_oculta);

        double[][] net_oculta_saida = multi_matrix(arq.pesos_saida, appendColumn(i_entrada_oculta, 1));
        double[][] i_oculta_saida = funcao_ativacao(net_oculta_saida);

        Resultado res = new Resultado();
        res.net_entrada_oculta = net_entrada_oculta;
        res.i_entrada_oculta = i_entrada_oculta;
        res.net_oculta_saida = net_oculta_saida;
        res.i_oculta_saida = i_oculta_saida;

        return res;
    }

    private void clearGrafico(){
        if(grafico != null)
            grafico.clear();
    }
    
    public ArrayList backpropagation(Arquitetura arquitetura, double[][] dados, double taxa, double limiar, ArrayList<String> rotulo_classes, int max_epocas) {
        arq = arquitetura;
        clearGrafico();
        int epocas = 0, i, j, k;
        Object saida;
        double erro_quadratico = 2 * limiar, soma;
        double[][] entrada, erro, erro_grad_saida, erro_grad_oculta, pesos_saida, i_saida, desejado;
        Resultado res;
        pesos_saida = new double[arq.pesos_saida.length][arq.pesos_saida[0].length - 1];
        entrada = new double[arq.getEntradas()][1];
        erro = new double[arq.getSaidas()][1];
        erro_grad_saida = new double[arq.getSaidas()][1];
        erro_grad_oculta = new double[arq.getOcultas()][1];

        desejado = new double[rotulo_classes.size()][rotulo_classes.size()];

        Map<Object, Integer> index;
        index = new HashMap<>();

        //Separação da saida desejada
        for (i = 0; i < rotulo_classes.size(); i++)
        {
            System.out.println(rotulo_classes.get(i));
            index.put(rotulo_classes.get(i), i);
        }
        for (i = 0; i < desejado.length; i++)
            desejado[i][i] = 1;

        while (erro_quadratico > limiar && epocas < max_epocas) {
            erro_quadratico = 0;

            //Treino de todos os exemplos (iterado)
            for (i = 0; i < dados.length; i++) {
                //Separar um exemplo da base teste
                for (j = 0; j < entrada.length; j++)
                    entrada[j][0] = dados[i][j];
                saida = rotulo_classes.get((int) dados[i][j]);

                //Verificar saida da rede para o exemplo separado
                res = propagation(arq, entrada);
                i_saida = res.i_oculta_saida;

                //Cálculo dos erros das saídas
                for (j = 0, soma = 0.0; j < i_saida.length; j++) {
                    erro[j][0] = desejado[j][index.get(saida)] - i_saida[j][0];
                    soma += erro[j][0] * erro[j][0];
                    //Gradiente do erro da saída de cada neurônio
                    erro_grad_saida[j][0] = erro[j][0] * der_funcao_ativacao(i_saida[j][0]);
                }
                //Soma erro quadrático
                erro_quadratico = erro_quadratico + 0.5 * soma;

                //Gradiente do erro para camada oculta
                for (j = 0; j < arq.pesos_saida.length; j++)
                    for (k = 0; k < arq.getOcultas(); k++)
                        pesos_saida[j][k] = arq.pesos_saida[j][k];

                double[][] aux = multi_matrix(transposta(erro_grad_saida),pesos_saida);
                for (j = 0; j < erro_grad_saida[0].length; j++)
                    erro_grad_oculta[j][0] = der_funcao_ativacao(res.i_entrada_oculta[j][0]) * aux[0][j];

                //Ajuste dos pesos
                //Saida
                aux = multi_matrix(erro_grad_saida, transposta(appendColumn(res.i_entrada_oculta, 1)));
                for (j = 0; j < arq.pesos_saida.length; j++)
                    for (k = 0; k < arq.pesos_saida[0].length; k++)
                        arq.pesos_saida[j][k] += taxa * aux[j][k];
                //Oculta
                aux = multi_matrix(erro_grad_oculta, transposta(appendColumn(entrada, 1)));
                for (j = 0; j < arq.pesos_oculta.length; j++)
                    for (k = 0; k < arq.pesos_oculta[0].length; k++)
                        arq.pesos_oculta[j][k] += taxa * aux[j][k];
            }
            erro_quadratico = erro_quadratico / dados.length;
            notify(epocas, erro_quadratico);
            epocas++;
        }
        System.out.println("EPOCAS: "+epocas+"ERRO:"+erro_quadratico);
        return new ArrayList();
    }

    @Override
    public void register(Observador o) {
        grafico = o;
    }

    @Override
    public void remove(Observador o) {
        grafico = null;
    }

    @Override
    public void notify(double x, double y) {
        if(grafico != null){
            grafico.update(x,y);
            try{
                Thread.sleep(2);
            }catch(Exception e){
            }
        }
            
    }
}
