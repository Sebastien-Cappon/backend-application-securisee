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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.service.IRuleNameService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the RuleNames. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class RuleNameController {

	@Autowired
	IRuleNameService iRuleNameService;

	/**
	 * A method which retrieves the active user and registers it in the
	 * <code>remoteUser</code> attribute of the current <code>Model</code> in order
	 * to display the name of the connected user at the top of the page.
	 * 
	 * @return An <code>Object</code>.
	 */
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/ruleName/list</code> URI with
	 * a <code>Model</code> as parameter. It calls the <code>IRuleNameService</code>
	 * method <code>getRuleNameList()</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page.
	 * 
	 * @frontCall RuleName list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/ruleName/list")
	public String get_ruleNameListPage(Model model) {
		model.addAttribute("ruleNames", iRuleNameService.getRuleNameList());
		return "ruleName/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/ruleName/add</code> URI. It
	 * only returns the URI of a template page.
	 * 
	 * @frontCall RuleName add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/ruleName/add")
	public String get_ruleNameAddForm(RuleName ruleName) {
		return "ruleName/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/ruleName/update</code> URI
	 * with a ruleName id as <code>PathVariable</code> and a <code>Model</code> as
	 * parameter. It calls the <code>IRuleNameService</code> method
	 * <code>getRuleNameById(int id)</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page of the current
	 * ruleName.
	 * 
	 * @frontCall RuleName update form page.
	 * 
	 * @errorThrown <code>NO_CONTENT</code> if the ruleName concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/ruleName/update/{id}")
	public String get_ruleNameUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = iRuleNameService.getRuleNameById(id);

		if (ruleName == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			model.addAttribute("ruleName", ruleName);
			return "ruleName/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/ruleName/validate</code> URI
	 * with a valid <code>RuleName</code> and a <code>Model</code> as parameter. It
	 * calls the <code>IRuleNameService</code> method
	 * <code>addOrUpdateRuleName(RuleName ruleName)</code> in order to save the new
	 * ruleName in the database. Then, it redirects to the ruleName list page.
	 * 
	 * @frontCall RuleName add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/ruleName/validate")
	public String postRuleName_fromRuleNameAddForm(@Valid RuleName ruleName, BindingResult result) {
		if (!result.hasErrors()) {
			iRuleNameService.addOrUpdateRuleName(ruleName);
			return "redirect:/ruleName/list";
		}

		return "ruleName/add";
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/ruleName/update</code> URI
	 * with a ruleName id as <code>PathVariable</code>, a valid
	 * <code>RuleName</code> and a <code>Model</code> as parameter. It calls the
	 * <code>IRuleNameService</code> method
	 * <code>addOrUpdateRuleName(RuleName ruleName)</code> in order to update, in
	 * the database, the ruleName whose id is passed in parameter. Then, it
	 * redirects to the ruleName list page.
	 * 
	 * @frontCall RuleName update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/ruleName/update/{id}")
	public String putRuleName_fromRuleNameUpdateForm(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result) {
		if (!result.hasErrors()) {
			ruleName.setId(id);
			iRuleNameService.addOrUpdateRuleName(ruleName);

			return "redirect:/ruleName/list";
		}

		return "ruleName/update";
	}

	/**
	 * A <code>RequestMapping</code> method on the <code>/ruleName/delete</code> URI
	 * with a ruleName id as <code>PathVariable</code>. It calls the
	 * <code>IRuleNameService</code> method <code>deleteRuleNameById(int id)</code>.
	 * Then, it redirects to the ruleName list page.
	 * 
	 * @frontCall RuleName list page.
	 * 
	 * @errorThrown <code>NO_CONTENT</code> if the ruleName concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/ruleName/delete/{id}")
	public String deleteRuleName_fromRuleNameListPage(@PathVariable("id") Integer id) {
		Integer deletedRuleName = iRuleNameService.deleteRuleNameById(id);

		if (deletedRuleName == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/ruleName/list";
		}
	}
}