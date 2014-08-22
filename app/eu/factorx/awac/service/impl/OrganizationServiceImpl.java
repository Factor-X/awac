package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Organization saveOrUpdate(final Organization entity) {
		Organization organization = super.saveOrUpdate(entity);

		Scope scope = new Scope(organization);
		scopeService.saveOrUpdate(scope);

		return organization;
	}

	public Organization findByName(String name){
		List<Organization> resultList = JPA.em().createNamedQuery(Organization.FIND_BY_NAME, Organization.class)
				.setParameter("name", name).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one organization with name = '" + name + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}
}
