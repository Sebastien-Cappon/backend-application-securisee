package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	@GetMapping("/ruleName/list")
	public String get_ruleNameListPage(Model model) {
		model.addAttribute("ruleNames", iRuleNameService.getRuleNameList());
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String get_ruleNameAddForm(RuleName ruleName) {
		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String get_ruleNameUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = iRuleNameService.getRuleNameById(id);
		
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/validate")
	public String postRuleName_fromRuleNameAddForm(@Valid RuleName ruleName, BindingResult result) {
		if (!result.hasErrors()) {
			iRuleNameService.addOrUpdateRuleName(ruleName);
			return "redirect:/ruleName/list";
		}

		return "ruleName/add";
	}

	@PostMapping("/ruleName/update/{id}")
	public String postRuleName_fromRuleNameUpdateForm(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result) {
		if (!result.hasErrors()) {
			ruleName.setId(id);
			iRuleNameService.addOrUpdateRuleName(ruleName);

			return "redirect:/ruleName/list";
		}
		
		return "ruleName/update";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName_fromRuleNameListPage(@PathVariable("id") Integer id) {
		iRuleNameService.deleteRuleNameById(id);
		return "redirect:/ruleName/list";
	}
}