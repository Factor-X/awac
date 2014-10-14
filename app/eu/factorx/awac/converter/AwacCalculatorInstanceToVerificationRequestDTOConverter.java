package eu.factorx.awac.converter;

import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by florian on 9/10/14.
 */
class AwacCalculatorInstanceToVerificationRequestDTOConverter implements Converter<AwacCalculatorInstance, VerificationRequestDTO> {

    @Autowired
    private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;
    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;
    @Autowired
    private SiteToSiteDTOConverter siteToSiteDTOConverter;
    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    @Override
    public VerificationRequestDTO convert(AwacCalculatorInstance awacCalculatorInstance) {

        //control
        if (awacCalculatorInstance.getVerificationRequest().getOrganizationVerifier() == null) {
            throw new MyrmexRuntimeException("cannot convert because there is not organization verifier defined");
        }

        VerificationRequestDTO dto = new VerificationRequestDTO();

        dto.setId(awacCalculatorInstance.getId());

        //organization customer
        dto.setOrganizationCustomer(organizationToOrganizationDTOConverter.convert(awacCalculatorInstance.getScope().getOrganization()));

        //scope
        if (awacCalculatorInstance.getScope().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {

            dto.setScope(siteToSiteDTOConverter.convert(((Site) awacCalculatorInstance.getScope())));
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

        //verifier
        for (Account verifier : awacCalculatorInstance.getVerificationRequest().getVerifierList()) {
            dto.addVerifier(accountToPersonDTOConverter.convert(verifier));
        }

        return dto;

    }

}
