/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.controllers;

import java.util.HashMap;
import java.util.Map;

import eu.factorx.awac.common.Constants;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.business.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Http;
import play.mvc.Result;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.MustChangePasswordExceptionsDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.dto.myrmex.post.ForgotPasswordDTO;
import eu.factorx.awac.dto.myrmex.post.TestAuthenticateDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;

@org.springframework.stereotype.Controller
public class AuthenticationController extends AbstractController {

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Autowired
	private VelocityGeneratorService velocityGeneratorService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;

	@Autowired
	private PeriodService periodService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AccountProductAssociationService accountProductAssociationService;

	@Transactional(readOnly = true)
	public Result testAuthentication() {

		TestAuthenticateDTO dto = extractDTOFromRequest(TestAuthenticateDTO.class);

		if (securedController.isAuthenticated()) {

			Account account = securedController.getCurrentUser();

            InterfaceTypeCode interfaceTypeCode =InterfaceTypeCode.getByKey(dto.getInterfaceName());

			if (account.getOrganization().getInterfaceCode().equals(interfaceTypeCode)) {
				return ok(conversionService.convert(securedController.getCurrentUser(), LoginResultDTO.class));
			}
            else{
                //logout !!
                logout();
            }

		}
        //the error must be empty to do not be displayed !
		return unauthorized();
	}


	// authenticate action cf routes
	@Transactional(readOnly = false)
	public Result authenticate() {

		ConnectionFormDTO connectionFormDTO = this.extractDTOFromRequest(ConnectionFormDTO.class);

		Account account = null;


		if ( (connectionFormDTO.getLogin()==null) && (connectionFormDTO.getPassword()==null) ) {

            InterfaceTypeCode interfaceTypeCode =InterfaceTypeCode.getByKey(connectionFormDTO.getInterfaceName());

            // generate specific cookie for interface type code - household, little emitter, ...

            String cookieName = Constants.COOKIE.ANONYMOUS.NAME + interfaceTypeCode.getKey();
			Http.Cookie cookie = request().cookie(cookieName);
			if ((cookie!=null) && (!StringUtils.isBlank(cookie.value()))) {

				Logger.info("Anonymous login with cookie for :" + interfaceTypeCode.getKey());
				account = accountService.findByIdentifier(cookie.value());
                String anonymousUserName = Constants.ANONYMOUS.USERNAME + interfaceTypeCode.getKey();
				if (! account.getPerson().getFirstname().equals(anonymousUserName) ) {
					Logger.info("Not an anonymous user login try");
					throw new MyrmexRuntimeException(BusinessErrorType.LOGIN_PASSWORD_PAIR_NOT_FOUND);
				}

			} else {
				// anonymous login
				Logger.info("Anonymous login without cookie");
				// generate dummy user and password
				// if person doesn't already exist, create it

				String lastName = Long.toString(System.currentTimeMillis());
				String firstName = Constants.ANONYMOUS.USERNAME + interfaceTypeCode.getKey();
                String identifier = firstName + lastName;
				String password = Long.toString(System.nanoTime());
				String email = identifier + Constants.ANONYMOUS.EMAILDOMAIN;


				Person person = new Person(lastName, firstName, email);
				personService.saveOrUpdate(person);

                //InterfaceTypeCode interfaceTypeCode =InterfaceTypeCode.getByKey(connectionFormDTO.getInterfaceName());

				Organization org = new Organization("YourOrg" + lastName, interfaceTypeCode);
				organizationService.saveOrUpdate(org);

				//create account
				account = new Account(org, person, identifier, password);
				account.setIsAdmin(true);

				//save account
				accountService.saveOrUpdate(account);

				// create site & period
				if (InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
					Product product = new Product(org, "YourProduct" + lastName);
					product.getListPeriodAvailable().add(periodService.findLastYear());
					productService.saveOrUpdate(product);

					AccountProductAssociation apa = new AccountProductAssociation();
					apa.setAccount(account);
					apa.setProduct(product);
					accountProductAssociationService.saveOrUpdate(apa);
				} else {
					Site site = new Site(org, "YourSite");
					site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");
					site.getListPeriodAvailable().add(periodService.findLastYear());
					siteService.saveOrUpdate(site);

					AccountSiteAssociation asa = new AccountSiteAssociation();
					asa.setAccount(account);
					asa.setSite(site);
					accountSiteAssociationService.saveOrUpdate(asa);
				}

				response().setCookie(cookieName, identifier);
			} // else cookie does not exist

		} else {
			// authenticated login
			//test if the login exist
			account = accountService.findByIdentifier(connectionFormDTO.getLogin());

			//control account
			if (account == null) {
				//use the same message for both login and password error
				// "The couple login / password was not found"
                throw new MyrmexRuntimeException(BusinessErrorType.LOGIN_PASSWORD_PAIR_NOT_FOUND);
			}

			//test password
			if (!accountService.controlPassword(connectionFormDTO.getPassword(), account)) {
				//use the same message for both login and password error
                throw new MyrmexRuntimeException(BusinessErrorType.LOGIN_PASSWORD_PAIR_NOT_FOUND);
			}

			//control interface
            InterfaceTypeCode interfaceTypeCode =InterfaceTypeCode.getByKey(connectionFormDTO.getInterfaceName());

			if (interfaceTypeCode == null) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_VALID_INTERFACE,account.getOrganization().getInterfaceCode().getKey());
			} else if (!interfaceTypeCode.equals(account.getOrganization().getInterfaceCode())) {
				//use the same message for both login and password error
				Logger.info(interfaceTypeCode + "");
				Logger.info(account.getOrganization() + "");
				// return unauthorized(new ExceptionsDTO("This account is not for " + interfaceTypeCode.getKey() + " but for " + account.getOrganization().getInterfaceCode().getKey() + ". Please switch calculator and retry."));
				return unauthorized(new ExceptionsDTO(BusinessErrorType.WRONG_INTERFACE_FOR_USER, interfaceTypeCode.getKey(), account.getOrganization().getInterfaceCode().getKey()));
			}

			//control acitf
			if (!account.getActive()) {
				// "Votre compte est actuellement suspendue. Contactez votre administrateur."
				return unauthorized(new ExceptionsDTO(BusinessErrorType.SUSPENDED_ACCOUNT));
			}

			//control change password
			if (account.getNeedChangePassword()) {

				if (connectionFormDTO.getNewPassword() != null) {
					account.setPassword(connectionFormDTO.getNewPassword());
					account.setNeedChangePassword(false);
					accountService.saveOrUpdate(account);
				} else {
					//use the same message for both login and password error
					return unauthorized(new MustChangePasswordExceptionsDTO());
				}
			}
		} // end of else not anonymous

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		//convert and send it
		LoginResultDTO dto = conversionService.convert(account, LoginResultDTO.class);

		return ok(dto);

	} // end of authenticate action

	// logout action cf routes
	@Transactional(readOnly = true)
	public Result logout() {

		session().clear();

		return ok(new ReturnDTO());
	}


	@Transactional(readOnly = false)
	public Result forgotPassword() {

		ForgotPasswordDTO dto = extractDTOFromRequest(ForgotPasswordDTO.class);

		Account account = accountService.findByIdentifier(dto.getIdentifier());

		if (account == null) {
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_IDENTIFIER));
		}

		//generate password
		String password = KeyGenerator.generateRandomPassword(10);


		// retrieve traductions
		HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
		String title = traductions.get("RESET_PASSWORD_EMAIL_SUBJECT").getLabel(account.getPerson().getDefaultLanguage());
        String content = traductions.get("RESET_PASSWORD_EMAIL_CONTENT").getLabel(account.getPerson().getDefaultLanguage());


        Map values = new HashMap<String, Object>();
		values.put("title", title);
        content = content.replace("${password}",password);
        values.put("content", content);

		//values.put("password", password);


		String velocityContent = velocityGeneratorService.generate(velocityGeneratorService.getTemplateNameByMethodName(), values);

		// send email for invitation
		EmailMessage email = new EmailMessage(account.getPerson().getEmail(), title, velocityContent);
		emailService.send(email);

		//save new password
		account.setPassword(password);
		account.setNeedChangePassword(true);
		accountService.saveOrUpdate(account);

		return ok(new ReturnDTO());
	}

} // end of controller class

