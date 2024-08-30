package com.barber.oauth.services;

import com.barber.userCommons.entity.User;

public interface IUserService {

	public User getUserByEmail(String email);
}
