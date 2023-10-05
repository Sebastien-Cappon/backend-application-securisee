package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.poseidon.service.ILoginService;

@Controller
@RequestMapping("app")
public class LoginController {

	@Autowired
	private ILoginService iLoginService;

	@GetMapping("login")
	public ModelAndView login() {
		return iLoginService.login();
	}

	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		return iLoginService.getAllUserArticles();
	}

	@GetMapping("error")
	public ModelAndView error() {
		return iLoginService.error();
	}
}