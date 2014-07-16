package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;

@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements
		OrganizationService {
}
