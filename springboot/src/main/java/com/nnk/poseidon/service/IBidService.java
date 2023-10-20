package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Bid;

/**
 * <code>BidService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IBidService {

	public List<Bid> getBidList();
	public Bid getBidById(Integer id);

	public Bid addOrUpdateBid(Bid bid);

	public Integer deleteBidById(Integer id);
}