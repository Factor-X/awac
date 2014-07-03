package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.account.Person;

public interface PersonService extends PersistenceService<Person> {

	List<Person> findByIdentifier(String identifier);
	
//	List<Person> findByType(String type);

}
