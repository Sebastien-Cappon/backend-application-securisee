package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.repository.ICurvePointRepository;

@Service
public class CurvePointService implements ICurvePointService {

	@Autowired
	ICurvePointRepository iCurvePointRepository;

	@Override
	public List<CurvePoint> getCurvePointList() {
		return iCurvePointRepository.findAll();
	}

	@Override
	public CurvePoint getCurvePointById(Integer id) {
		if(iCurvePointRepository.findById(id).isPresent()) {
			return iCurvePointRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public CurvePoint addOrUpdateCurvePoint(CurvePoint curvePoint) {
		return iCurvePointRepository.save(curvePoint);
	}

	@Override
	public Integer deleteCurvePointById(Integer id) {
		if(iCurvePointRepository.findById(id).isPresent()) {
			iCurvePointRepository.delete(iCurvePointRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}
