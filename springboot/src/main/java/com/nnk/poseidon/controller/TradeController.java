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

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.service.ITradeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Trades. As the methods return views,
 * <code>@Controller</code> is used instead of <code>@RestController</code>.
 * Indeed, the response doesn't have to be serialized via
 * <code>@ResponseBody</code>
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Controller
public class TradeController {

	@Autowired
	ITradeService iTradeService;

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
	 * A <code>GetMapping</code> method on the <code>/trade/list</code> URI with a
	 * <code>Model</code> as parameter. It calls the <code>ITradeService</code>
	 * method <code>getTradeList()</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page.
	 * 
	 * @frontCall Trade list page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/trade/list")
	public String get_tradeListPage(Model model) {
		model.addAttribute("trades", iTradeService.getTradeList());
		return "trade/list";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/trade/add</code> URI. It only
	 * returns the URI of a template page.
	 * 
	 * @frontCall Trade add form page.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/trade/add")
	public String get_tradeAddForm(Trade trade) {
		return "trade/add";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/trade/update</code> URI with a
	 * trade id as <code>PathVariable</code> and a <code>Model</code> as parameter.
	 * It calls the <code>ITradeService</code> method
	 * <code>getTradeById(int id)</code> in order to get <code>Model</code>
	 * attribute, before returning the URI of a template page of the current trade.
	 * 
	 * @frontCall Trade update form page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the trade concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@GetMapping("/trade/update/{id}")
	public String get_tradeUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = iTradeService.getTradeById(id);

		if (trade == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			model.addAttribute("trade", trade);
			return "trade/update";
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/trade/validate</code> URI
	 * with a valid <code>Trade</code> and a <code>Model</code> as parameter. It
	 * calls the <code>ITradeService</code> method
	 * <code>addOrUpdateTrade(Trade trade)</code> in order to save the new trade in
	 * the database. Then, it redirects to the trade list page.
	 * 
	 * @frontCall Trade add form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PostMapping("/trade/validate")
	public String postTrade_fromTradeAddForm(@Valid Trade trade, BindingResult result) {
		if (!result.hasErrors()) {
			iTradeService.addOrUpdateTrade(trade);
			return "redirect:/trade/list";
		}

		return "trade/add";
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/trade/update</code> URI with
	 * a trade id as <code>PathVariable</code>, a valid <code>Trade</code> and a
	 * <code>Model</code> as parameter. It calls the <code>ITradeService</code>
	 * method <code>addOrUpdateTrade(Trade trade)</code> in order to update, in the
	 * database, the trade whose id is passed in parameter. Then, it redirects to
	 * the trade list page.
	 * 
	 * @frontCall Trade update form.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@PutMapping("/trade/update/{id}")
	public String putTrade_fromTradeUpdateForm(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result) {
		if (!result.hasErrors()) {
			trade.setId(id);
			iTradeService.addOrUpdateTrade(trade);

			return "redirect:/trade/list";
		}

		return "trade/update";
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/trade/delete</code> URI with a
	 * trade id as <code>PathVariable</code>. It calls the
	 * <code>ITradeService</code> method <code>deleteTradeById(int id)</code>. Then,
	 * it redirects to the trade list page.
	 * 
	 * @frontCall Trade list page.
	 * 
	 * @throws <code>INTERNAL_SERVER_ERROR</code> if the trade concerned doesn't
	 * exist.
	 * 
	 * @return A template view URI as <code>String</code>.
	 */
	@RequestMapping("/trade/delete/{id}")
	public String deleteTrade_fromTradeListPage(@PathVariable("id") Integer id) {
		Integer deletedTrade = iTradeService.deleteTradeById(id);

		if (deletedTrade == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			return "redirect:/trade/list";
		}
	}
}