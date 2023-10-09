package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.repository.IUserRepository;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository iUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = iUserRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + "not found.");
		}
		
		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(username)
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
		
		return userDetails;
	}
}