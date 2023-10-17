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
		CurvePoint curvePoint = iCurvePointRepository.findById(id).get();
		
		if(curvePoint == null) {
			throw new IllegalArgumentException("Invalid CurvePoint Id:" + id);
		}
		
		return curvePoint;
	}

	@Override
	public CurvePoint addOrUpdateCurvePoint(CurvePoint curvePoint) {
		return iCurvePointRepository.save(curvePoint);
	}

	@Override
	public void deleteCurvePointById(Integer id) {
		CurvePoint curvePoint = iCurvePointRepository.findById(id).get();
		
		if(curvePoint == null) {
			throw new IllegalArgumentException("Invalid Bid Id:" + id);
		}
		
		iCurvePointRepository.delete(curvePoint);
	}
}
