package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.ScopeService;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Organization saveOrUpdate(final Organization entity) {

		boolean createScope = false;
		if (entity.getId() == null) {
			createScope = true;
		}

		Organization organization = super.saveOrUpdate(entity);

		if (createScope) {
			Scope scope = new Scope(organization);
			scopeService.saveOrUpdate(scope);
		}
		return organization;
	}

	@Override
	public void remove(final Organization organization) {
		Scope scope = scopeService.findByOrganization(organization);
		if (scope != null) {
			scopeService.remove(scope);
		}
		super.remove(organization);
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
