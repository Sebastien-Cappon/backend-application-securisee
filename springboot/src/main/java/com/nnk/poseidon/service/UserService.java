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
		if(iUserRepository.findById(id).isPresent()) {
			return iUserRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public User addOrUpdateUser(User user) {
		return iUserRepository.save(user);
	}

	@Override
	public Integer deleteUserById(Integer id) {
		if(iUserRepository.findById(id).isPresent()) {
			iUserRepository.delete(iUserRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}