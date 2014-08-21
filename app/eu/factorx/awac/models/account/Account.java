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
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;

import javax.persistence.*;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("account")
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_IDENTIFIER, query = "select p from Account p where p.identifier = :identifier"),
		@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select p from Account p where p.email = :email"),
})
public class Account extends Person {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_IDENTIFIER = "Account.findByIdentifier";
	public static final String FIND_BY_EMAIL = "Account.findByEmail";
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Organization organization;

	protected Account() {
		super();
	}

    /*
     * Constructor
     */

	public Account(Organization organization, String identifier, String password, String lastname, String firstname) {
		super(identifier, password, lastname, firstname, new Address("", "", "", ""));
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String toString() {
		String string = super.toString();
		return string;
	}
}
