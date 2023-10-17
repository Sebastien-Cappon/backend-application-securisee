package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.repository.IBidRepository;

@Service
public class BidService implements IBidService {

	@Autowired
	IBidRepository iBidRepository;

	@Override
	public List<Bid> getBidList() {
		return iBidRepository.findAll();
	}

	@Override
	public Bid getBidById(Integer id) {
		Bid bid = iBidRepository.findById(id).get();
		
		if(bid == null) {
			throw new IllegalArgumentException("Invalid Bid Id:" + id);
		}
		
		return bid;
	}

	@Override
	public Bid addOrUpdateBid(Bid bid) {
		return iBidRepository.save(bid);
	}

	@Override
	public void deleteBidById(Integer id) {
		Bid bid = iBidRepository.findById(id).get();
		
		if(bid == null) {
			throw new IllegalArgumentException("Invalid Bid Id:" + id);
		}
		
		iBidRepository.delete(bid);
	}
}