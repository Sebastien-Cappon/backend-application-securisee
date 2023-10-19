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

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.service.IBidService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class BidEndpointsIT {
	
	private static final Logger logger = LoggerFactory.getLogger(BidEndpointsIT.class);
	private Bid bid = DomainObjectBuilders.createBid(null, "AccountIT", "TypeIT", 10d, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IBidService iBidService;
	
	@BeforeAll
	public void fillH2Database() {
		logger.info("bidlist table in the H2 test database filled.");
		
		for (int i = 0; i < 3; i++) {
			iBidService.addOrUpdateBid(bid);
		}
	}
	
	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username="user", roles={"USER"})
	public void get_bidListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/bid/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/list"))
			.andExpect(model().attributeExists("bids"));
	}
	
	@Test
	@Order(2)
	@WithMockUser(username="user", roles={"USER"})
	public void get_bidAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/bid/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/add"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(3)
	@WithMockUser(username="user", roles={"USER"})
	public void get_bidUpdateForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/bid/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/update"))
			.andExpect(model().attributeExists("bid"));
	}

	@Test
	@Order(4)
	@WithMockUser(username="user", roles={"USER"})
	public void get_bidUpdateForm_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/bid/update/{id}", "9999"))
			.andExpect(status().isInternalServerError());
	}
	
	@Test
	@Order(5)
	@WithMockUser(username="user", roles={"USER"})
	public void postBid_fromBidAddForm_shouldSuccessAndRedirectToBidListPage() throws Exception {
		mockMvc.perform(post("/bid/validate")
				.flashAttr("bid", bid)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}
	
	@Test
	@Order(6)
	@WithMockUser(username="user", roles={"USER"})
	public void postBid_fromBidAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/bid/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/add"))
			.andExpect(model().attributeExists("bid"));
	}
	
	@Test
	@Order(7)
	@WithMockUser(username="user", roles={"USER"})
	public void postBid_fromBidUpdateForm_shouldSuccessAndRedirectToBidListPage() throws Exception {
		mockMvc.perform(post("/bid/update/{id}", "1")
				.flashAttr("bid", bid)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}
	
	@Test
	@Order(8)
	@WithMockUser(username="user", roles={"USER"})
	public void postBid_fromBidUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/bid/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("bid/update"))
			.andExpect(model().attributeExists("bid"));
	}
	
	@Test
	@Order(9)
	@WithMockUser(username="user", roles={"USER"})
	public void deleteBid_fromBidListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/bid/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/bid/list"));
	}
	
	@Test
	@Order(10)
	@WithMockUser(username="user", roles={"USER"})
	public void deleteBid_fromBidListPage_shouldThrowInternalServerError() throws Exception {
		mockMvc.perform(get("/bid/delete/{id}", "9999")
				.with(csrf()))
			.andExpect(status().isInternalServerError());
	}
}