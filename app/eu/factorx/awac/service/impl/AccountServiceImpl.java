package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;

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
	public Account findByEmailAndInterfaceCode(String email, InterfaceTypeCode interfaceTypeCode){
		List<Account> resultList = JPA.em().createNamedQuery(Account.FIND_BY_EMAIL_AND_INTERFACE_CODE, Account.class)
				.setParameter("email", email).setParameter("interface_code", interfaceTypeCode).getResultList();
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
