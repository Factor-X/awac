package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.account.Account;

public interface AccountService extends PersistenceService<Account> {

	public Account findByIdentifier(String identifier);

	public List<Account> findByEmail(String email);

	public boolean controlPassword(String password, Account account);

}
