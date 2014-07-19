package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "period")
public class Period extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String label;

	public Period() {
	}

	public Period(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String param) {
		this.label = param;
	}

}