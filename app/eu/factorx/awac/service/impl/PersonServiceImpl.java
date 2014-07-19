package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;
import org.springframework.stereotype.Repository;

@Repository
public class PersonServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {

}
