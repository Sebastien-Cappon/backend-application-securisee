package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.CurvePoint;

public interface ICurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}