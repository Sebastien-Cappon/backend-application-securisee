package com.nnk.poseidon.service;

import java.util.List;

import com.nnk.poseidon.domain.CurvePoint;

/**
 * <code>CurvePointService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface ICurvePointService {

	public List<CurvePoint> getCurvePointList();
	public CurvePoint getCurvePointById(Integer id);

	public CurvePoint addOrUpdateCurvePoint(CurvePoint curvePoint);

	public Integer deleteCurvePointById(Integer id);
}