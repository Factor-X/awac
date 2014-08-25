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
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

// import for JAXB annotations -- JAXB stack

@Entity
public class Administrator extends Account {

	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}

	public Administrator(Long id){
		super();
		setId(id);
	}

	public Administrator(Organization organization, Person person, String identifier, String password, InterfaceTypeCode interfaceCode) {
		super(organization, person, identifier, password, interfaceCode);
	}
}
