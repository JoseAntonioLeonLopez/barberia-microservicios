package com.barber.userservice.service;

import com.barber.userCommons.entity.*;
import com.barber.userservice.exception.RoleAlreadyExistsException;
import com.barber.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new RoleAlreadyExistsException("Role with name '" + role.getName() + "' already exists");
        }
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    public Role updateRole(Long id, Role roleDetails) {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);
        if (!existingRoleOptional.isPresent()) {
            return null;
        }

        Role existingRole = existingRoleOptional.get();
        if (!existingRole.getName().equals(roleDetails.getName()) && roleRepository.findByName(roleDetails.getName()) != null) {
            throw new RoleAlreadyExistsException("Role with name '" + roleDetails.getName() + "' already exists");
        }

        existingRole.setName(roleDetails.getName());
        return roleRepository.save(existingRole);
    }

    public boolean deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }
        roleRepository.deleteById(id);
        return true;
    }

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
}
