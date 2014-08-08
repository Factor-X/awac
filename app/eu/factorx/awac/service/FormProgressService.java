package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;


public interface FormProgressService  extends PersistenceService<FormProgress> {

    List<FormProgress> findByPeriodAndByScope(Period period, Scope scope);

	FormProgress findByPeriodAndByScopeAndForm(Period period, Scope scope, Form form);
}
