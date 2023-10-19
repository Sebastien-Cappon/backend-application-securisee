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

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.UserService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

	private User user = DomainObjectBuilders.createUser(1, "Username", "P@sswo4d", "Fullname", "USER");
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	@Order(1)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void get_userListPage_shouldReturnOk() throws Exception {
		List<User> userList = new ArrayList<>(Arrays.asList(user, user));
		
		when(userService.getUserList())
			.thenReturn(userList);
		
		mockMvc.perform(get("/user/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"))
			.andExpect(model().attributeExists("users"));
	}
	
	@Test
	@Order(2)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void get_userAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/add"))
			.andExpect(model().attributeExists("user"));
	}
	
	@Test
	@Order(3)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void get_UserUpdateForm_shouldReturnOk() throws Exception {
		when(userService.getUserById(any(Integer.class)))
			.thenReturn(user);
	
		mockMvc.perform(get("/user/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"))
			.andExpect(model().attributeExists("user"));
	}
	
	@Test
	@Order(4)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void postUser_fromUserAddForm_shouldSuccessAndRedirectToUserListPage() throws Exception {
		List<User> userList = new ArrayList<>(Arrays.asList(user, user));

		when(userService.getUserList())
			.thenReturn(userList);
		when(userService.addOrUpdateUser(any(User.class)))
			.thenReturn(user);
		
		mockMvc.perform(post("/user/validate")
				.flashAttr("user", user)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}
	
	@Test
	@Order(5)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void postUser_fromUserAddForm_shouldFailAndReturnOk() throws Exception {
		List<User> userList = new ArrayList<>(Arrays.asList(user, user));

		when(userService.getUserList())
			.thenReturn(userList);
		when(userService.addOrUpdateUser(any(User.class)))
			.thenReturn(user);
		
		mockMvc.perform(post("/user/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/add"))
			.andExpect(model().attributeExists("user"));
	}
	
	@Test
	@Order(6)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void postUser_fromUserUpdateForm_shouldSuccessAndRedirectToUserListPage() throws Exception {
		List<User> userList = new ArrayList<>(Arrays.asList(user, user));

		when(userService.getUserList())
			.thenReturn(userList);
		when(userService.addOrUpdateUser(any(User.class)))
			.thenReturn(user);
		
		mockMvc.perform(post("/user/update/{id}", "1")
				.flashAttr("user", user)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}
	
	@Test
	@Order(7)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void postUser_fromUserUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<User> userList = new ArrayList<>(Arrays.asList(user, user));

		when(userService.getUserList())
			.thenReturn(userList);
		when(userService.addOrUpdateUser(any(User.class)))
			.thenReturn(user);
		
		mockMvc.perform(post("/user/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update"))
			.andExpect(model().attributeExists("user"));
	}
	
	@Test
	@Order(8)
	@WithMockUser(username="admin", roles={"ADMIN"})
	public void deleteUser_fromUserListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/user/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/user/list"));
	}
}