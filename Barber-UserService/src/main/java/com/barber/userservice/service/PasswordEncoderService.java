package com.barber.userservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// Servicio para la codificación de contraseñas utilizando BCrypt
@Service
public class PasswordEncoderService {

    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor que inicializa el codificador de contraseñas
    public PasswordEncoderService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Método para codificar una contraseña
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
