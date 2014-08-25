package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.service.AdministratorService;

@Repository
public class AdministratorServiceImpl extends AbstractJPAPersistenceServiceImpl<Administrator> implements
		AdministratorService {
}
