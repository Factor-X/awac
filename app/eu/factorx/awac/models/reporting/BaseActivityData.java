package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import javax.persistence.*;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.Unit;

@Embeddable
public class BaseActivityData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Embedded
	@Basic(optional = false)
	private BaseActivityDataCode key;

	@Embedded
	@Basic(optional = false)
	private ActivityCategoryCode activityCategory;

	@Embedded
	@Basic(optional = false)
	private ActivitySubCategoryCode activitySubCategory;

	@Embedded
	@Basic(optional = false)
	private ActivityTypeCode activityType;

	@Embedded
	@Basic(optional = false)
	private ActivitySourceCode activitySource;

	@Basic(optional = false)
	private Boolean activityOwnership;

	@Transient
	private Double value;

	@ManyToOne(optional = false)
	private Unit unit;

	@Basic(optional = true)
	private Integer rank;

	@Basic(optional = true)
	private String specificPurpose;

	/**
	 * When this field is not null, we have to check all BADs having the same 'alternative group', and keep BADs with lowest rank
	 */
	@Basic(optional = true)
	private String alternativeGroup;
	
	public BaseActivityData() {
		super();
	}

	/**
	 * Deprecated! Use instead the constructor taking a 'alternativeGroup' parameter
	 * @param key
	 * @param activityCategory
	 * @param activitySubCategory
	 * @param activityType
	 * @param activitySource
	 * @param activityOwnership
	 * @param value
	 * @param unit
	 * @param rank
	 * @param specificPurpose
	 */
	@Deprecated
	public BaseActivityData(BaseActivityDataCode key, ActivityCategoryCode activityCategory,
	                        ActivitySubCategoryCode activitySubCategory, ActivityTypeCode activityType,
	                        ActivitySourceCode activitySource, Boolean activityOwnership, Double value, Unit unit, Integer rank,
	                        String specificPurpose) {
		super();
		this.key = key;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.activityOwnership = activityOwnership;
		this.value = value;
		this.unit = unit;
		this.rank = rank;
		this.specificPurpose = specificPurpose;
	}
	
	/**
	 * @param key
	 * @param activityCategory
	 * @param activitySubCategory
	 * @param activityType
	 * @param activitySource
	 * @param activityOwnership
	 * @param value
	 * @param unit
	 * @param rank
	 * @param specificPurpose
	 * @param alternativeGroup
	 */
	public BaseActivityData(BaseActivityDataCode key, ActivityCategoryCode activityCategory, ActivitySubCategoryCode activitySubCategory, ActivityTypeCode activityType,
			ActivitySourceCode activitySource, Boolean activityOwnership, Double value, Unit unit, Integer rank, String specificPurpose, String alternativeGroup) {
		super();
		this.key = key;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.activityOwnership = activityOwnership;
		this.value = value;
		this.unit = unit;
		this.rank = rank;
		this.specificPurpose = specificPurpose;
		this.alternativeGroup = alternativeGroup;
	}

	public BaseActivityDataCode getKey() {
		return key;
	}

	public void setKey(BaseActivityDataCode key) {
		this.key = key;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public String getAlternativeGroup() {
		return alternativeGroup;
	}

	public void setAlternativeGroup(String alternativeGroup) {
		this.alternativeGroup = alternativeGroup;
	}

	@Override
	public String toString() {
		return "BaseActivityData [key=" + key + ", activityCategory=" + activityCategory + ", activitySubCategory=" + activitySubCategory
				+ ", activityType=" + activityType + ", activitySource=" + activitySource + ", activityOwnership=" + activityOwnership
				+ ", value=" + value + ", unit=" + unit + ", rank=" + rank + ", specificPurpose=" + specificPurpose + ", alternativeGroup=" + alternativeGroup + "]";
	}
	
}
