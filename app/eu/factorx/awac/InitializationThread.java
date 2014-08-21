package eu.factorx.awac;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;

import play.db.jpa.JPA;
import play.libs.F;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class InitializationThread extends Thread {

	private boolean initialized;

	private ApplicationContext ctx;

	public InitializationThread(ApplicationContext ctx) {
		this.ctx = ctx;
		initialized = false;
	}

	public void run() {
		JPA.withTransaction(new F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				createInitialData(ctx);
				createInMemoryData();
				initialized = true;
			}
		});

	}


	public static void createInMemoryData() {
		createInMemoryTranslations();
	}

	public static void createInMemoryTranslations() {

		TranslationsDTO enDTO = new TranslationsDTO("EN");
		TranslationsDTO frDTO = new TranslationsDTO("FR");
		TranslationsDTO nlDTO = new TranslationsDTO("NL");

		List<CodeLabel> codeLabels;

		// SURVEY
		codeLabels = JPA.em().createQuery("select o from CodeLabel o where o.codeList = :cl", CodeLabel.class)
				.setParameter("cl", CodeList.TRANSLATIONS_SURVEY)
				.getResultList();

		for (CodeLabel codeLabel : codeLabels) {
			enDTO.put(codeLabel.getKey(), codeLabel.getLabelEn());
			frDTO.put(codeLabel.getKey(), codeLabel.getLabelFr());
			nlDTO.put(codeLabel.getKey(), codeLabel.getLabelNl());
		}

		// INTERFACE
		codeLabels = JPA.em().createQuery("select o from CodeLabel o where o.codeList = :cl", CodeLabel.class)
				.setParameter("cl", CodeList.TRANSLATIONS_INTERFACE)
				.getResultList();

		for (CodeLabel codeLabel : codeLabels) {
			enDTO.put(codeLabel.getKey(), codeLabel.getLabelEn());
			frDTO.put(codeLabel.getKey(), codeLabel.getLabelFr());
			nlDTO.put(codeLabel.getKey(), codeLabel.getLabelNl());
		}

		// ERROR MESSAGES
		codeLabels = JPA.em().createQuery("select o from CodeLabel o where o.codeList = :cl", CodeLabel.class)
				.setParameter("cl", CodeList.TRANSLATIONS_ERROR_MESSAGES)
				.getResultList();

		for (CodeLabel codeLabel : codeLabels) {
			enDTO.put(codeLabel.getKey(), codeLabel.getLabelEn());
			frDTO.put(codeLabel.getKey(), codeLabel.getLabelFr());
			nlDTO.put(codeLabel.getKey(), codeLabel.getLabelNl());
		}

		InMemoryData.translations.put("EN", enDTO);
		InMemoryData.translations.put("FR", frDTO);
		InMemoryData.translations.put("NL", nlDTO);

	}

	private void createInitialData(ApplicationContext ctx) {
		// Get Hibernate session
		Session session = JPA.em().unwrap(Session.class);

		// Check if the database is empty
		@SuppressWarnings("unchecked")

		List<Account> account = session.createCriteria(Account.class).list();

		if(account.isEmpty()){
			AwacInitialData.createAwacInitialData(ctx, session);
		}
	}

	public boolean isInitialized() {
		return initialized;
	}
}
