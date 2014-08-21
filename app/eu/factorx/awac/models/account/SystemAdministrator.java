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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("system_admin")
public class SystemAdministrator extends Account {

	private static final long serialVersionUID = 1L;

	public SystemAdministrator() {
		super();
	}

	public SystemAdministrator(Organization organization, Person person, String identifier, String password, InterfaceTypeCode interfaceCode) {
		super(organization, person, identifier, password, interfaceCode);
	}
}
