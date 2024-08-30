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
public class InfoAdditionalToken implements TokenEnhancer{

	@Autowired
	private IUserService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<String, Object>();
		
		User user = usuarioService.getUserByEmail(authentication.getName());
		info.put("email", user.getEmail());
		info.put("phoneNumber", user.getPhoneNumber());
		info.put("role", user.getRole());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
