package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.User;

public interface IUserService {

	public String home(Model model);
	public String addUser(User bid);
	public String showUpdateForm(Integer id, Model model);

	public String validate(User user, BindingResult result, Model model);
	public String updateUser(Integer id, User user, BindingResult result, Model model);

	public String deleteUser(Integer id, Model model);
}