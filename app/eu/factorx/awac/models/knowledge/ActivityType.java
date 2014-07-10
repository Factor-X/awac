package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;

import eu.factorx.awac.models.AbstractEntity;

@Entity
public class ActivityType extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO i18n?
	private String identifier;

	protected ActivityType() {
		super();
	}

	public ActivityType(String identifier) {
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
