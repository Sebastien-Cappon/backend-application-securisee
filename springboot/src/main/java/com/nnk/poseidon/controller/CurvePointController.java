package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.service.ICurvePointService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CurvePointController {

	@Autowired
	ICurvePointService iCurvePointService;

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest request) {
	    return request.getRemoteUser();
	}
	
	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		return iCurvePointService.home(model);
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid) {
		return iCurvePointService.addBidForm(bid);
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return iCurvePointService.showUpdateForm(id, model);
	}
	
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		return iCurvePointService.validate(curvePoint, result, model);
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
		return iCurvePointService.updateBid(id, curvePoint, result, model);
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		return iCurvePointService.deleteBid(id, model);
	}
}