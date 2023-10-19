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
		if(iTradeRepository.findById(id).isPresent()) {
			return iTradeRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public Trade addOrUpdateTrade(Trade trade) {
		return iTradeRepository.save(trade);
	}

	@Override
	public Integer deleteTradeById(Integer id) {
		if(iTradeRepository.findById(id).isPresent()) {
			iTradeRepository.delete(iTradeRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}