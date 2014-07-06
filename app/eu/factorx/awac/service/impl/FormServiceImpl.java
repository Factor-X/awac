package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Hibernate;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.FormService;

public class FormServiceImpl extends AbstractJPAPersistenceServiceImpl<Form> implements FormService {

	@Override
	public Form findByIdentifier(String identifier) {
		List<Form> resultList = JPA.em().createNamedQuery(Form.FIND_BY_IDENTIFIER, Form.class)
				.setParameter("identifier", identifier).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one form with identifier = '" + identifier + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		Form form = resultList.get(0);
		Hibernate.initialize(form.getQuestions());
		return form;
	}

}
