package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.RuleName;

public interface IRuleNameService {

	public List<RuleName> getRuleNameList();
	public RuleName getRuleNameById(Integer id);
	
	public RuleName addOrUpdateRuleName(RuleName ruleName);
	
	public Integer deleteRuleNameById(Integer id);
}