package com.nnk.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A class that receives the request default URL request. As the methods return
 * views, <code>@Controller</code> is used instead of
 * <code>@RestController</code>. Indeed, the response doesn't have to be
 * serialized via <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class HomeController {

	/**
	 * A <code>GetMapping</code> method on the default URL with a <code>Model</code>
	 * as parameter. It only returns the URI of a template page.
	 * 
	 * @frontCall Landing page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
}