package eu.factorx.awac.service;

import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;

@Component
public interface UnitConversionService extends PersistenceService<UnitConversionFormula> {

	UnitConversionFormula findByUnitAndYear(Unit unit, Integer year);

	Double convertToReferenceUnit(Double value, Unit currentUnit, Integer year);

	Double convertFromReferenceUnit(Double value, Unit targetUnit, Integer year);

	Double convert(Double value, Unit currentUnit, Unit targetUnit, Integer year);

	Double evaluateExpression(String strExpression, Double variableValue) throws ExpressionException;

}
