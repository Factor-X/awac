package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;

<<<<<<< HEAD
public interface AccountService extends PersistenceService<Account> {
=======
/**
 * Created by root on 3/07/14.
 */
public interface AccountService extends PersistenceService<Account> {

    public Account findByIdentifier(String identifier);

>>>>>>> b6dc4c0925243c4ac737e5336f5b23602cc0d12b
}
