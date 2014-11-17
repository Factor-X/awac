package eu.factorx.awac.service.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;

import java.util.List;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {

	public Organization findByName(String name) {
		TypedQuery<Organization> query = JPA.em().createNamedQuery(Organization.FIND_BY_NAME, Organization.class)
				.setParameter("name", name);
		Organization singleResult = null;
		try {
			singleResult = query.getSingleResult();
		} catch (NoResultException e) {
			Logger.warn("Cannot find an Organization with name = '{}'", name);
		}
		return singleResult;
	}

    @Override
    public List<Organization> findByInterfaceTypeCode(InterfaceTypeCode interfaceTypeCode) {

      return  JPA.em().createNamedQuery(Organization.FIND_BY_INTERFACE_CODE  , Organization.class)
                .setParameter("interfaceCode", interfaceTypeCode).getResultList();
    }
}
