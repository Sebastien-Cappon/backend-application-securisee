package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Bid;

public interface IBidService {

	public List<Bid> getBidList();
	public Bid getBidById(Integer id);
	
	public Bid addOrUpdateBid(Bid bid);
	
	public void deleteBidById(Integer id);
}