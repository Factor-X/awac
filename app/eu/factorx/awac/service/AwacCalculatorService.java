package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;

public interface AwacCalculatorService extends PersistenceService<AwacCalculator> {

	public AwacCalculator findByCode(InterfaceTypeCode interfaceTypeCode);

}
