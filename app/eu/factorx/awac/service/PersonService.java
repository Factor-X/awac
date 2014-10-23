package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Person;

import java.util.List;

public interface PersonService extends PersistenceService<Person> {

	public Person getByEmail(String email);
}


