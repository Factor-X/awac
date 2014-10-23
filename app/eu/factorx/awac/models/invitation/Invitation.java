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
import eu.factorx.awac.models.business.Organization;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "invitation")
@NamedQueries({
		@NamedQuery(name = Invitation.FIND_BY_KEY, query = "select i from Invitation i where i.genkey = :genkey"),
		@NamedQuery(name = Invitation.FIND_BY_EMAIL, query = "select i from Invitation i where i.email = :email"),
        @NamedQuery(name = Invitation.FIND_BY_EMAIL_AND_INTERFACE, query = "select i from Invitation i where i.email = :email and i.organization.interfaceCode = :interface")
})
public class Invitation extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_KEY = "Invitation.findByKey";
	public static final String FIND_BY_EMAIL = "Invitation.findByEmail";
	private static final long serialVersionUID = 1L;
    public static final java.lang.String FIND_BY_EMAIL_AND_INTERFACE = "Invitation_FIND_BY_EMAIL_AND_INTERFACE";


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

	@OneToOne
	private Organization organization;

	public Invitation() {
	}

	public Invitation(String email, String genkey, Organization organization) {
		this.setEmail(email);
		this.setGenkey(genkey);
		this.setOrganization(organization);

	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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


	@Override
	public String toString() {
		return "Invitation{" +
				"email=" + getEmail() +
				", genkey=" + getGenkey() +
				", organization=" + getOrganization().getName() +
				'}';
	}


}
