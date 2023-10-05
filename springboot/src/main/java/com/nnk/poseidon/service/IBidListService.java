package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.nnk.poseidon.domain.BidList;

import jakarta.validation.Valid;

public interface IBidListService {

	public String home(Model model);
	public String addBidForm(BidList bid);
	public String showUpdateForm(@PathVariable("id") Integer id, Model model);

	public String validate(@Valid BidList bid, BindingResult result, Model model);
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model);

	public String deleteBid(@PathVariable("id") Integer id, Model model);
}