package com.nnk.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.Bid;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal queries relative to <code>Bid</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IBidRepository extends JpaRepository<Bid, Integer> {

}