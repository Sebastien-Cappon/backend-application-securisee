package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.User;

/**
 * <code>UserService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IUserService {

	public List<User> getUserList();
	public User getUserById(Integer id);

	public User addOrUpdateUser(User user);

	public Integer deleteUserById(Integer id);
}