package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.RegistrationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexException;
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

@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class RegistrationController  extends AbstractController {

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
    private AccountSiteAssociationService accountSiteAssociationService;

    @Autowired
    private PeriodService periodService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Autowired
	private VelocityGeneratorService velocityGeneratorService;

    public Result verificationRegistration() {

        RegistrationDTO dto = extractDTOFromRequest(RegistrationDTO.class);

        // control organization name
        Organization organization = organizationService.findByName(dto.getOrganizationName());
        if(organization!=null){
            return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_MUNICIPALITY_NAME_ALREADY_USED));
        }

        //create organization
        organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.VERIFICATION);
        organizationService.saveOrUpdate(organization);

        //create administrator
        Account account = null;
        try {
            account = createAdministrator(dto.getPerson(), dto.getPassword(),organization);
        } catch (MyrmexException e) {
            return notFound(new ExceptionsDTO(e.getToClientMessage()));
        }

        //if the login and the password are ok, refresh the session
        securedController.storeIdentifier(account);

        // email submission
        handleEmailSubmission(account,InterfaceTypeCode.VERIFICATION);

        //create ConnectionFormDTO
        LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

        return ok(resultDto);
    }


    @Transactional(readOnly = false)
	public Result enterpriseRegistration() {

		EnterpriseAccountCreationDTO dto = extractDTOFromRequest(EnterpriseAccountCreationDTO.class);

		// control organization name
		Organization organization = organizationService.findByName(dto.getOrganizationName());
		if(organization!=null){
			play.Logger.info("Myrmex exception: Organization already exists...");
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_ORGANIZATION_NAME_ALREADY_USED));
		}

		//create organization
		organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.ENTERPRISE, dto.getOrganizationStatisticsAllowed());

		play.Logger.info("create organization...");
		organizationService.saveOrUpdate(organization);

		play.Logger.info("create admin...");
		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(),organization);
		} catch (MyrmexException e) {
			play.Logger.info("Myrmex exception:" + e.getToClientMessage());
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//create site
		play.Logger.info("create site...");
		Site site = new Site(organization, dto.getFirstSiteName());
		site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");

        //add last year period
        Period period = periodService.findLastYear();
        List<Period> listAvailablePeriod= new ArrayList<>();
        listAvailablePeriod.add(period);
        site.setListPeriodAvailable(listAvailablePeriod);

		play.Logger.info("add periods...");
        siteService.saveOrUpdate(site);
        organization.getSites().add(site);

        //create link between account and site
		play.Logger.info("create association...");
        AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site,account);
        accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account,InterfaceTypeCode.ENTERPRISE);

		//create ConnectionFormDTO
		play.Logger.info("create resultDTO...");
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}


	@Transactional(readOnly = false)
	public Result municipalityRegistration() {

		RegistrationDTO dto = extractDTOFromRequest(RegistrationDTO.class);

		// control organization name
		Organization organization = organizationService.findByName(dto.getOrganizationName());
		if(organization!=null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_MUNICIPALITY_NAME_ALREADY_USED));
		}

		//create organization
		organization = new Organization(dto.getOrganizationName(), InterfaceTypeCode.MUNICIPALITY, dto.getOrganizationStatisticsAllowed());
		organizationService.saveOrUpdate(organization);

		//create administrator
		Account account = null;
		try {
			account = createAdministrator(dto.getPerson(), dto.getPassword(),organization);
		} catch (MyrmexException e) {
			return notFound(new ExceptionsDTO(e.getToClientMessage()));
		}

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		// email submission
		handleEmailSubmission(account,InterfaceTypeCode.MUNICIPALITY);

		//create ConnectionFormDTO
		LoginResultDTO resultDto = conversionService.convert(account, LoginResultDTO.class);

		return ok(resultDto);
	}

	private void handleEmailSubmission (Account account,InterfaceTypeCode interfaceType) {

		// email purpose
		// retrieve traductions
		HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
		String subject = traductions.get("REGISTER_EMAIL_SUBJECT").getLabel(account.getPerson().getDefaultLanguage());

		Logger.info("handleEmailSubmission->interfaceTypeCode:" + interfaceType);
		String awacInterfaceTypeFragment;
		if (interfaceType.getKey().equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
			awacInterfaceTypeFragment=Configuration.root().getString("awac.enterprisefragment");
		} else if (interfaceType.getKey().equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
				awacInterfaceTypeFragment = Configuration.root().getString("awac.municipalityfragment");
			} else {
					awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");
		}

		// prepare email
		Map values = new HashMap<String, Object>();
		final String awacHostname = Configuration.root().getString("awac.hostname");
		String awacLoginUrlFragment = Configuration.root().getString("awac.loginfragment");

		String link = awacHostname+awacInterfaceTypeFragment+awacLoginUrlFragment;
		//String link = awacHostname + awacLoginUrlFragment;

		values.put("subject", subject);
		values.put("link", link);
		values.put("hostname", awacHostname);
		values.put("identifier", account.getIdentifier());


		String velocityContent = velocityGeneratorService.generate("registerInvitation.vm", values);

		// send confirmation email
		EmailMessage email = new EmailMessage(account.getPerson().getEmail(), subject, velocityContent);
		emailService.send(email);
	}



	private Account createAdministrator(PersonDTO personDTO, String password, Organization organization) throws MyrmexException{

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

		//create account
		Account administrator = new Account(organization,person,personDTO.getIdentifier(), password);
		administrator.setIsAdmin(true);

		//save account
		accountService.saveOrUpdate(administrator);

		return administrator;
	}



}
