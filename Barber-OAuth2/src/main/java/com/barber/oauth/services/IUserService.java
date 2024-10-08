package com.barber.oauth.services;

import com.barber.userCommons.entity.User;

public interface IUserService {
    // Método para obtener un usuario por su correo electrónico
    public User getUserByEmail(String email);
}
