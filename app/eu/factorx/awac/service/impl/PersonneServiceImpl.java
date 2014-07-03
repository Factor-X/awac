package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Repository
public class PersonneServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {
 
    @SuppressWarnings("unchecked")
	public List<Person> findByIdentifier(String identifier) {
        return JPA.em().createNamedQuery(Person.FIND_BY_IDENTIFIER).setParameter("identifier", identifier).getResultList();
    }
 
//	@SuppressWarnings("unchecked")
//	public List<Person> findByType(String type) {
//		return em.createNamedQuery(Person.FIND_BY_TYPE)
//				.setParameter("type", type).getResultList();
//	}

}
