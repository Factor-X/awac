package eu.factorx.awac.dto.awac.get;

public class ReportLogNoSuitableFactorEntryDTO extends ReportLogEntryDTO {
	private String biActivityCategory;
	private String biActivitySubCategory;
	private String biUnit;
	private Double adValue;
	private String adUnit;
	private String biIndicatorCategory;
	private String adActivityType;
	private String adActivitySource;

    public ReportLogNoSuitableFactorEntryDTO() {
    }

    public ReportLogNoSuitableFactorEntryDTO(String biActivityCategory, String biActivitySubCategory, String biUnit, Double adValue, String adUnit, String biIndicatorCategory, String adActivityType, String adActivitySource) {
		this.biActivityCategory = biActivityCategory;
		this.biActivitySubCategory = biActivitySubCategory;
		this.biUnit = biUnit;
		this.adValue = adValue;
		this.adUnit = adUnit;
		this.biIndicatorCategory = biIndicatorCategory;
		this.adActivityType = adActivityType;
		this.adActivitySource = adActivitySource;
	}

    public void setBiActivityCategory(String biActivityCategory) {
        this.biActivityCategory = biActivityCategory;
    }

    public void setBiActivitySubCategory(String biActivitySubCategory) {
        this.biActivitySubCategory = biActivitySubCategory;
    }

    public void setBiUnit(String biUnit) {
        this.biUnit = biUnit;
    }

    public void setAdValue(Double adValue) {
        this.adValue = adValue;
    }

    public void setAdUnit(String adUnit) {
        this.adUnit = adUnit;
    }

    public void setBiIndicatorCategory(String biIndicatorCategory) {
        this.biIndicatorCategory = biIndicatorCategory;
    }

    public void setAdActivityType(String adActivityType) {
        this.adActivityType = adActivityType;
    }

    public void setAdActivitySource(String adActivitySource) {
        this.adActivitySource = adActivitySource;
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
}
