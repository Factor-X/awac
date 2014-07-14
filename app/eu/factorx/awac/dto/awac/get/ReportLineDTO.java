package eu.factorx.awac.dto.awac.get;

import java.io.Serializable;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;

public class ReportLineDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private IndicatorCategoryCode indicatorCategory;

	private Double scope1Value = Double.valueOf(0);

	private Double scope2Value = Double.valueOf(0);

	private Double scope3Value = Double.valueOf(0);

	// TODO Delete this field: The translation should occur in client, but for test purpose...
	private String translatedIndicatorCategory;

	public ReportLineDTO() {
		super();
	}

	public ReportLineDTO(IndicatorCategoryCode IndicatorCategory) {
		super();
		this.indicatorCategory = IndicatorCategory;
	}

	public ReportLineDTO(IndicatorCategoryCode IndicatorCategory, Double scope1Value, Double scope2Value,
			Double scope3Value) {
		super();
		this.indicatorCategory = IndicatorCategory;
		this.scope1Value = scope1Value;
		this.scope2Value = scope2Value;
		this.scope3Value = scope3Value;
	}

	public IndicatorCategoryCode getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(IndicatorCategoryCode IndicatorCategory) {
		this.indicatorCategory = IndicatorCategory;
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

	public Double addScope1Value(Double value) {
		return (scope1Value += value);
	}

	public Double addScope2Value(Double value) {
		return (scope2Value += value);
	}

	public Double addScope3Value(Double value) {
		return (scope3Value += value);
	}

	public String getTranslatedIndicatorCategory() {
		return translatedIndicatorCategory;
	}

	public void setTranslatedIndicatorCategory(String translatedIndicatorCategory) {
		this.translatedIndicatorCategory = translatedIndicatorCategory;
	}

}
