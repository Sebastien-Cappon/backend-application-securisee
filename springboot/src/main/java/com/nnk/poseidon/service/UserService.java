package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.repository.IUserRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>User</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;

	/**
	 * A <code>GET</code> method that returns a <code>User</code> list after calling
	 * the <code>findAll()</code> derived query from <code>IUserRepository</code>.
	 * 
	 * @return A <code>User</code> list.
	 */
	@Override
	public List<User> getUserList() {
		return iUserRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>User</code> whose id is passed
	 * as parameter after calling the <code>findById()</code> derived query from
	 * <code>IUserRepository</code>.
	 * 
	 * @return A <code>User</code> or <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public User getUserById(Integer id) {
		if (iUserRepository.findById(id).isPresent()) {
			return iUserRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>User</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>IUserRepository</code>.
	 * 
	 * @return A <code>User</code>.
	 */
	@Override
	public User addOrUpdateUser(User user) {
		return iUserRepository.save(user);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>IUserRepository</code> if the user whose id is passed as parameter
	 * exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>User</code>
	 *              doesn't exists in the database.
	 */
	@Override
	public Integer deleteUserById(Integer id) {
		if (iUserRepository.findById(id).isPresent()) {
			iUserRepository.delete(iUserRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}