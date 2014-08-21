package eu.factorx.awac;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import play.libs.F;
import play.libs.Yaml;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;

@Component
public class InitializationThread extends Thread {

	private boolean initialized;

	@Autowired
	private AwacInitialData awacInitialData;

	
	public InitializationThread() {
		super();
		initialized = false;
	}

	public void run() {
		JPA.withTransaction(new F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				createInitialData();
				initialized = true;
			}
		});

	}

	private void createInitialData() {
		// Get Hibernate session
		Session session = JPA.em().unwrap(Session.class);

		// Check if the database is empty
		@SuppressWarnings("unchecked")
		List<Administrator> administrators = session.createCriteria(Person.class).list();
		if (administrators.isEmpty()) {
			@SuppressWarnings("unchecked")
			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
			// save data into DB in relevant order.

			for (Object entity : all.get("organizations")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("administrators")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("accounts")) {
				session.saveOrUpdate(entity);
			}

			awacInitialData.createAwacInitialData(session);
		}
	}

	public boolean isInitialized() {
		return initialized;
	}
}
