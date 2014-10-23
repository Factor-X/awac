package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

public interface AccountService extends PersistenceService<Account> {

	public Account findByIdentifier(String identifier);

	public List<Account> findByEmail(String email);

	public boolean controlPassword(String password, Account account);

    public Account findByEmailAndInterface(String email, InterfaceTypeCode interfaceTypeCode);
}
