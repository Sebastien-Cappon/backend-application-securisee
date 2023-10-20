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

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.service.IRuleNameService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RuleNameEndpointsIT {

	private static final Logger logger = LoggerFactory.getLogger(RatingEndpointsIT.class);
	private RuleName ruleName = DomainObjectBuilders.createRuleName(null, "NameIT", "DescriptionIT", "JsonIT", "TemplateIT", "SqlStrIT", "SqlPartIT");

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IRuleNameService iRuleNameService;

	@BeforeAll
	public void fillH2Database() {
		logger.info("rulename table in the H2 test database filled.");

		for (int i = 0; i < 3; i++) {
			iRuleNameService.addOrUpdateRuleName(ruleName);
		}
	}

	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ruleNameListPage_shouldReturnOk() throws Exception {
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
		mockMvc.perform(get("/ruleName/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_ruleNameUpdateForm_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/ruleName/update/{id}", "9999"))
			.andExpect(status().isInternalServerError());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameAddForm_shouldSuccessAndRedirectToRuleNameListPage() throws Exception {
		mockMvc.perform(post("/ruleName/validate")
				.flashAttr("ruleName", ruleName)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/ruleName/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameUpdateForm_shouldSuccessAndRedirectToRuleNameListPage() throws Exception {
		mockMvc.perform(post("/ruleName/update/{id}", "1")
				.flashAttr("ruleName", ruleName)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postRuleName_fromRuleNameUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/ruleName/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"))
			.andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@Order(9)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRuleName_fromRuleNameListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/ruleName/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/ruleName/list"));
	}

	@Test
	@Order(10)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteRuleName_fromRuleNameListPage_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/ruleName/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().isInternalServerError());
	}
}