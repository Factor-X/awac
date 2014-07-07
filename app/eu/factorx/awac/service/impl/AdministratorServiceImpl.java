package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.service.AdministratorService;
import org.springframework.stereotype.Repository;

@Repository
public class AdministratorServiceImpl extends AbstractJPAPersistenceServiceImpl<Administrator> implements
		AdministratorService {
}
