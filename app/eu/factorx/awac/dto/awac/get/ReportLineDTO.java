package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.io.Serializable;

public class ReportLineDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String indicatorName;

	private String color;

	private Double scope1Value = Double.valueOf(0);

	private Double scope2Value = Double.valueOf(0);

	private Double scope3Value = Double.valueOf(0);

	private Double percentage = Double.valueOf(0);

	private Double outOfScopeValue = Double.valueOf(0);

	public ReportLineDTO() {
		super();
	}

	public ReportLineDTO(String indicatorName) {
		super();
		this.indicatorName = indicatorName;
	}

	public ReportLineDTO(String indicatorName, String color, Double scope1Value, Double scope2Value, Double scope3Value, Double outOfScopeValue, Double percentage) {
		super();
		this.indicatorName = indicatorName;
		this.color = color;
		this.scope1Value = scope1Value;
		this.scope2Value = scope2Value;
		this.scope3Value = scope3Value;
		this.outOfScopeValue = outOfScopeValue;
		this.percentage = percentage;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
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

	public Double addScope1Value(Double value) {
		return (scope1Value += value);
	}

	public Double addScope2Value(Double value) {
		return (scope2Value += value);
	}

	public Double addScope3Value(Double value) {
		return (scope3Value += value);
	}

	public Double addOutOfScopeValue(Double value) {
		return (outOfScopeValue += value);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
