package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.repository.IRatingRepository;

@Service
public class RatingService implements IRatingService {

	@Autowired
	IRatingRepository iRatingRepository;

	@Override
	public String home(Model model) {
		// TODO: find all Rating, add to model
		return "rating/list";
	}

	@Override
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	@Override
	public String showUpdateForm(Integer id, Model model) {
		// TODO: get Rating by Id and to model then show to the form
		return "rating/update";
	}

	@Override
	public String validate(Rating rating, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Rating list
		return "rating/add";
	}

	@Override
	public String updateRating(Integer id, Rating rating, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list
		return "redirect:/rating/list";
	}

	@Override
	public String deleteRating(Integer id, Model model) {
		// TODO: Find Rating by Id and delete the Rating, return to Rating list
		return "redirect:/rating/list";
	}
}