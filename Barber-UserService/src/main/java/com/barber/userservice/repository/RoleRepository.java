package com.barber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barber.userCommons.entity.Role;

// Repositorio para manejar operaciones CRUD de la entidad Role
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Método para buscar un rol por su nombre
    Role findByName(String name);
}
