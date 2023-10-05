package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.poseidon.repository.IUserRepository;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("login");

		return modelAndView;
	}

	@Override
	public ModelAndView getAllUserArticles() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("users", iUserRepository.findAll());
		modelAndView.setViewName("user/list");

		return modelAndView;
	}

	@Override
	public ModelAndView error() {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";

		modelAndView.addObject("errorMsg", errorMessage);
		modelAndView.setViewName("403");

		return modelAndView;
	}
}