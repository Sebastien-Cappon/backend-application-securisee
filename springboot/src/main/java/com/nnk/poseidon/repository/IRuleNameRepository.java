package com.nnk.poseidon.repository;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal queries relative to <code>RuleName</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.poseidon.domain.RuleName;

public interface IRuleNameRepository extends JpaRepository<RuleName, Integer> {

}