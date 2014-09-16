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
package eu.factorx.awac.models.association;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Site;

// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "account_site_association")
@NamedQueries({
		@NamedQuery(name = AccountSiteAssociation.FIND_BY_SITE, query = "select a from AccountSiteAssociation a where a.site.id = :site_id"),
})
public class AccountSiteAssociation extends AuditedAbstractEntity {

	/**
	 * :identifier = ...
	 */
	public static final String FIND_BY_SITE = "AccountSiteAssociation.findBySite";
	private static final long serialVersionUID = 1L;


	@OneToOne
	private Site site;

	@OneToOne
	private Account account;


	public AccountSiteAssociation() {
	}

	public AccountSiteAssociation(Site site, Account account) {
		this.setSite(site);
		this.setAccount(account);
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	@Override
	public String toString() {
		return "AccountSiteAssociation{" +
				"accountList=" + getAccount().getIdentifier() +
				", siteId=" + getSite().getId() +
				'}';
	}


}
