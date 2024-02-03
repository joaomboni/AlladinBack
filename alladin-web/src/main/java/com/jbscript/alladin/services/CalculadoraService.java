package com.jbscript.alladin.services;
//Calculos
import com.jbscript.alladin.calculadora.ValorIntrinseco;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService
{
    private static final double GRAHAM_MULTIPLIER = 22.5;

    public double calcularValorIntrinseco(@NotNull ValorIntrinseco valorIntrinseco) {
        return Math.sqrt(GRAHAM_MULTIPLIER * valorIntrinseco.getLpa() * valorIntrinseco.getVpa());
    }

    public double precoAtual(@NotNull ValorIntrinseco valorIntrinseco) {
        return valorIntrinseco.getPrecoTela();
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
    public double margemSeguranca(ValorIntrinseco valorIntrinseco) {
        double valorIntrinsecoCalculado = calcularValorIntrinseco(valorIntrinseco);
        double precoAtual = precoAtual(valorIntrinseco);
        return (((valorIntrinsecoCalculado - precoAtual) / precoAtual) * 100);
        //return  mS * 100;
    }
}
