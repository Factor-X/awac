package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Component
public class PersonneServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {

}
