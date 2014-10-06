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
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

// import for JAXB annotations -- JAXB stack

@Entity
public class SystemAdministrator extends Account {

	private static final long serialVersionUID = 1L;

	public SystemAdministrator() {
		super();
	}

	public SystemAdministrator(Organization organization, Person person, String identifier, String password) {
		super(organization, person, identifier, password);
	}
}
