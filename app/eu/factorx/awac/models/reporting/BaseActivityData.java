package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.ActivityCategory;
import eu.factorx.awac.models.knowledge.ActivitySource;
import eu.factorx.awac.models.knowledge.ActivityType;
import eu.factorx.awac.models.knowledge.Unit;

public class BaseActivityData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Scope scope;

	private Integer rank;

	private String specificPurpose;

	private ActivityCategory activityCategory;

	private ActivityType activityType;

	private ActivitySource activitySource;

	private Boolean activityOwnership;

	private Unit unit;

	private Double value;

	public BaseActivityData() {
		super();
	}

	public BaseActivityData(Scope scope, Integer rank, String specificPurpose, ActivityCategory activityCategory,
			ActivityType activityType, ActivitySource activitySource, Boolean activityOwnership, Unit unit, Double value) {
		super();
		this.scope = scope;
		this.rank = rank;
		this.specificPurpose = specificPurpose;
		this.activityCategory = activityCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.activityOwnership = activityOwnership;
		this.unit = unit;
		this.value = value;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getSpecificPurpose() {
		return specificPurpose;
	}

	public void setSpecificPurpose(String specificPurpose) {
		this.specificPurpose = specificPurpose;
	}

	public ActivityCategory getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategory activityCategory) {
		this.activityCategory = activityCategory;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public ActivitySource getActivitySource() {
		return activitySource;
	}

	public void setActivitySource(ActivitySource activitySource) {
		this.activitySource = activitySource;
	}

	public Boolean getActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(Boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
