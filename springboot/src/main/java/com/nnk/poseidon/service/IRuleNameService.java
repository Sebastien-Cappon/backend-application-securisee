package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.RuleName;

/**
 * <code>RuleNameService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IRuleNameService {

	public List<RuleName> getRuleNameList();
	public RuleName getRuleNameById(Integer id);

	public RuleName addOrUpdateRuleName(RuleName ruleName);

	public Integer deleteRuleNameById(Integer id);
}