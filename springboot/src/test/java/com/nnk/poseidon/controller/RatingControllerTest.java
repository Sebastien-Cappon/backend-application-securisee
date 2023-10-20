package com.nnk.poseidon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.service.RatingService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = RatingController.class)
@TestMethodOrder(OrderAnnotation.class)
public class RatingControllerTest {

	private Rating rating = DomainObjectBuilders.createRating(1, "Moodys", "SandP", "Fitch", 2);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingService ratingService;

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ratingListPage_shouldReturnOk() throws Exception {
		List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating, rating));

		when(ratingService.getRatingList())
			.thenReturn(ratingList);

		mockMvc.perform(get("/rating/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/list"))
			.andExpect(model().attributeExists("ratings"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ratingAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/rating/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "user", roles={ "USER" })
	public void get_ratingUpdateForm_shouldReturnOk() throws Exception {
		when(ratingService.getRatingById(any(Integer.class)))
			.thenReturn(rating);
		
		mockMvc.perform(get("/rating/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRRating_fromRatingAddForm_shouldSuccessAndRedirectToRatingListPage() throws Exception {
		List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating, rating));

		when(ratingService.getRatingList())
			.thenReturn(ratingList);
		when(ratingService.addOrUpdateRating(any(Rating.class)))
			.thenReturn(rating);

		mockMvc.perform(post("/rating/validate")
				.flashAttr("rating", rating)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRating_fromRatingAddForm_shouldFailAndReturnOk() throws Exception {
		List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating, rating));

		when(ratingService.getRatingList())
			.thenReturn(ratingList);
		when(ratingService.addOrUpdateRating(any(Rating.class)))
			.thenReturn(rating);

		mockMvc.perform(post("/rating/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRating_fromRatingUpdateForm_shouldSuccessAndRedirectToRatingListPage() throws Exception {
		List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating, rating));

		when(ratingService.getRatingList())
			.thenReturn(ratingList);
		when(ratingService.addOrUpdateRating(any(Rating.class)))
			.thenReturn(rating);

		mockMvc.perform(post("/rating/update/{id}", "1")
				.flashAttr("rating", rating)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRating_fromRatingUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating, rating));

		when(ratingService.getRatingList())
			.thenReturn(ratingList);
		when(ratingService.addOrUpdateRating(any(Rating.class)))
			.thenReturn(rating);

		mockMvc.perform(post("/rating/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRating_fromRatingListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/rating/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}
}