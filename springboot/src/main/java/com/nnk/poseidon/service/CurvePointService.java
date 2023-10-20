package com.nnk.poseidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.poseidon.domain.CurvePoint;
import com.nnk.poseidon.repository.ICurvePointRepository;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>CurvePoint</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class CurvePointService implements ICurvePointService {

	@Autowired
	ICurvePointRepository iCurvePointRepository;

	/**
	 * A <code>GET</code> method that returns a <code>CurvePoint</code> list after
	 * calling the <code>findAll()</code> derived query from
	 * <code>ICurvePointRepository</code>.
	 * 
	 * @return A <code>CurvePoint</code> list.
	 */
	@Override
	public List<CurvePoint> getCurvePointList() {
		return iCurvePointRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>CurvePoint</code> whose id is
	 * passed as parameter after calling the <code>findById()</code> derived query
	 * from <code>ICurvePointRepository</code>.
	 * 
	 * @return A <code>CurvePoint</code> or <code>null</code> if it doesn't exist in
	 *         the database.
	 */
	@Override
	public CurvePoint getCurvePointById(Integer id) {
		if (iCurvePointRepository.findById(id).isPresent()) {
			return iCurvePointRepository.findById(id).get();
		} else {
			return null;
		}
	}

	/**
	 * A <code>POST</code> method that returns a <code>CurvePoint</code> passed as
	 * parameter and saved or updated in the database after calling the
	 * <code>save()</code> derived query from <code>ICurvePointRepository</code>.
	 * 
	 * @return A <code>CurvePoint</code>.
	 */
	@Override
	public CurvePoint addOrUpdateCurvePoint(CurvePoint curvePoint) {
		return iCurvePointRepository.save(curvePoint);
	}

	/**
	 * An <code>GET</code> method that calls the derived query <code>delete</code>
	 * from <code>ICurvePointRepository</code> if the curvePoint whose id is passed
	 * as parameter exists.
	 * 
	 * @singularity An <code>Integer<code> is returned for dealing with exception
	 * 				in the Controller layer.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *              <code>CurvePoint</code> doesn't exists in the database.
	 */
	@Override
	public Integer deleteCurvePointById(Integer id) {
		if (iCurvePointRepository.findById(id).isPresent()) {
			iCurvePointRepository.delete(iCurvePointRepository.findById(id).get());
			return 1;
		} else {
			return null;
		}
	}
}