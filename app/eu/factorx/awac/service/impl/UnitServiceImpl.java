package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;
import eu.factorx.awac.service.UnitService;

@Component
public class UnitServiceImpl extends AbstractJPAPersistenceServiceImpl<Unit> implements UnitService {

	@Autowired
	private UnitCategoryService unitCategoryService;

	@Override
	public Unit findBySymbol(String symbol) {
		List<Unit> resultList = JPA.em().createNamedQuery(Unit.FIND_BY_SYMBOL, Unit.class)
				.setParameter("symbol", symbol).getResultList();
		if (resultList.size() == 0) {
			return null;
		}
		if (resultList.size() > 1) {
			String errorMsg = "More than one unit with symbol = '" + symbol + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		return resultList.get(0);
	}

    public Unit findByCode(UnitCode unitCode){
        List<Unit> resultList = JPA.em().createNamedQuery(Unit.FIND_BY_CODE, Unit.class)
                .setParameter("unitCode", unitCode).getResultList();
        if (resultList.size() == 0) {
            return null;
        }
       if (resultList.size() > 1) {
            String errorMsg = "More than one unit with symbol = '" + unitCode + "'";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        return resultList.get(0);

    }

	@Override
	public List<Unit> findByCategoryCode(UnitCategoryCode unitCategoryCode) {
		UnitCategory unitCategory = unitCategoryService.findByCode(unitCategoryCode);
        return JPA.em().createNamedQuery(Unit.FIND_BY_CATEGORY, Unit.class)
                .setParameter("category", unitCategory).getResultList();
	}

}
