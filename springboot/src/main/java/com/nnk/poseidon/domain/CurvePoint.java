package com.nnk.poseidon.domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message = "CurveId is mandatory")
	@Positive(message = "CurveId must be strictly positive")
	private Integer curveId;
	private Timestamp asOfDate;
	@NotNull(message = "Term is mandatory")
	private Double term;
	@NotNull(message = "Value is mandatory")
	private Double value;
	private Timestamp creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "[" + id + "]" + "[" + curveId + "]" + "[" + asOfDate + "]" + "[" + term + "]" + "[" + value + "]" + "["
				+ creationDate + "]";
	}
}