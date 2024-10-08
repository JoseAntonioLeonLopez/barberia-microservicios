package com.barber.ApiGateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;  // Inyecta el filtro de autenticación JWT.

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()  // Permite todas las solicitudes OPTIONS sin autenticación (preflight requests de CORS).
                .pathMatchers("/api/security/oauth/**").permitAll()  // Permite el acceso sin autenticación a rutas relacionadas con OAuth.
                .pathMatchers(HttpMethod.GET, "/api/appointments/**", "/api/users/email/*", "/api/users/{userId}", "/api/roles/name/{name}").permitAll()  // Permite solicitudes GET a rutas específicas.
                .pathMatchers(HttpMethod.POST, "/api/users").permitAll()  // Permite solicitudes POST para crear usuarios.
                .pathMatchers(HttpMethod.DELETE, "/api/appointments/client/**").permitAll()  // Permite solicitudes DELETE a rutas específicas.
                .anyExchange().authenticated()  // Requiere autenticación para todas las demás rutas.
                .and()
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)  // Añade el filtro de autenticación JWT en la cadena de seguridad.
                .csrf().disable()  // Deshabilita CSRF si no es necesario (útil en APIs REST).
                .cors()  // Habilita CORS usando la configuración desde application.yml.
                .and()
                .build();  // Construye la cadena de filtros de seguridad.
    }
}
