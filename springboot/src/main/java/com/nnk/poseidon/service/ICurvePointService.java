package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.CurvePoint;

public interface ICurvePointService {

	public List<CurvePoint> getCurvePointList();
	public CurvePoint getCurvePointById(Integer id);
	
	public CurvePoint addOrUpdateCurvePoint(CurvePoint curvePoint);
	
	public Integer deleteCurvePointById(Integer id);
}