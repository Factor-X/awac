package eu.factorx.awac.models.knowledge;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "unit_category")
public class UnitCategory extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
