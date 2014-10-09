package eu.factorx.awac.dto.awac.get;

public class NoSuitableIndicatorDTO extends ReportLogEntryDTO {

	private String activityCategory;
	private String activitySubCategory;
	private String activityType;
	private String activitySource;
	private Double value;
	private String unit;

	public NoSuitableIndicatorDTO(String activityCategory, String activitySubCategory, String activityType, String activitySource, Double value, String unit) {
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.value = value;
		this.unit = unit;
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

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivitySource() {
		return activitySource;
	}

	public void setActivitySource(String activitySource) {
		this.activitySource = activitySource;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
