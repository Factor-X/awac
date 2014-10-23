package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.io.Serializable;

public class ReportLineDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String indicatorName;

	private String color;

	private Double leftScope1Value     = Double.valueOf(0);
	private Double leftScope2Value     = Double.valueOf(0);
	private Double leftScope3Value     = Double.valueOf(0);
	private Double leftPercentage      = Double.valueOf(0);
	private Double leftOutOfScopeValue = Double.valueOf(0);

	private Double rightScope1Value = Double.valueOf(0);
	private Double rightScope2Value = Double.valueOf(0);
	private Double rightScope3Value = Double.valueOf(0);
	private Double rightPercentage  = Double.valueOf(0);
	private Double rightOutOfScopeValue = Double.valueOf(0);
	private Integer order;

	public ReportLineDTO() {
		super();
	}

	public ReportLineDTO(String indicatorName) {
		super();
		this.indicatorName = indicatorName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public Double getLeftPercentage() {
		return leftPercentage;
	}

	public void setLeftPercentage(Double leftPercentage) {
		this.leftPercentage = leftPercentage;
	}

	public Double getLeftOutOfScopeValue() {
		return leftOutOfScopeValue;
	}

	public void setLeftOutOfScopeValue(Double leftOutOfScopeValue) {
		this.leftOutOfScopeValue = leftOutOfScopeValue;
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

	public Double getRightPercentage() {
		return rightPercentage;
	}

	public void setRightPercentage(Double rightPercentage) {
		this.rightPercentage = rightPercentage;
	}

	public Double getRightOutOfScopeValue() {
		return rightOutOfScopeValue;
	}

	public void setRightOutOfScopeValue(Double rightOutOfScopeValue) {
		this.rightOutOfScopeValue = rightOutOfScopeValue;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}
}
