package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nnk.poseidon.domain.User;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal queries relative to <code>User</code> entities.
 * 
 * @singularity Derived query <code>findByUsername</code> is created for
 *              authentication purpose only.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IUserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	public User findByUsername(String username);
}