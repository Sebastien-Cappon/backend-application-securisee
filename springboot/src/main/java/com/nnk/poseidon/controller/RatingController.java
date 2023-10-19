package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.service.IRatingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RatingController {

	@Autowired
	IRatingService iRatingService;

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
	    return httpServletRequest.getRemoteUser();
	}
	
	@GetMapping("/rating/list")
	public String get_ratingListPage(Model model) {
		model.addAttribute("ratings", iRatingService.getRatingList());
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String get_ratingAddForm(Rating rating) {
		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String get_ratingUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = iRatingService.getRatingById(id);
		
		if(rating == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			model.addAttribute("rating", rating);
			return "rating/update";
		}
	}

	@PostMapping("/rating/validate")
	public String postRating_fromRatingAddForm(@Valid Rating rating, BindingResult result) {
		if (!result.hasErrors()) {
			iRatingService.addOrUpdateRating(rating);
			return "redirect:/rating/list";
		}

		return "rating/add";
	}

	@PostMapping("/rating/update/{id}")
	public String postRating_fromRatingUpdateForm(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result) {
		if (!result.hasErrors()) {
			rating.setId(id);
			iRatingService.addOrUpdateRating(rating);

			return "redirect:/rating/list";
		}
		
		return "rating/update";
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating_fromRatingListPage(@PathVariable("id") Integer id) {
		Integer deletedRating = iRatingService.deleteRatingById(id);
		
		if(deletedRating == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return "redirect:/rating/list";
		}
	}
}