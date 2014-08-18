package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;

@Component
public class UnitCategoryServiceImpl extends AbstractJPAPersistenceServiceImpl<UnitCategory> implements UnitCategoryService {

	@Override
	public List<UnitCategory> findAll() {
		long start = System.currentTimeMillis();
		Criteria criteria = JPA.em().unwrap(Session.class).createCriteria(UnitCategory.class);
		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<UnitCategory> result = criteria.list();
		Logger.info(">>>>>>>>>>> UnitCategoryServiceImpl.findAll() - Took " + (System.currentTimeMillis() - start) + "msec");
		return result;
	}

}
