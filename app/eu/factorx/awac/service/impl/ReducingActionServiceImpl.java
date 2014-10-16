package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.ReducingAction;
import eu.factorx.awac.service.ReducingActionService;

@Component
public class ReducingActionServiceImpl extends AbstractJPAPersistenceServiceImpl<ReducingAction> implements ReducingActionService {

	@Override
	public List<ReducingAction> findByOrganization(Organization organization) {
		List<Scope> scopes = new ArrayList<>();
		scopes.add(organization);
		for (Site site : organization.getSites()) {
			scopes.add(site);
		}

		return JPA.em().createNamedQuery(ReducingAction.FIND_BY_SCOPES, ReducingAction.class).setParameter("scopes", scopes).getResultList();
	}

}
