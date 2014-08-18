package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.EmailChangeDTO;
import eu.factorx.awac.dto.awac.post.PasswordChangeDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.util.BusinessErrorType;

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

		currentUser.setLastname(personDTO.getLastName());
		currentUser.setFirstname(personDTO.getFirstName());
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

		currentUser.setEmail(emailChangeDTO.getNewEmail());
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

	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

}
