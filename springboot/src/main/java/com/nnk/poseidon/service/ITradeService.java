package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.Trade;

public interface ITradeService {

	public String home(Model model);
	public String addUser(Trade bid);
	public String showUpdateForm(Integer id, Model model);

	public String validate(Trade trade, BindingResult result, Model model);
	public String updateTrade(Integer id, Trade trade, BindingResult result, Model model);

	public String deleteTrade(Integer id, Model model);
}