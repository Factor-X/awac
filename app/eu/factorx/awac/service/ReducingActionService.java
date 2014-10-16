package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.knowledge.ReducingAction;

public interface ReducingActionService extends PersistenceService<ReducingAction> {

	List<ReducingAction> findByOrganization(Organization organization);

}
