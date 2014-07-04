package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "unit_category")
public class UnitCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String code;

	@OneToMany(mappedBy = "category")
	private List<Unit> units;

	public UnitCategory() {
		super();
	}

	public UnitCategory(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
