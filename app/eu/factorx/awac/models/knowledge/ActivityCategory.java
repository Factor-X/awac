package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.AbstractEntity;

@Entity
public class ActivityCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO i18n?
	private String identifier;

	@ManyToOne(optional = true)
	private ActivityCategory parentCategory;

	protected ActivityCategory() {
		super();
	}

	public ActivityCategory(String identifier, ActivityCategory parentCategory) {
		super();
		this.identifier = identifier;
		this.parentCategory = parentCategory;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ActivityCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ActivityCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

}
