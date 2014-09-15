package eu.factorx.awac.models.reporting;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.BaseIndicator;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseActivityResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private BaseIndicator baseIndicator;

	private BaseActivityData activityData;

	private Factor factor;

	protected BaseActivityResult() {
		super();
	}

	public BaseActivityResult(BaseIndicator baseIndicator, BaseActivityData activityData, Factor factor) {
		super();
		this.baseIndicator = baseIndicator;
		this.activityData = activityData;
		this.factor = factor;
	}

	public BaseIndicator getBaseIndicator() {
		return baseIndicator;
	}

	public void setBaseIndicator(BaseIndicator baseIndicator) {
		this.baseIndicator = baseIndicator;
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

	@Override
	public String toString() {
		return "BaseActivityResult [baseIndicator='" + baseIndicator.getKey() + "' (scope " + baseIndicator.getIsoScope().getKey() +  "), activityData='" + activityData.getKey().getKey() + "' (rank = " + activityData.getRank() + "), factor='" + factor.getKey() + "', value = " + getNumericValue() + "]";
	}

}
