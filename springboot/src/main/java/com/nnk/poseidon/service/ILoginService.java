package com.nnk.poseidon.service;

import org.springframework.web.servlet.ModelAndView;

public interface ILoginService {

	public ModelAndView login();
	public ModelAndView getAllUserArticles();
	public ModelAndView error();
}