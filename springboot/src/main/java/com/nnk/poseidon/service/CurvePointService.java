package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.repository.ICurvePointRepository;

@Service
public class CurvePointService implements ICurvePointService {

	@Autowired
	ICurvePointRepository iCurvePointRepository;

	@Override
	public String home(Model model) {
		// TODO: find all Curve Point, add to model
		return "curvePoint/list";
	}

	@Override
	public String addBidForm(CurvePoint bid) {
		return "curvePoint/add";
	}

	@Override
	public String showUpdateForm(Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		return "curvePoint/update";
	}

	@Override
	public String validate(CurvePoint curvePoint, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Curve list
		return "curvePoint/add";
	}
	
	@Override
	public String updateBid(Integer id, CurvePoint curvePoint, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Curve and return
		// Curve list
		return "redirect:/curvePoint/list";
	}

	@Override
	public String deleteBid(Integer id, Model model) {
		// TODO: Find Curve by Id and delete the Curve, return to Curve list
		return "redirect:/curvePoint/list";
	}
}
