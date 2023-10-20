package com.nnk.poseidon.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.IUserService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UserEndpointsIT {

	private static final Logger logger = LoggerFactory.getLogger(RatingEndpointsIT.class);
	private User user = DomainObjectBuilders.createUser(null, "UsernameIT", "P@55w0rdIT", "FullnameIT", "ADMIN");

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IUserService iUserService;

	@BeforeAll
	public void fillH2Database() {
		logger.info("user table in the H2 test database filled.");

		for (int i = 0; i < 3; i++) {
			iUserService.addOrUpdateUser(user);
		}
	}

	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void get_userListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"))
			.andExpect(model().attributeExists("users"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void get_userAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/add"))
			.andExpect(model().attributeExists("user"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void get_UserUpdateForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"))
			.andExpect(model().attributeExists("user"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void get_UserUpdateForm_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/user/update/{id}", "9999"))
			.andExpect(status().isInternalServerError());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void postUser_fromUserAddForm_shouldSuccessAndRedirectToUserListPage() throws Exception {
		mockMvc.perform(post("/user/validate")
				.flashAttr("user", user)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void postUser_fromUserAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/user/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/add"))
			.andExpect(model().attributeExists("user"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void postUser_fromUserUpdateForm_shouldSuccessAndRedirectToUserListPage() throws Exception {
		mockMvc.perform(post("/user/update/{id}", "1")
				.flashAttr("user", user).with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void postUser_fromUserUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/user/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"))
			.andExpect(model().attributeExists("user"));
	}

	@Test
	@Order(9)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void deleteUser_fromUserListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}

	@Test
	@Order(10)
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void deleteUser_fromUserListPage_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/user/delete/{id}", "9999")
				.with(csrf()))
			.andExpect(status().isInternalServerError());
	}
}