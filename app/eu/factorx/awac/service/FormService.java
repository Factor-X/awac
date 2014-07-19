package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

public interface FormService extends PersistenceService<Form> {

	Form findByIdentifier(String identifier);

}
