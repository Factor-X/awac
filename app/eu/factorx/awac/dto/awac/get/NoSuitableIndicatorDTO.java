package eu.factorx.awac.dto.awac.get;

public class NoSuitableIndicatorDTO extends ReportLogEntryDTO {

	private String key;
	private String activityCategory;
	private String activitySubCategory;

	public NoSuitableIndicatorDTO() {
	}

	public NoSuitableIndicatorDTO(
		String key,
		String activityCategory,
		String activitySubCategory
	) {
		this.key = key;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(String activityCategory) {
		this.activityCategory = activityCategory;
	}

	public String getActivitySubCategory() {
		return activitySubCategory;
	}

	public void setActivitySubCategory(String activitySubCategory) {
		this.activitySubCategory = activitySubCategory;
	}
}
