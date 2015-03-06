package eu.factorx.awac.controllers;

import eu.factorx.awac.common.Constants;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.post.ChangeUserStatusDTO;
import eu.factorx.awac.dto.awac.post.EmailChangeDTO;
import eu.factorx.awac.dto.awac.post.PasswordChangeDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.AnonymousPersonDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ActiveAccountDTO;
import eu.factorx.awac.dto.myrmex.post.AdminAccountDTO;
import eu.factorx.awac.dto.myrmex.post.MainVerifierAccountDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
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

        // check if identifier already exists
        Account account = accountService.findByIdentifier(anonymousPersonDTO.getIdentifier());
        if (account != null) {
            // this kind of messages open security issues
            // do we need to give a more general message?
            throw new MyrmexRuntimeException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
        }

		//control email
		if(currentUser.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)){
			for (Account accountToTest : accountService.findByEmail(anonymousPersonDTO.getEmail())) {
				if(accountToTest.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
					!accountToTest.getId().equals(currentUser.getId())){
					throw new MyrmexRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED);
				}
			}
		}

        currentUser.setIdentifier(anonymousPersonDTO.getIdentifier());
        currentUser.setPassword(anonymousPersonDTO.getPassword());
        currentUser.setEmail(anonymousPersonDTO.getEmail());
        currentUser.setLastname(anonymousPersonDTO.getLastName());
        currentUser.setFirstname(anonymousPersonDTO.getFirstName());

        accountService.saveOrUpdate(currentUser);

        // remove cookie
        String cookieName = Constants.COOKIE.ANONYMOUS.NAME + currentUser.getOrganization().getInterfaceCode().getKey();
        response().discardCookie(cookieName);

        return ok(new ReturnDTO());
    }


    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result saveUserProfileData() {
        Account currentUser = securedController.getCurrentUser();
        PersonDTO personDTO = extractDTOFromRequest(PersonDTO.class);

        if (!personDTO.getIdentifier().equals(currentUser.getIdentifier())) {
            throw new MyrmexFatalException("Security issue: sent data does not match authenticated user data!");
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

        if (!accountService.controlPassword(emailChangeDTO.getPassword(), currentUser)) {
            return unauthorized(new ExceptionsDTO(BusinessErrorType.INVALID_PASSWORD));
        }

		//control email
		if(currentUser.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)){
			for (Account account : accountService.findByEmail(emailChangeDTO.getNewEmail())) {
				if(account.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
					!account.getId().equals(currentUser.getId())){
					throw new MyrmexRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED);
				}
			}
		}

		currentUser.setEmail(emailChangeDTO.getNewEmail().toLowerCase());
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
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result setStatus() {
        ChangeUserStatusDTO dto = extractDTOFromRequest(ChangeUserStatusDTO.class);

        Logger.info("dto:"+dto);

        //only for, verification interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }

        //load account
        Account account = accountService.findByIdentifier(dto.getIdentifier());

        // not from my organization
        if (!account.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
            throw new MyrmexRuntimeException("");
        }

        // not if it's myself
        if (account.equals(securedController.getCurrentUser())) {
            throw new MyrmexRuntimeException("");
        }



        //change status
        if (dto.getNewStatus().equals("user")) {
            Logger.info("become usqer");
            account.setIsAdmin(false);
            account.setIsMainVerifier(false);
        } else if (dto.getNewStatus().equals("main_verifier")) {
            Logger.info("become main_v");
            account.setIsAdmin(false);
            account.setIsMainVerifier(true);
        } else if (dto.getNewStatus().equals("admin")) {
            Logger.info("become admin");
            account.setIsAdmin(true);
            account.setIsMainVerifier(true);
        }

        Logger.info("==>"+dto.getNewStatus());
        Logger.info("account=>"+account);

        accountService.saveOrUpdate(account);

        return ok(new ReturnDTO());
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result isAdminAccount() {

        AdminAccountDTO dto = extractDTOFromRequest(AdminAccountDTO.class);

        Account account = accountService.findByIdentifier(dto.getIdentifier());

        // not from my organization
        if (!account.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
            throw new MyrmexRuntimeException("");
        }

        // not if it's myself
        if (account.equals(securedController.getCurrentUser())) {
            throw new MyrmexRuntimeException("");
        }

        account.setIsAdmin(dto.getIsAdmin());

        accountService.saveOrUpdate(account);

        return ok(new ReturnDTO());
    }


}
