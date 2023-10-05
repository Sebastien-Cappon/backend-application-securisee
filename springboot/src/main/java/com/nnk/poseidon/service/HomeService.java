package com.nnk.poseidon.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HomeService implements IHomeService {

	@Override
	public String home(Model model) {
		return "home";
	}

	@Override
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}
}