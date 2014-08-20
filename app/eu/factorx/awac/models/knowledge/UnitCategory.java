package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.UnitCategoryCode;

@Entity
@Table(name = "unit_category")
@NamedQueries({
		@NamedQuery(name = UnitCategory.FIND_BY_NAME, query = "select uc from UnitCategory uc where uc.name = :name"),
		@NamedQuery(name = UnitCategory.FIND_BY_CODE, query = "select uc from UnitCategory uc where uc.unitCategoryCode = :unitCategoryCode"),
})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UnitCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * @param String name : a {@link String}
	 */
	public static final String FIND_BY_NAME = "UnitCategory.findByName";

	/**
	 * @param UnitCategoryCode unitCategoryCode : a {@link UnitCategoryCode}
	 */
	public static final String FIND_BY_CODE = "UnitCategory.findByCode";

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "ref"))})
	private UnitCategoryCode unitCategoryCode;

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

	public UnitCategory(UnitCategoryCode unitCategoryCode, String name, String symbol, Unit mainUnit) {
		super();
		this.unitCategoryCode = unitCategoryCode;
		this.name = name;
		this.symbol = symbol;
		this.mainUnit = mainUnit;
	}

	public UnitCategoryCode getUnitCategoryCode() {
		return unitCategoryCode;
	}

	public void setUnitCategoryCode(UnitCategoryCode unitCategoryCode) {
		this.unitCategoryCode = unitCategoryCode;
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
