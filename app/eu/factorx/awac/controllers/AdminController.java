package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.NotificationService;
import eu.factorx.awac.util.data.importer.CodeLabelImporter;
import eu.factorx.awac.util.data.importer.TranslationImporter;

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
	
	@Autowired
	private TranslationImporter translationImporter;

	@Autowired
	private CodeLabelImporter codeLabelImporter;

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
		// import new translations code labels
		translationImporter.run();

		return (ok());
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result resetCodeLabels() {
		// reset code labels cache
		codeLabelService.resetCache();
		// import new translations code labels
		codeLabelImporter.run();

		return (ok());
	}

}
