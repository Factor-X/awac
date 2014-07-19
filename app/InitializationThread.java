import eu.factorx.awac.AwacInitialData;
import eu.factorx.awac.InMemoryData;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
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

	private ApplicationContext ctx;

	public InitializationThread(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void run() {
		JPA.withTransaction(new F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				createInitialData(ctx);
				createInMemoryData(ctx);
			}
		});

	}


	private void createInMemoryData(ApplicationContext ctx) {
		createInMemoryTranslations(ctx);
	}

	private void createInMemoryTranslations(ApplicationContext ctx) {

		TranslationsDTO enDTO = new TranslationsDTO("EN");
		TranslationsDTO frDTO = new TranslationsDTO("FR");
		TranslationsDTO nlDTO = new TranslationsDTO("NL");

		List<CodeLabel> codeLabels = JPA.em().createQuery("select o from CodeLabel o where o.codeList = :cl", CodeLabel.class)
				.setParameter("cl", CodeList.TRANSLATIONS_SURVEY)
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
		List<Administrator> administrators = session.createCriteria(Person.class).list();
		if (administrators.isEmpty()) {
			@SuppressWarnings("unchecked")
			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data-awac.yml");
			// save data into DB in relevant order.

			System.out.println(all);
			for (Object entity : all.get("organizations")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("administrators")) {
				session.saveOrUpdate(entity);
			}
			for (Object entity : all.get("accounts")) {
				session.saveOrUpdate(entity);
			}

			AwacInitialData.createAwacInitialData(ctx, session);
		}
	}


}
