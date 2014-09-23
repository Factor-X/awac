package eu.factorx.awac.models.reporting;

import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Factor;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseActivityResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private BaseIndicator baseIndicator;

	private BaseActivityData activityData;

	private Factor factor;

	private Site site;

	protected BaseActivityResult() {
		super();
	}

	public BaseActivityResult(BaseIndicator baseIndicator, BaseActivityData activityData, Factor factor, Site site) {
		super();
		this.baseIndicator = baseIndicator;
		this.activityData = activityData;
		this.factor = factor;
		this.site = site;
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Double getNumericValue() {
		if (activityData == null || factor == null) {
			return null;
		}
		Double activityDataValue = activityData.getValue();
		Double factorValue = factor.getCurrentValue();
		if (site != null) {
			return (activityDataValue * factorValue * site.getPercentOwned() / 100.0);
		} else {
			return (activityDataValue * factorValue);
		}
	}

	@Override
	public String toString() {
		return "BaseActivityResult [baseIndicator='" + baseIndicator.getCode().getKey() + "' (scope " + baseIndicator.getIsoScope().getKey() + "), activityData='" + activityData.getKey().getKey() + "' (rank = " + activityData.getRank() + "), factor='" + factor.getKey() + "', value = " + getNumericValue() + "]";
	}

}
