package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.FormService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Repository
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
		if(resultList.size()==0){
			return null;
		}
		Form form = resultList.get(0);
		Hibernate.initialize(form.getQuestionSets());
		return form;
	}

}
