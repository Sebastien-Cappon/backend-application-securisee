package com.nnk.poseidon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.RequestDispatcher;

@WebMvcTest(controllers = ErrorControllerImpl.class)
@TestMethodOrder(OrderAnnotation.class)
public class ErrorControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@Order(1)
	@WithMockUser(username="user", roles={"USER"})
	public void handleError_shouldReturnOk_andDisplayError400Page() throws Exception {
		mockMvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

	@Test
	@Order(2)
	@WithMockUser(username="user", roles={"USER"})
	public void handleError_shouldReturnOk_andDisplayError403Page() throws Exception {
		mockMvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 403))
			.andExpect(status().isOk())
			.andExpect(view().name("error/403"));
	}

	@Test
	@Order(3)
	@WithMockUser(username="user", roles={"USER"})
	public void handleError_shouldReturnOk_andDisplayError404Page() throws Exception {
		mockMvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@Test
	@Order(4)
	@WithMockUser(username="user", roles={"USER"})
	public void handleError_shouldReturnOk_andDisplayError500Page() throws Exception {
		mockMvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
			.andExpect(status().isOk())
			.andExpect(view().name("error/500"));
	}

	@Test
	@Order(5)
	@WithMockUser(username="user", roles={"USER"})
	public void handleError_shouldReturnOk_andDisplayErrorDefaultPage() throws Exception {
		mockMvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 418))
			.andExpect(status().isOk())
			.andExpect(view().name("error/default"));
	}
}