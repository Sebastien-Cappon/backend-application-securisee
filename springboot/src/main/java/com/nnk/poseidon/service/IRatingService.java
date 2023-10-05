package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.Rating;

public interface IRatingService {

	public String home(Model model);
	public String addRatingForm(Rating rating);
	public String showUpdateForm(Integer id, Model model);

	public String validate(Rating rating, BindingResult result, Model model);
	public String updateRating(Integer id, Rating rating, BindingResult result, Model model);

	public String deleteRating(Integer id, Model model);
}