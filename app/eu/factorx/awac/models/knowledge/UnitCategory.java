package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "unit_category")
public class UnitCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String ref;

	// TODO i18n?
	private String name;

	private String symbol;

	@ManyToOne(optional = true)
	private Unit mainUnit;

	@OneToMany(mappedBy = "category")
	private List<Unit> units;

	public UnitCategory() {
		super();
	}

	public UnitCategory(String ref, String name, String symbol, Unit mainUnit) {
		super();
		this.ref = ref;
		this.name = name;
		this.symbol = symbol;
		this.mainUnit = mainUnit;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Unit getMainUnit() {
		return mainUnit;
	}

	public void setMainUnit(Unit mainUnit) {
		this.mainUnit = mainUnit;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
