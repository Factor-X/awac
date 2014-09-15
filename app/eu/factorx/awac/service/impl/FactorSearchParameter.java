package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

public class FactorSearchParameter {

	private IndicatorCategoryCode indicatorCategory;

	private ActivitySourceCode activitySource;

	private ActivityTypeCode activityType;

	private Unit unitIn;

	private Unit unitOut;

	/**
	 * 
	 */
	public FactorSearchParameter() {
		super();
	}

	/**
	 * @param indicatorCategory
	 * @param activitySource
	 * @param activityType
	 * @param unitIn
	 * @param unitOut
	 */
	public FactorSearchParameter(IndicatorCategoryCode indicatorCategory, ActivitySourceCode activitySource, ActivityTypeCode activityType,
			Unit unitIn, Unit unitOut) {
		super();
		this.indicatorCategory = indicatorCategory;
		this.activitySource = activitySource;
		this.activityType = activityType;
		this.unitIn = unitIn;
		this.unitOut = unitOut;
	}

	/**
	 * @param baseIndicator
	 * @param baseActivityData
	 */
	public FactorSearchParameter(BaseIndicator baseIndicator, BaseActivityData baseActivityData) {
		super();
		this.indicatorCategory = baseIndicator.getIndicatorCategory();
		this.activitySource = baseActivityData.getActivitySource();
		this.activityType = baseActivityData.getActivityType();
		this.unitIn = baseActivityData.getUnit();
		this.unitOut = baseIndicator.getUnit();
	}

	public IndicatorCategoryCode getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(IndicatorCategoryCode indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	public ActivitySourceCode getActivitySource() {
		return activitySource;
	}

	public void setActivitySource(ActivitySourceCode activitySource) {
		this.activitySource = activitySource;
	}

	public ActivityTypeCode getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeCode activityType) {
		this.activityType = activityType;
	}

	public Unit getUnitIn() {
		return unitIn;
	}

	public void setUnitIn(Unit unitIn) {
		this.unitIn = unitIn;
	}

	public Unit getUnitOut() {
		return unitOut;
	}

	public void setUnitOut(Unit unitOut) {
		this.unitOut = unitOut;
	}

	@Override
	public String toString() {
		return "FactorSearchParameter [indicatorCategory='" + indicatorCategory
				+ "', activitySource='" + activitySource
				+ "', activityType='" + activityType
				+ "', unitIn='" + unitIn
				+ "', unitOut='" + unitOut + "']";
	}

}
