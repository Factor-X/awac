package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.service.CodesEquivalenceService;

@Component
public class CodesEquivalenceServiceImpl extends AbstractJPAPersistenceServiceImpl<CodesEquivalence> implements CodesEquivalenceService {

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(CodesEquivalence.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} code equivalences", nbDeleted);
	}

}
