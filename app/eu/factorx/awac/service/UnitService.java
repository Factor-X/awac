package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public interface UnitService extends PersistenceService<Unit> {

	List<UnitCategory> findAllUnitCategories
}
