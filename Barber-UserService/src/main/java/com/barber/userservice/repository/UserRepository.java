package com.barber.userservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barber.userCommons.entity.*;

// Repositorio para manejar operaciones CRUD de la entidad User
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Método para buscar un usuario por su dirección de correo electrónico
    User findByEmail(String email);
    
    // Método para buscar un usuario por su número de teléfono
    User findByPhoneNumber(String phoneNumber);
    
    // Método para buscar usuarios por su ID de rol
    List<User> findByRoleId(Long roleId);
}
