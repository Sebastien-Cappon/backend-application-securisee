package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.RuleName;

public interface IRuleNameService {

	public String home(Model model);
	public String addRuleForm(RuleName bid);
	public String showUpdateForm(Integer id, Model model);
	
	public String validate(RuleName ruleName, BindingResult result, Model model);
	public String updateRuleName(Integer id, RuleName ruleName, BindingResult result, Model model);
	
	public String deleteRuleName(Integer id, Model model);
}