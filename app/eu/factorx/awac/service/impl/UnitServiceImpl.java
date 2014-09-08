package eu.factorx.awac.service.impl;

import java.util.List;

import eu.factorx.awac.models.code.type.UnitCode;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.UnitService;

@Component
public class UnitServiceImpl extends AbstractJPAPersistenceServiceImpl<Unit> implements UnitService {

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

}
