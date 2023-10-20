package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Trade;

/**
 * <code>TradeService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface ITradeService {

	public List<Trade> getTradeList();
	public Trade getTradeById(Integer id);

	public Trade addOrUpdateTrade(Trade trade);

	public Integer deleteTradeById(Integer id);
}