package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.AwacCalculatorLanguagesDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDataDTO;
import eu.factorx.awac.dto.awac.post.SendEmailDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.awac.shared.MapDTO;
import eu.factorx.awac.dto.awac.shared.ResultMessageDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.AwacCalculatorService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Controller
public class OrganizationDataController extends AbstractController {


    @Autowired
    private OrganizationService   organizationService;
    @Autowired
    private ConversionService     conversionService;
    @Autowired
    private EmailService          emailService;
    @Autowired
    private AwacCalculatorService awacCalculatorService;


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result sendEmail() {

        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }

        SendEmailDTO dto = extractDTOFromRequest(SendEmailDTO.class);

        //load interface code
        final InterfaceTypeCode interfaceTypeCode;

        if (dto.getCalculatorKey().equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.ENTERPRISE;
        } else if (dto.getCalculatorKey().equals(InterfaceTypeCode.EVENT.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.EVENT;
        } else if (dto.getCalculatorKey().equals(InterfaceTypeCode.HOUSEHOLD.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.HOUSEHOLD;
        } else if (dto.getCalculatorKey().equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.MUNICIPALITY;
        } else if (dto.getCalculatorKey().equals(InterfaceTypeCode.LITTLEEMITTER.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.LITTLEEMITTER;
        } else {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }


        //send one email by organization
        List<Organization> organizations = organizationService.findByInterfaceTypeCode(interfaceTypeCode);
        for (Organization organization : organizations) {
            if (dto.isOnlyOrganizationSharedData() == false ||
                (organization.getStatisticsAllowed() != null && organization.getStatisticsAllowed() == true)) {

                //load the email list
                Set<String> emailList = new HashSet<>();

                for (Account account : organization.getAccounts()) {
                    if (account.getActive()) {
                        emailList.add(account.getPerson().getEmail());
                    }
                }

                //send email
                if (emailList.size() > 0) {
                    EmailMessage email = new EmailMessage(emailList, dto.getEmailTitle(), dto.getEmailContent());
                    emailService.send(email);
                }
            }
        }

        return ok(new ResultMessageDTO());
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getOrganizationData() {

        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }

        List<Organization> organizationList = organizationService.findAll();

        ListDTO<OrganizationDataDTO> result = new ListDTO<>();

        for (Organization organization : organizationList) {
            if (!organization.getInterfaceCode().equals(InterfaceTypeCode.ADMIN) &&
                !organization.getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
                result.add(conversionService.convert(organization, OrganizationDataDTO.class));
            }
        }

        return ok(result);
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getLanguagesData() {

        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }

        MapDTO<AwacCalculatorLanguagesDTO> languages = new MapDTO<>();
        for (AwacCalculator awacCalculator : awacCalculatorService.findAll()) {
            AwacCalculatorLanguagesDTO dto = new AwacCalculatorLanguagesDTO(awacCalculator.isFrEnabled(), awacCalculator.isNlEnabled() ,awacCalculator.isEnEnabled());
            languages.getMap().put(awacCalculator.getInterfaceTypeCode().getKey(), dto);
        }

        return ok(languages);
    }


    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result toggleLanguage(String calculator, String lang) {

        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
        }

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode(calculator));

        if (lang.toLowerCase().equals("fr")) {
            awacCalculator.setFrEnabled(!awacCalculator.isFrEnabled());
        }
        if (lang.toLowerCase().equals("nl")) {
            awacCalculator.setNlEnabled(!awacCalculator.isNlEnabled());
        }
        if (lang.toLowerCase().equals("en")) {
            awacCalculator.setEnEnabled(!awacCalculator.isEnEnabled());
        }

        awacCalculatorService.saveOrUpdate(awacCalculator);

        return ok();
    }

}
