package com.nnk.poseidon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>User</code>. It contains getters and setters, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>User</code> is linked to the <code>users</code> table of
 *              the database. The <code>password</code> had to follow a precise
 *              structure. For reasons of efficiency and optimisation, a regex
 *              via the annotation <code>@Pattern</code> was preferred to a
 *              custom validator.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Username is mandatory")
	private String username;
	@NotBlank(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$", message = "8 chars min : a-Z 0-9 !@#$%^&*")
	private String password;
	@NotBlank(message = "FullName is mandatory")
	private String fullname;
	@NotBlank(message = "Role is mandatory")
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * An override method for user-friendly display of <code>User</code> attributes
	 * in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>User</code>.
	 */
	@Override
	public String toString() {
		return "[" + id + "]" + "[" + username + "]" + "[" + password + "]" + "[" + fullname + "]" + "[" + role + "]";
	}
}