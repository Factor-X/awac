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

import java.sql.Timestamp;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.factorx.awac.common.AccountStatusType;
import eu.factorx.awac.models.AbstractEntity;

// import for TimeStamp
// imports for validation and constraints annotations
// import for Json annotations -- jackson stack
// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
@NamedQueries({
    @NamedQuery(name = Person.FIND_ALL, query = "select p from Person p"),
    @NamedQuery(name = Person.TOTAL_RESULT, query = "select count(p) from Person p"),
    @NamedQuery(name = Person.FIND_BY_IDENTIFIER, query = "select p from Person p where p.identifier = :identifier"),
//    @NamedQuery(name = Person.FIND_BY_TYPE, query = "select p from Person p where p.type = :type")
})
public class Person extends AbstractEntity {
	/**
	 * :identifier = ...
	 */
    public static final String FIND_BY_IDENTIFIER = "Person.findByIdentifier";
    
    public static final String FIND_BY_TYPE = "Person.findByType";

    private static final long serialVersionUID = 1L;

	@Version
	// in order to improve optimistic locking.
	private Timestamp lastUpdate;

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

	@Embedded
	private Address address;

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
		this.address = address;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public AccountStatusType getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatusType accountStatus) {
		this.accountStatus = accountStatus;
	}

	// public static Finder<Long, Person> find = new Finder<Long, Person>(
	// Long.class, Person.class
	// );
	//
//	 /**
//	 * Retrieve a Person from identifier
//	 */
//	 public static Person findByIdentifier(String identifier) {
//		 return find.where().eq("identifier", identifier).findUnique();
	
//	 }
	//
	// /**
	// * Rename a Person with newName
	// */
	// public static String rename(Long personId, String newName) {
	// play.Logger.info("entering Personne.rename - id: " + personId +
	// " new name: " + newName);
	// Person person = find.ref(personId);
	// person.lastname = newName;
	// person.update();
	// return newName;
	// }

	/**
	 * getter and setter of embedded object address useful to avoid NPE when
	 * saving an embedded object
	 */


} // end of Person class
