package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Trade;
import com.nnk.poseidon.repository.ITradeRepository;

@Service
public class TradeService implements ITradeService {

	@Autowired
	ITradeRepository iTradeRepository;

	@Override
	public List<Trade> getTradeList() {
		return iTradeRepository.findAll();
	}

	@Override
	public Trade getTradeById(Integer id) {
		Trade trade = iTradeRepository.findById(id).get();
		
		if(trade == null) {
			throw new IllegalArgumentException("Invalid Trade Id:" + id);
		}
		
		return trade;
	}

	@Override
	public Trade addOrUpdateTrade(Trade trade) {
		return iTradeRepository.save(trade);
	}

	@Override
	public void deleteTradeById(Integer id) {
		Trade trade = iTradeRepository.findById(id).get();
		
		if(trade == null) {
			throw new IllegalArgumentException("Invalid Trade Id:" + id);
		}
		
		iTradeRepository.delete(trade);
	}
}