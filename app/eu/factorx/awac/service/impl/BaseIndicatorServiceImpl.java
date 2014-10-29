package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.BaseIndicatorService;

@Component
public class BaseIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<BaseIndicator> implements BaseIndicatorService {

	@Override
	public void removeAll() {
		JPA.em().createNamedQuery(BaseIndicator.REMOVE_ALL).executeUpdate();
	}

}
