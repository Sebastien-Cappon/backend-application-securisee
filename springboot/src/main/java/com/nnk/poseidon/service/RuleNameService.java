package com.nnk.poseidon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.repository.IRuleNameRepository;

public class RuleNameService implements IRuleNameService {

	@Autowired
	IRuleNameRepository iRuleNameRepository;

	@Override
	public String home(Model model) {
		// TODO: find all RuleName, add to model
		return "ruleName/list";
	}

	@Override
	public String addRuleForm(RuleName bid) {
		return "ruleName/add";
	}

	@Override
	public String showUpdateForm(Integer id, Model model) {
		// TODO: get RuleName by Id and to model then show to the form
		return "ruleName/update";
	}

	@Override
	public String validate(RuleName ruleName, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return RuleName list
		return "ruleName/add";
	}

	@Override
	public String updateRuleName(Integer id, RuleName ruleName, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update RuleName and
		// return RuleName list
		return "redirect:/ruleName/list";
	}

	@Override
	public String deleteRuleName(Integer id, Model model) {
		// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
		return "redirect:/ruleName/list";
	}
}