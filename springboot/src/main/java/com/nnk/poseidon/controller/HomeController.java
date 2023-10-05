package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.poseidon.service.IHomeService;

@Controller
public class HomeController
{
	
	@Autowired
	IHomeService iHomeService;
	
	@RequestMapping("/")
	public String home(Model model)
	{
		return iHomeService.home(model);
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return iHomeService.adminHome(model);
	}
}