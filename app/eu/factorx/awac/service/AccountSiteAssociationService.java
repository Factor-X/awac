package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.invitation.Invitation;

import java.util.List;

public interface AccountSiteAssociationService extends PersistenceService<AccountSiteAssociation> {

	public List<AccountSiteAssociation> findBySite(Site site);

    public List<AccountSiteAssociation> findByAccount(Account account);

}
