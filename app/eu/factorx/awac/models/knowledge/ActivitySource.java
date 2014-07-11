package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;

import eu.factorx.awac.models.AbstractEntity;

@Entity
public class ActivitySource extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO i18n?
	private String identifier;

	protected ActivitySource() {
		super();
	}

	public ActivitySource(String identifier) {
		super();
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
