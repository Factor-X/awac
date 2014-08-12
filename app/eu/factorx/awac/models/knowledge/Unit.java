package eu.factorx.awac.models.knowledge;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "unit")
@NamedQueries({
		@NamedQuery(name = Unit.FIND_ALL, query = "select u from Unit u"),
		@NamedQuery(name = Unit.FIND_BY_SYMBOL, query = "select u from Unit u where u.symbol = :symbol"),
})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Unit extends AbstractEntity {

	public static final String COLUMN_NAME_REF = "ref";
	public static final String COLUMN_NAME_SYMBOL = "symbol";
	public static final String FIND_ALL = "Unit.findAll";
	/**
	 * @param symbol : a {@link String}
	 */
	public static final String FIND_BY_SYMBOL = "Unit.findBySymbol";
	private static final long serialVersionUID = 1L;
	// TODO labels? i18n?
	@Column(nullable = true)
	private String name = null;

	@Column(name = COLUMN_NAME_REF, nullable = false, unique = true)
	private String ref;

	@Column(name = COLUMN_NAME_SYMBOL, nullable = false)
	private String symbol;

	@ManyToOne()
	private UnitCategory category;

	protected Unit() {
		super();
	}

	public Unit(String ref, String name, String symbol, UnitCategory category) {
		super();
		this.ref = ref;
		this.name = name;
		this.symbol = symbol;
		this.category = category;
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

	public UnitCategory getCategory() {
		return category;
	}

	public void setCategory(UnitCategory category) {
		this.category = category;
	}

}