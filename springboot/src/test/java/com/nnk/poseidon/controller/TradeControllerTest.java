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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.service.TradeService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = TradeController.class)
@TestMethodOrder(OrderAnnotation.class)
public class TradeControllerTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Timestamp timeStampTest = Timestamp.valueOf(LocalDate.parse("2023-10-02", dateTimeFormatter).atStartOfDay());
	Trade trade = DomainObjectBuilders.createTrade(1, "Account", "Type", 1d, 2d, 3d, 4d, "Benchmark", timeStampTest, "Security", "Status", "Trader", "Book", "CreationName", timeStampTest, "RevisionName", timeStampTest, "DealName", "DealType", "SourceListId", "Side");

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TradeService tradeService;

	@Test
	@Order(1)
	@WithMockUser(username="user", roles={"USER"})
	public void get_tradeListPage_shouldReturnOk() throws Exception {
		List<Trade> tradeList = new ArrayList<>(Arrays.asList(trade, trade));
		
		when(tradeService.getTradeList())
			.thenReturn(tradeList);
		
		mockMvc.perform(get("/trade/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/list"))
			.andExpect(model().attributeExists("trades"));
	}
	
	@Test
	@Order(2)
	@WithMockUser(username="user", roles={"USER"})
	public void get_tradeAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/trade/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"))
			.andExpect(model().attributeExists("trade"));
	}
	
	@Test
	@Order(3)
	@WithMockUser(username="user", roles={"USER"})
	public void get_tradeUpdateForm_shouldReturnOk() throws Exception {
		when(tradeService.getTradeById(any(Integer.class)))
			.thenReturn(trade);
		
		mockMvc.perform(get("/trade/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"))
			.andExpect(model().attributeExists("trade"));
	}
	
	@Test
	@Order(4)
	@WithMockUser(username="user", roles={"USER"})
	public void postTrade_fromTradeAddForm_shouldSuccessAndRedirectToTradeListPage() throws Exception {
		List<Trade> tradeList = new ArrayList<>(Arrays.asList(trade, trade));

		when(tradeService.getTradeList())
			.thenReturn(tradeList);
		when(tradeService.addOrUpdateTrade(any(Trade.class)))
			.thenReturn(trade);
		
		mockMvc.perform(post("/trade/validate")
				.flashAttr("trade", trade)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/trade/list"));
	}
	
	@Test
	@Order(5)
	@WithMockUser(username="user", roles={"USER"})
	public void postTrade_fromTradeAddForm_shouldFailAndReturnOk() throws Exception {
		List<Trade> tradeList = new ArrayList<>(Arrays.asList(trade, trade));

		when(tradeService.getTradeList())
			.thenReturn(tradeList);
		when(tradeService.addOrUpdateTrade(any(Trade.class)))
			.thenReturn(trade);
		
		mockMvc.perform(post("/trade/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"))
			.andExpect(model().attributeExists("trade"));
	}
	
	@Test
	@Order(6)
	@WithMockUser(username="user", roles={"USER"})
	public void postTrade_fromTradeUpdateForm_shouldSuccessAndRedirectToTradeListPage() throws Exception {
		List<Trade> tradeList = new ArrayList<>(Arrays.asList(trade, trade));

		when(tradeService.getTradeList())
			.thenReturn(tradeList);
		when(tradeService.addOrUpdateTrade(any(Trade.class)))
			.thenReturn(trade);
		
		mockMvc.perform(post("/trade/update/{id}", "1")
				.flashAttr("trade", trade)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/trade/list"));
	}
	
	@Test
	@Order(7)
	@WithMockUser(username="user", roles={"USER"})
	public void postTrade_fromTradeUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<Trade> tradeList = new ArrayList<>(Arrays.asList(trade, trade));

		when(tradeService.getTradeList())
			.thenReturn(tradeList);
		when(tradeService.addOrUpdateTrade(any(Trade.class)))
			.thenReturn(trade);
		
		mockMvc.perform(post("/trade/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"))
			.andExpect(model().attributeExists("trade"));
	}
	
	@Test
	@Order(8)
	@WithMockUser(username="user", roles={"USER"})
	public void deleteTrade_fromTradeListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/trade/delete/{id}", "1")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location", "/trade/list"));
	}
}
