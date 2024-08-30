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
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager{

	@Value("${config.security.oauth.jwt.key}")
	private String llaveJwt; 
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(llaveJwt.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null));
	}
}
