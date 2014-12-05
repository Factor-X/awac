package eu.factorx.awac.service;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;

import java.util.List;
import java.util.Map;

public interface FormService extends PersistenceService<Form> {

	Form findByIdentifier(String identifier);

	List<Form> findByCalculator(AwacCalculator awacCalculator);

	Map<CodeList, List<InterfaceTypeCode>> getInterfaceTypesByCodeList();

}
