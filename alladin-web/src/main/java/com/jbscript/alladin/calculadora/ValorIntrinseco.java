package com.jbscript.alladin.calculadora;

public class ValorIntrinseco
{
    private double precoTela;
    private double dividendYield;
    private double lpa;
    private double vpa;
    public ValorIntrinseco(double precoTela, double dividendYield, double lpa, double vpa)
    {
    this.precoTela = precoTela;
    this.dividendYield = dividendYield;
    this.lpa = lpa;
    this.vpa = vpa;
    }
    public double getPrecoTela() {
        return precoTela;
    }

    public void setPrecoTela(double precoTela) {
        this.precoTela = precoTela;
    }

    public double getDividendYield() {
        return dividendYield / 100;
    }

    public void setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
    }
    public double getLpa() {
    return lpa;
    }

    public double getVpa() {
    return vpa;
    }
}
