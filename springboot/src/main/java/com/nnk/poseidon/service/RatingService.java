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
		if(iRatingRepository.findById(id).isPresent()) {
			return iRatingRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public Rating addOrUpdateRating(Rating rating) {
		return iRatingRepository.save(rating);
	}

	@Override
	public Integer deleteRatingById(Integer id) {
		if(iRatingRepository.findById(id).isPresent()) {
			iRatingRepository.delete(iRatingRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}