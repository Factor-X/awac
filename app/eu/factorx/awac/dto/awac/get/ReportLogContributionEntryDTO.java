package eu.factorx.awac.dto.awac.get;

public class ReportLogContributionEntryDTO extends ReportLogEntryDTO {
	private String biActivityCategory;
	private String biActivitySubCategory;
	private String biUnit;
	private Double adValue;
	private String adUnit;
	private String biIndicatorCategory;
	private String adActivityType;
	private String adActivitySource;
	private String fUnitIn;
	private String fUnitOut;
	private Double fValue;
	private Double value;

	public ReportLogContributionEntryDTO(String biActivityCategory, String biActivitySubCategory, String biUnit, Double adValue, String adUnit, String biIndicatorCategory, String adActivityType, String adActivitySource, String fUnitIn, String fUnitOut, Double fValue, Double value) {
		this.biActivityCategory = biActivityCategory;
		this.biActivitySubCategory = biActivitySubCategory;
		this.biUnit = biUnit;
		this.adValue = adValue;
		this.adUnit = adUnit;
		this.biIndicatorCategory = biIndicatorCategory;
		this.adActivityType = adActivityType;
		this.adActivitySource = adActivitySource;
		this.fUnitIn = fUnitIn;
		this.fUnitOut = fUnitOut;
		this.fValue = fValue;
		this.value = value;
	}

	public String getBiActivityCategory() {
		return biActivityCategory;
	}

	public String getBiActivitySubCategory() {
		return biActivitySubCategory;
	}

	public String getBiUnit() {
		return biUnit;
	}

	public Double getAdValue() {
		return adValue;
	}

	public String getAdUnit() {
		return adUnit;
	}

	public String getBiIndicatorCategory() {
		return biIndicatorCategory;
	}

	public String getAdActivityType() {
		return adActivityType;
	}

	public String getAdActivitySource() {
		return adActivitySource;
	}

	public String getfUnitIn() {
		return fUnitIn;
	}

	public String getfUnitOut() {
		return fUnitOut;
	}

	public Double getfValue() {
		return fValue;
	}

	public Double getValue() {
		return value;
	}
}
