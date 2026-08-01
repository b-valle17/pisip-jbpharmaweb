package com.pisip.jbpharmaweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // 👈 1. IMPORTAR

@Configuration
public class WebClientConfig implements WebMvcConfigurer { // 👈 2. IMPLEMENTAR INTERFAZ
	
    @Autowired
    private AuthInterceptor authInterceptor;
	
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080/api").build();
    }
	 
    @Override // 👈 3. ANOTACIÓN @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/autenticacion/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico"
                );
    }
}