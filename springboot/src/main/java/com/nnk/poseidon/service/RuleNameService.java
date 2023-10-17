package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.RuleName;
import com.nnk.poseidon.repository.IRuleNameRepository;

@Service
public class RuleNameService implements IRuleNameService {

	@Autowired
	IRuleNameRepository iRuleNameRepository;

	@Override
	public List<RuleName> getRuleNameList() {
		return iRuleNameRepository.findAll();
	}

	@Override
	public RuleName getRuleNameById(Integer id) {
		RuleName ruleName = iRuleNameRepository.findById(id).get();
		
		if(ruleName == null) {
			throw new IllegalArgumentException("Invalid RuleName Id:" + id);
		}
		
		return ruleName;
	}

	@Override
	public RuleName addOrUpdateRuleName(RuleName ruleName) {
		return iRuleNameRepository.save(ruleName);
	}

	@Override
	public void deleteRuleNameById(Integer id) {
		RuleName ruleName = iRuleNameRepository.findById(id).get();
		
		if(ruleName == null) {
			throw new IllegalArgumentException("Invalid RuleName Id:" + id);
		}
		
		iRuleNameRepository.delete(ruleName);
	}
}