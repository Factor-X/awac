package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.business.Product;

import java.util.List;

public interface AccountProductAssociationService extends PersistenceService<AccountProductAssociation> {

	public List<AccountProductAssociation> findByProduct(Product product);

    public List<AccountProductAssociation> findByAccount(Account account);

}
