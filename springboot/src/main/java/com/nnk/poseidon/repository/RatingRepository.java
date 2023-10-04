package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}