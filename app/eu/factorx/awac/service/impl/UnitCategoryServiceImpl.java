package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;

@Component
public class UnitCategoryServiceImpl extends AbstractJPAPersistenceServiceImpl<UnitCategory> implements UnitCategoryService {

	// custom cache! unit and unit categories are "strictly" read-only data for now...
	// TODO Improve this
	private static List<UnitCategory> allCategories = null;

	@Override
	public List<UnitCategory> findAll() {
		if (allCategories == null) {
			allCategories = super.findAll();
		}
		return allCategories;
	}

}
