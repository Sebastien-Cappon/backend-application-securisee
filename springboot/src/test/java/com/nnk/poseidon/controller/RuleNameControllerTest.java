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

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.service.RuleNameService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = RuleNameController.class)
@TestMethodOrder(OrderAnnotation.class)
public class RuleNameControllerTest {

	private RuleName ruleName = DomainObjectBuilders.createRuleName(1, "Name", "Description", "Json", "Template", "SQL", "SQL Part");

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuleNameService ruleNameService;

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ruleNameListPage_shouldReturnOk() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>(Arrays.asList(ruleName, ruleName));

		when(ruleNameService.getRuleNameList())
			.thenReturn(ruleNameList);

		mockMvc.perform(get("/ruleName/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/list"))
			.andExpect(model().attributeExists("ruleNames"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ruleNameAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/ruleName/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ruleNameUpdateForm_shouldReturnOk() throws Exception {
		when(ruleNameService.getRuleNameById(any(Integer.class)))
			.thenReturn(ruleName);
		
		mockMvc.perform(get("/ruleName/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameAddForm_shouldSuccessAndRedirectToRuleNameListPage() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>(Arrays.asList(ruleName, ruleName));

		when(ruleNameService.getRuleNameList())
			.thenReturn(ruleNameList);
		when(ruleNameService.addOrUpdateRuleName(any(RuleName.class)))
			.thenReturn(ruleName);

		mockMvc.perform(post("/ruleName/validate")
				.flashAttr("ruleName", ruleName)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameAddForm_shouldFailAndReturnOk() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>(Arrays.asList(ruleName, ruleName));

		when(ruleNameService.getRuleNameList())
			.thenReturn(ruleNameList);
		when(ruleNameService.addOrUpdateRuleName(any(RuleName.class)))
			.thenReturn(ruleName);

		mockMvc.perform(post("/ruleName/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameUpdateForm_shouldSuccessAndRedirectToRuleNameListPage() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>(Arrays.asList(ruleName, ruleName));

		when(ruleNameService.getRuleNameList())
			.thenReturn(ruleNameList);
		when(ruleNameService.addOrUpdateRuleName(any(RuleName.class)))
			.thenReturn(ruleName);

		mockMvc.perform(post("/ruleName/update/{id}", "1")
				.flashAttr("ruleName", ruleName)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>(Arrays.asList(ruleName, ruleName));

		when(ruleNameService.getRuleNameList())
			.thenReturn(ruleNameList);
		when(ruleNameService.addOrUpdateRuleName(any(RuleName.class)))
			.thenReturn(ruleName);

		mockMvc.perform(post("/ruleName/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRuleName_fromRuleNameListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/ruleName/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}
}