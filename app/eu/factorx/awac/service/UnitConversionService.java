package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;
import org.springframework.stereotype.Component;

@Component
public interface UnitConversionService extends PersistenceService<UnitConversionFormula> {

	UnitConversionFormula findByUnitAndYear(Unit unit, Integer year);

	Double convertToReferenceUnit(Double value, Unit currentUnit, Integer year);

	Double convertFromReferenceUnit(Double value, Unit targetUnit, Integer year);

	Double convert(Double value, Unit currentUnit, Unit targetUnit, Integer year);

}
