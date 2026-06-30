package com.DecoMood.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//Clase para configurar la ubicacions de la fotos para el proyecto
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    //Esto es para decirle a springboot, cuando te llegue una peticion de una URL determinada busca aqui.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //estas son las reglas para buscar, dentro de imagenes archivos o subdirectorios.
        registry.addResourceHandler("/imagenes/**")

                //Tener puedo file: equivale a disco duro, internamente spring hacer
                // GET http://localhost:8080/imagenes/perro.jpg y spring ve esto -> /imagenes/perro.jpg
                .addResourceLocations("file:" + uploadDir + "/");
    }
}
