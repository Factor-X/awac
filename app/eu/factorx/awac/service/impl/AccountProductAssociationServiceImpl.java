package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.AccountProductAssociationService;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class AccountProductAssociationServiceImpl extends AbstractJPAPersistenceServiceImpl<AccountProductAssociation> implements AccountProductAssociationService {

    @Override
    public List<AccountProductAssociation> findByProduct(Product product) {
        List<AccountProductAssociation> resultList = JPA.em().createNamedQuery(AccountProductAssociation.FIND_BY_PRODUCT, AccountProductAssociation.class)
                .setParameter("product_id", product.getId()).getResultList();
        return resultList;
    }

    @Override
    public List<AccountProductAssociation> findByAccount(Account account) {
        List<AccountProductAssociation> resultList = JPA.em().createNamedQuery(AccountProductAssociation.FIND_BY_ACCOUNT, AccountProductAssociation.class)
                .setParameter("account", account).getResultList();
        return resultList;
    }

}
