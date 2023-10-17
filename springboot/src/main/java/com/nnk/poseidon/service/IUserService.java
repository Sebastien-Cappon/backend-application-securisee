package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.User;

public interface IUserService {
	
	public List<User> getUserList();
	public User getUserById(Integer id);

	public User addOrUpdateUser(User user);

	public void deleteUserById(Integer id);
}