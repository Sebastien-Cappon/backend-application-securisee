package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Trade;

public interface ITradeService {

	public List<Trade> getTradeList();
	public Trade getTradeById(Integer id);
	
	public Trade addOrUpdateTrade(Trade trade);
	
	public void deleteTradeById(Integer id);
}