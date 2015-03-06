package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AuditedAbstractEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Cache;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "factor_value")
@NamedQueries({
	@NamedQuery(
		name = FactorValue.REMOVE_ALL,
		query = "delete from FactorValue fv where fv.id is not null")
})
@Cacheable(true)
public class FactorValue extends AuditedAbstractEntity {

	public static final String REMOVE_ALL = "FactorValue.removeAll";
	private static final long serialVersionUID = 1L;
	private Double value;

	private Integer dateIn;

	private Integer dateOut;

	@ManyToOne(optional = false)
	private Factor factor;

	protected FactorValue() {
		super();
	}

	public FactorValue(Double value, Integer dateIn, Integer dateOut, Factor factor) {
		super();
		this.value = value;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.factor = factor;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double param) {
		this.value = param;
	}

	public Integer getDateIn() {
		return dateIn;
	}

	public void setDateIn(Integer param) {
		this.dateIn = param;
	}

	public Integer getDateOut() {
		return dateOut;
	}

	public void setDateOut(Integer param) {
		this.dateOut = param;
	}

	public Factor getFactor() {
		return factor;
	}

	public void setFactor(Factor param) {
		this.factor = param;
	}

}
