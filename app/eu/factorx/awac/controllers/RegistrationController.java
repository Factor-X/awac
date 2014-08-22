package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.logging.Logger;


@org.springframework.stereotype.Controller
public class RegistrationController  extends Controller {

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private SiteService siteService;

	@Transactional(readOnly = false)
	public Result enterpriseRegistration() {

		EnterpriseAccountCreationDTO dto = extractDTOFromRequest(EnterpriseAccountCreationDTO.class);

		// control organization name
		Organization organization = organizationService.findByName(dto.getOrganizationName());
		if(organization!=null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_ORGANIZATION_NAME_ALREADY_USED));
		}

		//create organization
		organization = new Organization(dto.getOrganizationName());

		organizationService.saveOrUpdate(organization);

		//create administrator
		try {
			createAdministrator(dto.getPerson(), dto.getPassword(),InterfaceTypeCode.ENTERPRISE,organization);
		} catch (MyrmexException e) {
			throw new MyrmexRuntimeException(e.getToClientMessage()); //return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		Site site = new Site(organization, dto.getFirstSiteName());
		siteService.saveOrUpdate(site);


		return ok(new ReturnDTO());
	}


	@Transactional(readOnly = false)
	public Result enterpriseMunicipality() {

		MunicipalityAccountCreationDTO dto = extractDTOFromRequest(MunicipalityAccountCreationDTO.class);

		// control organization name
		Organization organization = organizationService.findByName(dto.getMunicipalityName());
		if(organization!=null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_MUNICIPALITY_NAME_ALREADY_USED));
		}

		//create organization
		organization = new Organization(dto.getMunicipalityName());

		organizationService.saveOrUpdate(organization);

		//create administrator
		try {
			createAdministrator(dto.getPerson(), dto.getPassword(),InterfaceTypeCode.MUNICIPALITY,organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		Site site = new Site(organization, dto.getMunicipalityName());
		siteService.saveOrUpdate(site);

		return ok(new ReturnDTO());
	}

	private Administrator createAdministrator(PersonDTO personDTO, String password, InterfaceTypeCode interfaceCode, Organization organization) throws MyrmexException{

		//control email
		Person person = personService.getByEmail(personDTO.getEmail());

		if(person !=null){
			throw new MyrmexException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
			//TODO email already existing
		}

		//control identifier
		Account account = accountService.findByIdentifier(personDTO.getIdentifier());
		if(account!=null){
			throw new MyrmexException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
		}

		person = new Person(personDTO.getLastName(), personDTO.getFirstName(), personDTO.getEmail());

		personService.saveOrUpdate(person);

		//create account
		//TODO encode password !!
		Administrator administrator = new Administrator(organization,person,personDTO.getIdentifier(), password, interfaceCode);

		//save account
		administratorService.saveOrUpdate(administrator);

		return administrator;
	}


	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}



}
