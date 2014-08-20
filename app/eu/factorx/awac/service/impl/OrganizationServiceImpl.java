package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {

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
