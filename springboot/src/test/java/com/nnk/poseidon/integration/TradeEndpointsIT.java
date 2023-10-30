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

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.service.ITradeService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TradeEndpointsIT {

	private static final Logger logger = LoggerFactory.getLogger(RatingEndpointsIT.class);
	private Trade trade = DomainObjectBuilders.createTrade(null, "AccountIT", "TypeIT", 1d, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ITradeService iTradeService;

	@BeforeAll
	public void fillH2Database() {
		logger.info("trade table in the H2 test database filled.");

		for (int i = 0; i < 3; i++) {
			iTradeService.addOrUpdateTrade(trade);
		}
	}

	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_tradeListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/trade/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/list"))
			.andExpect(model().attributeExists("trades"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_tradeAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/trade/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"))
			.andExpect(model().attributeExists("trade"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_tradeUpdateForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/trade/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"))
			.andExpect(model().attributeExists("trade"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_tradeUpdateForm_shouldThrowNoContent() throws Exception {
		mockMvc.perform(get("/trade/update/{id}", "9999"))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postTrade_fromTradeAddForm_shouldSuccessAndRedirectToTradeListPage() throws Exception {
		mockMvc.perform(post("/trade/validate")
				.flashAttr("trade", trade)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/trade/list"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postTrade_fromTradeAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/trade/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"))
			.andExpect(model().attributeExists("trade"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void puTrade_fromTradeUpdateForm_shouldSuccessAndRedirectToTradeListPage() throws Exception {
		mockMvc.perform(put("/trade/update/{id}", "1")
				.flashAttr("trade", trade)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/trade/list"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putTrade_fromTradeUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(put("/trade/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"))
			.andExpect(model().attributeExists("trade"));
	}

	@Test
	@Order(9)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteTrade_fromTradeListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/trade/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/trade/list"));
	}

	@Test
	@Order(10)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteTrade_fromTradeListPage_shouldThrowNNoContent() throws Exception {
		mockMvc.perform(delete("/trade/delete/{id}", "9999")
				.with(csrf()))
			.andExpect(status().isNoContent());
	}
}