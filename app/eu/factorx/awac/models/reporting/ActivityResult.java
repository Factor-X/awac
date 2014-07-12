package eu.factorx.awac.models.reporting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.knowledge.Indicator;

@MappedSuperclass
public class ActivityResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Indicator indicator;

	private List<BaseActivityData> activityData;

	protected ActivityResult() {
		super();
	}

	public ActivityResult(Indicator indicator, List<BaseActivityData> activityData) {
		super();
		this.indicator = indicator;
		this.activityData = activityData;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public List<BaseActivityData> getActivityData() {
		return activityData;
	}

	public void setActivityData(List<BaseActivityData> activityData) {
		this.activityData = activityData;
	}

}
