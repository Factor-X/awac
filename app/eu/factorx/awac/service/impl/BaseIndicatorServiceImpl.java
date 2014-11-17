package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.BaseIndicatorCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.BaseIndicatorService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class BaseIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<BaseIndicator> implements BaseIndicatorService {

	@Override
	public void removeAll() {
		JPA.em().createNamedQuery(BaseIndicator.REMOVE_ALL).executeUpdate();
	}

	@Override
	public BaseIndicator getByCode(BaseIndicatorCode code) {
		List<BaseIndicator> resultList = JPA.em().createNamedQuery(BaseIndicator.FIND_BY_CODE, BaseIndicator.class)
				.setParameter("code", code).getResultList();

		if (resultList.size() > 1) {
			String errorMsg = "More than one BaseIndicator with code = '" + code + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}

