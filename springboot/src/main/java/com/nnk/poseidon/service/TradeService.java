package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.repository.ITradeRepository;

@Service
public class TradeService implements ITradeService {

	@Autowired
	ITradeRepository iTradeRepository;

	@Override
	public String home(Model model) {
		// TODO: find all Trade, add to model
		return "trade/list";
	}

	@Override
	public String addUser(Trade bid) {
		return "trade/add";
	}

	@Override
	public String showUpdateForm(Integer id, Model model) {
		// TODO: get Trade by Id and to model then show to the form
		return "trade/update";
	}

	@Override
	public String validate(Trade trade, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Trade list
		return "trade/add";
	}

	@Override
	public String updateTrade(Integer id, Trade trade, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Trade and return
		// Trade list
		return "redirect:/trade/list";
	}

	@Override
	public String deleteTrade(Integer id, Model model) {
		// TODO: Find Trade by Id and delete the Trade, return to Trade list
		return "redirect:/trade/list";
	}
}