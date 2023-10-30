package com.nnk.poseidon.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.service.IRatingService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RatingEndpointsIT {

	private static final Logger logger = LoggerFactory.getLogger(RatingEndpointsIT.class);
	private Rating rating = DomainObjectBuilders.createRating(null, "MoodysIT", "SandPIT", "FitchIT", 1);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IRatingService iRatingService;

	@BeforeAll
	public void fillH2Database() {
		logger.info("rating table in the H2 test database filled.");

		for (int i = 0; i < 3; i++) {
			iRatingService.addOrUpdateRating(rating);
		}
	}

	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ratingListPage_shouldReturnOk() throws Exception {
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
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ratingUpdateForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/rating/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ratingUpdateForm_shouldThrowNoContent() throws Exception {
		mockMvc.perform(get("/rating/update/{id}", "9999"))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRRating_fromRatingAddForm_shouldSuccessAndRedirectToRatingListPage() throws Exception {
		mockMvc.perform(post("/rating/validate")
				.flashAttr("rating", rating)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRating_fromRatingAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/rating/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putRating_fromRatingUpdateForm_shouldSuccessAndRedirectToRatingListPage() throws Exception {
		mockMvc.perform(put("/rating/update/{id}", "1")
				.flashAttr("rating", rating)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putRating_fromRatingUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(put("/rating/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"))
			.andExpect(model().attributeExists("rating"));
	}

	@Test
	@Order(9)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRating_fromRatingListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/rating/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@Order(10)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRating_fromRatingListPage_shouldThrowNocontent() throws Exception {
		mockMvc.perform(delete("/rating/delete/{id}", "9999")
				.with(csrf()))
			.andExpect(status().isNoContent());
	}
}