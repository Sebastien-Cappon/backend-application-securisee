package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.repository.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public List<User> getUserList() {
		return iUserRepository.findAll();
	}

	@Override
	public User getUserById(Integer id) {
		User user = iUserRepository.findById(id).get();
		
		if (user == null) {
			throw new IllegalArgumentException("Invalid User Id:" + id);
		}
		
		return user;
	}

	@Override
	public User addOrUpdateUser(User user) {
		return iUserRepository.save(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		User user = iUserRepository.findById(id).get();
		
		if(user == null) {
			throw new IllegalArgumentException("Invalid User Id:" + id);
		}
		
		iUserRepository.delete(user);
	}
}