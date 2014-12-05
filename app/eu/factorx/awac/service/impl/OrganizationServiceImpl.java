package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.OrganizationService;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {

	@Override
	public List<Organization> findByInterfaceTypeCode(InterfaceTypeCode interfaceTypeCode) {

		return JPA.em().createNamedQuery(Organization.FIND_BY_INTERFACE_CODE, Organization.class)
				.setParameter("interfaceCode", interfaceTypeCode).getResultList();
	}
}
