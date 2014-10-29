package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.models.Notification;

@Component
public class NotificationToNotificationDTOConverter implements Converter<Notification, NotificationDTO> {

	@Override
	public NotificationDTO convert(Notification notification) {
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setKind(notification.getKind());
		notificationDTO.setMessageFr(notification.getMessageFr());
		notificationDTO.setMessageNl(notification.getMessageNl());
		notificationDTO.setMessageEn(notification.getMessageEn());
		notificationDTO.setCreation(notification.getTechnicalSegment().getCreationDate());
		notificationDTO.setSince(notification.getSince());
		notificationDTO.setUntil(notification.getUntil());
		return notificationDTO;
	}
}
