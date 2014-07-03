package eu.factorx.awac.models.reporting;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "report")
public class Report extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public Report() {
	}

	@ManyToMany
	private List<Indicator> indicators;

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> param) {
		this.indicators = param;
	}

}