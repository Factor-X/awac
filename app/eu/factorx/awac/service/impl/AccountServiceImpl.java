package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;
import java.util.Set;

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

	@Override
	public List<Account> findByEmail(String email){
		List<Account> resultList = JPA.em().createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
				.setParameter("email", email).getResultList();

		return resultList;
	}

	@Override
	public Account saveOrUpdate(Account account){
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
