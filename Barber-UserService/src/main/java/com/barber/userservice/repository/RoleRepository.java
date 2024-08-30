package com.barber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barber.userCommons.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}

