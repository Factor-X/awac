package eu.factorx.awac.service.impl;

import java.util.List;

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
		if (resultList.size() > 1) {
			String errorMsg = "More than one unit with symbol = '" + symbol + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public List<String> findAllSymbols() {
		List<String> resultList = JPA.em().createNamedQuery(Unit.FIND_ALL_SYMBOLS, String.class).getResultList();
		return resultList;
	}

}
