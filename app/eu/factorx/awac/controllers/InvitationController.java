package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.awac.post.RegisterInvitationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Configuration;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.Map;

@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class InvitationController extends AbstractController {


    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private VelocityGeneratorService velocityGeneratorService;

    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CodeLabelService codeLabelService;


    @Transactional
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result launchInvitation() {

        // get InvitationDTO from request
        EmailInvitationDTO dto = extractDTOFromRequest(EmailInvitationDTO.class);
        //Logger.info("Host Organization Invitation Name: " + dto.getOrganizationName());
        Logger.info("Guest Email Invitation : " + dto.getInvitationEmail());


        // get organization name through securedController
        Organization org = organizationService.findByName(securedController.getCurrentUser().getOrganization().getName());

        Logger.info("lauchInvitation->interfaceTypeCode:" + org.getInterfaceCode());
        String awacInterfaceTypeFragment = null;
        if (org.getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.enterprisefragment");
        } else if (org.getInterfaceCode().equals(InterfaceTypeCode.MUNICIPALITY)) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.municipalityfragment");
        } else if (org.getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");
        }

        // compute key
        String key = KeyGenerator.generateRandomKey(dto.getInvitationEmail().length());
        Logger.info("Email Invitation generated key : " + key);

        // store key and user
        Invitation invitation = new Invitation(dto.getInvitationEmail(), key, securedController.getCurrentUser().getOrganization());
        invitationService.saveOrUpdate(invitation);

        String awacHostname = Configuration.root().getString("awac.hostname");
        String awacRegistrationUrl = Configuration.root().getString("awac.registrationfragment");

        String link = awacHostname + awacInterfaceTypeFragment + awacRegistrationUrl + key;

        // retrieve traductions
        HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
        String subject = traductions.get("INVITATION_EMAIL_SUBJECT").getLabel(securedController.getCurrentUser().getPerson().getDefaultLanguage());

        Map values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("link", link);
        values.put("hostname", awacHostname);
        values.put("organization", securedController.getCurrentUser().getOrganization().getName());

        String velocityContent = velocityGeneratorService.generate(velocityGeneratorService.getTemplateNameByMethodName(), values);

        // send email for invitation
        EmailMessage email = new EmailMessage(dto.getInvitationEmail(), subject, velocityContent);
        emailService.send(email);

        //create InvitationResultDTO

        return ok(new ResultsDTO());
    }

    @Transactional(readOnly = false)
    public Result registerInvitation() {

        //Logger.info("request body:" + request().body().asJson());
        // get InvitationDTO from request
        RegisterInvitationDTO dto = extractDTOFromRequest(RegisterInvitationDTO.class);
        Logger.info("Registering Invitation : " + dto.getPerson().getEmail());
        //Logger.info("dump: " + dto.toString());

        // check if invitation exist
        Invitation invitation = invitationService.findByGenkey(dto.getKey());
        if (invitation == null) {
            throw new MyrmexRuntimeException(BusinessErrorType.INVITATION_NOT_VALID);
        }

        // create person
        Person person = new Person(dto.getPerson().getLastName(), dto.getPerson().getFirstName(), dto.getPerson().getEmail());
        personService.saveOrUpdate(person);

        // create account
        Account account = new Account(invitation.getOrganization(), person, dto.getPerson().getIdentifier(), dto.getPassword());
        account = accountService.saveOrUpdate(account);

        // ONLY FOR municipality : assign the new user of the site
        //if (securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.MUNICIPALITY)) {
//		if (invitation.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.MUNICIPALITY)) {
//            for (Site site : invitation.getOrganization().getSites()) {
//                AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site, account);
//                accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);
//            }
//        }

        // delete invitation
        invitationService.remove(invitation);

        // retrieve traductions
        HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
        String subject = traductions.get("REGISTER_EMAIL_SUBJECT").getLabel(account.getPerson().getDefaultLanguage());

        Logger.info("registerInvitation->interfaceTypeCode:" + invitation.getOrganization().getInterfaceCode());
        String awacInterfaceTypeFragment = null;
        if (invitation.getOrganization().getInterfaceCode().getKey().equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.enterprisefragment");
        } else if (invitation.getOrganization().getInterfaceCode().getKey().equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.municipalityfragment");

        } else if (invitation.getOrganization().getInterfaceCode().getKey().equals(InterfaceTypeCode.VERIFICATION.getKey())) {
            awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");
        }

        // prepare email
        Map values = new HashMap<String, Object>();
        final String awacHostname = Configuration.root().getString("awac.hostname");
        String awacLoginUrlFragment = Configuration.root().getString("awac.loginfragment");

        String link = awacHostname + awacInterfaceTypeFragment + awacLoginUrlFragment;

        values.put("subject", subject);
        values.put("link", link);
        values.put("hostname", awacHostname);
        values.put("identifier", account.getIdentifier());


        String velocityContent = velocityGeneratorService.generate(velocityGeneratorService.getTemplateNameByMethodName(), values);

        // send confirmation email
        EmailMessage email = new EmailMessage(dto.getPerson().getEmail(), subject, velocityContent);
        emailService.send(email);


        //create InvitationResultDTO
        return ok(new ResultsDTO());
    }


}
