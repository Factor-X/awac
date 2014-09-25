package eu.factorx.awac.service.impl.reporting;

public class MergedReportResultIndicatorAggregation {
	private String indicator;

	private Double leftTotalValue;
	private Double leftScope1Value;
	private Double leftScope2Value;
	private Double leftScope3Value;
	private Double leftOutOfScopeValue;

	private Double rightTotalValue;
	private Double rightScope1Value;
	private Double rightScope2Value;
	private Double rightScope3Value;
	private Double rightOutOfScopeValue;

	public MergedReportResultIndicatorAggregation() {
	}

	public MergedReportResultIndicatorAggregation(String indicator) {
		this.indicator = indicator;
	}


	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Double getLeftTotalValue() {
		return leftTotalValue;
	}

	public void setLeftTotalValue(Double leftTotalValue) {
		this.leftTotalValue = leftTotalValue;
	}

	public Double getLeftScope1Value() {
		return leftScope1Value;
	}

	public void setLeftScope1Value(Double leftScope1Value) {
		this.leftScope1Value = leftScope1Value;
	}

	public Double getLeftScope2Value() {
		return leftScope2Value;
	}

	public void setLeftScope2Value(Double leftScope2Value) {
		this.leftScope2Value = leftScope2Value;
	}

	public Double getLeftScope3Value() {
		return leftScope3Value;
	}

	public void setLeftScope3Value(Double leftScope3Value) {
		this.leftScope3Value = leftScope3Value;
	}

	public Double getLeftOutOfScopeValue() {
		return leftOutOfScopeValue;
	}

	public void setLeftOutOfScopeValue(Double leftOutOfScopeValue) {
		this.leftOutOfScopeValue = leftOutOfScopeValue;
	}

	public Double getRightTotalValue() {
		return rightTotalValue;
	}

	public void setRightTotalValue(Double rightTotalValue) {
		this.rightTotalValue = rightTotalValue;
	}

	public Double getRightScope1Value() {
		return rightScope1Value;
	}

	public void setRightScope1Value(Double rightScope1Value) {
		this.rightScope1Value = rightScope1Value;
	}

	public Double getRightScope2Value() {
		return rightScope2Value;
	}

	public void setRightScope2Value(Double rightScope2Value) {
		this.rightScope2Value = rightScope2Value;
	}

	public Double getRightScope3Value() {
		return rightScope3Value;
	}

	public void setRightScope3Value(Double rightScope3Value) {
		this.rightScope3Value = rightScope3Value;
	}

	public Double getRightOutOfScopeValue() {
		return rightOutOfScopeValue;
	}

	public void setRightOutOfScopeValue(Double rightOutOfScopeValue) {
		this.rightOutOfScopeValue = rightOutOfScopeValue;
	}
}
