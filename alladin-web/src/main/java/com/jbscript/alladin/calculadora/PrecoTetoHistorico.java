package com.jbscript.alladin.calculadora;

public class PrecoTetoHistorico
{
    private double precoTela;
    private double lucro_anual01;
    private double lucro_anual02;
    private double lucro_anual03;
    private double lucro_anual04;
    private double lucro_anual05;
    private double lucro_anual06;
    private double lucro_anual07;
    private double payout_medio;
    private double nu_acoes;


    public PrecoTetoHistorico(double lucro_anual01, double lucro_anual02, double lucro_anual03, double lucro_anual04, double lucro_anual05, double lucro_anual06, double lucro_anual07, double payout_medio, double nu_acoes, double precoTela) {
        this.lucro_anual01 = lucro_anual01;
        this.lucro_anual02 = lucro_anual02;
        this.lucro_anual03 = lucro_anual03;
        this.lucro_anual04 = lucro_anual04;
        this.lucro_anual05 = lucro_anual05;
        this.lucro_anual06 = lucro_anual06;
        this.lucro_anual07 = lucro_anual07;
        this.payout_medio = payout_medio;
        this.nu_acoes = nu_acoes;
        this.precoTela = precoTela;
    }

    public double getPrecoTela() {
        return precoTela;
    }

    public void setPrecoTela(double precoTela) {
        this.precoTela = precoTela;
    }

    public double getLucro_anual01() {
        return lucro_anual01;
    }

    public void setLucro_anual01(double lucro_anual01) {
        this.lucro_anual01 = lucro_anual01;
    }

    public double getLucro_anual02() {
        return lucro_anual02;
    }

    public void setLucro_anual02(double lucro_anual02) {
        this.lucro_anual02 = lucro_anual02;
    }

    public double getLucro_anual03() {
        return lucro_anual03;
    }

    public void setLucro_anual03(double lucro_anual03) {
        this.lucro_anual03 = lucro_anual03;
    }

    public double getLucro_anual04() {
        return lucro_anual04;
    }

    public void setLucro_anual04(double lucro_anual04) {
        this.lucro_anual04 = lucro_anual04;
    }

    public double getLucro_anual05() {
        return lucro_anual05;
    }

    public void setLucro_anual05(double lucro_anual05) {
        this.lucro_anual05 = lucro_anual05;
    }

    public double getLucro_anual06() {
        return lucro_anual06;
    }

    public void setLucro_anual06(double lucro_anual06) {
        this.lucro_anual06 = lucro_anual06;
    }

    public double getLucro_anual07() {
        return lucro_anual07;
    }

    public void setLucro_anual07(double lucro_anual07) {
        this.lucro_anual07 = lucro_anual07;
    }

    public double getPayout_medio() {
        return payout_medio;
    }

    public void setPayout_medio(double payout_medio) {
        this.payout_medio = payout_medio;
    }

    public double getNu_acoes() {
        return nu_acoes;
    }

    public void setNu_acoes(double nu_acoes) {
        this.nu_acoes = nu_acoes;
    }
}
