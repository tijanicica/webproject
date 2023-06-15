package com.tim23.webproject.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsRunner implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        /*registry.addMapping("/**")
                .allowedMethods("GET", "PUT", "POST", "DELETE")
                .allowCredentials(true);*/

         registry.addMapping("/api/**") // Specify the endpoint(s) you want to apply CORS configuration to
            .allowedOrigins("http://localhost:8081", "http://localhost:9090") // Add your allowed origins here
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
