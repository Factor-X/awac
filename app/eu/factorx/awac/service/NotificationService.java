package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.Notification;

public interface NotificationService extends PersistenceService<Notification> {

	public List<Notification> findCurrentOnes();

}
