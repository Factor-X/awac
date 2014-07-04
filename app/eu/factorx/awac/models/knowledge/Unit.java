package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "unit")
public class Unit extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToOne(optional = false)
	private UnitCategory category;

	public Unit() {
	}

	public Unit(String name, UnitCategory category) {
		super();
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public UnitCategory getCategory() {
		return category;
	}

	public void setCategory(UnitCategory category) {
		this.category = category;
	}

}