package eu.factorx.awac.models.reporting;

import javax.persistence.Entity;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "indicator")
public class Indicator extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public Indicator() {
	}

}