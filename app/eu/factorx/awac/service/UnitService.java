package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.knowledge.Unit;

public interface UnitService extends PersistenceService<Unit> {

	public Unit findBySymbol(String symbol);

    public Unit findByCode(UnitCode unitCode);

    public List<Unit> findByCategoryCode(UnitCategoryCode unitCategoryCode);

}
