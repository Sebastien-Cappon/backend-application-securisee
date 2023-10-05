package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.nnk.poseidon.domain.BidList;
import com.nnk.poseidon.repository.IBidListRepository;

import jakarta.validation.Valid;

@Service
public class BidListService implements IBidListService {

	@Autowired
	IBidListRepository iBidListRepository;

	@Override
	public String home(Model model) {
		// TODO: call service find all bids to show to the view
		return "bidList/list";
	}

	@Override
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	@Override
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		return "bidList/update";
	}

	@Override
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return bid list
		return "bidList/add";
	}

	@Override
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid
		return "redirect:/bidList/list";
	}

	@Override
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		return "redirect:/bidList/list";
	}
}