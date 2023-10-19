package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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
	
	@GetMapping("/curvePoint/list")
	public String get_curvePointListPage(Model model) {
		model.addAttribute("curvePoints", iCurvePointService.getCurvePointList());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String get_curvePointAddForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String get_curvePointUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = iCurvePointService.getCurvePointById(id);
		
		if(curvePoint == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			model.addAttribute("curvePoint", curvePoint);
			return "curvePoint/update";
		}
	}
	
	@PostMapping("/curvePoint/validate")
	public String postCurvePoint_fromCurvePointAddForm(@Valid CurvePoint curvePoint, BindingResult result) {
		if (!result.hasErrors()) {
			iCurvePointService.addOrUpdateCurvePoint(curvePoint);
			return "redirect:/curvePoint/list";
		}

		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String postCurvePoint_fromCurvePointUpdateForm(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result) {
		if (!result.hasErrors()) {
			curvePoint.setId(id);
			iCurvePointService.addOrUpdateCurvePoint(curvePoint);

			return "redirect:/curvePoint/list";
		}
		
		return "curvePoint/update";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteCurvePoint_fromCurvePointListPage(@PathVariable("id") Integer id) {
		Integer deletedCurvePoint = iCurvePointService.deleteCurvePointById(id);;
		
		if(deletedCurvePoint == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return "redirect:/curvePoint/list";
		}
	}
}