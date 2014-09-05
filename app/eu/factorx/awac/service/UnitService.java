package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.knowledge.Unit;

public interface UnitService extends PersistenceService<Unit> {

	public Unit findBySymbol(String symbol);

    public Unit findByCode(UnitCode unitCode);

}
