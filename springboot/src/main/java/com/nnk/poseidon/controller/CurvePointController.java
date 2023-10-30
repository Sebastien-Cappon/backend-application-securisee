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

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.service.ICurvePointService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the CurvePoints. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class CurvePointController {

	@Autowired
	ICurvePointService iCurvePointService;

	/**
	 * A method which retrieves the active user and registers it in the
	 * <code>remoteUser</code> attribute of the current <code>Model</code> in order
	 * to display the name of the connected user at the top of the page.
	 * 
	 * @return An <code>Object</code>.
	 */
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest request) {
		return request.getRemoteUser();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/curvePoint/list</code> URI
	 * with a <code>Model</code> as parameter. It calls the
	 * <code>ICurvePointService</code> method <code>getCurvePointList()</code> in
	 * order to get <code>Model</code> attribute, before returning the URI of a
	 * template page.
	 * 
	 * @frontCall CurvePoint list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/curvePoint/list")
	public String get_curvePointListPage(Model model) {
		model.addAttribute("curvePoints", iCurvePointService.getCurvePointList());
		return "curvePoint/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/curvePoint/add</code> URI. It
	 * only returns the URI of a template page.
	 * 
	 * @frontCall CurvePoint add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/curvePoint/add")
	public String get_curvePointAddForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/curvePoint/update</code> URI
	 * with a curvePoint id as <code>PathVariable</code> and a <code>Model</code> as
	 * parameter. It calls the <code>ICurvePointService</code> method
	 * <code>getCurvePointById(int id)</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page of the current
	 * curvePoint.
	 * 
	 * @frontCall CurvePoint update form page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the curvePoint concerned
	 * doesn't exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/curvePoint/update/{id}")
	public String get_curvePointUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = iCurvePointService.getCurvePointById(id);

		if (curvePoint == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			model.addAttribute("curvePoint", curvePoint);
			return "curvePoint/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/curvePoint/validate</code>
	 * URI with a valid <code>CurvePoint</code> and a <code>Model</code> as
	 * parameter. It calls the <code>ICurvePointService</code> method
	 * <code>addOrUpdateCurvePoint(CurvePoint curvePoint)</code> in order to save
	 * the new curvePoint in the database. Then, it redirects to the curvePoint list
	 * page.
	 * 
	 * @frontCall CurvePoint add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/curvePoint/validate")
	public String postCurvePoint_fromCurvePointAddForm(@Valid CurvePoint curvePoint, BindingResult result) {
		if (!result.hasErrors()) {
			iCurvePointService.addOrUpdateCurvePoint(curvePoint);
			return "redirect:/curvePoint/list";
		}

		return "curvePoint/add";
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/curvePoint/update</code> URI
	 * with a curvePoint id as <code>PathVariable</code>, a valid
	 * <code>CurvePoint</code> and a <code>Model</code> as parameter. It calls the
	 * <code>ICurvePointService</code> method
	 * <code>addOrUpdateCurvePoint(CurvePoint curvePoint)</code> in order to update,
	 * in the database, the curvePoint whose id is passed in parameter. Then, it
	 * redirects to the curvePoint list page.
	 * 
	 * @frontCall CurvePoint update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/curvePoint/update/{id}")
	public String putCurvePoint_fromCurvePointUpdateForm(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result) {
		if (!result.hasErrors()) {
			curvePoint.setId(id);
			iCurvePointService.addOrUpdateCurvePoint(curvePoint);

			return "redirect:/curvePoint/list";
		}

		return "curvePoint/update";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/curvePoint/delete</code> URI
	 * with a curvePoint id as <code>PathVariable</code>. It calls the
	 * <code>ICurvePointService</code> method
	 * <code>deleteCurvePointById(int id)</code>. Then, it redirects to the
	 * curvePoint list page.
	 * 
	 * @frontCall CurvePoint list page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the curvePoint concerned
	 * doesn't exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/curvePoint/delete/{id}")
	public String deleteCurvePoint_fromCurvePointListPage(@PathVariable("id") Integer id) {
		Integer deletedCurvePoint = iCurvePointService.deleteCurvePointById(id);

		if (deletedCurvePoint == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/curvePoint/list";
		}
	}
}