package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.ReducingAction;

public interface ReducingActionService extends PersistenceService<ReducingAction> {

	List<ReducingAction> findByScopes(List<Scope> authorizedScopes);

}
