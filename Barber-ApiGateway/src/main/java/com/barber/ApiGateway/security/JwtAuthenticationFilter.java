package com.barber.ApiGateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private AuthenticationManagerJwt authenticationManager;  // Inyecta el AuthenticationManagerJwt.

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Obtiene el header Authorization y verifica si empieza con "Bearer ".
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))  // Si no hay token, sigue el filtro sin autenticación.
            .map(token -> {
                String jwt = token.replace("Bearer ", "");  // Remueve "Bearer " del token.
                return jwt;
            })
            // Pasa el token al AuthenticationManagerJwt para autenticación.
            .flatMap(token -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
            // Si la autenticación es exitosa, asocia el contexto de seguridad con el usuario autenticado.
            .flatMap(authentication -> {
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            });
    }
}
