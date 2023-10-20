package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.Rating;

/**
 * <code>RatingService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IRatingService {

	public List<Rating> getRatingList();
	public Rating getRatingById(Integer id);

	public Rating addOrUpdateRating(Rating rating);

	public Integer deleteRatingById(Integer id);
}