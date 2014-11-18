package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.FormProgressService;


@Component
public class FormProgressServiceImpl extends AbstractJPAPersistenceServiceImpl<FormProgress> implements FormProgressService {

    @Override
    public List<FormProgress> findByPeriodAndByScope(Period period, Scope scope) {
        List<FormProgress> resultList = JPA.em().createNamedQuery(FormProgress.FIND_BY_PERIOD_AND_SCOPE, FormProgress.class)
                .setParameter("period", period).setParameter("scope",scope).getResultList();
        return resultList;
    }

	@Override
	public FormProgress findByPeriodAndByScopeAndForm(Period period, Scope scope, Form form) {
		List<FormProgress> resultList = JPA.em().createNamedQuery(FormProgress.FIND_BY_PERIOD_AND_SCOPE_AND_FORM, FormProgress.class)
				.setParameter("period", period).setParameter("scope", scope).setParameter("form",form).getResultList();

		if (resultList.size() > 1) {
			String errorMsg = "More than one formProgress for criteria : period :  "+period+", scope : "+scope+", form : "+form;
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}
}
