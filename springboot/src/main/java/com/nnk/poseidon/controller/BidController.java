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

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.service.IBidService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Bids. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class BidController {

	@Autowired
	IBidService iBidService;

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
	 * A <code>GetMapping</code> method on the <code>/bid/list</code> URI with a
	 * <code>Model</code> as parameter. It calls the <code>IBidService</code> method
	 * <code>getBidList()</code> in order to get <code>Model</code> attribute,
	 * before returning the URI of a template page.
	 * 
	 * @frontCall Bid list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/bid/list")
	public String get_bidListPage(Model model) {
		model.addAttribute("bids", iBidService.getBidList());
		return "bid/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/bid/add</code> URI. It only
	 * returns the URI of a template page.
	 * 
	 * @frontCall Bid add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/bid/add")
	public String get_bidAddForm(Bid bid) {
		return "bid/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/bid/update</code> URI with a
	 * bid id as <code>PathVariable</code> and a <code>Model</code> as parameter. It
	 * calls the <code>IBidService</code> method <code>getBidById(int id)</code> in
	 * order to get <code>Model</code> attribute, before returning the URI of a
	 * template page of the current bid.
	 * 
	 * @frontCall Bid update form page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the bid concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/bid/update/{id}")
	public String get_bidUpdateForm(@PathVariable("id") Integer id, Model model) {
		Bid bid = iBidService.getBidById(id);

		if (bid == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			model.addAttribute("bid", bid);
			return "bid/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/bid/validate</code> URI with
	 * a valid <code>Bid</code> and a <code>Model</code> as parameter. It calls the
	 * <code>IBidService</code> method <code>addOrUpdateBid(Bid bid)</code> in order
	 * to save the new bid in the database. Then, it redirects to the bid list page.
	 * 
	 * @frontCall Bid add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/bid/validate")
	public String postBid_fromBidAddForm(@Valid Bid bid, BindingResult result) {
		if (!result.hasErrors()) {
			iBidService.addOrUpdateBid(bid);
			return "redirect:/bid/list";
		}

		return "bid/add";
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/bid/update</code> URI with a
	 * bid id as <code>PathVariable</code>, a valid <code>Bid</code> and a
	 * <code>Model</code> as parameter. It calls the <code>IBidService</code> method
	 * <code>addOrUpdateBid(Bid bid)</code> in order to update, in the database, the
	 * bid whose id is passed in parameter. Then, it redirects to the bid list page.
	 * 
	 * @frontCall Bid update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/bid/update/{id}")
	public String putBid_fromBidUpdateForm(@PathVariable("id") Integer id, @Valid Bid bid, BindingResult result) {
		if (!result.hasErrors()) {
			bid.setId(id);
			iBidService.addOrUpdateBid(bid);

			return "redirect:/bid/list";
		}

		return "bid/update";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/bid/delete</code> URI with a
	 * bid id as <code>PathVariable</code>. It calls the <code>IBidService</code>
	 * method <code>deleteBidById(int id)</code>. Then, it redirects to the bid list
	 * page.
	 * 
	 * @frontCall Bid list page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the bid concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/bid/delete/{id}")
	public String deleteBid_fromBidListPage(@PathVariable("id") Integer id) {
		Integer deletedBid = iBidService.deleteBidById(id);

		if (deletedBid == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/bid/list";
		}
	}
}