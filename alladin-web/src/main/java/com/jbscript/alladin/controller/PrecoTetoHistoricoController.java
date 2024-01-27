package com.jbscript.alladin.controller;

import com.jbscript.alladin.services.CalculadoraPrecoTetoHistorico;
import com.jbscript.alladin.calculadora.PrecoTetoHistorico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class PrecoTetoHistoricoController
{
    private final CalculadoraPrecoTetoHistorico calculadoraPrecoTetoHistorico;

    @Autowired
    public PrecoTetoHistoricoController(CalculadoraPrecoTetoHistorico calculadoraPrecoTetoHistorico){
        this.calculadoraPrecoTetoHistorico = calculadoraPrecoTetoHistorico;
    }

    @PostMapping("/calcular-teto-historico") //endpoint
    public Map<String, Object> calculadoraPrecoTeto(@RequestParam double lucro_anual01,
                                                    @RequestParam double lucro_anual02,
                                                    @RequestParam double lucro_anual03,
                                                    @RequestParam double lucro_anual04,
                                                    @RequestParam double lucro_anual05,
                                                    @RequestParam double lucro_anual06,
                                                    @RequestParam double lucro_anual07,
                                                    @RequestParam double payout_medio,
                                                    @RequestParam double nu_acoes,
                                                    @RequestParam double precoTelaAtual) {
        PrecoTetoHistorico precoTetoHistorico = new PrecoTetoHistorico(lucro_anual01, 
                lucro_anual02, lucro_anual03,lucro_anual04, lucro_anual05, lucro_anual06,
                lucro_anual07, payout_medio, nu_acoes,precoTelaAtual);

        double margemSeguranca = calculadoraPrecoTetoHistorico.margemSeguranca(precoTetoHistorico);
        String recomendarCompra = calculadoraPrecoTetoHistorico.recomendarCompra(margemSeguranca);
        
        /* Imprime Teste de Saída */
        //System.out.println(precoTetoHistorico.getPrecoTelaAtual());
        //System.out.println(precoTetoHistorico.getLucro_anual01());
        //System.out.println(precoTetoHistorico.getNu_acoes());
        
        Map<String, Object> resultados = new HashMap<>();
        resultados.put("precoTelaAtual", precoTetoHistorico.getPrecoTelaAtual());
        resultados.put("preco_teto_historico", calculadoraPrecoTetoHistorico.precoTetoHistoricoResultado(precoTetoHistorico));
        resultados.put("margemSeguranca", margemSeguranca);
        resultados.put("recomendarCompra", recomendarCompra);
        return resultados;
    }
    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/calcular-teto-historico")
                    .allowedOrigins("http://localhost:8080") // Permita solicitações deste domínio
                    .allowedMethods("POST"); // Defina os métodos HTTP permitidos
        }
    }
}
