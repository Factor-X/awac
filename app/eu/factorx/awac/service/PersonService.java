package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Person;

public interface PersonService extends PersistenceService<Person> {

	Person findByIdentifier(String identifier);

	// List<Person> findByType(String type);

}
