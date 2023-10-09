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

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.service.IRuleNameService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RuleNameController {

	@Autowired
	IRuleNameService iRuleNameService;

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
	    return httpServletRequest.getRemoteUser();
	}
	
	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		return iRuleNameService.home(model);
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		return iRuleNameService.addRuleForm(bid);
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return iRuleNameService.showUpdateForm(id, model);
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		return iRuleNameService.validate(ruleName, result, model);
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
		return iRuleNameService.updateRuleName(id, ruleName, result, model);
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		return iRuleNameService.deleteRuleName(id, model);
	}
}