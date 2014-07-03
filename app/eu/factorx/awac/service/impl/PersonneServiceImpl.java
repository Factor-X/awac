package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Repository
public class PersonneServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {
 
    @SuppressWarnings("unchecked")
	public Person findByIdentifier(String identifier) {
        List<Person> resultList = JPA.em().createNamedQuery(Person.FIND_BY_IDENTIFIER).setParameter("identifier", identifier).getResultList();
        if (resultList.size() > 1) {
        	String errorMsg = "More than one account with identifier = '" + identifier + "'";
        	Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
        }
		return resultList.get(0);
    }
 
//	@SuppressWarnings("unchecked")
//	public List<Person> findByType(String type) {
//		return em.createNamedQuery(Person.FIND_BY_TYPE)
//				.setParameter("type", type).getResultList();
//	}

}
