package com.nnk.poseidon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.service.BidService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = BidController.class)
@TestMethodOrder(OrderAnnotation.class)
public class BidControllerTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Timestamp timeStampTest = Timestamp.valueOf(LocalDate.parse("2023-10-02", dateTimeFormatter).atStartOfDay());
	private Bid bid = DomainObjectBuilders.createBid(1, "Account", "Type", 1d, 2d, 3d, 4d, "Benchmark", timeStampTest, "Commentary", "Security", "Status", "Trader", "Book", "Creation name", timeStampTest, "Revision name", timeStampTest, "Deal name", "Deal type", "Source list id", "Side");

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BidService bidService;

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_bidListPage_shouldReturnOk() throws Exception {
		List<Bid> bidList = new ArrayList<>(Arrays.asList(bid, bid));

		when(bidService.getBidList()).thenReturn(bidList);

		mockMvc.perform(get("/bid/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/list"))
			.andExpect(model().attributeExists("bids"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_bidAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/bid/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/add"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_bidUpdateForm_shouldReturnOk() throws Exception {
		when(bidService.getBidById(any(Integer.class)))
			.thenReturn(bid);
		
		mockMvc.perform(get("/bid/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/update"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postBid_fromBidAddForm_shouldSuccessAndRedirectToBidListPage() throws Exception {
		List<Bid> bidList = new ArrayList<>(Arrays.asList(bid, bid));

		when(bidService.getBidList())
			.thenReturn(bidList);
		when(bidService.addOrUpdateBid(any(Bid.class)))
			.thenReturn(bid);

		mockMvc.perform(post("/bid/validate")
				.flashAttr("bid", bid)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postBid_fromBidAddForm_shouldFailAndReturnOk() throws Exception {
		List<Bid> bidList = new ArrayList<>(Arrays.asList(bid, bid));

		when(bidService.getBidList())
			.thenReturn(bidList);
		when(bidService.addOrUpdateBid(any(Bid.class)))
			.thenReturn(bid);

		mockMvc.perform(post("/bid/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/add"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putBid_fromBidUpdateForm_shouldSuccessAndRedirectToBidListPage() throws Exception {
		List<Bid> bidList = new ArrayList<>(Arrays.asList(bid, bid));

		when(bidService.getBidList())
			.thenReturn(bidList);
		when(bidService.addOrUpdateBid(any(Bid.class)))
			.thenReturn(bid);

		mockMvc.perform(put("/bid/update/{id}", "1")
				.flashAttr("bid", bid)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putBid_fromBidUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<Bid> bidList = new ArrayList<>(Arrays.asList(bid, bid));

		when(bidService.getBidList())
			.thenReturn(bidList);
		when(bidService.addOrUpdateBid(any(Bid.class)))
			.thenReturn(bid);

		mockMvc.perform(put("/bid/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/update"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteBid_fromBidListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/bid/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}
}