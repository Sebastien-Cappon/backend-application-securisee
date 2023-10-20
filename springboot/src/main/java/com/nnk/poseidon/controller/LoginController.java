package com.nnk.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * A class that receives the request default URL request. As the methods return
 * views, <code>@Controller</code> is used instead of
 * <code>@RestController</code>. Indeed, the response doesn't have to be
 * serialized via <code>@ResponseBody</code>
 * 
 * @author [NPC]Stéphanie, Sébastien Cappon
 * @version 1.1
 */
@Controller
@RequestMapping("app")
public class LoginController {

	/**
	 * A <code>GetMapping</code> method on the <code>/login</code> URI. It only call
	 * the Spring Security default login page.
	 * 
	 * @frontCall Logging page.
	 * 
	 * @return A <code>ModelAndView</code>.
	 */
	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("login");

		return modelAndView;
	}
}