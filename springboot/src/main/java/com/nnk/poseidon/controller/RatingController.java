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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.service.IRatingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Rating. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class RatingController {

	@Autowired
	IRatingService iRatingService;

	/**
	 * A method which retrieves the active user and registers it in the
	 * <code>remoteUser</code> attribute of the current <code>Model</code> in order
	 * to display the name of the connected user at the top of the page.
	 * 
	 * @return An <code>Object</code>.
	 */
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/rating/list</code> URI with a
	 * <code>Model</code> as parameter. It calls the <code>IRatingService</code>
	 * method <code>getRatingList()</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page.
	 * 
	 * @frontCall Rating list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/rating/list")
	public String get_ratingListPage(Model model) {
		model.addAttribute("ratings", iRatingService.getRatingList());
		return "rating/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/rating/add</code> URI. It only
	 * returns the URI of a template page.
	 * 
	 * @frontCall Rating add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/rating/add")
	public String get_ratingAddForm(Rating rating) {
		return "rating/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/rating/update</code> URI with
	 * a rating id as <code>PathVariable</code> and a <code>Model</code> as
	 * parameter. It calls the <code>IRatingService</code> method
	 * <code>getRatingById(int id)</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page of the current rating.
	 * 
	 * @frontCall Rating update form page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the rating concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/rating/update/{id}")
	public String get_ratingUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = iRatingService.getRatingById(id);

		if (rating == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			model.addAttribute("rating", rating);
			return "rating/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/rating/validate</code> URI
	 * with a valid <code>Rating</code> and a <code>Model</code> as parameter. It
	 * calls the <code>IRatingService</code> method
	 * <code>addOrUpdateRating(Rating rating)</code> in order to save the new rating
	 * in the database. Then, it redirects to the rating list page.
	 * 
	 * @frontCall Rating add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/rating/validate")
	public String postRating_fromRatingAddForm(@Valid Rating rating, BindingResult result) {
		if (!result.hasErrors()) {
			iRatingService.addOrUpdateRating(rating);
			return "redirect:/rating/list";
		}

		return "rating/add";
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/rating/update</code> URI with
	 * a rating id as <code>PathVariable</code>, a valid <code>Rating</code> and a
	 * <code>Model</code> as parameter. It calls the <code>IRatingService</code>
	 * method <code>addOrUpdateRating(Rating rating)</code> in order to update, in
	 * the database, the rating whose id is passed in parameter. Then, it redirects
	 * to the rating list page.
	 * 
	 * @frontCall Rating update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/rating/update/{id}")
	public String putRating_fromRatingUpdateForm(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result) {
		if (!result.hasErrors()) {
			rating.setId(id);
			iRatingService.addOrUpdateRating(rating);

			return "redirect:/rating/list";
		}

		return "rating/update";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/rating/delete</code> URI with
	 * a rating id as <code>PathVariable</code>. It calls the
	 * <code>IRAtingService</code> method <code>deleteRAtingById(int id)</code>.
	 * Then, it redirects to the rating list page.
	 * 
	 * @frontCall Rating list page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the rating concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/rating/delete/{id}")
	public String deleteRating_fromRatingListPage(@PathVariable("id") Integer id) {
		Integer deletedRating = iRatingService.deleteRatingById(id);

		if (deletedRating == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/rating/list";
		}
	}
}