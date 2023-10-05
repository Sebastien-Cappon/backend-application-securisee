package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.service.IRatingService;

import jakarta.validation.Valid;

@Controller
public class RatingController {

	@Autowired
	IRatingService iRatingService;

	@RequestMapping("/rating/list")
	public String home(Model model) {
		return iRatingService.home(model);
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		return iRatingService.addRatingForm(rating);
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return iRatingService.showUpdateForm(id, model);
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		return iRatingService.validate(rating, result, model);
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		return iRatingService.updateRating(id, rating, result, model);
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		return iRatingService.deleteRating(id, model);
	}
}