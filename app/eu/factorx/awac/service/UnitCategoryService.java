package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.knowledge.UnitCategory;

public interface UnitCategoryService extends PersistenceService<UnitCategory> {

	UnitCategory findByCode(UnitCategoryCode unitCategoryCode);

    UnitCategory findByName(String name);
}
