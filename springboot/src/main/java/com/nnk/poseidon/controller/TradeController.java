package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.service.ITradeService;

import jakarta.validation.Valid;

@Controller
public class TradeController {

	@Autowired
	ITradeService iTradeService;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		return iTradeService.home(model);
	}

	@GetMapping("/trade/add")
	public String addUser(Trade bid) {
		return iTradeService.addUser(bid);
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return iTradeService.showUpdateForm(id, model);
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		return iTradeService.validate(trade, result, model);
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		return iTradeService.updateTrade(id, trade, result, model);
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		return iTradeService.deleteTrade(id, model);
	}
}