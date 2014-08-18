package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class AdminController extends Controller {


	@Autowired
	private ConversionService   conversionService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private SecuredController   securedController;

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

}
