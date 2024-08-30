package com.barber.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barber.userCommons.entity.*;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    List<User> findByRoleId(Long roleId);
}

