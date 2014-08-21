package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.EmailChangeDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.PasswordChangeDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ForgotPasswordDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.AdministratorService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PersonService;
import eu.factorx.awac.util.BusinessErrorType;

import eu.factorx.awac.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class UserProfileController extends Controller {

	@Autowired
	private SecuredController securedController;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AdministratorService  administratorService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getUserProfileData() {
		Account currentUser = securedController.getCurrentUser();
		PersonDTO personDTO = conversionService.convert(currentUser, PersonDTO.class);
		return ok(personDTO);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveUserProfileData() {
		Account currentUser = securedController.getCurrentUser();
		PersonDTO personDTO = extractDTOFromRequest(PersonDTO.class);

		if (!personDTO.getIdentifier().equals(currentUser.getIdentifier())) {
			throw new RuntimeException("Security issue: sent data does not match authenticated user data!");
		}

		currentUser.getPerson().setLastname(personDTO.getLastName());
		currentUser.getPerson().setFirstname(personDTO.getFirstName());
		accountService.saveOrUpdate(currentUser);

		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateEmail() {
		Account currentUser = securedController.getCurrentUser();
		EmailChangeDTO emailChangeDTO = extractDTOFromRequest(EmailChangeDTO.class);

		// TODO Implement a real security check... and password should be encoded!
		if (!currentUser.getPassword().equals(emailChangeDTO.getPassword())) {
			return unauthorized(new ExceptionsDTO(BusinessErrorType.INVALID_PASSWORD));
		}

		currentUser.getPerson().setEmail(emailChangeDTO.getNewEmail().toLowerCase());
		accountService.saveOrUpdate(currentUser);

		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updatePassword() {
		Account currentUser = securedController.getCurrentUser();
		PasswordChangeDTO passwordChangeDTO = extractDTOFromRequest(PasswordChangeDTO.class);

		// TODO Implement a real security check... and password should be encoded!
		if (!currentUser.getPassword().equals(passwordChangeDTO.getOldPassword())) {
			return unauthorized(new ExceptionsDTO(BusinessErrorType.INVALID_PASSWORD));
		}

		currentUser.setPassword(passwordChangeDTO.getNewPassword());
		accountService.saveOrUpdate(currentUser);

		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = false)
	public Result createAccountForEnterprise(){

		EnterpriseAccountCreationDTO dto = extractDTOFromRequest(EnterpriseAccountCreationDTO.class);

		//control identifier
		Account account = accountService.findByIdentifier(dto.getPersonDTO().getIdentifier());
		if(account!=null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED));
			//TODO control if the person already exist
		}

		// control
		Organization organization = organizationService.findByName(dto.getOrganizationName());
		if(account!=null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_ORGANIZATION_NAME_ALREADY_USED));
		}

		//create organization
		organization = new Organization(dto.getOrganizationName());

		organizationService.saveOrUpdate(organization);

		InterfaceTypeCode interfaceCode = new InterfaceTypeCode(dto.getInterfaceCode());

		Person person = new Person(dto.getPersonDTO().getLastName(), dto.getPersonDTO().getFirstName(), dto.getPersonDTO().getEmail());

		//create account
		//TODO encode password !!
		Administrator administrator = new Administrator(organization,person,dto.getPersonDTO().getIdentifier(), dto.getPassword(), interfaceCode);

		//save account
		administratorService.saveOrUpdate(administrator);

		return ok(new ReturnDTO());
	}



	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

}
