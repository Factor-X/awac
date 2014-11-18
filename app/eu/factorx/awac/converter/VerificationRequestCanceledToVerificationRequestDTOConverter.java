package eu.factorx.awac.converter;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;
import org.springframework.stereotype.Component;

/**
 * Created by florian on 9/10/14.
 */
@Component
class VerificationRequestCanceledToVerificationRequestDTOConverter implements Converter<VerificationRequestCanceled, VerificationRequestDTO> {

    @Autowired
    private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;
    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;
    @Autowired
    private SiteToSiteDTOConverter siteToSiteDTOConverter;
    @Autowired
    private ProductToProductDTOConverter productToProductDTOConverter;
    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    @Override
    public VerificationRequestDTO convert(VerificationRequestCanceled verificationRequestCanceled) {

        VerificationRequestDTO dto = new VerificationRequestDTO();

        dto.setId(verificationRequestCanceled.getId());

        //organization customer
        dto.setOrganizationCustomer(organizationToOrganizationDTOConverter.convert(verificationRequestCanceled.getAwacCalculatorInstance().getScope().getOrganization()));

        //organization vrifier
        dto.setOrganizationVerifier(organizationToOrganizationDTOConverter.convert(verificationRequestCanceled.getOrganizationVerifier()));


        //contact
        dto.setContact(accountToPersonDTOConverter.convert(verificationRequestCanceled.getContact()));

        //scope
        if (verificationRequestCanceled.getAwacCalculatorInstance().getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
            dto.setScope(siteToSiteDTOConverter.convert(((Site) verificationRequestCanceled.getAwacCalculatorInstance().getScope())));
        }
        else if (verificationRequestCanceled.getAwacCalculatorInstance().getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.ORG)) {
            dto.setScope(organizationToOrganizationDTOConverter.convert(((Organization) verificationRequestCanceled.getAwacCalculatorInstance().getScope())));
        }
        else if (verificationRequestCanceled.getAwacCalculatorInstance().getScope().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
            dto.setScope(productToProductDTOConverter.convert(((Product) verificationRequestCanceled.getAwacCalculatorInstance().getScope())));
        }

        //period
        dto.setPeriod(periodToPeriodDTOConverter.convert(verificationRequestCanceled.getAwacCalculatorInstance().getPeriod()));

        //phoneNumber
        dto.setContactPhoneNumber(verificationRequestCanceled.getEmailVerificationContent().getPhoneNumber());

        //comment
        dto.setComment(verificationRequestCanceled.getEmailVerificationContent().getContent());

        return dto;

    }

}
