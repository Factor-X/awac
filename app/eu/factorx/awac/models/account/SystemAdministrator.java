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

import javax.persistence.Entity;

import eu.factorx.awac.models.business.Organization;

// import for JAXB annotations -- JAXB stack

@Entity
public class SystemAdministrator extends Account {

	private static final long serialVersionUID = 1L;

	public SystemAdministrator() {
		super();
	}

	public SystemAdministrator(Organization organization, String lastname, String firstname, String email, String identifier, String password) {
		super(organization, lastname,firstname,email, identifier, password);
	}
}
