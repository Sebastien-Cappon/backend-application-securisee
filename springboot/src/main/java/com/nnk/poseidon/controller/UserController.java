package com.nnk.poseidon.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
	    return httpServletRequest.getRemoteUser();
	}
	
	@GetMapping("/user/list")
	public String get_userListPage(Model model) {
		model.addAttribute("users", iUserService.getUserList());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String get_userAddForm(User user) {
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String get_userUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = iUserService.getUserById(id);
		user.setPassword("");
		
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/validate")
	public String postUser_fromUserAddForm(@Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			iUserService.addOrUpdateUser(user);

			return "redirect:/user/list";
		}

		return "user/add";
	}

	@PostMapping("/user/update/{id}")
	public String postUser_fromUserUpdateForm(@PathVariable("id") Integer id, @Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			user.setId(id);
			iUserService.addOrUpdateUser(user);

			return "redirect:/user/list";
		}

		return "user/update";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser_fromUserListPage(@PathVariable("id") Integer id) {
		iUserService.deleteUserById(id);
		return "redirect:/user/list";
	}
}