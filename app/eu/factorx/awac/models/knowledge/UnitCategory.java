package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "unit_category")
@NamedQueries({
		@NamedQuery(name = UnitCategory.FIND_BY_NAME, query = "select uc from UnitCategory uc where uc.name = :name"),
})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UnitCategory extends AbstractEntity {

	/**
	 * @param String name : a {@link String}
	 */
	public static final String FIND_BY_NAME = "UnitCategory.findByName";
	private static final long serialVersionUID = 1L;
	private String ref;

	// TODO i18n?
	private String name;

	private String symbol;

	@ManyToOne(optional = true)
	private Unit mainUnit;

	@OneToMany(mappedBy = "category")
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
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
