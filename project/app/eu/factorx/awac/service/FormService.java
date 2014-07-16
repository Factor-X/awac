package eu.factorx.awac.service;

import eu.factorx.awac.models.forms.Form;

public interface FormService extends PersistenceService<Form> {

	Form findByIdentifier(String identifier);

}
