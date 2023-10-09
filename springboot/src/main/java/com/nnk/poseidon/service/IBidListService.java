package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.BidList;

public interface IBidListService {

	public String home(Model model);
	public String addBidForm(BidList bid);
	public String showUpdateForm(Integer id, Model model);

	public String validate(BidList bid, BindingResult result, Model model);
	public String updateBid(Integer id, BidList bidList, BindingResult result, Model model);

	public String deleteBid(Integer id, Model model);
}