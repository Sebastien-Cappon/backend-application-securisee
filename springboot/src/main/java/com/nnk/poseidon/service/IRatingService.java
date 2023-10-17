package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Rating;

public interface IRatingService {

	public List<Rating> getRatingList();
	public Rating getRatingById(Integer id);
	
	public Rating addOrUpdateRating(Rating rating);
	
	public void deleteRatingById(Integer id);
}