package com.barber.userservice.service;

import java.util.List;

import com.barber.userCommons.entity.*;

public interface IUserService {
	User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    List<User> findByRoleId(Long roleId);
}
