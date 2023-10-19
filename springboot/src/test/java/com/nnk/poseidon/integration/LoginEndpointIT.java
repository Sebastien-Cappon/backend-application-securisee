package com.nnk.poseidon.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.IUserService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class LoginEndpointIT {
	private static final Logger logger = LoggerFactory.getLogger(RatingEndpointsIT.class);
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private User user = DomainObjectBuilders.createUser(null, "UsernameIT", encoder.encode("P@55w0rdIT"), "FullnameIT", "ADMIN");
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IUserService iUserService;
	
	@BeforeAll
	public void fillH2Database() {
		logger.info("user table in the H2 test database filled.");
		iUserService.addOrUpdateUser(user);
	}
	
	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}
	
	@Test
	@Order(1)
	public void login_shouldReturnSuccessAndRedirectToBidListPage() throws Exception {
		mockMvc.perform(formLogin("/login")
				.user("UsernameIT")
				.password("P@55w0rdIT"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}
	

	@Test
	@Order(2)
	public void login_shouldFailAndRedirectToLoginPage() throws Exception {
		mockMvc.perform(formLogin("/login")
				.user("AbsentUserIT")
				.password("D03snt€xist"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/login?error"));
	}
}