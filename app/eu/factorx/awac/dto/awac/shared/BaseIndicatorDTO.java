package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

public class BaseIndicatorDTO extends DTO {

	private String key;

	private String isoScopeKey;

	private String indicatorCategoryKey;

	private String activityCategoryKey;

	private String activitySubCategoryKey;

	private Boolean activityOwnership;

	private String unitKey;

	public BaseIndicatorDTO() {
		super();
	}

	public BaseIndicatorDTO(String key, String isoScopeKey, String indicatorCategoryKey, String activityCategoryKey, String activitySubCategoryKey, Boolean activityOwnership,
			String unitKey) {
		super();
		this.key = key;
		this.isoScopeKey = isoScopeKey;
		this.indicatorCategoryKey = indicatorCategoryKey;
		this.activityCategoryKey = activityCategoryKey;
		this.activitySubCategoryKey = activitySubCategoryKey;
		this.activityOwnership = activityOwnership;
		this.unitKey = unitKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIsoScopeKey() {
		return isoScopeKey;
	}

	public void setIsoScopeKey(String isoScopeKey) {
		this.isoScopeKey = isoScopeKey;
	}

	public String getIndicatorCategoryKey() {
		return indicatorCategoryKey;
	}

	public void setIndicatorCategoryKey(String indicatorCategoryKey) {
		this.indicatorCategoryKey = indicatorCategoryKey;
	}

	public String getActivityCategoryKey() {
		return activityCategoryKey;
	}

	public void setActivityCategoryKey(String activityCategoryKey) {
		this.activityCategoryKey = activityCategoryKey;
	}

	public String getActivitySubCategoryKey() {
		return activitySubCategoryKey;
	}

	public void setActivitySubCategoryKey(String activitySubCategoryKey) {
		this.activitySubCategoryKey = activitySubCategoryKey;
	}

	public Boolean getActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(Boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public String getUnitKey() {
		return unitKey;
	}

	public void setUnitKey(String unitKey) {
		this.unitKey = unitKey;
	}

	@Override
	public String toString() {
		return "BaseIndicatorDTO [key=" + key + ", isoScopeKey=" + isoScopeKey + ", indicatorCategoryKey=" + indicatorCategoryKey + ", activityCategoryKey=" + activityCategoryKey
				+ ", activitySubCategoryKey=" + activitySubCategoryKey + ", activityOwnership=" + activityOwnership + ", unitKey=" + unitKey + "]";
	}

}
