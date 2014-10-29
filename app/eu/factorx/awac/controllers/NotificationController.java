package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Result;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.service.NotificationService;

@org.springframework.stereotype.Controller
public class NotificationController extends AbstractController {

	@Autowired
	private ConversionService   conversionService;
	@Autowired
	private NotificationService notificationService;

	@Transactional(readOnly = true)
	public Result getNotifications() {
		List<Notification> currentOnes = notificationService.findCurrentOnes();
		List<NotificationDTO> dtos = new ArrayList<>();
		for (Notification notification : currentOnes) {
			dtos.add(conversionService.convert(notification, NotificationDTO.class));
		}
		return ok(new NotificationsDTO(dtos));
	}

}
