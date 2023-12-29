package com.jbscript.alladin.controller;

import com.jbscript.alladin.services.CalculadoraService;
import com.jbscript.alladin.calculadora.ValorIntrinseco;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost"})
//@RequestMapping("/seu-endpoint")


@RestController
public class ValorIntrinsecoController {

    private final CalculadoraService calculadoraService;

    @Autowired
    public ValorIntrinsecoController(CalculadoraService calculadoraService) {
        this.calculadoraService = calculadoraService;
    }


    @PostMapping("/calcular-valor-intrinseco") // endpoint
    public Map<String, Object> calcularValorIntrinseco(@RequestParam double precoTela,
                                                       @RequestParam double dividendYield,
                                                       @RequestParam double lpa,
                                                       @RequestParam double vpa) {
        ValorIntrinseco valorIntrinseco = new ValorIntrinseco(precoTela, dividendYield, lpa, vpa);

        double margemSeguranca = calculadoraService.margemSeguranca(valorIntrinseco);
        String recomendarCompra = calculadoraService.recomendarCompra(margemSeguranca);

        Map<String, Object> resultados = new HashMap<>();
        resultados.put("valorIntrinseco", calculadoraService.calcularValorIntrinseco(valorIntrinseco));
        resultados.put("precoAtual", valorIntrinseco.getPrecoTela()); // Aqui obtenha diretamente o preço atual
        resultados.put("margemSeguranca", margemSeguranca);
        resultados.put("recomendarCompra", recomendarCompra);
        return resultados;
    }
    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(@NotNull CorsRegistry registry) {
            registry.addMapping("/calcular-valor-intrinseco")
                    //.allowedOrigins("http://146.235.47.198")// Permita solicitações deste domínio
                    .allowedOrigins("http://localhost:8080")
                    .allowedMethods("POST"); // Defina os métodos HTTP permitidos
        }
    }
}