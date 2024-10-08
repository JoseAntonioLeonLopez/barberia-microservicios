package com.barber.userservice.service;

import com.barber.userCommons.entity.*;
import com.barber.userservice.exception.RoleAlreadyExistsException;
import com.barber.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementación del servicio para la gestión de roles
@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private RoleRepository roleRepository; // Repositorio para la persistencia de roles

    // Crea un nuevo rol, lanzando una excepción si ya existe
    public Role createRole(Role role) {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new RoleAlreadyExistsException("Role with name '" + role.getName() + "' already exists");
        }
        return roleRepository.save(role); // Guarda y retorna el rol creado
    }

    // Obtiene todos los roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Busca un rol por su ID
    public Role getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null); // Retorna el rol si existe, o null si no
    }

    // Actualiza un rol existente, lanzando una excepción si el nombre ya existe
    public Role updateRole(Long id, Role roleDetails) {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);
        if (!existingRoleOptional.isPresent()) {
            return null; // Retorna null si el rol no existe
        }

        Role existingRole = existingRoleOptional.get();
        // Verifica si el nombre del rol a actualizar ya existe
        if (!existingRole.getName().equals(roleDetails.getName()) && roleRepository.findByName(roleDetails.getName()) != null) {
            throw new RoleAlreadyExistsException("Role with name '" + roleDetails.getName() + "' already exists");
        }

        existingRole.setName(roleDetails.getName()); // Actualiza el nombre del rol
        return roleRepository.save(existingRole); // Guarda y retorna el rol actualizado
    }

    // Elimina un rol por su ID
    public boolean deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            return false; // Retorna false si el rol no existe
        }
        roleRepository.deleteById(id); // Elimina el rol
        return true; // Retorna true si la eliminación fue exitosa
    }

    // Busca un rol por su nombre
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
