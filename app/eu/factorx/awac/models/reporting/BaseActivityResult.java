package eu.factorx.awac.models.reporting;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseActivityResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Indicator indicator;

	private BaseActivityData activityData;

	private Factor factor;

	protected BaseActivityResult() {
		super();
	}

	public BaseActivityResult(Indicator indicator, BaseActivityData activityData, Factor factor) {
		super();
		this.indicator = indicator;
		this.activityData = activityData;
		this.factor = factor;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public BaseActivityData getActivityData() {
		return activityData;
	}

	public void setActivityData(BaseActivityData activityData) {
		this.activityData = activityData;
	}

	public Factor getFactor() {
		return factor;
	}

	public void setFactor(Factor factor) {
		this.factor = factor;
	}

	public Double getNumericValue() {
		if (activityData == null || factor == null) {
			return null;
		}
		Double activityDataValue = activityData.getValue();
		Double factorValue = factor.getCurrentValue();
		return (activityDataValue * factorValue);
	}
}
