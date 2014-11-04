package eu.factorx.awac.converter;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import org.springframework.stereotype.Component;

/**
 * Created by florian on 9/10/14.
 */
@Component
class AwacCalculatorInstanceToVerificationRequestDTOConverter implements Converter<AwacCalculatorInstance, VerificationRequestDTO> {

    @Autowired
    private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;
    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;
    @Autowired
    private SiteToSiteDTOConverter siteToSiteDTOConverter;
    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;
    @Autowired
    private ProductToProductDTOConverter productToProductDTOConverter;


    @Override
    public VerificationRequestDTO convert(AwacCalculatorInstance awacCalculatorInstance) {

        VerificationRequestDTO dto = new VerificationRequestDTO();

        dto.setId(awacCalculatorInstance.getId());

        //organization customer
        dto.setOrganizationCustomer(organizationToOrganizationDTOConverter.convert(awacCalculatorInstance.getScope().getOrganization()));

        //organization vrifier
        if(awacCalculatorInstance.getVerificationRequest().getOrganizationVerifier()!=null) {
            dto.setOrganizationVerifier(organizationToOrganizationDTOConverter.convert(awacCalculatorInstance.getVerificationRequest().getOrganizationVerifier()));
        }

        //scope
        if (awacCalculatorInstance.getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
            dto.setScope(siteToSiteDTOConverter.convert(((Site) awacCalculatorInstance.getScope())));
        }
        else if (awacCalculatorInstance.getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.ORG)) {
            dto.setScope(organizationToOrganizationDTOConverter.convert(((Organization)awacCalculatorInstance.getScope())));
        }
        else if (awacCalculatorInstance.getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
            dto.setScope(productToProductDTOConverter.convert(((Product) awacCalculatorInstance.getScope())));
        }

        //period
        dto.setPeriod(periodToPeriodDTOConverter.convert(awacCalculatorInstance.getPeriod()));

        //contact
        dto.setContact(accountToPersonDTOConverter.convert(awacCalculatorInstance.getVerificationRequest().getContact()));

        //phoneNumber
        dto.setContactPhoneNumber(awacCalculatorInstance.getVerificationRequest().getEmailVerificationContent().getPhoneNumber());

        //comment
        dto.setComment(awacCalculatorInstance.getVerificationRequest().getEmailVerificationContent().getContent());

        //status
        dto.setStatus(awacCalculatorInstance.getVerificationRequest().getVerificationRequestStatus().getKey());

        //verification rejected comment
        dto.setVerificationRejectedComment(awacCalculatorInstance.getVerificationRequest().getVerificationRejectedComment());

        //VerificationSuccessFileId
        if(awacCalculatorInstance.getVerificationRequest().getVerificationResultDocument()!=null) {
            dto.setVerificationSuccessFileId(awacCalculatorInstance.getVerificationRequest().getVerificationResultDocument().getId());
        }
        //verifier
        for (Account verifier : awacCalculatorInstance.getVerificationRequest().getVerifierList()) {
            dto.addVerifier(accountToPersonDTOConverter.convert(verifier));
        }

        return dto;

    }

}
