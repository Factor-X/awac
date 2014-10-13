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
import javax.xml.bind.annotation.XmlTransient;

import eu.factorx.awac.models.forms.VerificationRequest;
import play.data.validation.Constraints.Required;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

import java.util.List;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_IDENTIFIER, query = "select p from Account p where p.identifier = :identifier"),
		@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a, Person p where p.email = :email and a.person = p"),
})
public class Account extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_IDENTIFIER = "Account.findByIdentifier";
	public static final String FIND_BY_EMAIL = "Account.findByEmail";
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
	private Organization organization;

	@ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
	private Person person;

	@Column(nullable = false)
	private String identifier;

	@JsonIgnore
	// ignore password field when render in JSON
	@XmlTransient
	// ignore password field when render in XML
	@Required
	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean active = Boolean.TRUE;

	@Column(nullable = false, name = "need_change_password")
	private Boolean needChangePassword = Boolean.FALSE;

	@Column(nullable = false, name = "is_admin")
	private Boolean isAdmin = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mm_verifierrequest_account",
            joinColumns  = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "verifier_id", referencedColumnName = "id"))
    private List<VerificationRequest> verificationRequestList;

	public Account() {
	}

	public Account(Organization organization, Person person, String identifier, String password) {
		this.organization = organization;
		this.person = person;
		this.identifier = identifier;
		this.password = password;
	}

	public Account(Organization organization, Person person, String identifier, String password, Boolean active, Boolean needChangePassword, Boolean isAdmin) {
		this.organization = organization;
		this.person = person;
		this.identifier = identifier;
		this.password = password;
		this.active = active;
		this.needChangePassword = needChangePassword;
		this.isAdmin = isAdmin;
	}

    public List<VerificationRequest> getVerificationRequestList() {
        return verificationRequestList;
    }

    public void setVerificationRequestList(List<VerificationRequest> verificationRequestList) {
        this.verificationRequestList = verificationRequestList;
    }

    public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getNeedChangePassword() {
		return needChangePassword;
	}

	public void setNeedChangePassword(Boolean needChangePassword) {
		this.needChangePassword = needChangePassword;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Account{" +
				"organization=" + organization +
				", person=" + person +
				", identifier='" + identifier + '\'' +
				", password='" + password + '\'' +
				", active=" + active +
				", needChangePassword=" + needChangePassword +
				", isAdmin=" + isAdmin +
				'}';
	}
}
