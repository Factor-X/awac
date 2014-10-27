package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.BaseIndicatorService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

@Component
public class BaseIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<BaseIndicator> implements BaseIndicatorService {

	@Override
	public void removeAll() {
		JPA.em().createNamedQuery(BaseIndicator.REMOVE_ALL).executeUpdate();
	}

}
