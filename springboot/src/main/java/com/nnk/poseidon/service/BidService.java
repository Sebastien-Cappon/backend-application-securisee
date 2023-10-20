package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.repository.IBidRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Bid</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class BidService implements IBidService {

	@Autowired
	IBidRepository iBidRepository;

	/**
	 * A <code>GET</code> method that returns a <code>Bid</code> list after calling
	 * the <code>findAll()</code> derived query from <code>IBidRepository</code>.
	 * 
	 * @return A <code>Bid</code> list.
	 */
	@Override
	public List<Bid> getBidList() {
		return iBidRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>Bid</code> whose id is passed
	 * as parameter after calling the <code>findById()</code> derived query from
	 * <code>IBidRepository</code>.
	 * 
	 * @return A <code>Bid</code> or <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public Bid getBidById(Integer id) {
		if (iBidRepository.findById(id).isPresent()) {
			return iBidRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>Bid</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>IBidRepository</code>.
	 * 
	 * @return A <code>Bid</code>.
	 */
	@Override
	public Bid addOrUpdateBid(Bid bid) {
		return iBidRepository.save(bid);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>IBidRepository</code> if the bid whose id is passed as parameter
	 * exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>Bid</code>
	 *              doesn't exists in the database.
	 */
	@Override
	public Integer deleteBidById(Integer id) {
		if (iBidRepository.findById(id).isPresent()) {
			iBidRepository.delete(iBidRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}