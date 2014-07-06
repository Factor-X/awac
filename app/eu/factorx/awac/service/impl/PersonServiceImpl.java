package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Repository
public class PersonServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {

}
