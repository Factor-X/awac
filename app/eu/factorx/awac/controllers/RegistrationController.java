package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
	private ConversionService conversionService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private SecuredController securedController;

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
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(),InterfaceTypeCode.ENTERPRISE,organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		Site site = new Site(organization, dto.getFirstSiteName());
		siteService.saveOrUpdate(site);
		organization.getSites().add(site);

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
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
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(),InterfaceTypeCode.MUNICIPALITY,organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		Site site = new Site(organization, dto.getMunicipalityName());
		siteService.saveOrUpdate(site);
		organization.getSites().add(site);

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}

	private Account createAdministrator(PersonDTO personDTO, String password, InterfaceTypeCode interfaceCode, Organization organization) throws MyrmexException{

		//control identifier
		Account account = accountService.findByIdentifier(personDTO.getIdentifier());
		if(account!=null){
			throw new MyrmexException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
		}


		//control email
		Person person = personService.getByEmail(personDTO.getEmail());

		// if person doesn't already exist, create it
		if(person ==null){
			person = new Person(personDTO.getLastName(), personDTO.getFirstName(), personDTO.getEmail());
			personService.saveOrUpdate(person);
		}
		else{
			//test if an account for the same calculator already exists
			for(Account accountToTest : accountService.findByEmail(person.getEmail())){
				if(accountToTest.getInterfaceCode().equals(interfaceCode)){
					//TODO translate
					throw new MyrmexException("Un compte avec le même email existe déjà pour ce calculateur");
				}
			}
		}

		//create account
		//TODO encode password !!
		Account administrator = new Account(organization,person,personDTO.getIdentifier(), password, interfaceCode);
		administrator.setIsAdmin(true);

		//save account
		accountService.saveOrUpdate(administrator);

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
