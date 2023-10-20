package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.repository.IRuleNameRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>RuleName</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class RuleNameService implements IRuleNameService {

	@Autowired
	IRuleNameRepository iRuleNameRepository;

	/**
	 * A <code>GET</code> method that returns a <code>RuleName</code> list after
	 * calling the <code>findAll()</code> derived query from
	 * <code>IRuleNameRepository</code>.
	 * 
	 * @return A <code>RuleName</code> list.
	 */
	@Override
	public List<RuleName> getRuleNameList() {
		return iRuleNameRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>RuleName</code> whose id is
	 * passed as parameter after calling the <code>findById()</code> derived query
	 * from <code>IRuleNameRepository</code>.
	 * 
	 * @return A <code>RuleName</code> or <code>null</code> if it doesn't exist in
	 *         the database.
	 */
	@Override
	public RuleName getRuleNameById(Integer id) {
		if (iRuleNameRepository.findById(id).isPresent()) {
			return iRuleNameRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>RuleName</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>IRuleNameRepository</code>.
	 * 
	 * @return A <code>RuleName</code>.
	 */
	@Override
	public RuleName addOrUpdateRuleName(RuleName ruleName) {
		return iRuleNameRepository.save(ruleName);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>IRuleNameRepository</code> if the ruleName whose id is passed as
	 * parameter exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *              <code>RuleName</code> doesn't exists in the database.
	 */
	@Override
	public Integer deleteRuleNameById(Integer id) {
		if (iRuleNameRepository.findById(id).isPresent()) {
			iRuleNameRepository.delete(iRuleNameRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}