package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.*;

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
    @Autowired
    private PeriodService periodService;

    public void run() {
        Logger.info("Linking accounts to sites (enterprise data only)");

        for (Organization organization : organizationService.findAll()) {
            Site site = organization.getSites().get(0);
            List<Account> accounts = organization.getAccounts();
            for (Account account : accounts) {

                accountSiteAssociationService.saveOrUpdate(new AccountSiteAssociation(site, account));
                Logger.info("Linked account " + account + " to site " + site);

                Logger.info("Linking sites to periods");
            }

            List<Period> periodList = new ArrayList<>();

            if (accounts.get(0).getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {
                periodList.addAll(getDistinctPeriodsBySite(site));
            } else {
                periodList.addAll(periodService.findAll());
            }
            site.setListPeriodAvailable(periodList);

            siteService.saveOrUpdate(site);
        }
    }

    private List<Period> getDistinctPeriodsBySite(Site site) {

        List<QuestionSetAnswer> questionSetAnswerList = questionSetAnswerService.findByScope(site);

        List<Period> list = new ArrayList<>();

        //add 2013
        list.add(periodService.findByCode(PeriodCode.P2013));

        for(QuestionSetAnswer qsa : questionSetAnswerList){
            if(!list.contains(qsa.getPeriod())) {
                list.add(qsa.getPeriod());
            }
        }


        return list;
    }

}
