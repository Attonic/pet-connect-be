package com.petconnectbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura manipuladores de recursos estáticos.
     * Mapeia a URL pública '/images/**' para o diretório físico 'uploads/' no servidor.
     * Isso permite que o frontend acesse as imagens dos pets que foram salvas localmente.
     * 
     * Exemplo: Uma requisição para http://localhost:8080/images/pet-foto.jpg
     * servirá o arquivo localizado em [raiz-do-projeto]/uploads/pet-foto.jpg
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/");
    }
}
