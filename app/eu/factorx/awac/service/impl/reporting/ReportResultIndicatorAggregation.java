package eu.factorx.awac.service.impl.reporting;

public class ReportResultIndicatorAggregation {
	private String indicator;

	private Double totalValue;
	private Double scope1Value;
	private Double scope2Value;
	private Double scope3Value;
	private Double outOfScopeValue;

	public ReportResultIndicatorAggregation() {
	}

	public ReportResultIndicatorAggregation(String indicator) {
		this.indicator = indicator;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Double getScope1Value() {
		return scope1Value;
	}

	public void setScope1Value(Double scope1Value) {
		this.scope1Value = scope1Value;
	}

	public Double getScope2Value() {
		return scope2Value;
	}

	public void setScope2Value(Double scope2Value) {
		this.scope2Value = scope2Value;
	}

	public Double getScope3Value() {
		return scope3Value;
	}

	public void setScope3Value(Double scope3Value) {
		this.scope3Value = scope3Value;
	}

	public Double getOutOfScopeValue() {
		return outOfScopeValue;
	}

	public void setOutOfScopeValue(Double outOfScopeValue) {
		this.outOfScopeValue = outOfScopeValue;
	}
}
