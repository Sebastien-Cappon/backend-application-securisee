package com.nnk.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.nnk.poseidon.domain.User;
import com.nnk.poseidon.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Users. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class UserController {

	@Autowired
	private IUserService iUserService;

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
	 * A <code>GetMapping</code> method on the <code>/user/list</code> URI with a
	 * <code>Model</code> as parameter. It calls the <code>IUserService</code>
	 * method <code>getUserList()</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page.
	 * 
	 * @frontCall User list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/user/list")
	public String get_userListPage(Model model) {
		model.addAttribute("users", iUserService.getUserList());
		return "user/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/user/add</code> URI. It only
	 * returns the URI of a template page.
	 * 
	 * @frontCall User add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/user/add")
	public String get_userAddForm(User user) {
		return "user/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/user/update</code> URI with a
	 * user id as <code>PathVariable</code> and a <code>Model</code> as parameter.
	 * It calls the <code>IUserService</code> method
	 * <code>getUserById(int id)</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page of the current user.
	 * 
	 * @frontCall User update form page.
	 * 
	 * @errorThrown <code>NO_CONTENT</code> if the user concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/user/update/{id}")
	public String get_userUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = iUserService.getUserById(id);

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			user.setPassword("");

			model.addAttribute("user", user);
			return "user/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/user/validate</code> URI with
	 * a valid <code>User</code> and a <code>Model</code> as parameter. It calls the
	 * <code>IUserService</code> method <code>addOrUpdateUser(User user)</code> in
	 * order to save the new user in the database. Then, it redirects to the user
	 * list page.
	 * 
	 * @frontCall User add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/user/validate")
	public String postUser_fromUserAddForm(@Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			iUserService.addOrUpdateUser(user);

			return "redirect:/user/list";
		}

		return "user/add";
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/user/update</code> URI with a
	 * user id as <code>PathVariable</code>, a valid <code>User</code> and a
	 * <code>Model</code> as parameter. It calls the <code>IUserService</code>
	 * method <code>addOrUpdateUser(User user)</code> in order to update, in the
	 * database, the user whose id is passed in parameter. Then, it redirects to the
	 * user list page.
	 * 
	 * @frontCall User update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/user/update/{id}")
	public String putUser_fromUserUpdateForm(@PathVariable("id") Integer id, @Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			user.setId(id);
			iUserService.addOrUpdateUser(user);

			return "redirect:/user/list";
		}

		return "user/update";
	}

	/**
	 * A <code>RequestMapping</code> method on the <code>/user/delete</code> URI with a
	 * user id as <code>PathVariable</code>. It calls the <code>IUserService</code>
	 * method <code>deleteUserById(int id)</code>. Then, it redirects to the user
	 * list page.
	 * 
	 * @frontCall User list page.
	 * 
	 * @errorThrown <code>NO_CONTENT</code> if the user concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/user/delete/{id}")
	public String deleteUser_fromUserListPage(@PathVariable("id") Integer id) {
		Integer deletedUser = iUserService.deleteUserById(id);

		if (deletedUser == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/user/list";
		}
	}
}