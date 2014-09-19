package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.AccountSiteAssociationService;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class AccountSiteAssociationServiceImpl extends AbstractJPAPersistenceServiceImpl<AccountSiteAssociation> implements AccountSiteAssociationService {

    @Override
    public List<AccountSiteAssociation> findBySite(Site site) {
        List<AccountSiteAssociation> resultList = JPA.em().createNamedQuery(AccountSiteAssociation.FIND_BY_SITE, AccountSiteAssociation.class)
                .setParameter("site_id", site.getId()).getResultList();
        return resultList;
    }

    @Override
    public List<AccountSiteAssociation> findByAccount(Account account) {
        List<AccountSiteAssociation> resultList = JPA.em().createNamedQuery(AccountSiteAssociation.FIND_BY_ACCOUNT, AccountSiteAssociation.class)
                .setParameter("account", account).getResultList();
        return resultList;
    }

}
