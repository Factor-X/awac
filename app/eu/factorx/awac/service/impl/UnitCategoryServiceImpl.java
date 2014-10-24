package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UnitCategoryServiceImpl extends AbstractJPAPersistenceServiceImpl<UnitCategory> implements UnitCategoryService {

	@Override
	public List<UnitCategory> findAll() {

		TypedQuery<UnitCategory> query = JPA.em().createQuery("select e from UnitCategory e", UnitCategory.class);
		return query.getResultList();

	}

	@Override
	public UnitCategory findByCode(UnitCategoryCode unitCategoryCode) {

		List<UnitCategory> resultList = JPA.em().createNamedQuery(UnitCategory.FIND_BY_CODE, UnitCategory.class)
			.setParameter("unitCategoryCode", unitCategoryCode).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one unit category with code = '" + unitCategoryCode.getKey() + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}
