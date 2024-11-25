package com.ecoeduca.ecoeduca.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webconfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS para todas as rotas
                .allowedOrigins("http://localhost:4200") // Permite apenas o frontend do Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*"); // Todos os cabeçalhos
    }

    private static final Logger logger = LoggerFactory.getLogger(webconfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Configurando mapeamento de recursos para imagens...");
        registry.addResourceHandler("/imagens/**")
        .addResourceLocations("classpath:/static/imagens/");

        logger.info("Mapeamento de imagens configurado com sucesso!");
    }
}
