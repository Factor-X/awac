package eu.factorx.awac.models.knowledge;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@NamedQueries({
		@NamedQuery(name = UnitConversionFormula.FIND_BY_UNIT_AND_YEAR, query = "select ucf from UnitConversionFormula ucf where (ucf.year is null or ucf.year = :year) and ucf.unit = :unit"),
})
public class UnitConversionFormula extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_UNIT_AND_YEAR = "UnitConversionFormula.findByUnitAndYear";

	public static final String VARIABLE_NAME = "(x|X)";

	@ManyToOne(optional = false)
	private Unit unit;

	@Basic(optional = false)
	private String unitToReference;

	@Basic(optional = false)
	private String referenceToUnit;

	@Basic(optional = true)
	private Integer year;

	public UnitConversionFormula() {
		super();
	}

	public UnitConversionFormula(Unit unit, String unitToReference, String referenceToUnit, Integer year) {
		super();
		this.unit = unit;
		this.unitToReference = unitToReference;
		this.referenceToUnit = referenceToUnit;
		this.year = year;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getUnitToReference() {
		return unitToReference;
	}

	public void setUnitToReference(String unitToReference) {
		this.unitToReference = unitToReference;
	}

	public String getReferenceToUnit() {
		return referenceToUnit;
	}

	public void setReferenceToUnit(String referenceToUnit) {
		this.referenceToUnit = referenceToUnit;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
