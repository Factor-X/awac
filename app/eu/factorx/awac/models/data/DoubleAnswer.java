package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;

import eu.factorx.awac.models.knowledge.Unit;

@Entity
public class DoubleAnswer extends QuestionAnswer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double value;
	private Unit unit;

	public DoubleAnswer() {
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double param) {
		this.value = param;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit param) {
		this.unit = param;
	}

}