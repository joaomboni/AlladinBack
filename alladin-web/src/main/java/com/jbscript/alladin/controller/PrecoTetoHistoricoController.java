package com.jbscript.alladin.controller;

import com.jbscript.alladin.services.CalculadoraPrecoTetoHistorico;
import com.jbscript.alladin.calculadora.PrecoTetoHistorico;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.bind.annotation.CrossOrigin;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
*/
@CrossOrigin(origins = {"http://localhost"})
@RestController
public class PrecoTetoHistoricoController
{
    //private final Logger logger = LoggerFactory.getLogger(PrecoTetoHistoricoController.class);
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
                                                    //@RequestParam double lucro_anual07,
                                                    @RequestParam double payout_medio,
                                                    @RequestParam double nu_acoes,
                                                    @RequestParam double precoTelaAtual) {
        PrecoTetoHistorico precoTetoHistorico = new PrecoTetoHistorico(lucro_anual01, 
                lucro_anual02, lucro_anual03,lucro_anual04, lucro_anual05, lucro_anual06,
                /*lucro_anual07,*/ payout_medio, nu_acoes,precoTelaAtual);
                
        
        //logger.info("Recebendo solicitação POST em /calcular-teto-historico");

        double margemSeguranca = calculadoraPrecoTetoHistorico.margemSeguranca(precoTetoHistorico);
        String recomendarCompra = calculadoraPrecoTetoHistorico.recomendarCompra(margemSeguranca);
        
        /* Imprime Teste de Saída */
        //System.out.println(precoTetoHistorico.getPrecoTelaAtual());
        //System.out.println(precoTetoHistorico.getLucro_anual01());
        //System.out.println(precoTetoHistorico.getNu_acoes());
        //logger.info("Operação concluída com sucesso.");
        
        Map<String, Object> resultados = new HashMap<>();
        resultados.put("precoTelaAtual", precoTetoHistorico.getPrecoTelaAtual());
        resultados.put("preco_teto_historico", calculadoraPrecoTetoHistorico.precoTetoHistoricoResultado(precoTetoHistorico));
        resultados.put("margemSeguranca", margemSeguranca);
        resultados.put("recomendarCompra", recomendarCompra);
        return resultados;
    }
    @Configuration
    @EnableWebMvc
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(@NotNull CorsRegistry registry) {
            registry.addMapping("/calcular-teto-historico")
                    .allowedOrigins("http://localhost") // Permita solicitações deste domínio
                    .allowedMethods("POST"); // Defina os métodos HTTP permitidos
        }
    }
}
