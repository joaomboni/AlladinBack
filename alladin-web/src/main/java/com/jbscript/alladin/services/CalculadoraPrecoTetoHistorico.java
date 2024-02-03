package com.jbscript.alladin.services;

import com.jbscript.alladin.calculadora.PrecoTetoHistorico;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraPrecoTetoHistorico
{
    private static final int ARRAY_LUCRO = 6;
    private static final double LUCRO_MINIMO = 0.06;

    public double avarage(@NotNull PrecoTetoHistorico precoTetoHistorico)
    {
        double lucro_anual01 = precoTetoHistorico.getLucro_anual01();
        double lucro_anual02 = precoTetoHistorico.getLucro_anual02();
        double lucro_anual03 = precoTetoHistorico.getLucro_anual03();
        double lucro_anual04 = precoTetoHistorico.getLucro_anual04();
        double lucro_anual05 = precoTetoHistorico.getLucro_anual05();
        double lucro_anual06 = precoTetoHistorico.getLucro_anual06();
        //double lucro_anual07 = precoTetoHistorico.getLucro_anual07();
        //double acoes_circulantes = precoTetoHistorico.getNu_acoes();
        
        double media_lpa = ((lucro_anual01 + lucro_anual02 + lucro_anual03 + lucro_anual04
                + lucro_anual05 + lucro_anual06 /*+ lucro_anual07*/) / ARRAY_LUCRO);
        return media_lpa;
    }

    public double payout(@NotNull PrecoTetoHistorico precoTetoHistorico)
    {
        double payout_medio = precoTetoHistorico.getPayout_medio();
        return (payout_medio / 100);
    }

    public double lpa_medio(PrecoTetoHistorico precoTetoHistorico)
    {
        return avarage(precoTetoHistorico) / precoTetoHistorico.getNu_acoes();
    }

    public  double dpa_medio(PrecoTetoHistorico precoTetoHistorico)
    {
        return avarage(precoTetoHistorico) * payout(precoTetoHistorico);
    }

    public double precoTetoHistoricoResultado (PrecoTetoHistorico precoTetoHistorico){
        return dpa_medio(precoTetoHistorico) / LUCRO_MINIMO;
    }

    public double precoAtual(@NotNull PrecoTetoHistorico precoTetoHistorico) { 
        return  precoTetoHistorico.getPrecoTelaAtual();
    }
    
    private static final String n_compra = "Não recomendação de compra";
    private static final String cautela = "Recomendação de compra com cautela";
    private static final String compra_excelente = "Recomendação de compra excelente";
    public String recomendarCompra(double margemSeguranca) {
        if (margemSeguranca < 20) {
            return n_compra;
        } else if (margemSeguranca >= 20 && margemSeguranca <= 30) {
            return cautela;
        } else {
            return compra_excelente;
        }
    }
    public double margemSeguranca(PrecoTetoHistorico precoTetoHistorico) {
        double precoTetoHistoricoResultado = precoTetoHistoricoResultado(precoTetoHistorico);
        double precoAtual = precoAtual(precoTetoHistorico);
        return (((precoTetoHistoricoResultado - precoAtual) / precoAtual) * 100);
        //return  mS * 100;
    }
}
