package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Repository
public class PersonServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {

}
