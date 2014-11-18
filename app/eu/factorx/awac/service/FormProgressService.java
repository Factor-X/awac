package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;


public interface FormProgressService  extends PersistenceService<FormProgress> {

    List<FormProgress> findByPeriodAndByScope(Period period, Scope scope);

	FormProgress findByPeriodAndByScopeAndForm(Period period, Scope scope, Form form);
}
