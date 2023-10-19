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
		if(iBidRepository.findById(id).isPresent()) {
			return iBidRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public Bid addOrUpdateBid(Bid bid) {
		return iBidRepository.save(bid);
	}

	@Override
	public Integer deleteBidById(Integer id) {
		if(iBidRepository.findById(id).isPresent()) {
			iBidRepository.delete(iBidRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}