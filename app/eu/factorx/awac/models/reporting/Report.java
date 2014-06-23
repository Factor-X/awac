package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import eu.factorx.awac.models.reporting.Indicator;

import java.util.Collection;

import javax.persistence.ManyToMany;

@Entity
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	public Report() {
	}

	@Id
	private long id;
	@ManyToMany
	private Collection<Indicator> indicators;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<Indicator> getIndicators() {
	    return indicators;
	}

	public void setIndicators(Collection<Indicator> param) {
	    this.indicators = param;
	}

}