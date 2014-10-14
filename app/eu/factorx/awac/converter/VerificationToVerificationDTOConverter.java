package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.get.VerificationDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.data.answer.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by florian on 10/10/14.
 */
@Component
public class VerificationToVerificationDTOConverter implements Converter<Verification , VerificationDTO> {

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    public VerificationDTO convert(Verification verification) {
        VerificationDTO dto = new VerificationDTO();

        dto.setComment(verification.getComment());
        dto.setStatus(verification.getVerificationStatus().getKey());
        dto.setVerifier(accountToPersonDTOConverter.convert(verification.getVerifier()));

        return dto;
    }
}