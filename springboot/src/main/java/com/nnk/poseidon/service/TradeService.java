package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.repository.ITradeRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Trade</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class TradeService implements ITradeService {

	@Autowired
	ITradeRepository iTradeRepository;

	/**
	 * A <code>GET</code> method that returns a <code>Trade</code> list after
	 * calling the <code>findAll()</code> derived query from
	 * <code>ITradeRepository</code>.
	 * 
	 * @return A <code>Trade</code> list.
	 */
	@Override
	public List<Trade> getTradeList() {
		return iTradeRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>Trade</code> whose id is
	 * passed as parameter after calling the <code>findById()</code> derived query
	 * from <code>ITradeRepository</code>.
	 * 
	 * @return A <code>Trade</code> or <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public Trade getTradeById(Integer id) {
		if (iTradeRepository.findById(id).isPresent()) {
			return iTradeRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>Trade</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>ITradeRepository</code>.
	 * 
	 * @return A <code>Trade</code>.
	 */
	@Override
	public Trade addOrUpdateTrade(Trade trade) {
		return iTradeRepository.save(trade);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>ITradeRepository</code> if the trade whose id is passed as
	 * parameter exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *              <code>Trade</code> doesn't exists in the database.
	 */
	@Override
	public Integer deleteTradeById(Integer id) {
		if (iTradeRepository.findById(id).isPresent()) {
			iTradeRepository.delete(iTradeRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}