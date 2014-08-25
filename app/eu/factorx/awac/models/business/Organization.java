package eu.factorx.awac.models.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;

@Entity
@Table(name = "organization")
@NamedQueries({
		@NamedQuery(name = Organization.FIND_BY_NAME, query = "select p from Organization p where p.name = :name"),
})
public class Organization extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_NAME = "Organization.findByName";

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Site> sites = new ArrayList<>();
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	List<Account> accounts = new ArrayList<>();

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Organization)) return false;
		if (!super.equals(o)) return false;

		Organization that = (Organization) o;

		if (!name.equals(that.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + name.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Organization{" +
				super.toString()+
				"name='" + name + '\'' +
				'}';
	}
}