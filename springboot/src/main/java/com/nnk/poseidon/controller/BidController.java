package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.service.IBidService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BidController {

	@Autowired
	IBidService iBidService;
	
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
	    return httpServletRequest.getRemoteUser();
	}

	@GetMapping("/bid/list")
	public String get_bidListPage(Model model) {
		model.addAttribute("bids", iBidService.getBidList());
		return "bid/list";
	}

	@GetMapping("/bid/add")
	public String get_bidAddForm(Bid bid) {
		return "bid/add";
	}

	@GetMapping("/bid/update/{id}")
	public String get_bidUpdateForm(@PathVariable("id") Integer id, Model model) {
		Bid bid = iBidService.getBidById(id);
		
		model.addAttribute("bid", bid);
		return "bid/update";
	}

	@PostMapping("/bid/validate")
	public String postBid_fromBidAddForm(@Valid Bid bid, BindingResult result) {
		if (!result.hasErrors()) {
			iBidService.addOrUpdateBid(bid);
			return "redirect:/bid/list";
		}

		return "bid/add";
	}

	@PostMapping("/bid/update/{id}")
	public String postBid_fromBidUpdateForm(@PathVariable("id") Integer id, @Valid Bid bid, BindingResult result) {
		if (!result.hasErrors()) {
			bid.setId(id);
			iBidService.addOrUpdateBid(bid);

			return "redirect:/bid/list";
		}
		
		return "bid/update";
	}

	@GetMapping("/bid/delete/{id}")
	public String deleteBid_fromBidListPage(@PathVariable("id") Integer id) {
		iBidService.deleteBidById(id);
		return "redirect:/bid/list";
	}
}