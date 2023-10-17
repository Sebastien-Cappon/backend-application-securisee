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

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.service.CurvePointService;
import com.nnk.poseidon.util.DomainObjectBuilders;

@WebMvcTest(controllers = CurvePointController.class)
@TestMethodOrder(OrderAnnotation.class)
public class CurvePointControllerTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Timestamp timeStampTest = Timestamp.valueOf(LocalDate.parse("2023-10-02", dateTimeFormatter).atStartOfDay());
	private CurvePoint curvePoint = DomainObjectBuilders.createCurvePoint(1, 1, timeStampTest, 2d, 3d, timeStampTest);

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurvePointService curvePointService;

	@Test
	@Order(1)
	@WithMockUser(username="user", roles={"USER"})
	public void get_curvePointListPage_shouldReturnOk() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>(Arrays.asList(curvePoint, curvePoint));
		
		when(curvePointService.getCurvePointList())
			.thenReturn(curvePointList);
		
		mockMvc.perform(get("/curvePoint/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/list"))
			.andExpect(model().attributeExists("curvePoints"));
	}
	
	@Test
	@Order(2)
	@WithMockUser(username="user", roles={"USER"})
	public void get_curvePointAddForm_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/curvePoint/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"))
			.andExpect(model().attributeExists("curvePoint"));
	}
	
	@Test
	@Order(3)
	@WithMockUser(username="user", roles={"USER"})
	public void get_curvePointUpdateForm_shouldReturnOk() throws Exception {
		when(curvePointService.getCurvePointById(any(Integer.class)))
			.thenReturn(curvePoint);
		
		mockMvc.perform(get("/curvePoint/update/{id}", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"))
			.andExpect(model().attributeExists("curvePoint"));
	}
	
	@Test
	@Order(4)
	@WithMockUser(username="user", roles={"USER"})
	public void postCurvePoint_fromCurvePointAddForm_shouldSuccessAndRedirectToCurvePointListPage() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>(Arrays.asList(curvePoint, curvePoint));

		when(curvePointService.getCurvePointList())
			.thenReturn(curvePointList);
		when(curvePointService.addOrUpdateCurvePoint(any(CurvePoint.class)))
			.thenReturn(curvePoint);
		
		mockMvc.perform(post("/curvePoint/validate")
				.flashAttr("curvePoint", curvePoint)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/curvePoint/list"));
	}
	
	@Test
	@Order(5)
	@WithMockUser(username="user", roles={"USER"})
	public void postCurvePoint_fromCurvePointAddForm_shouldFailAndReturnOk() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>(Arrays.asList(curvePoint, curvePoint));
		
		when(curvePointService.getCurvePointList())
			.thenReturn(curvePointList);
		when(curvePointService.addOrUpdateCurvePoint(any(CurvePoint.class)))
			.thenReturn(curvePoint);
		
		mockMvc.perform(post("/curvePoint/validate")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"))
			.andExpect(model().attributeExists("curvePoint"));
	}
	
	@Test
	@Order(6)
	@WithMockUser(username="user", roles={"USER"})
	public void postCurvePoint_fromCurvePointUpdateForm_shouldSuccessAndRedirectToCurvePointListPage() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>(Arrays.asList(curvePoint, curvePoint));

		when(curvePointService.getCurvePointList())
			.thenReturn(curvePointList);
		when(curvePointService.addOrUpdateCurvePoint(any(CurvePoint.class)))
			.thenReturn(curvePoint);
		
		mockMvc.perform(post("/curvePoint/update/{id}", "1")
				.flashAttr("curvePoint", curvePoint)
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/curvePoint/list"));
	}
	
	@Test
	@Order(7)
	@WithMockUser(username="user", roles={"USER"})
	public void postCurvePoint_fromCurvePointUpdateForm_shouldFailAndReturnOk() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>(Arrays.asList(curvePoint, curvePoint));

		when(curvePointService.getCurvePointList())
			.thenReturn(curvePointList);
		when(curvePointService.addOrUpdateCurvePoint(any(CurvePoint.class)))
			.thenReturn(curvePoint);
		
		mockMvc.perform(post("/curvePoint/update/{id}", "1")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"))
			.andExpect(model().attributeExists("curvePoint"));
	}
	
	@Test
	@Order(8)
	@WithMockUser(username="user", roles={"USER"})
	public void deleteCurvePoint_fromCurvePointListPage_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/curvePoint/delete/{id}", "1")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location", "/curvePoint/list"));
	}
}
