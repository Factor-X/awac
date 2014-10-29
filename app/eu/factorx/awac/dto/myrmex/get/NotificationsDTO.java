package eu.factorx.awac.dto.myrmex.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;

public class NotificationsDTO extends DTO {

	private List<NotificationDTO> notifications;

	public NotificationsDTO() {
		this.notifications = new ArrayList<NotificationDTO>();
	}

	public NotificationsDTO(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}

	public List<NotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}
}
