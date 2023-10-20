package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.repository.IUserRepository;

/**
 * A class that implements <code>UserDetailsService</code>, a native SpringBoot
 * class used to retrieve username, password and role for authentication.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class AuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	IUserRepository iUserRepository;

	/**
	 * A method that finds a user in the database and loads it into a
	 * <code>UserDetails</code> before returning it.
	 * 
	 * @return An <code>UserDetails</code> or <code>null</code> if the user which
	 *         username passed as parameter doesn't exist in the database.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = iUserRepository.findByUsername(username);

		if (user == null) {
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