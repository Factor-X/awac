package eu.factorx.awac.models.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.account.Account;

@Entity
@Table(name = "organization")
@NamedQueries({
		@NamedQuery(name = Organization.FIND_BY_NAME, query = "select p from Organization p where p.name = :name"),
})
public class Organization extends Scope {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_NAME = "Organization.findByName";

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;

	private String naceCode;

	private String description;

	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Site> sites = new ArrayList<>();

	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	List<Account> accounts = new ArrayList<>();

	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE) // -> hibernate-specific annotation to fix DDL generation problem
	List<OrganizationEvent> events = new ArrayList<>();

	protected Organization() {
		super();
	}

	public Organization(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNaceCode() {
		return naceCode;
	}

	public void setNaceCode(String naceCode) {
		this.naceCode = naceCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> param) {
		this.sites = param;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<OrganizationEvent> getEvents() {
		return events;
	}

	public void setEvents(List<OrganizationEvent> events) {
		this.events = events;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Organization)) {
			return false;
		}
		Organization rhs = (Organization) obj;
		return new EqualsBuilder().append(this.name, rhs.name).isEquals();
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + name.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name='" + name + "']";
	}
}