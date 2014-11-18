package eu.factorx.awac.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.service.NotificationService;

@Component
public class NotificationServiceImpl extends AbstractJPAPersistenceServiceImpl<Notification> implements NotificationService {

	@Override
	public List<Notification> findCurrentOnes() {

		TypedQuery<Notification> query = JPA.em().createQuery("" +
				"select n from Notification n " +
				"where (n.since = null and n.until > :now) " +
				"or (n.since < :now and n.until > :now) " +
				"or (n.since < :now and n.until = null) " +
				"or (n.since = null and n.until = null)",
			Notification.class);
		query.setParameter("now", DateTime.now());

		return query.getResultList();
	}

}
