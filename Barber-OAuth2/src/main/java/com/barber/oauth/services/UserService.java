package com.barber.oauth.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.barber.oauth.clients.UserFeignClient;

@Service
public class UserService implements UserDetailsService, IUserService{

	@Autowired
	private UserFeignClient client; // Cliente Feign para obtener información del usuario
	
	// Carga un usuario por su nombre de usuario (correo electrónico)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.barber.userCommons.entity.User user = client.getUserByEmail(email);
		
		// Lanzar excepción si el usuario no se encuentra
		if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
		
		// Convertir el rol del usuario en una autoridad
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
		
		// Retornar el usuario con sus detalles y roles
		return new User(
                user.getEmail(), 
                user.getPassword(), 
                Collections.singletonList(authority) // Convertir el rol a una lista de autoridades
        );
	}

	// Método para obtener un usuario por su correo electrónico
	@Override
	public com.barber.userCommons.entity.User getUserByEmail(String email) {
		return client.getUserByEmail(email);
	}

}
