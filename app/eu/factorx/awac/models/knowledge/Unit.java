package eu.factorx.awac.models.knowledge;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.UnitCode;

@Entity
@Table(name = "unit")
@NamedQueries({ @NamedQuery(name = Unit.FIND_ALL, query = "select u from Unit u"),
                @NamedQuery(name = Unit.FIND_BY_SYMBOL, query = "select u from Unit u where u.symbol = :symbol"),
                @NamedQuery(name = Unit.FIND_BY_CODE, query = "select u from Unit u where u.unitCode = :unitCode"),
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unit extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String COLUMN_NAME_REF = "ref";
	public static final String COLUMN_NAME_SYMBOL = "symbol";
	public static final String FIND_ALL = "Unit.findAll";
    /**
     * @param code
     *            : a {@link String}
     */
    public static final String FIND_BY_CODE  = "Unit.findByCode";
	/**
	 * @param symbol
	 *            : a {@link String}
	 */
	public static final String FIND_BY_SYMBOL = "Unit.findBySymbol";

	// TODO labels? i18n?
	@Column(nullable = true)
	private String name = null;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = COLUMN_NAME_REF)) })
	private UnitCode unitCode;

	@Column(name = COLUMN_NAME_SYMBOL, nullable = false)
	private String symbol;

	@ManyToOne()
	private UnitCategory category;

	protected Unit() {
		super();
	}

	public Unit(UnitCode unitCode, String name, String symbol, UnitCategory category) {
		super();
		this.unitCode = unitCode;
		this.name = name;
		this.symbol = symbol;
		this.category = category;
	}

	public UnitCode getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(UnitCode unitCode) {
		this.unitCode = unitCode;
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

	public UnitCategory getCategory() {
		return category;
	}

	public void setCategory(UnitCategory category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Unit [unitCode=" + unitCode + ", symbol=" + symbol + "]";
	}

}