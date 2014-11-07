package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;

import java.util.List;

public interface OrganizationService extends PersistenceService<Organization> {

	public Organization findByName(String name);

    public List<Organization> findByInterfaceTypeCode(InterfaceTypeCode interfaceTypeCode) ;
}
