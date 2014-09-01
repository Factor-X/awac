package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

/**
 * Created by florian on 29/08/14.
 */
public class BAD {

	private String baseActivityDataCode;
	private String name;
	private int rank;
	private String SpecificPurpose;

	//it's always an activityCategoryCode
	private String activityCategoryCode;

	private String activitySubCategory;

	private String activityType;

	private String activitySource;

	private boolean activityOwnership;

	private String unit;

	private String value;

	public BAD() {
	}

	public BAD(String baseActivityDataCode, String name, int rank, String specificPurpose, String activityCategoryCode, String activitySubCategory, String activityType, String activitySource, boolean activityOwnership, String unit, String value) {
		this.baseActivityDataCode = baseActivityDataCode;
		this.name = name;
		this.rank = rank;
		SpecificPurpose = specificPurpose;
		this.activityCategoryCode = activityCategoryCode;
		this.activitySubCategory = activitySubCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.activityOwnership = activityOwnership;
		this.unit = unit;
		this.value = value;
	}

	public String getBaseActivityDataCode() {
		return baseActivityDataCode;
	}

	public void setBaseActivityDataCode(String baseActivityDataCode) {
		this.baseActivityDataCode = baseActivityDataCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getSpecificPurpose() {
		return SpecificPurpose;
	}

	public void setSpecificPurpose(String specificPurpose) {
		SpecificPurpose = specificPurpose;
	}

	public String getActivityCategoryCode() {
		return activityCategoryCode;
	}

	public void setActivityCategoryCode(String activityCategoryCode) {
		this.activityCategoryCode = activityCategoryCode;
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

	public boolean isActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BAD{" +
				"baseActivityDataCode='" + baseActivityDataCode + '\'' +
				", name='" + name + '\'' +
				", rank=" + rank +
				", SpecificPurpose='" + SpecificPurpose + '\'' +
				", activityCategoryCode='" + activityCategoryCode + '\'' +
				", activitySubCategory='" + activitySubCategory + '\'' +
				", activityType='" + activityType + '\'' +
				", activitySource='" + activitySource + '\'' +
				", activityOwnership=" + activityOwnership +
				", unit='" + unit + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
