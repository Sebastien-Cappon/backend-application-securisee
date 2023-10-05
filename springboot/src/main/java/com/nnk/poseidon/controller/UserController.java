package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.IUserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private IUserService iUserService;

	@RequestMapping("/user/list")
	public String home(Model model) {
		return iUserService.home(model);
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		return iUserService.addUser(bid);
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return iUserService.showUpdateForm(id, model);
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		return iUserService.validate(user, result, model);
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		return iUserService.updateUser(id, user, result, model);
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		return iUserService.deleteUser(id, model);
	}
}