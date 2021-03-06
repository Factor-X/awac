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

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import eu.factorx.awac.models.code.type.LanguageCode;
import org.apache.commons.lang3.builder.EqualsBuilder;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.forms.VerificationRequest;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = Account.FIND_BY_IDENTIFIER, query = "select p from Account p where p.identifier = :identifier"),
		@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
})
public class Account extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_IDENTIFIER = "Account.findByIdentifier";
	public static final String FIND_BY_EMAIL = "Account.findByEmail";
	private static final long serialVersionUID = 1L;
    public static final java.lang.String FIND_BY_EMAIL_AND_INTERFACE = "Account_FIND_BY_EMAIL_AND_INTERFACE";

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
	private Organization organization;

	//TODO NEW
	@Required
	private String lastname;

	@Required
	private String firstname;

	@Constraints.Email
	@Column(nullable = false, unique = true)
	private String email;

	@Embedded
	@AttributeOverride(name = "key", column = @Column(name = "default_language", columnDefinition = "character varying(2) not null default 'FR'"))
	private LanguageCode defaultLanguage = LanguageCode.FRENCH;

	//TODO NEW

	@Column(nullable = false, unique = true)
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

    @Column(nullable = false, name = "is_main_verifier")
    private Boolean isMainVerifier = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mm_verifierrequest_account",
            joinColumns  = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "verifier_id", referencedColumnName = "id"))
    private Set<VerificationRequest> verificationRequestList;

	public Account() {
	}


	public Account(Organization organization, String lastname, String firstname, String email, String identifier, String password) {
		this.organization = organization;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.identifier = identifier;
		this.password = password;
	}

	public Account(Organization organization, String lastname, String firstname, String email, String identifier, String password, Boolean active, Boolean needChangePassword, Boolean isAdmin) {
		this.organization = organization;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.identifier = identifier;
		this.password = password;
		this.active = active;
		this.needChangePassword = needChangePassword;
		this.isAdmin = isAdmin;
	}

    public Set<VerificationRequest> getVerificationRequestList() {
        return verificationRequestList;
    }

    public void setVerificationRequestList(Set<VerificationRequest> verificationRequestList) {
        this.verificationRequestList = verificationRequestList;
    }

    public Boolean getIsMainVerifier() {
        return isMainVerifier;
    }

    public void setIsMainVerifier(Boolean isMainVerifier) {
        this.isMainVerifier = isMainVerifier;
    }

    public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (! (obj instanceof Account)) {
			return false;
		}
		Account rhs = (Account) obj;
		return new EqualsBuilder().append(this.identifier, rhs.identifier).append(this.organization.getInterfaceCode(), rhs.organization.getInterfaceCode()).isEquals();
	}

	@Override
	public String toString() {
		return "Account{" +
			"organization=" + organization +
			", lastname='" + lastname + '\'' +
			", firstname='" + firstname + '\'' +
			", email='" + email + '\'' +
			", defaultLanguage=" + defaultLanguage +
			", identifier='" + identifier + '\'' +
			", password='" + password + '\'' +
			", active=" + active +
			", needChangePassword=" + needChangePassword +
			", isAdmin=" + isAdmin +
			", isMainVerifier=" + isMainVerifier +
			", verificationRequestList=" + verificationRequestList +
			'}';
	}
}
