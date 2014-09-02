package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.util.data.importer.badImporter.BADImporter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.generated.AwacMunicipalityInitialData;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.FormService;
import eu.factorx.awac.service.NotificationService;
import eu.factorx.awac.util.data.importer.AwacDataImporter;
import eu.factorx.awac.util.data.importer.CodeLabelImporter;
import eu.factorx.awac.util.data.importer.TranslationImporter;

@org.springframework.stereotype.Controller
public class AdminController extends AbstractController {


	@Autowired
	private ConversionService   conversionService;
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CodeLabelService    codeLabelService;
	@Autowired
	private FormService 		formService;
	
	@Autowired
	private TranslationImporter translationImporter;

	@Autowired
	private CodeLabelImporter 	codeLabelImporter;

	@Autowired
	private AwacDataImporter 	awacDataImporter;

	@Autowired
	private AwacMunicipalityInitialData awacMunicipalityInitialData;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	// @AuthenticatedAsSystemAdmin
	public Result getAllNotifications() {
		Account currentUser = securedController.getCurrentUser();
		validateUserRights(currentUser);
		List<Notification> all = notificationService.findAll();
		List<NotificationDTO> dtos = new ArrayList<>();
		for (Notification notification : all) {
			dtos.add(conversionService.convert(notification, NotificationDTO.class));
		}
		return ok(new NotificationsDTO(dtos));
	}


	private void validateUserRights(Account currentUser) {
//		if (!securedController.isSystemAdministrator()) {
//			throw new RuntimeException("" +
//				"You are not allowed to access admin section. " +
//				"Your user login '" + currentUser.getIdentifier() + "' has been reported as potential hacker. " +
//				"Any further attempt will result in an investigation.");
//		}
	}

	@Transactional(readOnly = false)
	public Result resetCodeLabels() {
		if (!Play.application().isDev()) {
			return unauthorized();
		}
		// reset code labels cache
		codeLabelService.resetCache();
		// import code labels
		codeLabelImporter.run();
		// import translations
		translationImporter.run();

		return (ok());
	}

	@Transactional(readOnly = false)
	public Result resetIndicatorsAndFactors() {
		if (!Play.application().isDev()) {
			return unauthorized();
		}
		// import indicators and factors
		awacDataImporter.run();

		return (ok());
	}

	@Transactional(readOnly = false)
	public Result createMunicipalitySurveyData() {
		if (!Play.application().isDev()) {
			return unauthorized();
		}
		if (formService.findByIdentifier("TAB_C1") != null) {
			throw new RuntimeException("Municipality Survey DataCell has already been created");
		}
		awacMunicipalityInitialData.createSurvey(JPA.em().unwrap(Session.class));

		return (ok());
	}

	@Transactional(readOnly = true)
	public Result runBADImporter(){

		BADImporter badImporter = new BADImporter();

		badImporter.run();


		return (ok());
	}
}
