package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.Rating;
import com.nnk.poseidon.repository.IRatingRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Rating</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class RatingService implements IRatingService {

	@Autowired
	IRatingRepository iRatingRepository;

	/**
	 * A <code>GET</code> method that returns a <code>Rating</code> list after
	 * calling the <code>findAll()</code> derived query from
	 * <code>IRatingRepository</code>.
	 * 
	 * @return A <code>Rating</code> list.
	 */
	@Override
	public List<Rating> getRatingList() {
		return iRatingRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>Rating</code> whose id is
	 * passed as parameter after calling the <code>findById()</code> derived query
	 * from <code>IRatingRepository</code>.
	 * 
	 * @return A <code>Rating</code> or <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public Rating getRatingById(Integer id) {
		if (iRatingRepository.findById(id).isPresent()) {
			return iRatingRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>Rating</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>IRatingRepository</code>.
	 * 
	 * @return A <code>Rating</code>.
	 */
	@Override
	public Rating addOrUpdateRating(Rating rating) {
		return iRatingRepository.save(rating);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>IRatingRepository</code> if the rating whose id is passed as
	 * parameter exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *              <code>Rating</code> doesn't exists in the database.
	 */
	@Override
	public Integer deleteRatingById(Integer id) {
		if (iRatingRepository.findById(id).isPresent()) {
			iRatingRepository.delete(iRatingRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}