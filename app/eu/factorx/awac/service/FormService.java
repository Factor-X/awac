package eu.factorx.awac.service;

import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;

import java.util.List;

public interface FormService extends PersistenceService<Form> {

	Form findByIdentifier(String identifier);

	List<Form> findByCalculator(AwacCalculator awacCalculator);

}
