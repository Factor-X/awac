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

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.factorx.awac.common.AccountStatusType;
import eu.factorx.awac.models.AbstractEntity;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Timestamp;

// import for TimeStamp
// imports for validation and constraints annotations
// import for Json annotations -- jackson stack
// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
@NamedQueries({
		@NamedQuery(name = Person.FIND_BY_IDENTIFIER, query = "select p from Person p where p.identifier = :identifier"),
})
public abstract class Person extends AbstractEntity {
	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_IDENTIFIER = "Person.findByIdentifier";

	public static final String FIND_BY_TYPE = "Person.findByType";

	private static final long serialVersionUID = 1L;

	@Required
	private String identifier;

	@JsonIgnore
	// ignore password field when render in JSON
	@XmlTransient
	// ignore password field when render in XML
	@Required
	private String password;

	@Required
	private String lastname;

	@Required
	private String firstname;

	@Email
	private String email;

	// set status to unactive by default.
	@Required
	private AccountStatusType accountStatus = AccountStatusType.UNACTIVE;

	protected Person() {
	}

	public Person(String identifier, String password, String lastname,
	              String firstname) {
		// Dans le constructeur de la classe Personne
		this.identifier = identifier;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	// for YAML load
	public Person(String identifier, String password, String lastname,
	              String firstname, Address address) {
		// Dans le constructeur de la classe Personne
		this.identifier = identifier;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public String toString() {
		String string = "";
		string = string.concat("identifier:" + identifier);
		string = string.concat("lastname:" + lastname);
		string = string.concat("firstname:" + firstname);
		string = string.concat("email:" + email);
		return string;
	}


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountStatusType getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatusType accountStatus) {
		this.accountStatus = accountStatus;
	}

}
