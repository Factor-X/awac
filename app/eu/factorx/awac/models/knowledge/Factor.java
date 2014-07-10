package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "factor")
public class Factor extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public Factor() {
	}

	private String identifier;

	@ManyToOne
	private IndicatorCategory indicatorCategory;
	
	@ManyToOne
	private ActivityType activityType;
	
	@ManyToOne
	private ActivitySource activitySource;
	
	@ManyToOne
	private Unit unitIn;

	@ManyToOne
	private Unit unitOut;

	
	@OneToMany(mappedBy = "factor")
	private List<FactorValue> values;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String param) {
		this.identifier = param;
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