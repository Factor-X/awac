package eu.factorx.awac.util.data.importer;

import java.util.Map;

import javax.persistence.NoResultException;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.*;

@Component
public class AccountImporter extends WorkbookDataImporter {

	private static final String ACCOUNTS_WORKBOOK_PATH = "data_importer_resources/accounts/accounts.xls";

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private PersonService personService;
	
	public AccountImporter() {
		super();
	}

	@Override
	protected void importData() throws Exception {
		Map<String, Sheet> wbSheets = getWorkbookSheets(ACCOUNTS_WORKBOOK_PATH);

		// CREATE ORGANIZATIONS AND SITES
		Sheet sites = wbSheets.get("SITES");
		for (int i = 1; i < sites.getRows(); i++) {

			String org = getCellContent(sites, 0, i);
			String site = getCellContent(sites, 1, i);

			// do we have the organization in DB ?

			Organization organizationEntity;
			try {
				organizationEntity = (Organization) JPA.em().createQuery("select o from Organization o where o.name = :name").setParameter("name", org).getSingleResult();
			} catch (NoResultException ex) {
				organizationEntity = new Organization(org);
				organizationService.saveOrUpdate(organizationEntity);
				Logger.info("Created organization " + org);
			}

			// do we have the site in DB ?
			Site siteEntity;
			try {
				siteEntity = (Site) JPA.em().createQuery("select s from Site s where s.organization.id = :org_id and s.name = :name")
						.setParameter("org_id", organizationEntity.getId())
						.setParameter("name", site)
						.getSingleResult();
			} catch (NoResultException ex) {

				siteEntity = new Site(organizationEntity, site);
				siteService.saveOrUpdate(siteEntity);
				/*
				Scope scope = new Scope(siteEntity);
				scopeService.saveOrUpdate(scope);

				Logger.info("Created site (with scope) " + site + " for organization " + org);
				*/
			}

		}

		// CREATE ACCOUNTS
		Sheet accounts = wbSheets.get("ACCOUNTS");
		for (int i = 1; i < accounts.getRows(); i++) {

			String org = getCellContent(accounts, 0, i);
			String login = getCellContent(accounts, 1, i);
			String password = getCellContent(accounts, 2, i);
			String firstname = getCellContent(accounts, 3, i);
			String lastname = getCellContent(accounts, 4, i);
			String email = getCellContent(accounts, 5, i);

			// do we have the organization in DB ?
			Organization organizationEntity;
			try {
				organizationEntity = (Organization) JPA.em().createQuery("select o from Organization o where o.name = :name").setParameter("name", org).getSingleResult();
			} catch (NoResultException ex) {

				throw new Exception("Organization " + org + " not found when creating user " + login);
			}

			// do we have the account in DB ?
			Account accountEntity;
			try {
				accountEntity = (Account) JPA.em().createQuery("select o from Account o where o.identifier = :login").setParameter("login", login).getSingleResult();
			} catch (NoResultException ex) {
				Person person = new Person(lastname, firstname,email);

				personService.saveOrUpdate(person);

				accountEntity = new Account(organizationEntity, person, login, password, InterfaceTypeCode.ENTERPRISE);
				accountService.saveOrUpdate(accountEntity);
				Logger.info("Created user " + login + " for organization " + org);
			}
		}


	}
}
