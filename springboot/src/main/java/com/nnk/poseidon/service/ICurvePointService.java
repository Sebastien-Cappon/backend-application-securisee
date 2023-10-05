package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.CurvePoint;

public interface ICurvePointService {

	public String home(Model model);
	public String addBidForm(CurvePoint bid);
	public String showUpdateForm(Integer id, Model model);

	public String validate(CurvePoint curvePoint, BindingResult result, Model model);
	public String updateBid(Integer id, CurvePoint curvePoint, BindingResult result, Model model);

	public String deleteBid(Integer id, Model model);
}