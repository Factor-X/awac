package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.EmailChangeDTO;
import eu.factorx.awac.dto.awac.post.PasswordChangeDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.AnonymousPersonDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ActiveAccountDTO;
import eu.factorx.awac.dto.myrmex.post.AdminAccountDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.util.BusinessErrorType;

import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class UserProfileController extends AbstractController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private AccountService accountService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getUserProfileData() {
		Account currentUser = securedController.getCurrentUser();
		PersonDTO personDTO = conversionService.convert(currentUser, PersonDTO.class);
		return ok(personDTO);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveAnonymousUserProfileData() {
		Account currentUser = securedController.getCurrentUser();
		AnonymousPersonDTO anonymousPersonDTO = extractDTOFromRequest(AnonymousPersonDTO.class);

//		if (!personDTO.getIdentifier().equals(currentUser.getIdentifier())) {
//			throw new RuntimeException("Security issue: sent data does not match authenticated user data!");
//		}


		currentUser.setIdentifier(anonymousPersonDTO.getIdentifier());
		currentUser.setPassword(anonymousPersonDTO.getPassword());
		currentUser.getPerson().setEmail(anonymousPersonDTO.getEmail());
		currentUser.getPerson().setLastname(anonymousPersonDTO.getLastName());
		currentUser.getPerson().setFirstname(anonymousPersonDTO.getFirstName());
		try {
			accountService.saveOrUpdate(currentUser);
		} catch (Exception e) {
			throw new RuntimeException("Anonimous user can not be converted to formal user. Please try with another identifier.");
		}

		// remove cookie
		response().discardCookie("AWAC_ANONYMOUS_IDENTIFIER");

		return ok(new ReturnDTO());
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

		if (!accountService.controlPassword(emailChangeDTO.getPassword(), currentUser)) {
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

		if (!accountService.controlPassword(passwordChangeDTO.getOldPassword(), currentUser)) {
			return unauthorized(new ExceptionsDTO(BusinessErrorType.INVALID_PASSWORD));
		}

		currentUser.setPassword(passwordChangeDTO.getNewPassword());
		accountService.saveOrUpdate(currentUser);

		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result activeAccount() {

		//TODO control admin with annotation
		if (!securedController.isAdministrator()) {
			throw new MyrmexRuntimeException("");
		}

		ActiveAccountDTO dto = extractDTOFromRequest(ActiveAccountDTO.class);

		Account account = accountService.findByIdentifier(dto.getIdentifier());

		if (account == null) {
			throw new MyrmexRuntimeException("");
		}

		// not from my organization
		if (!account.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			//TODO write exception
			throw new MyrmexRuntimeException("");
		}

		// not if it's myself
		if (account.equals(securedController.getCurrentUser())) {
			//TODO write exception
			throw new MyrmexRuntimeException("");
		}

		account.setActive(dto.getIsActive());

		accountService.saveOrUpdate(account);

		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result isAdminAccount() {

		//TODO control admin with annotation
		if (!securedController.isAdministrator()) {
			throw new MyrmexRuntimeException("");
		}

		AdminAccountDTO dto = extractDTOFromRequest(AdminAccountDTO.class);

		Account account = accountService.findByIdentifier(dto.getIdentifier());

		// not from my organization
		if (!account.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			//TODO write exception
			throw new MyrmexRuntimeException("");
		}

		// not if it's myself
		if (account.equals(securedController.getCurrentUser())) {
			//TODO write exception
			throw new MyrmexRuntimeException("");
		}

		account.setIsAdmin(dto.getIsAdmin());

		accountService.saveOrUpdate(account);

		return ok(new ReturnDTO());
	}

}
