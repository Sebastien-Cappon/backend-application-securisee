package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.repository.IRatingRepository;

@Service
public class RatingService implements IRatingService {

	@Autowired
	IRatingRepository iRatingRepository;

	@Override
	public List<Rating> getRatingList() {
		return iRatingRepository.findAll();
	}

	@Override
	public Rating getRatingById(Integer id) {
		Rating rating = iRatingRepository.findById(id).get();
		
		if(rating == null) {
			throw new IllegalArgumentException("Invalid Rating Id:" + id);
		}
		
		return rating;
	}

	@Override
	public Rating addOrUpdateRating(Rating rating) {
		return iRatingRepository.save(rating);
	}

	@Override
	public void deleteRatingById(Integer id) {
		Rating rating = iRatingRepository.findById(id).get();
		
		if(rating == null) {
			throw new IllegalArgumentException("Invalid Rating Id:" + id);
		}
		
		iRatingRepository.delete(rating);
	}
}