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
package eu.factorx.awac.models.invitation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.factorx.awac.models.AuditedAbstractEntity;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "invitation")
@NamedQueries({
		@NamedQuery(name = Invitation.FIND_BY_KEY, query = "select i from Invitation i where i.genkey = :genkey"),
		@NamedQuery(name = Invitation.FIND_BY_EMAIL, query = "select i from Invitation i where i.email = :email"),
})
public class Invitation extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_KEY = "Invitation.findByKey";
	public static final String FIND_BY_EMAIL = "Invitation.findByEmail";
	private static final long serialVersionUID = 1L;


	@JsonIgnore
	// ignore password field when render in JSON
	@XmlTransient
	// ignore password field when render in XML
	@Required
	@Column(nullable = false)
	private String genkey;

	@Required
	@Column(nullable = false)
	private String email;

	public Invitation() {
	}

	public Invitation(String email, String genkey) {
		this.setEmail(email);
		this.setGenkey(genkey);
	}

	@Override
	public String toString() {
		return "Invitation{" +
				"email=" + getEmail() +
				", genkey=" + getGenkey() +
				'}';
	}

	public String getGenkey() {
		return genkey;
	}

	public void setGenkey(String genkey) {
		this.genkey = genkey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
