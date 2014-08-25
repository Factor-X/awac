package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Repository
public class PersonServiceImpl extends AbstractJPAPersistenceServiceImpl<Person> implements PersonService {

	@Override
	public Person getByEmail(String email){
		List<Person> resultList = JPA.em().createNamedQuery(Person.FIND_BY_EMAIL, Person.class)
				.setParameter("email", email).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one person with email = '" + email + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}
