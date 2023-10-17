package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.Bid;

public interface IBidRepository extends JpaRepository<Bid, Integer> {

}