package com.barber.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env; // Para acceder a las propiedades del entorno

	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // Para codificar contraseñas
	
	@Autowired
	private AuthenticationManager authenticationManager; // Para manejar la autenticación
    
    @Autowired
    private InfoAdditionalToken infoAdditionalToken; // Para agregar información adicional al token

    @Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// Configura el acceso a las claves del token
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

    @Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// Configura los detalles del cliente en memoria
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600) // 1 hora de validez del token
		.refreshTokenValiditySeconds(3600); // 1 hora de validez del refresh token
	}

    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// Configura los endpoints del servidor de autorización
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdditionalToken, accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain); // Añade los mejoradores del token
	}

    @Bean
	public JwtTokenStore tokenStore() {
		// Configura el almacenamiento del token JWT
		return new JwtTokenStore(accessTokenConverter());
	}

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        // Configura el convertidor de acceso del token JWT
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key")); // Establece la clave de firma
        return tokenConverter;
    }

}
