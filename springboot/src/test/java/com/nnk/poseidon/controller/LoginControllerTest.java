package com.nnk.poseidon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoginController.class)
public class LoginControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void login_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk());
	}
}