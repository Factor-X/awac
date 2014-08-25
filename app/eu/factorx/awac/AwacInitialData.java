package eu.factorx.awac;


import eu.factorx.awac.generated.AwacEnterpriseInitialData;
import eu.factorx.awac.generated.AwacMunicipalityInitialData;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.NotificationKind;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.UnitCategoryService;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.data.importer.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

@Component
public class AwacInitialData {

	@Autowired
	private MyrmexUnitsImporter myrmexUnitsImporter;

	@Autowired
	private CodeLabelImporter codeLabelImporter;

	@Autowired
	private AwacDataImporter awacDataImporter;

	@Autowired
	private AccountImporter accountImporter;

	@Autowired
	private TranslationImporter translationImporter;

	@Autowired
	private UnitCategoryService unitCategoryService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private AwacEnterpriseInitialData awacEnterpriseInitialData;

	@Autowired
	private AwacMunicipalityInitialData awacMunicipalityInitialData;

	public void createAwacInitialData(Session session) {

		Logger.info("===> CREATE AWAC INITIAL DATA -- START");
		long startTime = System.currentTimeMillis();

		// IMPORT MYRMEX UNITS
		myrmexUnitsImporter.run();

		// IMPORT CODES
		codeLabelImporter.run();

		// IMPORT AWAC DATA
		awacDataImporter.run();

		// ACCOUNTS
		accountImporter.run();

		// ACCOUNTS
		translationImporter.run();

		// PERIOD
		for (int i = 2013; i >= 2000; i--) {
			String label = String.valueOf(i);
			Period period1 = new Period(new PeriodCode(label), label);
			session.saveOrUpdate(period1);
		}

		// SURVEYS
		awacEnterpriseInitialData.createSurvey(session);
		awacMunicipalityInitialData.createSurvey(session);

		// NOTIFICATION
		Notification notification = new Notification();
		notification.setKind(NotificationKind.INFO);
		notification.setMessageFr("The website will be in maintenance tomorrow 2014-08-15.");
		session.saveOrUpdate(notification);

		Logger.info("===> CREATE AWAC INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
	}

}
