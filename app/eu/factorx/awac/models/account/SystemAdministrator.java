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

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("system_admin")
public class SystemAdministrator extends Person {

	private static final long serialVersionUID = 1L;

	protected SystemAdministrator() {
	}

	public SystemAdministrator(String identifier, String password, String lastname, String firstname) {
		super(identifier, password, lastname, firstname, new Address("", "", "", ""));
	}

}
