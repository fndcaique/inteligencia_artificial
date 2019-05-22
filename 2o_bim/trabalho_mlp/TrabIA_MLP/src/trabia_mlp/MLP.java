/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabia_mlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexandre LM
 */
public class MLP
{
    private Arquitetura arq;
    private double y;
    private double funcao_linear(double v)
    {
        return v/10;
    }
    
    private double der_funcao_linear(double v)
    {
        return 0.1;
    }
    
    private double funcao_logistica(double v)
    {
        y = 1/(1 + Math.exp(-v));
        return y;
    }
    
    private double der_funcao_logistica(double v)
    {
        return y * (1 - y);
    }
    
    private double funcao_hiperbolica(double v)
    {
        double aux = -2*v;
        y = (1 - Math.exp(aux))/(1 + Math.exp(aux));
        return y;
    }
    
    private double der_funcao_hiperbolica(double v)
    {
        return 1 - y*y;
    }
    
    private double[][] funcao_ativacao(double[][] net)
    {
        int i,j;
        double[][] retorno = new double[net.length][1];
        switch(arq.getTipo_funcao_ativacao())
        {
            case 0:
                for(i=0;i<net.length;i++)
                    for(j=0;j<net[0].length;j++)
                        retorno[i][j] = funcao_linear(net[i][j]);
                break;
            case 1:
                for(i=0;i<net.length;i++)
                    for(j=0;j<net[0].length;j++)
                        retorno[i][j] = funcao_logistica(net[i][j]);
                break;
            case 2:
                for(i=0;i<net.length;i++)
                    for(j=0;j<net[0].length;j++)
                        retorno[i][j] = funcao_hiperbolica(net[i][j]);
                break;
        }
        return retorno;
    }
    
    private double der_funcao_ativacao(double y)
    {
        double retorno=1;
        switch(arq.getTipo_funcao_ativacao())
        {
            case 0:retorno = funcao_linear(y);
                break;
            case 1:retorno = funcao_logistica(y);
                break;
            case 2:retorno = funcao_hiperbolica(y);
                break;
        }
        return retorno;
    }
    
    private double[][] appendColumn(double[][] m, double value)
    {
        double[][] res = new double[m.length+1][1];
        int i;
        for(i=0;i<m.length;i++)
            res[i][0] = m[i][0];
        res[i][0] = value;
        return res;
    }
    
    private double[][] appendColumn(double[] m, double value)
    {
        double[][] res = new double[m.length+1][1];
        int i;
        for(i=0;i<m.length;i++)
            res[i][0] = m[i];
        res[i][0] = value;
        return res;
    }
    
    private double[][] multi_matrix(double[][] m1, double[][] m2)
    {
        double[][] res = new double[m1.length][m2[0].length];
        int i,j,k;
        double soma;
        for(i=0; i<m1.length; i++)
            for(j=0;j<m2[0].length;j++)
            {
                for(k=0,soma=0.0;k<m2.length;k++)
                    soma += m1[i][k] * m2[k][j];
                res[i][j] = soma;
            }
        return res;
    }
    
    private double[][] multi_matrix(double[] m1, double[][] m2)
    {
        double[][] res = new double[m1.length][m2[0].length];
        int i,j,k;
        double soma;
        for(i=0;i<m2.length;i++)
        {
            for(j=0,soma=0.0;j<m2[0].length;j++)
                soma += m1[j] *m2[i][j];
            res[0][j] = soma;
        }
        return res;
    }
    
    public Resultado propagation(Arquitetura arq, double[] exemplo)
    {
        this.arq = arq;
        double[][] net_entrada_oculta = multi_matrix(arq.pesos_oculta,appendColumn(exemplo, 1));
        double[][] i_entrada_oculta = funcao_ativacao(net_entrada_oculta);
        
        double[][] net_oculta_saida = multi_matrix(arq.pesos_saida,appendColumn(i_entrada_oculta, 1));
        double[][] i_oculta_saida = funcao_ativacao(net_oculta_saida);
        
        Resultado res = new Resultado();
        res.net_entrada_oculta = net_entrada_oculta;
        res.i_entrada_oculta = i_entrada_oculta;
        res.net_oculta_saida = net_oculta_saida;
        res.i_oculta_saida = i_oculta_saida;
        
        return res;
    }
    
    public ArrayList backpropagation(Arquitetura arq, double[][] dados, double n, double limiar, int max_epocas)
    {
        double erro_quadratico = 2*limiar,soma;
        int epocas = 0,i,j;
        double saida;
        double[] entrada,erro;
        double[] erro_grad_saida,erro_grad_oculta;
        // 
        double[][] pesos_saida = new double[arq.pesos_saida.length][arq.pesos_saida[0].length-1];
        entrada = new double[arq.getEntradas()];
        erro = new double[arq.getSaidas()];
        erro_grad_saida = new double[0];
        erro_grad_oculta = new double[arq.pesos_oculta.length];
        double[][] i_saida;
        Resultado res;
        while(erro_quadratico > limiar && epocas < max_epocas)
        {
            erro_quadratico = 0;
            
            for(i=0;i<dados.length;i++)
            {
                for(j=0;j<entrada.length;j++)
                    entrada[j] = dados[i][j];
                saida = dados[i][j];
                
                res = propagation(arq, entrada);
                i_saida = res.i_oculta_saida;
                
                for(j=0,soma=0.0;j<i_saida.length;j++)
                {
                    erro[j] = saida - i_saida[j][0];
                    soma += erro[j] * erro[j];
                }
                
                erro_quadratico = erro_quadratico + 0.5 * soma;
                
                for(j=0;j<erro.length;j++)
                    erro_grad_saida[j] = erro[j] * der_funcao_ativacao(i_saida[j][0]);
                
                pesos_saida = arq.pesos_saida;
                
                for(j=0;j<erro_grad_saida.length;j++)
                {
                    double[][] aux = multi_matrix(erro_grad_saida, arq.pesos_saida);
                    erro_grad_oculta[j] = der_funcao_ativacao(res.i_entrada_oculta[0][j]) * aux[0][j];
                }
                
                
            }   
        }
        
        return new ArrayList();
    }
}

