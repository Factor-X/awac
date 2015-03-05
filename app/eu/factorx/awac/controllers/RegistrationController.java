package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.*;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.verification.post.VerificationRegistrationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Configuration;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class RegistrationController extends AbstractController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CodeLabelService codeLabelService;
	@Autowired
	private VerificationRequestService verificationRequestService;
	@Autowired
	private VelocityGeneratorService velocityGeneratorService;
	@Autowired
	private VerificationController verificationController;
	@Autowired
	private ProductService productService;
	@Autowired
	private AccountProductAssociationService accountProductAssociationService;

	/**
	 *
	 * create organization (with the lastName of the person)
	 * create administrator
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result householdRegistration() {

		HouseholdAccountCreationDTO dto = extractDTOFromRequest(HouseholdAccountCreationDTO.class);

		// control organization name

		//create organization
		Organization organization = new Organization(dto.getPerson().getLastName(), InterfaceTypeCode.HOUSEHOLD, dto.getOrganizationStatisticsAllowed());
		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.HOUSEHOLD);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);

	}

	/**
	 *
	 * create organization (with specific name)
	 * create administrator
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result littleemitterRegistration() {

		LittleEmitterAccountCreationDTO dto = extractDTOFromRequest(LittleEmitterAccountCreationDTO.class);

		// control organization name

		//create organization
		Organization organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.LITTLEEMITTER, dto.getOrganizationStatisticsAllowed());
		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.LITTLEEMITTER);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);

	}

	/**
	 * control the key
	 * create organizaiton (with specific name)
	 * create adminstrator
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result verificationRegistration() {

		VerificationRegistrationDTO dto = extractDTOFromRequest(VerificationRegistrationDTO.class);

		// control key
		VerificationRequest verificationRequest = verificationRequestService.findByKey(dto.getKey());
		if (verificationRequest == null || verificationRequest.getOrganizationVerifier() != null) {
			throw new MyrmexRuntimeException(BusinessErrorType.VERIFIER_REGISTRATION_NOT_VALID_REQUEST);
		}

		// create organization
		Organization organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.VERIFICATION);
		organization.setStatisticsAllowed(false);
		organizationService.saveOrUpdate(organization);

		// create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		// if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.VERIFICATION);

		// link the key
		verificationController.addRequestByKey(dto.getKey());

		// create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}


	/**
	 * create organzaiton (with specific name)
	 * create administrator
	 * create site
	 * assign lastPeriod to the site
	 * assign the account to the site
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result enterpriseRegistration() {

        Logger.warn("----------->je uis enterpriseRegistration ");

		EnterpriseAccountCreationDTO dto = extractDTOFromRequest(EnterpriseAccountCreationDTO.class);

		// control organization name

		//create organization
		Organization organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.ENTERPRISE, dto.getOrganizationStatisticsAllowed());

		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
            e.printStackTrace();
			throw new MyrmexRuntimeException(e.getToClientMessage());
		}

		//create site
		Site site = new Site(organization, dto.getFirstSiteName());
		site.setPercentOwned(100.0);
		site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");

		//add last year period
		Period period = periodService.findLastYear();
		List<Period> listAvailablePeriod = new ArrayList<>();
		listAvailablePeriod.add(period);
		site.setListPeriodAvailable(listAvailablePeriod);

		siteService.saveOrUpdate(site);
		organization.getSites().add(site);

		//create link between account and site
		AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site, account);
		accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.ENTERPRISE);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		Logger.warn("resultDto:"+resultDto);

		return ok(resultDto);
	}

	/**
	 * create organzaiton (with specific name)
	 * create administrator
	 * create site
	 * assign lastPeriod to the event
	 * assign the account to the event
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result eventRegistration() {

		EventAccountCreationDTO dto = extractDTOFromRequest(EventAccountCreationDTO.class);

		// control organization name

		//create organization
		Organization organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.EVENT, dto.getOrganizationStatisticsAllowed());

		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		Product product = new Product(organization,dto.getFirstProductName());

		//add last year period
		Period period = periodService.findLastYear();
		List<Period> listAvailablePeriod = new ArrayList<>();
		listAvailablePeriod.add(period);
		product.setListPeriodAvailable(listAvailablePeriod);

		productService.saveOrUpdate(product);
		organization.getProducts().add(product);

		//create link between account and site
		AccountProductAssociation accountSiteAssociation = new AccountProductAssociation(product, account);
		accountProductAssociationService.saveOrUpdate(accountSiteAssociation);

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.EVENT);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}

	/**
	 * create organzaiton (with specific name)
	 * create administrator
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result municipalityRegistration() {

		RegistrationDTO dto = extractDTOFromRequest(RegistrationDTO.class);

		// control organization name

		//create organization
		Organization organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.MUNICIPALITY, dto.getOrganizationStatisticsAllowed());
		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(), organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account, InterfaceTypeCode.MUNICIPALITY);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}

	/* --------
	 * PRIVATE FUNCTION
	 */

	private void handleEmailSubmission(Account account, InterfaceTypeCode interfaceType) {

		// email purpose
		// retrieve traductions
		HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
		String subject = traductions.get("REGISTER_EMAIL_SUBJECT").getLabel(account.getDefaultLanguage());
        String content = traductions.get("REGISTER_EMAIL_CONTENT").getLabel(account.getDefaultLanguage());

		Logger.info("handleEmailSubmission->interfaceTypeCode:" + interfaceType);
		String awacInterfaceTypeFragment;

		if (interfaceType.getKey().equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.enterprisefragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.municipalityfragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.HOUSEHOLD.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.householdfragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.LITTLEEMITTER.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.littleemitterfragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.EVENT.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.eventfragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.VERIFICATION.getKey())) {
			awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");
		} else {
			Logger.error("Cannot define url fragment for interface type = " + interfaceType.getKey());
			awacInterfaceTypeFragment = "";
		}

		// prepare email
		Map values = new HashMap<String, Object>();
		final String awacHostname = Configuration.root().getString("awac.hostname");
		String awacLoginUrlFragment = Configuration.root().getString("awac.loginfragment");

		String link = awacHostname + awacInterfaceTypeFragment + awacLoginUrlFragment;
		//String link = awacHostname + awacLoginUrlFragment;

        content = content.replace("${identifier}",account.getIdentifier());
        content = content.replace("${link}",link);

        values.put("subject", subject);
        values.put("content", content);

//		values.put("subject", subject);
//		values.put("link", link);
//		values.put("hostname", awacHostname);
//		values.put("identifier", account.getIdentifier());


		String velocityContent = velocityGeneratorService.generate("registerInvitation.vm", values);

		// send confirmation email
		EmailMessage email = new EmailMessage(account.getEmail(), subject, velocityContent);
		emailService.send(email);
	}


	private Account createAdministrator(PersonDTO personDTO, String password, Organization organization) throws MyrmexException {

		//control identifier
		Account account = accountService.findByIdentifier(personDTO.getIdentifier());
		if (account != null) {
			throw new MyrmexException(BusinessErrorType.INVALID_IDENTIFIER_ALREADY_USED);
		}

		//control email
		//if this is the verification interface AND
		// there is an account with the same email address AND
		// this account is also a verification account,
		//there is an error
		if(organization.getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)){
			for (Account accountToTest : accountService.findByEmail(personDTO.getEmail())) {
				if(accountToTest.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)){
					throw new MyrmexRuntimeException(BusinessErrorType.EMAIL_ALREADY_USED);
				}
			}
		}

		//create account
		Account administrator = new Account(organization, personDTO.getLastName(), personDTO.getFirstName(), personDTO.getEmail(), personDTO.getIdentifier(), password);
		administrator.setIsAdmin(true);

		//save account
		accountService.saveOrUpdate(administrator);

		return administrator;
	}


}
