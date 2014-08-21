package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

public interface AccountService extends PersistenceService<Account> {

	public Account findByIdentifier(String identifier);

	public Account findByEmailAndInterfaceCode(String email, InterfaceTypeCode interfaceTypeCode);

}
