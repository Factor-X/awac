package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;

public interface AccountService extends PersistenceService<Account> {

	public Account findByIdentifier(String identifier);

	public Account findByEmail(String email);

}
