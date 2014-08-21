/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
package eu.factorx.awac.models.account;

import eu.factorx.awac.models.business.Organization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("admin")
public class Administrator extends Account {

	private static final long serialVersionUID = 1L;

	protected Administrator() {
	}

	public Administrator(Organization organization, String identifier, String password, String lastname, String firstname) {
		super(organization, identifier, password, lastname, firstname);
	}
}
