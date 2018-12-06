package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.security.User;

public interface UserService {
	
	public List<User> findAll();
	
	public User save(User user);
	
	void delete(String username);

}
