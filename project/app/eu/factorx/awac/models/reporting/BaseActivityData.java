package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.knowledge.Unit;

@MappedSuperclass
public class BaseActivityData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer rank;

	private String specificPurpose;

	private ActivityCategoryCode activityCategory;

	private ActivitySubCategoryCode activitySubCategory;

	private ActivityTypeCode activityType;

	private ActivitySourceCode activitySource;

	private Boolean activityOwnership;

	private Unit unit;

	private Double value;

	public BaseActivityData() {
		super();
	}

	public BaseActivityData(Integer rank, String specificPurpose, ActivityCategoryCode activityCategory,
			ActivitySubCategoryCode activitySubCategory, ActivityTypeCode activityType,
			ActivitySourceCode activitySource, Boolean activityOwnership, Unit unit, Double value) {
		super();
		this.rank = rank;
		this.specificPurpose = specificPurpose;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.activityOwnership = activityOwnership;
		this.unit = unit;
		this.value = value;
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

	public ActivitySubCategoryCode getActivitySubCategory() {
		return activitySubCategory;
	}

	public void setActivitySubCategory(ActivitySubCategoryCode activitySubCategory) {
		this.activitySubCategory = activitySubCategory;
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
