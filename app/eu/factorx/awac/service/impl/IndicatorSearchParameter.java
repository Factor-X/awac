package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.reporting.BaseActivityData;

public class IndicatorSearchParameter {

	private InterfaceTypeCode interfaceType;

	private IndicatorTypeCode type;

	private ScopeTypeCode scopeType;

	private ActivityCategoryCode activityCategory;

	private ActivitySubCategoryCode activitySubCategory;

	private Boolean activityOwnership;

	private Boolean deleted;

	public IndicatorSearchParameter() {
		super();
	}

	/**
	 * Constructs an IndicatorSearchParameter to find (non-deleted) indicators of type {@link IndicatorTypeCode#CARBON} and scope {@link ScopeTypeCode#SITE}
	 * 
	 * @param activityCategory
	 * @param activitySubCategory
	 * @param activityOwnership
	 */
	public IndicatorSearchParameter(InterfaceTypeCode interfaceType, ActivityCategoryCode activityCategory, ActivitySubCategoryCode activitySubCategory,
			Boolean activityOwnership) {
		super();
		this.interfaceType = interfaceType;
		this.type = IndicatorTypeCode.CARBON;
		this.scopeType = ScopeTypeCode.SITE;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityOwnership = activityOwnership;
		this.deleted = Boolean.FALSE;
	}

	/**
	 * Constructs an IndicatorSearchParameter to find (non-deleted) indicators of type {@link IndicatorTypeCode#CARBON} and scope {@link ScopeTypeCode#SITE}
	 * 
	 * @param baseActivityData
	 */
	public IndicatorSearchParameter(InterfaceTypeCode interfaceType, BaseActivityData baseActivityData) {
		this(interfaceType, baseActivityData.getActivityCategory(), baseActivityData.getActivitySubCategory(), baseActivityData.getActivityOwnership());
	}

	public InterfaceTypeCode getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(InterfaceTypeCode interfaceType) {
		this.interfaceType = interfaceType;
	}

	public IndicatorTypeCode getType() {
		return type;
	}

	public void setType(IndicatorTypeCode type) {
		this.type = type;
	}

	public ScopeTypeCode getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeTypeCode scopeType) {
		this.scopeType = scopeType;
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

	public Boolean getActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(Boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "IndicatorSearchParameter [type='" + type.getKey() + "', scopeType='" + scopeType.getKey() + "', activityCategory='"
				+ activityCategory.getKey() + "', activitySubCategory='" + activitySubCategory.getKey() + "', activityOwnership="
				+ activityOwnership + ", deleted=" + deleted + "]";
	}

}
