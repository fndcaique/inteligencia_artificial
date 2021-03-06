/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabia_mlp;

/**
 *
 * @author Alexandre LM
 */
public class Arquitetura
{
    private int entradas,ocultas,saidas,tipo_funcao_ativacao,num_pesos_entrada_oculta,num_pesos_oculta_saida;
    public double[][] pesos_oculta,pesos_saida;
    
    public Arquitetura(int entradas, int ocultas, int saidas, int tipo_funcao_ativacao)
    {
        this.entradas = entradas;
        this.ocultas = ocultas;
        this.saidas = saidas;
        this.tipo_funcao_ativacao = tipo_funcao_ativacao;
        pesos_oculta = fillRandomValueMatrix(ocultas,entradas+1);
        num_pesos_entrada_oculta = ocultas*(entradas+1);
        pesos_saida = fillRandomValueMatrix(saidas,ocultas+1);
        num_pesos_oculta_saida = saidas*(ocultas+1);
        
    }

    private double[][] fillRandomValueMatrix(int linhas,int colunas)
    {
        double[][] m = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < colunas; j++)
            {
                m[i][j] = Math.random()-0.5;
            }
        return m;
    }
    
    public int getEntradas()
    {
        return entradas;
    }

    public void setEntradas(int entradas)
    {
        this.entradas = entradas;
    }

    public int getOcultas()
    {
        return ocultas;
    }

    public void setOcultas(int ocultas)
    {
        this.ocultas = ocultas;
    }

    public int getSaidas()
    {
        return saidas;
    }

    public void setSaidas(int saidas)
    {
        this.saidas = saidas;
    }

    public int getTipo_funcao_ativacao()
    {
        return tipo_funcao_ativacao;
    }

    public void setTipo_funcao_ativacao(int tipo_funcao_ativacao)
    {
        this.tipo_funcao_ativacao = tipo_funcao_ativacao;
    }
    
}
