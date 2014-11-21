package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;

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
	public List<Account> findByEmail(String email){
		List<Account> resultList = JPA.em().createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
				.setParameter("email", email).getResultList();

		return resultList;
	}

	@Override
	public Account saveOrUpdate(Account account){
		if (account.getId() == null) {
			Account existingAccount = findByIdentifier(account.getIdentifier());
			if (existingAccount != null) {
				throw new MyrmexRuntimeException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
			}
		}
		if(account.getPassword().length()< 30){
			StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
			account.setPassword(standardPasswordEncoder.encode(account.getPassword()));
		}
		return super.saveOrUpdate(account);
	}

	@Override
	public boolean controlPassword(String password, Account account){
		StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
		return standardPasswordEncoder.matches(password, account.getPassword());
	}

}
