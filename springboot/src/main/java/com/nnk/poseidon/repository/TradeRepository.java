package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.Trade;


public interface TradeRepository extends JpaRepository<Trade, Integer> {

}