package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.RuleName;

public interface IRuleNameRepository extends JpaRepository<RuleName, Integer> {

}