package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.service.DriverService;
import eu.factorx.awac.service.PersistenceService;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

@Repository
public class DriverServiceImpl implements DriverService {

	@Override
	public List<Driver> findAll() {
		return (List<Driver>) JPA.em().createQuery(String.format("select e from %s e", Driver.class.getName())).getResultList();
	}

}