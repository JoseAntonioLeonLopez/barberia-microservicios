package com.barber.appointmentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
            .csrf().disable() // Deshabilita CSRF.
            .cors() // Habilita CORS.
            .and()
            .authorizeRequests()
                .anyRequest().permitAll(); // Permitir todas las solicitudes.

        return http.build();
    }

    // Configuración CORS para permitir solicitudes desde el frontend.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173") // Permitir solicitudes desde el frontend.
                    .allowedMethods("*") // Permitir todos los métodos HTTP.
                    .allowedHeaders("*"); // Permitir todos los encabezados.
            }
        };
    }
}

