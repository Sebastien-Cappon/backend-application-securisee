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

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.service.ICurvePointService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CurvePointEnpointsIT {

	private static final Logger logger = LoggerFactory.getLogger(BidEndpointsIT.class);
	private CurvePoint curvePoint = DomainObjectBuilders.createCurvePoint(null, 1, null, 1d, 2d, null);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ICurvePointService iCurvePointService;

	@BeforeAll
	public void setup() {
		logger.info("curvepoint table in the H2 test database filled.");

		for (int i = 0; i < 3; i++) {
			iCurvePointService.addOrUpdateCurvePoint(curvePoint);
		}
	}

	@AfterAll
	public void teardDown() {
		logger.info("H2 test database closed.");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_curvePointListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/curvePoint/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/list"))
			.andExpect(model().attributeExists("curvePoints"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_curvePointAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/curvePoint/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"))
			.andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_curvePointUpdateForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/curvePoint/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"))
			.andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	@Order(4)
	@WithMockUser(username = "user", roles = { "USER" })
	public void get_curvePointUpdateForm_shouldThrowNoContent() throws Exception {
		mockMvc.perform(get("/curvePoint/update/{id}", "9999"))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postCurvePoint_fromCurvePointAddForm_shouldSuccessAndRedirectToCurvePointListPage() throws Exception {
		mockMvc.perform(post("/curvePoint/validate")
				.flashAttr("curvePoint", curvePoint)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/curvePoint/list"));
	}

	@Test
	@Order(6)
	@WithMockUser(username = "user", roles = { "USER" })
	public void postCurvePoint_fromCurvePointAddForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(post("/curvePoint/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"))
			.andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putCurvePoint_fromCurvePointUpdateForm_shouldSuccessAndRedirectToCurvePointListPage()
			throws Exception {
		mockMvc.perform(put("/curvePoint/update/{id}", "1")
				.flashAttr("curvePoint", curvePoint)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/curvePoint/list"));
	}

	@Test
	@Order(8)
	@WithMockUser(username = "user", roles = { "USER" })
	public void putCurvePoint_fromCurvePointUpdateForm_shouldFailAndReturnOk() throws Exception {
		mockMvc.perform(put("/curvePoint/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"))
			.andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	@Order(9)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteCurvePoint_fromCurvePointListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/curvePoint/delete/{id}", "1")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/curvePoint/list"));
	}

	@Test
	@Order(10)
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteCurvePoint_fromCurvePointListPage_shouldThrowNoContent() throws Exception {
		mockMvc.perform(delete("/curvePoint/delete/{id}", "9999")
				.with(csrf()))
			.andExpect(status().isNoContent());
	}
}