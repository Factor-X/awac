package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.knowledge.Unit;

@MappedSuperclass
public class BaseActivityData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Scope scope;

	private Integer rank;

	private String specificPurpose;

	private ActivityCategoryCode activityCategory;

	private ActivityTypeCode activityType;

	private ActivitySourceCode activitySource;

	private Boolean activityOwnership;

	private Unit unit;

	private Double value;

	protected BaseActivityData() {
		super();
	}

	public BaseActivityData(Scope scope, Integer rank, String specificPurpose, ActivityCategoryCode activityCategory,
			ActivityTypeCode activityType, ActivitySourceCode activitySource, Boolean activityOwnership, Unit unit,
			Double value) {
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

	public ActivityCategoryCode getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategoryCode activityCategory) {
		this.activityCategory = activityCategory;
	}

	public ActivityTypeCode getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeCode activityType) {
		this.activityType = activityType;
	}

	public ActivitySourceCode getActivitySource() {
		return activitySource;
	}

	public void setActivitySource(ActivitySourceCode activitySource) {
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
