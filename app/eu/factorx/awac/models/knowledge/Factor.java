package eu.factorx.awac.models.knowledge;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "factor")
public class Factor implements Serializable {

	private static final long serialVersionUID = 1L;

	public Factor() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	private Unit unitIn;
	@ManyToOne
	private Unit unitOut;
	@OneToMany(mappedBy = "factor")
	private List<FactorValue> values;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public Unit getUnitIn() {
		return unitIn;
	}

	public void setUnitIn(Unit param) {
		this.unitIn = param;
	}

	public Unit getUnitOut() {
		return unitOut;
	}

	public void setUnitOut(Unit param) {
		this.unitOut = param;
	}

	public List<FactorValue> getValues() {
		return values;
	}

	public void setValues(List<FactorValue> param) {
		this.values = param;
	}

}