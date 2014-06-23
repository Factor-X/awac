package eu.factorx.awac.models.reporting;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class Report extends Model {

	private static final long serialVersionUID = 1L;

	public Report() {
	}

	@Id
	private long id;
	@ManyToMany
	private List<Indicator> indicators;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> param) {
		this.indicators = param;
	}

}