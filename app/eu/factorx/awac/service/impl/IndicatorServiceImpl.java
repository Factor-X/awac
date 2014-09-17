package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.service.IndicatorService;

@Component

public class IndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<Indicator> implements IndicatorService {

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(Indicator.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} indicators", nbDeleted);
	}

}
