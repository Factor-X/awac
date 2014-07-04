package eu.factorx.awac.models.knowledge;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "factor_value")
public class FactorValue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private Double value;

	@Temporal(TemporalType.DATE)
	private Date dateIn;

	@Temporal(TemporalType.DATE)
	private Date dateOut;

	@ManyToOne(optional = false)
	private Factor factor;

	public FactorValue() {
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double param) {
		this.value = param;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date param) {
		this.dateIn = param;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date param) {
		this.dateOut = param;
	}

	public Factor getFactor() {
		return factor;
	}

	public void setFactor(Factor param) {
		this.factor = param;
	}

}