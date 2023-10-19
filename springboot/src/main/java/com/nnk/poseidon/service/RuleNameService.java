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
		if(iRuleNameRepository.findById(id).isPresent()) {
			return iRuleNameRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public RuleName addOrUpdateRuleName(RuleName ruleName) {
		return iRuleNameRepository.save(ruleName);
	}

	@Override
	public Integer deleteRuleNameById(Integer id) {
		if(iRuleNameRepository.findById(id).isPresent()) {
			iRuleNameRepository.delete(iRuleNameRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}