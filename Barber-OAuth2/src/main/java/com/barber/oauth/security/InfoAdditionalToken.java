package com.barber.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.barber.oauth.services.IUserService;
import com.barber.userCommons.entity.User;

@Component
public class InfoAdditionalToken implements TokenEnhancer {

	@Autowired
	private IUserService usuarioService; // Servicio para obtener información del usuario
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// Mapa para almacenar información adicional del usuario
		Map<String, Object> info = new HashMap<>();
		
		// Obtiene el usuario a partir del nombre (email) de la autenticación
		User user = usuarioService.getUserByEmail(authentication.getName());
		info.put("email", user.getEmail()); // Agrega el email al token
		info.put("phoneNumber", user.getPhoneNumber()); // Agrega el número de teléfono al token
		info.put("role", user.getRole()); // Agrega el rol del usuario al token
		
		// Establece la información adicional en el token de acceso
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken; // Devuelve el token de acceso mejorado
	}
}
