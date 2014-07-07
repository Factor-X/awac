package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by florian on 4/07/14.
 */
@Repository
public class OrganizationServiceImpl extends AbstractJPAPersistenceServiceImpl<Organization> implements OrganizationService {
}
