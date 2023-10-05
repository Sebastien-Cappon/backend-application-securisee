package com.nnk.poseidon.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.nnk.poseidon.domain.RuleName;

import jakarta.validation.Valid;

public interface IRuleNameService {

	public String home(Model model);
	public String addRuleForm(RuleName bid);
	public String showUpdateForm(@PathVariable("id") Integer id, Model model);
	
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model);
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model);
	
	public String deleteRuleName(@PathVariable("id") Integer id, Model model);
}