package com.jbscript.alladin.services;

import com.jbscript.alladin.calculadora.PrecoTetoAtual;
import org.springframework.stereotype.Service;

@Service
public class PrecoTetoAtualService
{
    private static final double LUCRO_MINIMO = 6.0 / 100;

    public double lucroMinimo(PrecoTetoAtual precoTetoAtual)
    {
        return LUCRO_MINIMO;
    }

    public double precoTetoAtual(PrecoTetoAtual precoTetoAtual)
    {
        double lucroMinimoAceito = lucroMinimo(precoTetoAtual);

        return (precoTetoAtual.getDy_ultimos() / lucroMinimoAceito);
    }
}
