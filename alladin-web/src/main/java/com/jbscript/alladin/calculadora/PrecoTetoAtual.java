package com.jbscript.alladin.calculadora;

public class PrecoTetoAtual
{
    private double dy_ultimos;
    public PrecoTetoAtual(double dy_ultimos) {
        this.dy_ultimos = dy_ultimos;
    }

    public double getDy_ultimos() {
        return dy_ultimos / 100;
    }

    public void setDy_ultimos(double dy_ultimos) {
        this.dy_ultimos = dy_ultimos;
    }

}
