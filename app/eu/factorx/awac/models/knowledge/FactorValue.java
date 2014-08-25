package eu.factorx.awac.models.knowledge;

import java.util.Date;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;

@Entity
@Table(name = "factor_value")
public class FactorValue extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	private Double value;

	@Temporal(TemporalType.DATE)
	private Date dateIn;

	@Temporal(TemporalType.DATE)
	private Date dateOut;

	@ManyToOne(optional = false)
	private Factor factor;

	protected FactorValue() {
		super();
	}

	public FactorValue(Double value, Date dateIn, Date dateOut, Factor factor) {
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