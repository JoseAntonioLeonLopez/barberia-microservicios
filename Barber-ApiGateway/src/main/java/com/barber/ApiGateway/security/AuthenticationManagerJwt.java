package com.barber.ApiGateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

	@Value("${config.security.oauth.jwt.key}")
	private String llaveJwt;  // Llave secreta para firmar el token JWT, se obtiene del archivo de configuración.

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();  // Extrae el token JWT de las credenciales.

		// Valida y parsea el token JWT usando la llave secreta.
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(llaveJwt.getBytes())  // Convierte la llave a bytes.
				.build()
				.parseClaimsJws(token)
				.getBody();  // Extrae las claims del token.

		// Retorna un objeto de autenticación que contiene el usuario (subject) y sus roles (vacío aquí).
		return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null));
	}
}

