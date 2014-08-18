package eu.factorx.awac.service;

import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.forms.Form;

import java.util.List;

public interface NotificationService extends PersistenceService<Notification> {

	public List<Notification> findCurrentOnes();

}
