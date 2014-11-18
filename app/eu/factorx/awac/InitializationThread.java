package eu.factorx.awac;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import play.libs.F;
import eu.factorx.awac.models.account.Account;

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
		List<Account> account = session.createCriteria(Account.class).list();

		if (account.isEmpty()) {
			awacInitialData.createAwacInitialData(session);
		}
	}

	public boolean isInitialized() {
		return initialized;
	}
}
