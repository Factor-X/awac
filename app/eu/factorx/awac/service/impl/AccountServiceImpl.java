package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.AccountService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class AccountServiceImpl extends AbstractJPAPersistenceServiceImpl<Account> implements AccountService {

	@Override
	public Account findByIdentifier(String identifier) {
		List<Account> resultList = JPA.em().createNamedQuery(Account.FIND_BY_IDENTIFIER, Account.class)
				.setParameter("identifier", identifier).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one account with identifier = '" + identifier + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Account findByEmail(String email){
		List<Account> resultList = JPA.em().createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
				.setParameter("email", email).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one account with identifier = '" + email + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}
}
