package eu.factorx.awac.util.data.importer;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.SiteService;

@Component
public class AccountSiteAssociationImporter {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;

	public void run() {
		Logger.info("Linking accounts to sites (enterprise data only)");
		for (Organization organization : organizationService.findAll()) {
			Site site = organization.getSites().get(0);
			List<Account> accounts = organization.getAccounts();
			for (Account account : accounts) {
				if (InterfaceTypeCode.ENTERPRISE.equals(account.getInterfaceCode())) {
					accountSiteAssociationService.saveOrUpdate(new AccountSiteAssociation(site, account));
					Logger.info("Linked account " + account + " to site " + site);
				}
			}
		}

		Logger.info("Linking sites to periods");
		Map<Site, Set<Period>> periodsBySites = getDistinctPeriodsBySite(questionSetAnswerService.findAll());
		for (Entry<Site, Set<Period>> entry : periodsBySites.entrySet()) {
			Site site = entry.getKey();
			List<Period> listPeriodAvailable = new ArrayList<>(entry.getValue());
			site.setListPeriodAvailable(listPeriodAvailable);
			siteService.saveOrUpdate(site);
			Logger.info("Linked site " + site + " to period(s): " + StringUtils.join(listPeriodAvailable, ','));
		}
	}

	private Map<Site, Set<Period>> getDistinctPeriodsBySite(List<QuestionSetAnswer> qsa) {
		Map<Site, Set<Period>> res = new HashMap<>();
		for (QuestionSetAnswer questionSetAnswer : qsa) {
			Site site = (Site) questionSetAnswer.getScope();
			Period period = questionSetAnswer.getPeriod();
			if (!res.containsKey(site)) {
				res.put(site, new HashSet<Period>());
			}
			res.get(site).add(period);
		}
		return res;
	}
}
