package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.service.ITradeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TradeController {

	@Autowired
	ITradeService iTradeService;

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
	    return httpServletRequest.getRemoteUser();
	}
	
	@GetMapping("/trade/list")
	public String get_tradeListPage(Model model) {
		model.addAttribute("trades", iTradeService.getTradeList());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String get_tradeAddForm(Trade trade) {
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String get_tradeUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = iTradeService.getTradeById(id);
		
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	@PostMapping("/trade/validate")
	public String postTrade_fromTradeAddForm(@Valid Trade trade, BindingResult result) {
		if (!result.hasErrors()) {
			iTradeService.addOrUpdateTrade(trade);
			return "redirect:/trade/list";
		}

		return "trade/add";
	}

	@PostMapping("/trade/update/{id}")
	public String postTrade_fromTradeUpdateForm(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result) {
		if (!result.hasErrors()) {
			trade.setId(id);
			iTradeService.addOrUpdateTrade(trade);

			return "redirect:/trade/list";
		}
		
		return "trade/update";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade_fromTradeListPage(@PathVariable("id") Integer id) {
		iTradeService.deleteTradeById(id);
		return "redirect:/trade/list";
	}
}