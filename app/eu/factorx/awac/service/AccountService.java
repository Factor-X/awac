package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

import java.util.List;
import java.util.Set;

public interface AccountService extends PersistenceService<Account> {

	public Account findByIdentifier(String identifier);

	public List<Account> findByEmail(String email);

	public boolean controlPassword(String password, Account account);

}
