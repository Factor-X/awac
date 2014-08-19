package eu.factorx.awac.controllers;

import eu.factorx.awac.InMemoryData;
import eu.factorx.awac.InitializationThread;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.NotificationService;
import eu.factorx.awac.util.data.importer.TranslationImporter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Controller
public class AdminController extends Controller {


	@Autowired
	private ConversionService   conversionService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private SecuredController   securedController;
	@Autowired
	private CodeLabelService    codeLabelService;

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

	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result resetTranslations() {

		// reset code labels cache
		codeLabelService.resetCache();

		// remove old translations code labels
		removeCodeLabelsByList(CodeList.TRANSLATIONS_SURVEY);
		removeCodeLabelsByList(CodeList.TRANSLATIONS_INTERFACE);
		removeCodeLabelsByList(CodeList.TRANSLATIONS_ERROR_MESSAGES);

		// import new translations code labels
		new TranslationImporter(JPA.em().unwrap(Session.class)).run();

		// refresh InMemory data
		InMemoryData.translations = new HashMap<>();
		InitializationThread.createInMemoryTranslations();

		return(ok());
	}

	private void removeCodeLabelsByList(CodeList codeList) {
		int nbRemoved = codeLabelService.removeCodeLabelsByList(codeList);
		Logger.info("Removed {} code labels of list '{}'", nbRemoved, codeList);
	}
	
}
