package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;

/**
 * Created by root on 3/07/14.
 */
public interface AccountService extends PersistenceService<Account> {

    public Account findByIdentifier(String identifier);

}
