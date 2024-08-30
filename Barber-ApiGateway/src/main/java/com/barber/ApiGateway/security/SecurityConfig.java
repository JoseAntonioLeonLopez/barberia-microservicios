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
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
	    return http.authorizeExchange()
	            .pathMatchers("/api/security/oauth/**").permitAll() // Permitir acceso sin autenticación a rutas de OAuth.
	            .pathMatchers(HttpMethod.GET, "/api/appointments/**", "/api/users/email/*", "/api/users/{userId}", "/api/roles/name/{name}").permitAll() // Permitir accesos GET a rutas específicas.
	            .anyExchange().authenticated() // Requerir autenticación para todas las demás rutas.
	            .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
	            .csrf().disable() // Deshabilitar CSRF si no es necesario.
	            .build();
	}
}
