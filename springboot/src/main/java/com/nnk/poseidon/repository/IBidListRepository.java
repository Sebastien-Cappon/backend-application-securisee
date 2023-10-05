package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.BidList;


public interface IBidListRepository extends JpaRepository<BidList, Integer> {

}