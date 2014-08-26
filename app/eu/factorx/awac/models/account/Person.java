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

import javax.persistence.*;

import eu.factorx.awac.models.code.type.LanguageCode;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import eu.factorx.awac.models.AuditedAbstractEntity;

// import for TimeStamp
// imports for validation and constraints annotations
// import for Json annotations -- jackson stack
// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "person")
@NamedQueries({
		@NamedQuery(name = Person.FIND_BY_EMAIL, query = "select p from Person p where p.email = :email"),
})
public class Person extends AuditedAbstractEntity {
	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_EMAIL = "Person.findByEmail";

	private static final long serialVersionUID = 1L;

	@Required
	private String lastname;

	@Required
	private String firstname;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@Embedded
	@AttributeOverride(name = "key", column = @Column(name = "default_language"))
	private LanguageCode defaultLanguage = LanguageCode.FRENCH;


	protected Person() {
	}

	public Person(String lastname,
	              String firstname,String email) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
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

	public LanguageCode getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(LanguageCode defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	@Override
	public String toString() {
		return "Person{" +
				"lastname='" + lastname + '\'' +
				", firstname='" + firstname + '\'' +
				", email='" + email + '\'' +
				", defaultLanguage=" + defaultLanguage +
				'}';
	}
}
