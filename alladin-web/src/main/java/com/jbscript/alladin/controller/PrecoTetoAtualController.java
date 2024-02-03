package com.jbscript.alladin.controller;

import com.jbscript.alladin.calculadora.PrecoTetoAtual;
import com.jbscript.alladin.services.PrecoTetoAtualService;
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
@RestController
public class PrecoTetoAtualController
{
    private final PrecoTetoAtualService precoTetoAtualService;

    @Autowired
    public PrecoTetoAtualController(PrecoTetoAtualService precoTetoAtualService){
        this.precoTetoAtualService = precoTetoAtualService;
    }

    @PostMapping("/preco-teto-atual") //endpoint
    public Map<String, Object> precoTetoAtual(@RequestParam double dy_ultimos){
        PrecoTetoAtual precoTetoAtual = new PrecoTetoAtual(dy_ultimos);

        Map<String, Object> resultados = new HashMap<>();
        resultados.put("preco_teto_atual", precoTetoAtualService.precoTetoAtual(precoTetoAtual));
        return resultados;
    }
    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/preco-teto-atual")
                    .allowedOrigins("http://localhost:8080") // Permita solicitações deste domínio
                    .allowedMethods("POST"); // Defina os métodos HTTP permitidos
        }
    }

}
